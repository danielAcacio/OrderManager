package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.controller.dto.usage.OrderMovementListDTO;
import pt.com.sibs.order.manager.controller.dto.usage.MovimentOrderListDTO;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.OrderStockMovementUsage;
import pt.com.sibs.order.manager.model.StockMovement;
import pt.com.sibs.order.manager.repository.OrderStockMovementUsageRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderStockMovementUsageService {

    private OrderStockMovementUsageRepository repository;
    private OrderService orderService;
    private StockMovementService stockMovementService;

    public OrderMovementListDTO getByOrder(Integer orderId){
        Order order = this.orderService.getById(orderId);
        List<OrderStockMovementUsage> usages = this.repository.getByOrderId(orderId);
        return new OrderMovementListDTO().build(order, usages);
    }

    public MovimentOrderListDTO getByMovement(Integer movementId){
        StockMovement movement = this.stockMovementService.getById(movementId);
        List<OrderStockMovementUsage> usages = this.repository.getByMovementId(movementId);
        return new MovimentOrderListDTO().build(movement, usages);
    }
}
