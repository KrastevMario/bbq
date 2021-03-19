package bbqRemake.masters.meatCooker;

import bbqRemake.bbqs.Bbq;
import bbqRemake.masters.Master;
import bbqRemake.products.Product;
import bbqRemake.products.meats.MeatBall;
import bbqRemake.products.meats.Pleskavica;
import bbqRemake.products.meats.Steak;

public class MasterMeatCooker extends Master {
    public MasterMeatCooker(String name, Bbq bbq) {
        super(name, bbq);
    }

    @Override
    protected void initializeProducingProducts() {
        this.allowedProducts.add(new MeatBall());
        this.allowedProducts.add(new Pleskavica());
        this.allowedProducts.add(new Steak());
    }

    @Override
    protected void insertProductInBbq(Product product) {
        switch(product.getType()){
            case MEATBALL -> this.bbq.addMeatBall();
            case PLESKAVICA -> this.bbq.addPleskavica();
            case STEAK -> this.bbq.addSteak();
            default -> System.out.println("Couldn't insert successfully the product(" + this.name + ")");
        }
    }
}
