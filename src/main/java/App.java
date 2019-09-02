import java.io.IOException;
import java.util.*;


class App {

    public static void main(String args[]) throws IOException {
        BookDatabaseManager manager = new BookDatabaseManager();

        Scanner in = new Scanner(System.in);


        System.out.println("To Add new Book, Type '1'\nTo Search Book in a Database ,Type '2'\nTo Order a Book, Type '3'\nTo View Books, Type '4'");
        System.out.println("Type 'q' to quit");
        String userInput = in.nextLine();
        String bookTitle ;
        while(!userInput.equals("q")){

            switch(userInput){

                case "1":
                    Book book = addBookInterface();
                    manager.addBookToDatabase(book);
                    System.out.println("Book has Been Added To the Database!");
                    break;

                case "2":
                    bookTitle = searchBookInterface();
                    manager.searchBookInDatabase(bookTitle);
                    break;

                case "3":
                    bookTitle = orderBookInterface();
                    manager.orderBook(bookTitle);
                    break;

                case "4":
                    SortFilter filter = viewBooksInterface();
                    manager.viewBooks(filter);
                    break;

                default:
                    System.out.println("Type Valid Option!!");
                    break;
            }

            System.out.println("To Add new Book, Type '1'\nTo Search Book in a Database ,Type '2'\nTo Order a Book, Type '3'\nTo View Books, Type '4'");
            System.out.println("Type 'q' to quit");
            userInput = in.nextLine();
        }

        /*Book b1 = new Book("Red Wolf", "Marlon James",  "123456749", "JKL", "English", 1996, (double) 999 , BindingType.HARDBOUND);
        manager.addBookToDatabase(b1);



        System.out.println("search");
        manager.searchBookInDatabase("Red Wolf");

        manager.searchBookInDatabase("vBUN")                                                                  ;

        SortFilter filter = new SortFilter(SortField.PUBLISHEDYEAR,SortOrder.DESC );
        manager.viewBooks(filter);

        *//*RandomFileGenerator gen = new RandomFileGenerator();
        gen.createCsvFileWithRandomValues();*/

    }

    // Interface for Retrieving New Book Details
    // Returns New Book Object to be added in the database
    public static Book addBookInterface(){

        Scanner in = new Scanner (System.in);

        System.out.println("Enter Book Title");
        String title = in.nextLine();

        System.out.println("Enter Book Author");
        String author = in.nextLine();

        System.out.println("Enter Book ISBN");
        String isbn = in.nextLine();

        System.out.println("Enter Book Publisher");
        String publisher = in.nextLine();

        System.out.println("Enter Book Language");
        String lang = in.nextLine();

        System.out.println("Enter Book Published Year");
        int pubYear = Integer.parseInt(in.nextLine());

        System.out.println("Enter Book Price");
        Double price = Double.parseDouble(in.nextLine());

        System.out.println("Enter Book Binding Type");
        BindingType bindingType = BindingType.valueOf(in.nextLine().toUpperCase());

        return new Book(title ,author,isbn, publisher, lang, pubYear, price, bindingType);
    }


    public static String searchBookInterface(){
        System.out.println("Enter Book Title");
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        return userInput;
    }

    public static String orderBookInterface(){
        System.out.println("Enter Book Title");
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        return userInput;
    }

    public static SortFilter viewBooksInterface(){
        Scanner in = new Scanner(System.in);
        SortField field ;
        SortOrder order;
        System.out.println("Enter '1' to sort on Title\n'2' to sort on Author\n'3' to sort on Published Year");
        String userInput = in.nextLine();

        switch(userInput){
            case "1":
                field = SortField.TITLE;
                break;

            case "2":
                field = SortField.AUTHOR;
                break;

            case "3":
                field = SortField.PUBLISHEDYEAR;
                break;

            default:
                field = SortField.TITLE;
                break;
        }

        System.out.println("Enter '1' to sort in Ascending\n'2' to sort in Descending");
        userInput = in.nextLine();
        switch(userInput){
            case "1":
                order = SortOrder.ASC;
                break;

            case "2":
                order = SortOrder.DESC;
                break;

            default:
                order = SortOrder.ASC;
                break;
        }

        return new SortFilter(field , order);

    }
}
