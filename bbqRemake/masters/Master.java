package bbqRemake.masters;

import bbqRemake.bbqs.Bbq;
import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.WhiteBread;

import java.util.ArrayList;
import java.util.List;

public abstract class Master extends Thread{
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
            //if the product is not at max, continue
            //if the product is at max, check if all the products are at max. If yes, wait
            // otherwise make the master to think of the creation of a new product and verify it again.
            boolean isAllMaxed = true;
            while(this.bbq.isMaxReached(generatedProduct) && isAllMaxed) {
                for (Product p : this.allowedProducts){
                    if(!this.bbq.isMaxReached(p)){
                        isAllMaxed = false;
                    }
                }
                if(isAllMaxed){ //if everything is maxed out, wait until a customer orders something
                    try {
                        synchronized (this) {
                            System.out.println("EVERYTHING IS MAXED - " +this.name + " WAITING");
                            wait();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("I GOT INTERRUPED IN BBQ - " + e.getMessage());
                    }
                }else {
                    generatedProduct = this.generateRandomProduct();
                }
            }

            //System.out.println("Product " + generatedProduct.getType() + " started creating");
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

    protected synchronized void insertProductInBbq(Product product){
        //every class will be able to insert its own items
    }

//    //get if the chosen product is at max. If not then it allows the creation. If it is max,
//    // check if all the products the master can create are at their max capacity. If they are, then wait
//    // if they are not, then make a new random from the list.
//    protected boolean isFullCapacity(Product product){ // -> allows you to check if 1 product has reached max capacity
//        switch (product.getType()){
//            case GRAINBREAD:
//                return bbq.isMaxGrainBread();
//            case WHITEBREAD:
//                return bbq.isMaxWhiteBread();
//            default:
//                System.out.println("AN ERROR OCCURED IN MASTER (default statement reached");
//                return true;
//        }
//    }
//
//    protected boolean isAllProductsMaxCapacity(){ // -> if all the products that a Master(chief) is creating are at max
//        for (Product p : this.allowedProducts){
//            switch (p.getType()){
//                case WHITEBREAD:
//                    if(!this.bbq.isMaxWhiteBread()) return false;
//                    break;
//                case GRAINBREAD:
//                    if(!this.bbq.isMaxGrainBread()) return false;
//                    break;
//                default:
//                    //default
//                    break;
//            }
//        }
//        return true;
//    }
}
