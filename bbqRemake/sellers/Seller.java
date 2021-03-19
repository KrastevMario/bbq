package bbqRemake.sellers;

import bbqRemake.bbqs.Bbq;
import bbqRemake.customers.Customer;
import bbqRemake.exceptions.InvalidCustomerException;
import bbqRemake.products.Product;

import java.util.List;

public class Seller extends Thread{

    private String name;
    private Bbq bbq;
    private Customer customer;
    public Seller(String name, Bbq bbq){
        //TODO: verify
        this.name = name;
        this.bbq = bbq;
    }

    @Override
    public void run() {
        while(true){
            //always check for the next customer
            if(this.bbq.hasNextCustomer()){
                //when the customer is taken, always check if he is served properly
                try {
                    this.customer = this.bbq.getNextCustomer();
                    System.out.println("took the next customer");
                    this.serveCustomer();
                } catch (InvalidCustomerException e) {
                    System.out.println("ERROR IN SELLER : - " + e.getMessage());
                }
            }else{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void serveCustomer(Customer customer){
//        //
//    }

    private synchronized void serveCustomer() throws InvalidCustomerException {
        if(this.customer == null){
            throw new InvalidCustomerException();
        }
        // * * serve the customer * *
        //take his order
        List<Product> orderFromCustomer = this.customer.getProductsList();
        System.out.println(this.customer.getName() + "'s ORDER: ");
        for (Product p : orderFromCustomer){
            System.out.println("----- " + p.getType());
        }
        //check each part of his order (in the list) until the list is empty.
        for (Product p : orderFromCustomer){
            //  if there's a part missing, make the seller wait until a master notifies that has cooked food.
            //      if the food is the right one, break the infinite loop and go to the next product
            switch (p.getType()){
                case WHITEBREAD:
                    while(true){
                        if(this.bbq.getWhiteBread()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK WHITE BREAD");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case GRAINBREAD:
                    while(true){
                        if(this.bbq.getGrainBread()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK GRAIN BREAD");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case MEATBALL:
                    while(true){
                        if(this.bbq.getMeatBall()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK MEAT BALL");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case STEAK:
                    while(true){
                        if(this.bbq.getSteak()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK STEAK");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case PLESKAVICA:
                    while(true){
                        if(this.bbq.getPleskavica()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK PLESKAVICA");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CABBAGECARROTSSALAD:
                    while(true){
                        if(this.bbq.getCabbageCarrotSalad()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK Cabbage and carrots Salad");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TOMATOESCUCUMBERSALAD:
                    while(true){
                        if(this.bbq.getTomatoesCucumberSalad()){
                            //TODO: add the price of the item to the order
                            System.out.println("TOOK tomatoes and cucumbers Salad");
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    System.out.println("THE FOOD IS NOT IN THE LIST OF ALLOWED ONES");
            }
        }
        System.out.println("Customer " + customer.getName() + " served SUCCESSFULLY");
        this.bbq.clientServedSuccessfully(this.customer);
    }
}
