package pt.com.sibs.order.manager.controller.dto.usage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.interfaces.SimpleDTO;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.OrderStockMovementUsage;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter @Setter
public class OrderMovementListDTO implements SimpleDTO {

    private DetailsOrderDTO order;
    private List<DetailsStockMovementDTO> movements;

    public OrderMovementListDTO build(Order order , List<OrderStockMovementUsage> usages){
        this.setOrder(new DetailsOrderDTO().build(order));
        this.setMovements(usages.stream().map(u-> new DetailsStockMovementDTO().build(u.getStockMovement())).collect(Collectors.toList()));
        return this;
    }


}
