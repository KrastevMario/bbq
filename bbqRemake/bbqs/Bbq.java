package bbqRemake.bbqs;

import bbqRemake.customers.Customer;
import bbqRemake.masters.Master;
import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.WhiteBread;
import bbqRemake.products.meats.MeatBall;
import bbqRemake.products.meats.Pleskavica;
import bbqRemake.products.meats.Steak;
import bbqRemake.products.salads.CabbageCarrotsSalad;
import bbqRemake.products.salads.TomatoesCucumberSalad;
import bbqRemake.sellers.Seller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Bbq extends Thread{

    private final String name;
    private Seller seller;
    private final Queue<Customer> customers;
    private final List<Master> masters;
    private final Map<Product, Integer> whiteBreadStorage;
    private final Map<Product, Integer> grainBreadStorage;
    private final Map<Product, Integer> meatBallStorage;
    private final Map<Product, Integer> steakStorage;
    private final Map<Product, Integer> pleskavicaStorage;
    private final Map<Product, Integer> cabbageCarrotsSaladStorage;
    private final Map<Product, Integer> tomatoesCucumberSaladStorage;

    //MAX THAT CAN BE SAVED (IN STORAGE)
    private static final int MAX_WHITE_BREAD = 2;
    private static final int MAX_GRAIN_BREAD = 3;
    private static final int MAX_STEAK = 3;
    private static final int MAX_PLESKAVICA = 2;
    private static final int MAX_MEAT_BALL = 4;
    private static final int MAX_CABBAGE_CARROTS_SALAD = 5000; //5kg (5000 gr)
    private static final int MAX_TOMATOES_CUCUMBER_SALAD = 5000;

    //MIN THAT CAN BE SERVED TO A CUSTOMER
    //********************* CUSTOMER'S PORTION *******************
    private static final int MIN_WHITE_BREAD = 1;
    private static final int MIN_GRAIN_BREAD = 1;
    private static final int MIN_STEAK = 1;
    private static final int MIN_PLESKAVICA = 1;
    private static final int MIN_MEAT_BALL = 1;
    private static final int MIN_CABBAGE_CARROTS_SALAD = 200; //0.2kg (200 gr)
    private static final int MIN_TOMATOES_CUCUMBER_SALAD = 200;

    public Bbq(String name){
        //TODO: verify
        this.name = name;
        this.customers = new LinkedList<>();
        this.whiteBreadStorage = new ConcurrentHashMap<>();
        this.grainBreadStorage = new ConcurrentHashMap<>();
        this.meatBallStorage = new ConcurrentHashMap<>();
        this.steakStorage = new ConcurrentHashMap<>();
        this.pleskavicaStorage = new ConcurrentHashMap<>();
        this.cabbageCarrotsSaladStorage = new ConcurrentHashMap<>();
        this.tomatoesCucumberSaladStorage = new ConcurrentHashMap<>();
        this.masters = new ArrayList<>();

        this.whiteBreadStorage.put(new WhiteBread(), 0);
        this.grainBreadStorage.put(new GrainBread(), 0);
        this.meatBallStorage.put(new MeatBall(), 0);
        this.steakStorage.put(new Steak(), 0);
        this.pleskavicaStorage.put(new Pleskavica(), 0);
        this.cabbageCarrotsSaladStorage.put(new CabbageCarrotsSalad(), 0);
        this.tomatoesCucumberSaladStorage.put(new TomatoesCucumberSalad(), 0);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Product w : this.whiteBreadStorage.keySet()) { //there's only 1 element in the collection but maybe for for future changes
                System.out.println("Product: " + w.getType() + " | " + this.whiteBreadStorage.get(w));
            }
            for (Product w : this.grainBreadStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.grainBreadStorage.get(w));
            }
            for (Product w : this.meatBallStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.meatBallStorage.get(w));
            }
            for (Product w : this.steakStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.steakStorage.get(w));
            }
            for (Product w : this.pleskavicaStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.pleskavicaStorage.get(w));
            }
            for (Product w : this.cabbageCarrotsSaladStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.cabbageCarrotsSaladStorage.get(w));
            }
            for (Product w : this.tomatoesCucumberSaladStorage.keySet()) {
                System.out.println("Product: " + w.getType() + " | " + this.tomatoesCucumberSaladStorage.get(w));
            }

        }
    }

    //******************** SELLERS **********************

    public void addSeller(Seller seller) {
        if(seller != null) {
            this.seller = seller;
        }
    }

    //********************* MASTERS ***********************
    public void addMaster(Master master){
        this.masters.add(master);
    }

    //************ CUSTOMERS *****************

    public void addCustomer(Customer customer){
        //TODO: verify

        System.out.println("A NEW CUSTOMER HAS ARRIVED: " + customer.getName());
        this.customers.offer(customer);
    }

    public boolean hasNextCustomer(){
        return this.customers.peek() != null;
    }

    public Customer getNextCustomer(){
        return this.customers.poll();
    }

    // ************************* SERVED CUSTOMER ***************************
    public void clientServedSuccessfully(Customer customer) {
        for (Master m : this.masters){
            synchronized (m){
                m.notifyAll();
            }
        }
        //save to file the order of the customer
        this.saveToFile(customer);
    }

    //*********************** SAVE FIlE ************************

    //************************* GET PRODUCT ***********************

    public boolean getWhiteBread() {
        if(this.whiteBreadStorage.get(new WhiteBread()) != null && this.whiteBreadStorage.get(new WhiteBread()) >= MIN_WHITE_BREAD){
            this.whiteBreadStorage.put(new WhiteBread(), this.whiteBreadStorage.get(new WhiteBread()) - MIN_WHITE_BREAD);
            return true;
        }
        return false;
    }

    public boolean getGrainBread() {
        if(this.grainBreadStorage.get(new GrainBread()) != null && this.grainBreadStorage.get(new GrainBread()) >= MIN_GRAIN_BREAD){
            this.grainBreadStorage.put(new GrainBread(), this.grainBreadStorage.get(new GrainBread()) - MIN_GRAIN_BREAD);
            return true;
        }
        return false;
    }

    public boolean getPleskavica() {
        if(this.pleskavicaStorage.get(new Pleskavica()) != null && this.pleskavicaStorage.get(new Pleskavica()) >= MIN_PLESKAVICA){
            this.pleskavicaStorage.put(new Pleskavica(), this.pleskavicaStorage.get(new Pleskavica()) - MIN_PLESKAVICA);
            return true;
        }
        return false;
    }

    public boolean getSteak() {
        if(this.steakStorage.get(new Steak()) != null && this.steakStorage.get(new Steak()) >= MIN_STEAK){ //
            this.steakStorage.put(new Steak(), this.steakStorage.get(new Steak()) - MIN_STEAK);
            return true;
        }
        return false;
    }

    public boolean getMeatBall() {
        if(this.meatBallStorage.get(new MeatBall()) != null && this.meatBallStorage.get(new MeatBall()) >= MIN_MEAT_BALL){ //1 minimum required to serve
            this.meatBallStorage.put(new MeatBall(), this.meatBallStorage.get(new MeatBall()) - MIN_MEAT_BALL);
            return true;
        }
        return false;
    }

    public boolean getCabbageCarrotSalad() {
        if(this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) != null &&
                this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) >= MIN_CABBAGE_CARROTS_SALAD){ // 500 -> minimum required to serve

            this.cabbageCarrotsSaladStorage.put(new CabbageCarrotsSalad(),
                    this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) - MIN_CABBAGE_CARROTS_SALAD);

            return true;
        }
        return false;
    }

    public boolean getTomatoesCucumberSalad() {
        if(this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) != null &&
                this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) >= MIN_TOMATOES_CUCUMBER_SALAD){

            this.tomatoesCucumberSaladStorage.put(new TomatoesCucumberSalad(),
                    this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) - MIN_TOMATOES_CUCUMBER_SALAD);
            return true;
        }
        return false;
    }

    //************************** VERIFY LIMITS *********************************
    public boolean isMaxReached(Product product){
        switch (product.getType()){
            case GRAINBREAD:
                return isMaxGrainBread();
            case WHITEBREAD:
                return isMaxWhiteBread();
            case STEAK:
                return isMaxSteak();
            case MEATBALL:
                return isMaxMeatBall();
            case PLESKAVICA:
                return isMaxPleskavica();
            case TOMATOESCUCUMBERSALAD:
                return isMaxTomatoCucumberSalad();
            case CABBAGECARROTSSALAD:
                return isMaxCabbageCarrotsSalad();
            default:
                //default
                break;
        }
        return true;
    }

    private synchronized boolean isMaxWhiteBread() {
    return this.whiteBreadStorage.get(new WhiteBread()) != null && this.whiteBreadStorage.get(new WhiteBread()) == MAX_WHITE_BREAD;
    }

    private synchronized boolean isMaxGrainBread() {
        return this.grainBreadStorage.get(new GrainBread()) != null && this.grainBreadStorage.get(new GrainBread()) == MAX_GRAIN_BREAD;
    }

    private synchronized boolean isMaxSteak() {
        return this.steakStorage.get(new Steak()) != null && this.steakStorage.get(new Steak()) == MAX_STEAK;
    }

    private synchronized boolean isMaxMeatBall() {
        return this.meatBallStorage.get(new MeatBall()) != null && this.meatBallStorage.get(new MeatBall()) == MAX_MEAT_BALL;
    }

    private synchronized boolean isMaxPleskavica() {
        return this.pleskavicaStorage.get(new Pleskavica()) != null && this.pleskavicaStorage.get(new Pleskavica()) == MAX_PLESKAVICA;
    }

    private synchronized boolean isMaxTomatoCucumberSalad() {
        return this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) != null &&
                this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) == MAX_TOMATOES_CUCUMBER_SALAD;
    }

    private synchronized boolean isMaxCabbageCarrotsSalad() {
        return this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) != null &&
                this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) == MAX_CABBAGE_CARROTS_SALAD;
    }

    // ************************** ADD PRODUCT (MASTER IS CREATING) ***************************
    public void addMeatBall() {
        synchronized (this.meatBallStorage) {
            if (this.meatBallStorage.get(new MeatBall()) != null &&
                    this.meatBallStorage.get(new MeatBall()) == MAX_MEAT_BALL) {
                //no more space for more breads.
                return;
            }
            if(this.meatBallStorage.get(new MeatBall()) != null && this.meatBallStorage.get(new MeatBall()) >= 0){
                this.meatBallStorage.put(new MeatBall(), this.meatBallStorage.get(new MeatBall()) + MeatBall.getWeight());
            }else {
                this.meatBallStorage.put(new MeatBall(), MeatBall.getWeight());
            }

            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }

    public void addSteak() {
        synchronized (this.steakStorage) {
            if (this.steakStorage.get(new Steak()) != null &&
                    this.steakStorage.get(new Steak()) == MAX_STEAK) {
                //no more space for more breads.
                return;
            }
            if(this.steakStorage.get(new Steak()) != null && this.steakStorage.get(new Steak()) >= 0){
                this.steakStorage.put(new Steak(), this.steakStorage.get(new Steak()) + Steak.getWeight());
            }else {
                this.steakStorage.put(new Steak(), Steak.getWeight());
            }

            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }

    public void addPleskavica() {
        synchronized (this.pleskavicaStorage) {
            if (this.pleskavicaStorage.get(new Pleskavica()) != null &&
                    this.pleskavicaStorage.get(new Pleskavica()) == MAX_PLESKAVICA) {
                //no more space for more breads.
                return;
            }
            if(this.pleskavicaStorage.get(new Pleskavica()) != null && this.pleskavicaStorage.get(new Pleskavica()) >= 0){
                this.pleskavicaStorage.put(new Pleskavica(), this.pleskavicaStorage.get(new Pleskavica()) + Pleskavica.getWeight());
            }else {
                this.pleskavicaStorage.put(new Pleskavica(), Pleskavica.getWeight());
            }

            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }

    public void addWhiteBread(){
        synchronized (this.whiteBreadStorage) {
            if (this.whiteBreadStorage.get(new WhiteBread()) != null &&
                    this.whiteBreadStorage.get(new WhiteBread()) == MAX_WHITE_BREAD) {
                //no more space for more breads.
                return;
            }
            if(this.whiteBreadStorage.get(new WhiteBread()) != null && this.whiteBreadStorage.get(new WhiteBread()) >= 0){
                this.whiteBreadStorage.put(new WhiteBread(), this.whiteBreadStorage.get(new WhiteBread()) + WhiteBread.getWeight());
            }else {
                this.whiteBreadStorage.put(new WhiteBread(), WhiteBread.getWeight());
            }
            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }

    public void addGrainBread() {
        synchronized (grainBreadStorage) {
            if (this.grainBreadStorage.get(new GrainBread()) != null &&
                    this.grainBreadStorage.get(new GrainBread()) == MAX_GRAIN_BREAD) {
                //out of space;
                return;
            }
            if(this.grainBreadStorage.get(new GrainBread()) != null && this.grainBreadStorage.get(new GrainBread()) > GrainBread.getWeight()){
                this.grainBreadStorage.put(new GrainBread(), this.grainBreadStorage.get(new GrainBread()) + 1);
            }else {
                this.grainBreadStorage.put(new GrainBread(), GrainBread.getWeight());
            }
            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }

    public void addCabbageCarrotSalad() {
        synchronized (this.cabbageCarrotsSaladStorage) {
            if (this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) != null &&
                    this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) == MAX_CABBAGE_CARROTS_SALAD) {
                //no more space for more breads.
                return;
            }
            if(this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) != null &&
                    this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) >= 0){

                this.cabbageCarrotsSaladStorage.put(new CabbageCarrotsSalad(),
                        this.cabbageCarrotsSaladStorage.get(new CabbageCarrotsSalad()) + CabbageCarrotsSalad.getWeight());
            }else {
                this.cabbageCarrotsSaladStorage.put(new CabbageCarrotsSalad(), CabbageCarrotsSalad.getWeight());
            }

            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }

    public void addTomatoesCucumberSalad() {
        synchronized (this.tomatoesCucumberSaladStorage) {
            if (this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) != null &&
                    this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) == MAX_TOMATOES_CUCUMBER_SALAD) {
                //no more space for more breads.
                return;
            }
            if(this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) != null &&
                    this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) >= 0){

                this.tomatoesCucumberSaladStorage.put(new TomatoesCucumberSalad(),
                        this.tomatoesCucumberSaladStorage.get(new TomatoesCucumberSalad()) + TomatoesCucumberSalad.getWeight());
            }else {
                this.tomatoesCucumberSaladStorage.put(new TomatoesCucumberSalad(), TomatoesCucumberSalad.getWeight());
            }

            synchronized (this.seller) {
                this.seller.notifyAll();
            }
        }
    }
}
