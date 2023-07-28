package pt.com.sibs.order.manager.controller.dto.interfaces;

import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

public interface ParseableDTO<T extends PersistentObject> extends BuildableDTO<T> {
    T parse();
}
