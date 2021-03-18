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
}
