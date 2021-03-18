package bbqRemake.products.bread;

import bbqRemake.products.Product;

public class GrainBread extends Product {

    public GrainBread(){
        this.price = 3;
        this.weight = 1;
        this.timeProducing = 3;
        this.type = ListOfProducts.GRAINBREAD;
    }
}
