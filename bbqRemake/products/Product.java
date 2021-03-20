package bbqRemake.products;

import java.util.Objects;

public abstract class Product {
    protected static int weight; //created (from a master) weight
    protected double price;
    protected int timeProducing; //seconds
    protected ListOfProducts type;

    public ListOfProducts getType() {
        return this.type;
    }

    public enum ListOfProducts{
        WHITEBREAD,
        GRAINBREAD,
        MEATBALL,
        PLESKAVICA,
        STEAK,
        CABBAGECARROTSSALAD,
        TOMATOESCUCUMBERSALAD
    }

    public enum ListOfMeats{
        MEATBALL,
        PLESKAVICA,
        STEAK
    }

    public enum ListOfSalads{
        CABBAGECARROTSSALAD,
        TOMATOESCUCUMBERSALAD
    }

    public enum ListOfBreads{
        WHITEBREAD,
        GRAINBREAD
    }

    public int getProducingTime(){
        return timeProducing;
    }

    public static int getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                timeProducing == product.timeProducing &&
                type == product.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, timeProducing, type);
    }
}
