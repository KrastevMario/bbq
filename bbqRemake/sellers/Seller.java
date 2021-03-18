package bbqRemake.sellers;

import bbqRemake.bbqs.Bbq;
import bbqRemake.customers.Customer;
import bbqRemake.exceptions.InvalidCustomerException;

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
            if(this.bbq.hasNextCustomer()){
                try {
                    this.customer = this.bbq.getNextCustomer();
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

    private void serveCustomer() throws InvalidCustomerException {
        if(this.customer == null){
            throw new InvalidCustomerException();
        }
        //serve the customer

    }
}
