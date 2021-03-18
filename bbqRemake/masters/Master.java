package bbqRemake.masters;

import bbqRemake.bbqs.Bbq;
import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.WhiteBread;

import java.util.ArrayList;
import java.util.List;

public class Master extends Thread{
    protected String name;
    protected List<Product> allowedProducts;
    protected Bbq bbq;
    protected Master(String name, Bbq bbq){
        //TODO: verify
        this.name = name;
        this.bbq = bbq;
        this.allowedProducts = new ArrayList<>();
        this.initializeProducingProducts();
    }

    protected void initializeProducingProducts(){
        //each master will have its own list
    }

    @Override
    public void run() {
        while(true){
            Product generatedProduct = this.generateRandomProduct();
            System.out.println("Product " + generatedProduct.getType() + " started creating");
            try {
                Thread.sleep(generatedProduct.getProducingTime() * 1000);
            } catch (InterruptedException e) {
                System.out.println("ERROR in " + this.name + " | " + e.getMessage());
            }
            System.out.println("the product: " + generatedProduct.getType() + " has been created " + "by " + this.name);
            synchronized (this.bbq) {
                this.insertProductInBbq(generatedProduct);
                this.bbq.notifyAll();
            }
        }
    }

    protected Product generateRandomProduct() {
        return this.allowedProducts.get(util.Randomizator.getRandomNumber(0, this.allowedProducts.size() - 1));
    }

    protected void insertProductInBbq(Product product){
        //every class will be able to insert its own items
    }
}
