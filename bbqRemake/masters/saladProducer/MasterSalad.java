package bbqRemake.masters.saladProducer;

import bbqRemake.bbqs.Bbq;
import bbqRemake.masters.Master;
import bbqRemake.products.Product;
import bbqRemake.products.salads.CabbageCarrotsSalad;
import bbqRemake.products.salads.TomatoesCucumberSalad;

public class MasterSalad extends Master {
    public MasterSalad(String name, Bbq bbq) {
        super(name, bbq);
    }

    @Override
    protected void initializeProducingProducts() {
        this.allowedProducts.add(new CabbageCarrotsSalad());
        this.allowedProducts.add(new TomatoesCucumberSalad());
    }

    @Override
    protected void insertProductInBbq(Product product) {
        switch(product.getType()){
            case CABBAGECARROTSSALAD -> this.bbq.addCabbageCarrotSalad();
            case TOMATOESCUCUMBERSALAD -> this.bbq.addTomatoesCucumberSalad();
            default -> System.out.println("Couldn't insert successfully the product(" + this.name + ")");
        }
    }
}
