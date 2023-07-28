package pt.com.sibs.order.manager.controller.dto.interfaces;

import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

public interface BuildableDTO<T extends PersistentObject> extends SimpleDTO{
    BuildableDTO<T> build(T t);
}
