import java.util.Scanner;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String language;
    private int publishedYear;
    private BindingType bindingType;
    private Double price;

    Book(String title,String author, String isbn, String publisher, String language, int publishedYear , Double price , BindingType bindingType){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher =publisher;
        this.language =language;
        this.publishedYear = publishedYear;
        this.bindingType = bindingType;
        this.price = price;

    }

    public String getAuthor(){
        return this.author;
    }

    public String getTitle(){return this.title;}

    public String getIsbn(){
        return this.isbn;
    }

    public String getPublisher(){return this.publisher;}

    public String getLanguage() { return this.language; }

    public int getPublishedYear(){
        return this.publishedYear;
    }

    public BindingType getBindingType() { return this.bindingType; }

    public Double getPrice() { return this.price; }


    private void setTitle(String title){
        this.title = title;
    }

    private void setAuthor(String author){
        this.author = author;
    }

    private void setIsbn(String isbn){
        this.isbn = isbn;
    }

    private void setPublisher(String publisher){
        this.publisher = publisher;
    }

    private void setLanguage(String language){
        this.language = language;
    }

    private void setPublishedYear(int publishedYear){
        this.publishedYear = publishedYear;
    }

    private void setBindingType(BindingType bindingType){
        this.bindingType = bindingType;
    }

    private void setPrice(Double price){
        this.price = price;
    }


    public void listView(){
        System.out.println("Title     :\t" + this.title + "\t" + "Author    :\t" + this.author + "\t" +"Price     :\t" + String.format("%.2f", this.price) + "\t\t" +"ISBN      :\t" + this.isbn + "\t" +"Year      : " + this.publishedYear);
    }

    public void detailsView(){
        System.out.println("Title     :\t" + this.title);
        System.out.println("Author    :\t" + this.author);
        System.out.println("Publisher :\t" + this.publisher);
        System.out.println("Price     :\t" + this.price);
        System.out.println("ISBN      :\t" + this.isbn);
        System.out.println("Year      :\t" + this.publishedYear);
        System.out.println();
    }
}
