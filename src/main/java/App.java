import java.io.IOException;
import java.util.*;


class App {

    public static void main(String args[]) throws IOException {


        Scanner in = new Scanner(System.in);
        String filePath;
        if (args.length == 1)
            filePath = args[0];
        else
            filePath = "/home/yashdeep/Documents/App/Write.csv";

        BookDatabaseManager manager = new BookDatabaseManager(filePath);
        Cart userCart = new Cart();




        System.out.println("To Add new Book, Type '1'\nTo Search Book in a Database ,Type '2'\nTo View Books, Type '3'\nTo View Cart, Type '4'\nTo Add book to the cart Type '5'\nTo Edit Cart Type '6'");
        System.out.println("Type 'q' to quit");
        String userInput = in.nextLine();
        String bookTitle;
        while (!userInput.equals("q")) {

            switch (userInput) {

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
                    SortFilter filter = viewBooksInterface();
                    manager.viewBooks(filter);
                    break;

                case "4":
                    userCart.showCart();
                    break;

                case "5":
                    System.out.println("Type Book ISBN number to add it to cart or Type 'b' to go back to Main Menu.");
                    userInput = in.nextLine();
                    if(!userInput.equals("b")){
                        Book book1 = manager.getBook(userInput);
                        if(book1==null){
                            System.out.println("Book Not Available!!");
                            break;
                        }
                        System.out.println("Type Number of Books you want to order.");
                        userInput = in.nextLine();
                        userCart.addProduct(book1,Integer.parseInt(userInput));
                        System.out.println("Book Added To Your Cart\n");
                    }
                    break;

                case "6":
                    while(true){
                        userCart.showCart();
                        System.out.println("Type '1' to Edit Quantity of Books\nType '2' to Remove Book\nType '3' to go back to Main Menu");
                        userInput = in.nextLine();
                        if(userInput.equals("1")){
                            System.out.println("Type ISBN of the Book to Edit");
                            String isbn = in.nextLine();
                            System.out.println("Type Quantity of the Books you want to order");
                            int quantity = Integer.parseInt(in.nextLine());
                            userCart.editQuantity(manager.getBook(isbn) , quantity);

                        }else if(userInput.equals("2")){
                            System.out.println("Type ISBN of the Book to Remove\n");
                            String isbn = in.nextLine();
                            userCart.removeItem(manager.getBook(isbn));
                        }
                        else break;
                    }
                    break;
                default:
                    System.out.println("Type Valid Option!!");
                    break;
            }

            System.out.println("To Add new Book, Type '1'\nTo Search Book in a Database ,Type '2'\nTo View Books, Type '3'\nTo View Cart, Type '4'\nTo Add book to the cart Type '5'\nTo Edit Cart Type '6'");
            System.out.println("Type 'q' to quit");
            userInput = in.nextLine();
        }
    }




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
        System.out.println("'1' to sort on Title\n'2' to sort on Author\n'3' to sort on Published Year");
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
