package pt.com.sibs.order.manager.model.interfaces;

import pt.com.sibs.order.manager.model.Item;

public interface StockItemHandler extends PersistentObject{
    public Item getItem();
}
