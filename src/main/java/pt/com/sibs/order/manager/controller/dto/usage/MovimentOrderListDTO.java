package pt.com.sibs.order.manager.controller.dto.usage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.interfaces.SimpleDTO;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.model.OrderStockMovementUsage;
import pt.com.sibs.order.manager.model.StockMovement;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MovimentOrderListDTO implements SimpleDTO {

    private DetailsStockMovementDTO movement;
    private List<DetailsOrderDTO> orders;

    public MovimentOrderListDTO build(StockMovement movement, List<OrderStockMovementUsage> list){
        this.setMovement(new DetailsStockMovementDTO().build(movement));
        this.setOrders(list.stream().map(u-> new DetailsOrderDTO().build(u.getOrder())).collect(Collectors.toList()));
        return this;
    }

}
