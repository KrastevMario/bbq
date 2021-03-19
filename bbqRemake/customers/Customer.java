package bbqRemake.customers;

import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.Bread;
import bbqRemake.products.bread.WhiteBread;
import bbqRemake.products.meats.Meat;
import bbqRemake.products.meats.MeatBall;
import bbqRemake.products.meats.Pleskavica;
import bbqRemake.products.meats.Steak;
import bbqRemake.products.salads.CabbageCarrotsSalad;
import bbqRemake.products.salads.Salad;
import bbqRemake.products.salads.TomatoesCucumberSalad;
import util.Randomizator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer{

    private String name;
    private List<Product> productsList;
    private List<Meat> meatsToOrder;
    private List<Bread> breadsToOrder;
    private List<Salad> saladsToOrder;
    public Customer(String name){
        //TODO: verify
        this.name = name;
        this.productsList = new ArrayList<>();
        this.meatsToOrder = new ArrayList<>();
        this.breadsToOrder = new ArrayList<>();
        this.saladsToOrder = new ArrayList<>();
        initAllFood();
        generateOrder();
    }

    public List<Product> getProductsList(){
        return Collections.unmodifiableList(this.productsList);
    }

    protected void generateOrder(){
        this.productsList.add(this.meatsToOrder.get(Randomizator.getRandomNumber(0, this.meatsToOrder.size() - 1)));
        this.productsList.add(this.breadsToOrder.get(Randomizator.getRandomNumber(0, this.breadsToOrder.size() - 1)));
        this.productsList.add(this.saladsToOrder.get(Randomizator.getRandomNumber(0, this.saladsToOrder.size() - 1)));
    }

    protected void initAllFood(){
        this.meatsToOrder.add(new Steak());
        this.meatsToOrder.add(new MeatBall());
        this.meatsToOrder.add(new Pleskavica());

        this.breadsToOrder.add(new WhiteBread());
        this.breadsToOrder.add(new GrainBread());

        this.saladsToOrder.add(new CabbageCarrotsSalad());
        this.saladsToOrder.add(new TomatoesCucumberSalad());
    }

    public String getName() {
        return this.name;
    }
}
