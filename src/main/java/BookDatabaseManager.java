import jdk.nashorn.internal.ir.SwitchNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class BookDatabaseManager{
    // private String pathToDatabaseDirectory = "/home/honey/IdeaProjects/App/";
    private String filePath ="/home/yashdeep/Documents/App/Write.csv";
    private HashMap<String ,Book> isbnToBookMap;
    private HashMap<String , List<String>> titleToISBNListMap;

    BookDatabaseManager( String filePath ) throws IOException {
        this.filePath = filePath;
        isbnToBookMap = new HashMap<String ,Book>();
        titleToISBNListMap = new HashMap<String , List<String>>();
        long startTime = System.currentTimeMillis();
        initializeDataset();
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.print(" Milli Seconds Taken for Initializing the Maps\n");

    }

    // Converts String CSV for a book to Book object
    private Book csvRowToBook(String csvRow){
        String data[] = csvRow.split(",");
        return new Book(data[0], data[1] , data[2], data[3] , data[4] , Integer.parseInt(data[5]) , new Double(data[6]) , BindingType.valueOf(data[7].toUpperCase()) );
    }


    // initializes isbnToBookMap and titleToISBNListMap
    private void initializeDataset() throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            Book book = csvRowToBook(row);
            this.addBookToDatabase(book);
            /*isbnToBookMap.put(book.getIsbn(), book);
            if(titleToISBNListMap.containsKey(book.getTitle())){
                titleToISBNListMap.get(book.getTitle()).add(book.getIsbn());
            }
            else{
                titleToISBNListMap.put(book.getTitle() , new ArrayList<Double>());
                titleToISBNListMap.get(book.getTitle()).add(book.getIsbn());
            }*/
        }
        csvReader.close();
    }

    // Adds Book to isbnToBookMap and titleToIsbnListMap
    public void addBookToDatabase(Book book){
        isbnToBookMap.put(book.getIsbn(), book);
        if(titleToISBNListMap.containsKey(book.getTitle())){
            titleToISBNListMap.get(book.getTitle()).add(book.getIsbn());
        }
        else{
            titleToISBNListMap.put(book.getTitle() , new ArrayList<String>());
            titleToISBNListMap.get(book.getTitle()).add(book.getIsbn());
        }
    }

    // Shows List of Books on the basis of filter ( field and order )
    public void viewBooks(SortFilter filter ){
        SortField field = filter.getField();
        TreeMap<String, Book> isbnToBookTreeMap ;
        //TreeSet<Double> isbnToBookTreeSet ;
        switch(field){
            case TITLE:
                Comparator titleComparator = getTitleComparator();
                if(filter.getOrder() == SortOrder.DESC){
                    isbnToBookTreeMap = new TreeMap<String, Book>(Collections.reverseOrder(titleComparator));
                }
                else{
                    isbnToBookTreeMap = new TreeMap<String, Book>(titleComparator);
                }
                break;

            case AUTHOR:
                Comparator authorComparator = getAuthorComparator();
                if(filter.getOrder() == SortOrder.DESC){
                    isbnToBookTreeMap = new TreeMap<String, Book>(Collections.reverseOrder(authorComparator));
                }
                else{
                    isbnToBookTreeMap = new TreeMap<String, Book>(authorComparator);
                }
                /*if(filter.getOrder() == SortOrder.DESC){
                    isbnToBookTreeSet = new TreeSet<Double>(Collections.reverseOrder(authorComparator));
                }
                else{
                    isbnToBookTreeSet = new TreeSet<Double>(authorComparator);
                }*/
                break;

            case PUBLISHEDYEAR:
                Comparator yearComparator = getPublishedYearComparator();
                if(filter.getOrder() == SortOrder.DESC){
                    isbnToBookTreeMap = new TreeMap<String, Book>(Collections.reverseOrder(yearComparator));
                }
                else{
                    isbnToBookTreeMap = new TreeMap<String, Book>(yearComparator);
                }
                break;

            default:
                System.out.println("Give Author or Title or Published Year as a field!");
                return;
        }

        long startTime = System.currentTimeMillis();

        isbnToBookTreeMap.putAll(isbnToBookMap);

        System.out.print(System.currentTimeMillis() - startTime);
        System.out.print(" Milli Seconds Taken to get Sorted Data\n");

        Scanner in= new Scanner(System.in);
        String input ;
        Iterator<Map.Entry<String, Book>> itr = isbnToBookTreeMap.entrySet().iterator();
        int counter = 0;
        int itemsPerView =10;
        int limit = itemsPerView;
        while(itr.hasNext())
        {
            Map.Entry<String, Book> entry = itr.next();
            entry.getValue().showBookInfo();
            counter += 1;
            if(counter == limit){
                System.out.println("Type '1' to continue\nType '2' to quit");
                input = in.nextLine();
                if(input.equals("2")){
                    break;
                }
                else{
                    limit = limit + itemsPerView;
                }
            }
        }

        /*for(Book book : isbnToBookTreeMap.values()){
            for(int i = 0;i < 10; i++){
                book.showBookInfo();
            }
            System.out.println("Type '1' to continue\nType '2' to quit");
            String input = in.nextLine();
            if(input.equals("2")){
                continue;
            }
            else{
                break;
            }

        }*/
    }

    // Searches book in the isbnToBookMap and titleToIsbnListMap
    public void searchBookInDatabase(String bookTitle){
        //long startTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        if(titleToISBNListMap.containsKey(bookTitle)){
            System.out.print(System.currentTimeMillis() - startTime);
            System.out.print(" Milli Seconds Taken to Search\n");
            List<String> isbnList= titleToISBNListMap.get(bookTitle);
            for(String isbn: isbnList){
                isbnToBookMap.get(isbn).showBookInfo();
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
        if(titleToISBNListMap.containsKey(bookTitle)){
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

}