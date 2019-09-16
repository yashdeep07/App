import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.TreeMap;

class BookDatabaseManager{
    private String filePath;

    private HashMap<String ,Book> isbnToBookMap;

    private TreeMap<String , List<Book>> titleToBookListMap;
    private TreeMap<String , List<Book>> authorToBookListMap;
    private TreeMap<Integer , List<Book>> yearToBookListMap;

    BookDatabaseManager( String filePath ) throws IOException {
        this.filePath = filePath;
        isbnToBookMap = new HashMap<>();
        titleToBookListMap = new TreeMap<>();
        authorToBookListMap = new TreeMap<>();
        yearToBookListMap = new TreeMap<>();


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


    private void iterateForward(ListIterator<Book> itr){
        int count = 0;
        while(itr.hasNext()){
            count += 1;
            itr.next().listView();
            if(count == 10){
                break;
            }
        }

    }

    private void iterateBackward(ListIterator<Book> itr){
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

        if(authorToBookListMap.containsKey(book.getAuthor())){
            authorToBookListMap.get(book.getAuthor()).add(book);
        }
        else{
            authorToBookListMap.put(book.getAuthor() , new ArrayList<Book>());
            authorToBookListMap.get(book.getAuthor()).add(book);
        }



        if(titleToBookListMap.containsKey(book.getTitle())){
            titleToBookListMap.get(book.getTitle()).add(book);
        }
        else{
            titleToBookListMap.put(book.getTitle() , new ArrayList<Book>());
            titleToBookListMap.get(book.getTitle()).add(book);
        }

        if(yearToBookListMap.containsKey(book.getPublishedYear())){
            yearToBookListMap.get(book.getPublishedYear()).add(book);
        }
        else{
            yearToBookListMap.put(book.getPublishedYear() , new ArrayList<Book>());
            yearToBookListMap.get(book.getPublishedYear()).add(book);
        }
    }



    public void viewBooks(SortFilter filter ){
        SortField field = filter.getField();
        ListIterator<Book> itr;
        List<Book> bookList;



        switch(field){
            case TITLE:
                if(filter.getOrder() == SortOrder.DESC){
                    long startTime = System.currentTimeMillis();
                    bookList = getBookListFromTitleTreeMap(filter.getOrder());
                    System.out.print(System.currentTimeMillis() - startTime);
                }
                else{
                    long startTime = System.currentTimeMillis();
                    bookList = getBookListFromTitleTreeMap(filter.getOrder());
                    System.out.print(System.currentTimeMillis() - startTime);
                }
                break;

            case AUTHOR:
                if(filter.getOrder() == SortOrder.DESC){
                    long startTime = System.currentTimeMillis();
                    bookList = getBookListFromAuthorTreeMap(filter.getOrder());
                    System.out.print(System.currentTimeMillis() - startTime);
                }
                else{
                    long startTime = System.currentTimeMillis();
                    bookList = getBookListFromAuthorTreeMap(filter.getOrder());
                    System.out.print(System.currentTimeMillis() - startTime);
                }

                break;

            case PUBLISHEDYEAR:
                if(filter.getOrder() == SortOrder.DESC){
                    long startTime = System.currentTimeMillis();
                    bookList = getBookListFromYearTreeMap(filter.getOrder());
                    System.out.print(System.currentTimeMillis() - startTime);
                }
                else{
                    long startTime = System.currentTimeMillis();
                    bookList = getBookListFromYearTreeMap(filter.getOrder());
                    System.out.print(System.currentTimeMillis() - startTime);
                }
                break;

            default:
                System.out.println("Give Author or Title or Published Year as a field!");
                return;
        }

        itr = bookList.listIterator();
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
            else if(input.equals("q")){
                break;
            }
            else{
                System.out.println("Type Valid Option!!");
            }
        }

    }


    public void searchBookInDatabase(String bookTitle){

        long startTime = System.currentTimeMillis();
        if(titleToBookListMap.containsKey(bookTitle)){
            System.out.print(System.currentTimeMillis() - startTime);
            System.out.print(" Milli Seconds Taken to Search\n");
            List<Book> bookList= titleToBookListMap.get(bookTitle);
            for(Book book: bookList){
                book.detailsView();
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
        if(titleToBookListMap.containsKey(bookTitle)){
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
    
    private List<Book>getBookListFromTitleTreeMap(SortOrder order){
        List<Book> bookList= new ArrayList<>();

        if(order == SortOrder.ASC){
            for(String key : titleToBookListMap.keySet())
            {
                bookList.addAll(titleToBookListMap.get(key));
            }
        }
        else{
            for(String key : titleToBookListMap.descendingKeySet())
            {
                bookList.addAll(titleToBookListMap.get(key));
            }
        }
        return bookList;
    }
    private List<Book>getBookListFromAuthorTreeMap(SortOrder order){
        List<Book> bookList= new ArrayList<>();
        if(order == SortOrder.ASC){
            for(String key : authorToBookListMap.keySet())
            {
                bookList.addAll(authorToBookListMap.get(key));
            }
        }
        else{
            for(String key : authorToBookListMap.descendingKeySet())
            {
                bookList.addAll(authorToBookListMap.get(key));
            }
        }


        return bookList;
    }

    private List<Book>getBookListFromYearTreeMap(SortOrder order){
        List<Book> bookList= new ArrayList<>();


        if(order == SortOrder.ASC){
            for(Integer key : yearToBookListMap.keySet())
            {
                bookList.addAll(yearToBookListMap.get(key));
            }
        }
        else{
            for(Integer key : yearToBookListMap.descendingKeySet())
            {
                bookList.addAll(yearToBookListMap.get(key));
            }
        }

        return bookList;
    }

}