package bbqRemake.products.bread;

import bbqRemake.products.Product;

public class WhiteBread extends Bread {

    public WhiteBread(){
        this.price = 4;
        weight = 1;
        this.timeProducing = 4;
        this.type = ListOfProducts.WHITEBREAD;
    }
}
