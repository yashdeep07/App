import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Book {
    private String title;
    private String author;
    private double isbn;
    private String publisher;
    private String language;
    private int publishedYear;
    private BindingType bindingType;
    private double price;
    public int count = 100;

    Book(String title, String author, double isbn){
        this.title = title;
        this.author = author;
        this.isbn = isbn;

        setPublisher();
        setLanguage();
        setPublishedYear();

        this.bindingType = setBindingType();
        setPrice();
        setCount();
    }

    Book(String title,String author, double isbn, String publisher, String language, int publishedYear, String bindingType, double price,int count ){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher =publisher;
        this.language =language;
        this.publishedYear = publishedYear;
        this.bindingType = BindingType.valueOf(bindingType);
        this.price = price;
        this.count = count;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getTitle(){return this.title;}

    private void setPublisher(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Publisher :");
        this.publisher = in.nextLine();
    }

    private void setLanguage(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Language :");
        this.language = in.nextLine();
    }

    private void setPublishedYear(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Published Year :");
        this.publishedYear = Integer.parseInt(in.nextLine());
    }

    public int getPublishedYear(){
        return this.publishedYear;
    }

    private BindingType setBindingType(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter BindingType :");
        String bind = in.nextLine();
        bind = bind.toUpperCase();
        return BindingType.valueOf(bind);
    }

    private void setPrice(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Price : ");
        this.price = in.nextDouble();
    }

    public void setCount(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Count : ");
        this.count = Integer.parseInt(in.nextLine());
    }

    public void showInfo(){
        System.out.println(this.title + ", " + this.author + " ," + this.publisher + " ," + Double.toString(this.isbn) + " ," + this.price +", " + this.bindingType + ", " + Integer.toString(this.publishedYear));
    }

    public static void showBooks(ArrayList<Double> arr){
        for(Double isbn : arr){
            Globals.isbnMap.get(isbn).showInfo();
        }
    }

    public static void showBooks(Set<Double> s){
        for(Double isbn : s){
            Globals.isbnMap.get(isbn).showInfo();
        }
    }
}
