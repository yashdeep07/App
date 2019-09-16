import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Iterator;
import java.util.TreeMap;

class BookDatabaseManager{
    private String filePath;

    private HashMap<String ,Book> isbnToBookMap;

    private TreeMap<String , List<String>> titleIndexes;
    private TreeMap<String , List<String>> authorIndexes;
    private TreeMap<Integer , List<String>> yearIndexes;

    BookDatabaseManager( String filePath ) throws IOException {
        this.filePath = filePath;
        isbnToBookMap = new HashMap<String ,Book>();
        titleIndexes = new TreeMap<String , List<String>>();
        authorIndexes = new TreeMap<String , List<String>>();
        yearIndexes = new TreeMap<Integer , List<String>>();


        long startTime = System.currentTimeMillis();
        initializeDataset();
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.print(" Milli Seconds Taken for Initializing the Maps\n");

    }

    /**
     Converts String CSV for a book to Book object
     */
    private Book csvRowToBook(String csvRow){
        String data[] = csvRow.split(",");
        return new Book(data[0], data[1] , data[2], data[3] , data[4] , Integer.parseInt(data[5]) , new Double(data[6]) , BindingType.valueOf(data[7].toUpperCase()) );
    }


    private void iterateForward(ListIterator<String> itr){
        int count = 0;
        while(itr.hasNext()){
            count += 1;
            isbnToBookMap.get(itr.next()).listView();
            if(count == 10){
                break;
            }
        }

    }

    private void iterateBackward(ListIterator<String> itr){
        int count = 20;
        while(itr.hasPrevious()){
            count -= 1;
            itr.previous();
            if(count == 0){
                break;
            }
        }
        iterateForward(itr);
    }


    /**
     * initializes isbnToBookMap and titleToISBNListMap
     */
     private void initializeDataset() throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            Book book = csvRowToBook(row);
            this.addBookToDatabase(book);
        }
        csvReader.close();
    }

    /**
     Adds Book to isbnToBookMap and titleToIsbnListMap
     */
    public void addBookToDatabase(Book book){
        isbnToBookMap.put(book.getIsbn(), book);

        if(authorIndexes.containsKey(book.getAuthor())){
            authorIndexes.get(book.getAuthor()).add(book.getIsbn());
        }
        else{
            authorIndexes.put(book.getAuthor() , new ArrayList<String>());
            authorIndexes.get(book.getAuthor()).add(book.getIsbn());
        }



        if(titleIndexes.containsKey(book.getTitle())){
            titleIndexes.get(book.getTitle()).add(book.getIsbn());
        }
        else{
            titleIndexes.put(book.getTitle() , new ArrayList<String>());
            titleIndexes.get(book.getTitle()).add(book.getIsbn());
        }

        if(yearIndexes.containsKey(book.getPublishedYear())){
            yearIndexes.get(book.getPublishedYear()).add(book.getIsbn());
        }
        else{
            yearIndexes.put(book.getPublishedYear() , new ArrayList<String>());
            yearIndexes.get(book.getPublishedYear()).add(book.getIsbn());
        }
    }


    // Shows List of Books on the basis of filter ( field and order )
    public void viewBooks(SortFilter filter ){
        SortField field = filter.getField();
        ListIterator<String> itr;
        List<String> tempIsbnList;


        switch(field){
            case TITLE:
                if(filter.getOrder() == SortOrder.DESC){
                    long startTime = System.currentTimeMillis();
                    tempIsbnList = getISBNListFromTitleMap(titleIndexes.descendingKeySet());
                    System.out.print(System.currentTimeMillis() - startTime);
                    itr = tempIsbnList.listIterator();
                }
                else{
                    long startTime = System.currentTimeMillis();
                    tempIsbnList = getISBNListFromTitleMap((NavigableSet<String>) titleIndexes.keySet());
                    System.out.print(System.currentTimeMillis() - startTime);
                    itr =  tempIsbnList.listIterator();
                }
                break;

            case AUTHOR:
                if(filter.getOrder() == SortOrder.DESC){
                    long startTime = System.currentTimeMillis();
                    tempIsbnList = getISBNListFromAuthorMap(authorIndexes.descendingKeySet());
                    System.out.print(System.currentTimeMillis() - startTime);
                    itr = tempIsbnList.listIterator();
                }
                else{
                    long startTime = System.currentTimeMillis();
                    tempIsbnList = getISBNListFromAuthorMap((NavigableSet<String>) authorIndexes.keySet());
                    System.out.print(System.currentTimeMillis() - startTime);
                    itr =  tempIsbnList.listIterator();
                }

                break;

            case PUBLISHEDYEAR:
                if(filter.getOrder() == SortOrder.DESC){
                    long startTime = System.currentTimeMillis();
                    tempIsbnList = getISBNListFromYearMap((NavigableSet<Integer>) yearIndexes.keySet());
                    System.out.print(System.currentTimeMillis() - startTime);
                    itr =  tempIsbnList.listIterator();
                }
                else{
                    long startTime = System.currentTimeMillis();
                    tempIsbnList = getISBNListFromYearMap(yearIndexes.descendingKeySet());
                    System.out.print(System.currentTimeMillis() - startTime);
                    itr =  tempIsbnList.listIterator();
                }
                break;

            default:
                System.out.println("Give Author or Title or Published Year as a field!");
                return;
        }

        System.out.print(" Milli Seconds Taken to get Sorted Data\n");

        Scanner in= new Scanner(System.in);
        String input ;

        iterateForward(itr);
        while(true){
            System.out.println("Type '1' for Next Page \nType '2' for Previous Page\nType '3' to see details of a Book\nType'q' to Stop/Quit");
            input = in.nextLine();
            if(input.equals("1")){
                iterateForward(itr);
            }
            else if(input.equals("2")){
                iterateBackward(itr);
            }
            else if(input.equals("3")){
                System.out.println("Type Book ISBN number to see Details.");
                input = in.nextLine();
                this.getBook(input).detailsView();
                System.out.println();
            }
            else{
                break;
            }
        }

    }

    // Searches book in the isbnToBookMap and titleToIsbnListMap
    public void searchBookInDatabase(String bookTitle){
        //long startTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        if(titleIndexes.containsKey(bookTitle)){
            System.out.print(System.currentTimeMillis() - startTime);
            System.out.print(" Milli Seconds Taken to Search\n");
            List<String> isbnList= titleIndexes.get(bookTitle);
            for(String isbn: isbnList){
                isbnToBookMap.get(isbn).detailsView();
            }
        }
        else{
            System.out.println(System.currentTimeMillis() - startTime);
            System.out.print(" Milli Seconds Taken to Search\n");
            System.out.println("Book Not Available!");
        }
    }

    // Checks if book is present in database then orders the book
    public void orderBook(String bookTitle){
        if(titleIndexes.containsKey(bookTitle)){
            /*List<String> isbnList= titleToISBNListMap.get(bookTitle);
            Set<String> isbnSet = new HashSet<String>();
            if(isbnList.size() > 1 ) {
                for (String isbn : isbnList) {
                    isbnSet.add(isbn);
                    isbnToBookMap.get(isbn).showBookInfo();
                }
                System.out.println("Type ISBN of the Book To order");
            }
            Scanner in = new Scanner(System.in);
            String isbnInput = in.nextLine();
            if(!isbnSet.contains(isbnInput)){
                System.out.println("Type ISBN number of one of the above books!");
            }*/
            System.out.println("Your order has been Placed!!");

        }
        else{
            System.out.println("Book Not Available!");
        }
    }


    public Book getBook(String isbn){
        return this.isbnToBookMap.get(isbn);
    }

    // Returns a Comparator Object To Sort the Database on 'Title' Field
    private Comparator<String> getTitleComparator(){

        return new Comparator<String>() {
            public int compare(String isbn1, String isbn2) {
                String title1 = isbnToBookMap.get(isbn1).getTitle();
                String title2 = isbnToBookMap.get(isbn2).getTitle();
                int compareValue = title1.compareTo(title2);
                if(compareValue >= 0 )
                    return 1;
                else
                    return -1;
            }
        };

    }

    // Returns a Comparator Object To Sort the Database on 'Author' Field
    private Comparator<String> getAuthorComparator(){

        return new Comparator<String>() {
            public int compare(String isbn1, String isbn2) {
                String author1 = isbnToBookMap.get(isbn1).getAuthor();
                String author2 = isbnToBookMap.get(isbn2).getAuthor();
                int compareValue = author1.compareTo(author2);
                if(compareValue >= 0 )
                    return 1;
                else
                    return -1;
            }
        };

    }

    // Returns a Comparator Object To Sort the Database on 'Published Year' Field
    private Comparator<String> getPublishedYearComparator(){

        return new Comparator<String>() {
            public int compare(String isbn1, String isbn2) {
                int pubYear1 = isbnToBookMap.get(isbn1).getPublishedYear();
                int pubYear2 = isbnToBookMap.get(isbn2).getPublishedYear();
                int compareValue = pubYear1 - pubYear2 ;
                if(compareValue >= 0)
                    return 1;
                else
                    return -1;
            }
        };

    }


    private List<String>getISBNListFromTitleMap(NavigableSet<String> keySet){
        List<String> isbnList= new ArrayList<String>();
        Iterator<String> itr = keySet.iterator();
        while(itr.hasNext())
        {
            List<String> list = titleIndexes.get(itr.next());
            for (String listElement : list){
                isbnList.add(listElement);
            }
        }

        return isbnList;
    }
    private List<String>getISBNListFromAuthorMap(NavigableSet<String> keySet){
        List<String> isbnList= new ArrayList<String>();
        Iterator<String> itr = keySet.iterator();
        while(itr.hasNext())
        {
            List<String> list = authorIndexes.get(itr.next());
            for (String listElement : list){
                isbnList.add(listElement);
            }
        }

        return isbnList;
    }

    private List<String>getISBNListFromYearMap(NavigableSet<Integer> keySet){
        List<String> isbnList= new ArrayList<String>();
        Iterator<Integer> itr = keySet.iterator();

        while(itr.hasNext())
        {
            List<String> list = yearIndexes.get(itr.next());
            for (String listElement : list){
                isbnList.add(listElement);
            }
        }

        return isbnList;
    }


}