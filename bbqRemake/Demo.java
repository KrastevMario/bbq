package bbqRemake;

import bbqRemake.bbqs.Bbq;
import bbqRemake.customers.Customer;
import bbqRemake.masters.breadBakers.MasterBaker;
import bbqRemake.sellers.Seller;

public class Demo {
    public static void main(String[] args) {
        Customer gosho = new Customer("Gosho");
        Customer pesho = new Customer("Pesho");

        Bbq bbq = new Bbq("Skarata na selo");

        MasterBaker pena = new MasterBaker("Pena", bbq);

        Seller kiro = new Seller("Kiro", bbq);

        bbq.addCustomer(gosho);
        bbq.addCustomer(pesho);

        pena.start();
        kiro.start();
        bbq.setDaemon(true);
        bbq.start();
    }
}

/*
customer -> has already an order in his mind. Waits in the bbq

seller -> asks a customer to come inside and order. If one of the products the customer wants is not yet in
the bbq, just wait().
She can interact with the bbq.

bbq -> has a queue of customers. Masters can insert inside the produced produced products. If a product has
reached the max capacity, the master won't be able to insert the product (discards it).

master -> creates products all the time. It interacts with the bbq to exchange info about the products.
(every master has a list of items that he can produce randomly).
Everytime an item is created, the masters should NotifyAll() on their bbq.




 */
