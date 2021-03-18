package bbqRemake.products;

public class Product {
    protected int weight;
    protected double price;
    protected int timeProducing; //seconds
    protected ListOfProducts type;

    public ListOfProducts getType() {
        return this.type;
    }

    public enum ListOfProducts{
        WHITEBREAD,
        GRAINBREAD
    }

    public int getProducingTime(){
        return timeProducing;
    }
}
