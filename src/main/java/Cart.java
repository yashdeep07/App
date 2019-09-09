import java.util.*;

class Cart {
    Map<Book, Integer> bookToQuantityMap;

    Cart() {
        this.bookToQuantityMap = new HashMap<Book, Integer>();
    }

    public void showCart() {
        if (this.bookToQuantityMap.size() == 0) {
            System.out.println("Your Cart is Empty!!\n");
        }
        for (Map.Entry<Book, Integer> entry : this.bookToQuantityMap.entrySet()){
            entry.getKey().listView();
            System.out.println("Quantity :\t" + entry.getValue() + "\n");
        }
    }


    public void addProduct(Book book, int quantity){
        this.bookToQuantityMap.put(book,quantity);
    }

    public void editQuantity(Book book, int quantity){
        this.bookToQuantityMap.put(book,quantity);
    }

    public void removeItem(Book book){
        this.bookToQuantityMap.remove(book);
    }

}
