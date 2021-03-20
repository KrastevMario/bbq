package bbqRemake.notebooks;

import bbqRemake.customers.Customer;
import bbqRemake.products.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    private static long counterId = 0;
    private List<Product> productsOrdered;
    private double totalSum;
    private Customer customer;
    private LocalDateTime dateOrdered;
    private long id;

    public Bill(List<Product> productsOrdered, Customer customer, LocalDateTime dateOrdered){
        //TODO: verify
        this.productsOrdered = productsOrdered;
        this.dateOrdered = dateOrdered;
        for (Product p : productsOrdered){
            totalSum += p.getPrice();
        }
        this.customer = customer;
        this.id = ++counterId;
    }

    public List<Product> getProductsOrdered() {
        return productsOrdered;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getDateOrdered() {
        return dateOrdered;
    }

    public long getId() {
        return id;
    }
}
