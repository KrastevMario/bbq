package bbqRemake.masters.breadBakers;

import bbqRemake.bbqs.Bbq;
import bbqRemake.masters.Master;
import bbqRemake.products.Product;
import bbqRemake.products.bread.GrainBread;
import bbqRemake.products.bread.WhiteBread;

import java.util.ArrayList;
import java.util.List;

public class MasterBaker extends Master {

    public MasterBaker(String name, Bbq bbq) {
        super(name, bbq);
    }

    @Override
    protected void initializeProducingProducts() {
        this.allowedProducts.add(new GrainBread());
        this.allowedProducts.add(new WhiteBread());
    }

    @Override
    protected void insertProductInBbq(Product product) {
        switch(product.getType()){
            case GRAINBREAD -> this.bbq.addGrainBread();
            case WHITEBREAD -> this.bbq.addWhiteBread();
            default -> System.out.println("Couldn't insert successfully the product(" + this.name + ")");
        }
    }
}
