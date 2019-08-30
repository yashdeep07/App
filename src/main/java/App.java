import java.util.*;


public class App {

    public App(Map < String, ArrayList < Double >> map1 ,Map < Double, Book > map2,ArrayList<Double> mlist) {
        this.initializeTitleMap(map1);
        this.intializeDataset(map2);
        this.initializeList(mlist);
    }

    private void initializeTitleMap (Map < String, ArrayList < Double >> map1){
        map1.put("Quichotte", new ArrayList<Double>(Arrays.asList((double) 123456789, (double) 123456788)));
        map1.put("The Testaments", new ArrayList<Double>(Arrays.asList((double) 123456779, (double) 123456778, (double) 123456777)));
        map1.put("The Anarchy", new ArrayList<Double>(Arrays.asList((double) 123456769, (double) 123456768)));
        map1.put("City Of Girls", new ArrayList<Double>(Arrays.asList((double) 123456759)));
        map1.put("Red Wolf", new ArrayList<Double>(Arrays.asList((double) 123456749)));
        map1.put("Rules for Visiting: A Novel", new ArrayList<Double>(Arrays.asList((double) 123456739)));
    }

    private void intializeDataset (Map < Double, Book > map2){
        map2.put((double) 123456789, new Book("Quichotte", "Salman Rushdie", 123456789, "JKL", "English", 1996, "PAPERBACK", 999, 100));
        map2.put((double) 123456788, new Book("Quichotte", "Salman Rushdie", 123456788, "JKL", "English", 1997, "HARDBOUND", 999, 100));
        map2.put((double) 123456779, new Book("The Testaments", "Margaret", 123456779, "KL", "English", 1996, "PAPERBACK", 999, 100));
        map2.put((double) 123456778, new Book("The Testaments", "Margaret", 123456778, "KL", "English", 1997, "DIGITAL", 999.99, 100));
        map2.put((double) 123456777, new Book("The Testaments", "Margaret", 123456777, "KL", "English", 1998, "HARDBOUND", 999, 100));
        map2.put((double) 123456769, new Book("The Anarchy", "William Dalrymple", 123456769, "KL", "English", 1996, "DIGITAL", 999, 100));
        map2.put((double) 123456768, new Book("The Anarchy", "William Dalrymple", 123456768, "KL", "English", 1996, "HARDBOUND", 999, 100));
        map2.put((double) 123456759, new Book("City Of Girls", "Elizabeth Gilbert", 123456759, "JKL", "English", 1996, "HARDBOUND", 999, 100));
        map2.put((double) 123456749, new Book("Red Wolf", "Marlon James", 123456749, "JKL", "English", 1996, "HARDBOUND", 999, 100));
        map2.put((double) 123456739, new Book("Rules for Visiting: A Novel", "Jessica Francis Kane", 123456739, "JKL", "English", 1996, "HARDBOUND", 999, 100));
            /*map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));
            map2.put("", new Book(ABC,DEF,123456789,JKL,"English",1996,STU,999));*/
    }

    private void initializeList(ArrayList<Double> mlist){
        mlist.add((double) 123456789);
        mlist.add((double) 123456788);
        mlist.add((double) 123456779);
        mlist.add((double) 123456778);
        mlist.add((double) 123456777);
        mlist.add((double) 123456769);
        mlist.add((double) 123456768);
        mlist.add((double) 123456759);
        mlist.add((double) 123456749);
        mlist.add((double) 123456739);

    }

/*
    public static void showMainMenu() {
        System.out.println("For Adding a book ,Type 'Add'");
        System.out.println("To Search a book ,Type 'Search'");
        System.out.println("To Buy using an ISBN number type 'Buy'");
    }

    public static void showAddMenu() {
        System.out.println("To Add Book ,Type '1'");
        System.out.println("To go back to Main Menu ,Type '2'");
    }

    public static void addInterface(String userInput, Scanner in) {
        System.out.println("Please Add book Details like this - Title,Author,ISBN,Publisher,Language,Published Year,Binding,Price");
        System.out.println("OR Type '0' to go back to Main Menu!");
        userInput = in.nextLine();
        if (userInput.equals("0")) {
            return;
        }

        String[] arrOfInputs = userInput.split(",");
        double isbn = Double.parseDouble(arrOfInputs[2]);// (title,author,123456789,publisher,lang,1996,binding,999)
        int pubYear = Integer.parseInt(arrOfInputs[5]);
        double price = Double.parseDouble(arrOfInputs[7]);

        Book book = new Book(arrOfInputs[0], arrOfInputs[1], isbn, arrOfInputs[3], arrOfInputs[4], pubYear, arrOfInputs[6], price, 100);
        Globals.isbnMap.put(isbn, book);
        if (!Globals.bookTitleMap.containsKey(arrOfInputs[0])) {
            Globals.bookTitleMap.put(arrOfInputs[0], new ArrayList<Double>());
        }
        Globals.bookTitleMap.get(arrOfInputs[0]).add(isbn);
        System.out.println("Book has been added to the Database");
    }

    public static void showSearchMenu() {
        System.out.println("To Search a Book ,Type '1'");
        System.out.println("To go back to Main Menu ,Type '2'");
    }

    public static void searchInterface(String userInput, Scanner in) {
        System.out.println("Please Type Book name");
        System.out.println("OR Type '0' to go back to Main Menu!");
        userInput = in.nextLine();
        if (userInput.equals("0")) {
            return;
        }
        if (Globals.bookTitleMap.containsKey(userInput)) {
            ArrayList<Double> arrList = Globals.bookTitleMap.get(userInput);
            for (int i = 0; i < arrList.size(); i++) {
                Book b = Globals.isbnMap.get(arrList.get(i));
                System.out.print(b.title);
                System.out.println("  ");
                System.out.print(b.isbn);
                System.out.print("  " + b.count);
                System.out.println("\n");
            }
        } else {
            System.out.println("Book is not Available");
            return;
        }


        System.out.println("To buy the book Type ISBN number of that book.");
        userInput = in.nextLine();
        if (userInput.equals("0")) {
            return;
        }
        double isbn = Double.parseDouble(userInput);
        if (Globals.isbnMap.containsKey(isbn)) {
            Globals.isbnMap.get(isbn).count -= 1;
            System.out.println("Your order has been placed!!");
        } else {
            System.out.println("Sorry book is not present");
        }


    }

    public static void main(String args[]) {


        Globals.isbnMap = intializeDataset(Globals.isbnMap);

        Globals.bookTitleMap = initializeTitleMap(Globals.bookTitleMap);


        Scanner in = new Scanner(System.in);
        showMainMenu();
        String userInput = in.nextLine();

        while (true) {
            if (userInput.equals("Add")) {
                showAddMenu();
                userInput = in.nextLine();
                if (userInput.equals("1")) {
                    addInterface(userInput, in);
                } else if (userInput.equals("3")) {
                    Globals.bookTitleMap.entrySet().forEach(entry -> {
                        System.out.println(entry.getKey() + " " + entry.getValue());
                    });
                } else {
                    continue;
                }
            } else if (userInput.equals("Search")) {
                showSearchMenu();
                userInput = in.nextLine();
                if (userInput.equals("1")) {
                    searchInterface(userInput, in);
                }
            } else if (userInput.equals("Buy")) {
                System.out.println("Please enter the ISBN number");
                userInput = in.nextLine();
                double isbn = Double.parseDouble(userInput);
                if (Globals.isbnMap.containsKey(isbn)) {
                    Globals.isbnMap.get(isbn).count -= 1;
                    System.out.println("Your order has been placed!!");
                } else {
                    System.out.println("Sorry book is not present");
                }
            } else {
                break;
            }

            showMainMenu();
            userInput = in.nextLine();
        }
        //System.out.println("Bro you breaked the chain!!");

    }

    */
}
