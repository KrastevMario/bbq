package bbqRemake;

import bbqRemake.bbqs.Bbq;
import bbqRemake.customers.Customer;

public class CustomerProducer extends Thread{

    private Bbq bbq;

    public CustomerProducer(Bbq bbq){
        if(bbq != null) {
            this.bbq = bbq;
        }
    }

    @Override
    public void run() {
        int i = 0;
        while(true) {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.bbq.addCustomer(new Customer("F" + ++i));
        }
    }
}
