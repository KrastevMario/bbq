package bbqRemake.products;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return weight == product.weight &&
                Double.compare(product.price, price) == 0 &&
                timeProducing == product.timeProducing &&
                type == product.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, price, timeProducing, type);
    }
}
