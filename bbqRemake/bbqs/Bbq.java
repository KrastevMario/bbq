package bbqRemake.bbqs;

import bbqRemake.customers.Customer;
import bbqRemake.masters.Master;
import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.WhiteBread;
import bbqRemake.sellers.Seller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Bbq extends Thread{

    private String name;
    private Seller seller;
    private Queue<Customer> customers;
    private List<Master> masters;
    private Map<Product, Integer> whiteBreadStorage;
    private Map<Product, Integer> grainBreadStorage;

    private Master mast;

    private final int maxWhiteBread = 2;
    private final int maxGrainBread = 3;

    public Bbq(String name){
        //TODO: verify
        this.name = name;
        this.customers = new LinkedList<>();
        this.whiteBreadStorage = new ConcurrentHashMap<>();
        this.grainBreadStorage = new ConcurrentHashMap<>();
        this.masters = new ArrayList<>();
    }

    public void addCustomer(Customer customer){
        //TODO: verify

        System.out.println("A NEW CUSTOMER HAS ARRIVED: " + customer.getName());
        this.customers.offer(customer);
    }

    public void addSeller(Seller seller) {
        if(seller != null) {
            this.seller = seller;
        }
    }

    public void addMaster(Master master){
        this.masters.add(master);
    }

    public boolean hasNextCustomer(){
        return this.customers.peek() != null;
    }

    public Customer getNextCustomer(){
        return this.customers.poll();
    }

    public void addWhiteBread(){
        synchronized (this.whiteBreadStorage) {
            if (this.whiteBreadStorage.get(new WhiteBread()) != null &&
                    this.whiteBreadStorage.get(new WhiteBread()) == this.maxWhiteBread) {
                //no more space for more breads.
                return;
            }
        }
        if(this.whiteBreadStorage.get(new WhiteBread()) != null && this.whiteBreadStorage.get(new WhiteBread()) > 0){
            this.whiteBreadStorage.put(new WhiteBread(), this.whiteBreadStorage.get(new WhiteBread()) + 1);
        }else {
            this.whiteBreadStorage.put(new WhiteBread(), 1);
        }

        synchronized (this.seller) {
            this.seller.notifyAll();
        }
    }

    public void addGrainBread() {
        synchronized (grainBreadStorage) {
            if (this.grainBreadStorage.get(new GrainBread()) != null &&
                    this.grainBreadStorage.get(new GrainBread()) == this.maxGrainBread) {
                //out of space;
                return;
            }
        }
        if(this.grainBreadStorage.get(new GrainBread()) != null && this.grainBreadStorage.get(new GrainBread()) > 0){
            this.grainBreadStorage.put(new GrainBread(), this.grainBreadStorage.get(new GrainBread()) + 1);
        }else {
            this.grainBreadStorage.put(new GrainBread(), 1);
        }
        synchronized (this.seller) {
            this.seller.notifyAll();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Product w : this.whiteBreadStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.whiteBreadStorage.get(w));
            }
            for (Product w : this.grainBreadStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.grainBreadStorage.get(w));
            }
        }
    }

    public boolean getWhiteBread() {
        if(this.whiteBreadStorage.get(new WhiteBread()) != null && this.whiteBreadStorage.get(new WhiteBread()) > 0){
            this.whiteBreadStorage.put(new WhiteBread(), this.whiteBreadStorage.get(new WhiteBread()) - 1);
            return true;
        }
        return false;
    }

    public boolean getGrainBread() {
        if(this.grainBreadStorage.get(new GrainBread()) != null && this.grainBreadStorage.get(new GrainBread()) > 0){
            this.grainBreadStorage.put(new GrainBread(), this.grainBreadStorage.get(new GrainBread()) - 1);
            return true;
        }
        return false;
    }

    public boolean isMaxReached(Product product){
        switch (product.getType()){
            case GRAINBREAD:
                return isMaxGrainBread();
            case WHITEBREAD:
                return isMaxWhiteBread();
            default:
                //default
                break;
        }
        return true;
    }

    private boolean isMaxWhiteBread() {
    return this.whiteBreadStorage.get(new WhiteBread()) != null && this.whiteBreadStorage.get(new WhiteBread()) == this.maxWhiteBread;
    }

    private boolean isMaxGrainBread() {
        return this.grainBreadStorage.get(new GrainBread()) != null && this.grainBreadStorage.get(new GrainBread()) == this.maxGrainBread;
    }

    public void clientServedSuccessfully() {
        for (Master m : this.masters){
            synchronized (m){
                m.notifyAll();
            }
        }
    }
}
