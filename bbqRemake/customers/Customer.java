package bbqRemake.customers;

import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.WhiteBread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer{

    private String name;
    private List<Product> productsList;
    public Customer(String name){
        //TODO: verify
        this.name = name;
        this.productsList = new ArrayList<>();
        generateOrder();
    }

    public List<Product> getProductsList(){
        return Collections.unmodifiableList(this.productsList);
    }

    protected void generateOrder(){
        this.productsList.add(new WhiteBread());
        this.productsList.add(new GrainBread());
    }

    public String getName() {
        return this.name;
    }
}
