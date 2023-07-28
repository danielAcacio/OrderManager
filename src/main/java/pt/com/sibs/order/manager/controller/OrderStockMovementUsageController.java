package pt.com.sibs.order.manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.com.sibs.order.manager.controller.dto.usage.OrderMovementListDTO;
import pt.com.sibs.order.manager.controller.dto.usage.MovimentOrderListDTO;
import pt.com.sibs.order.manager.service.OrderStockMovementUsageService;

@RestController
@AllArgsConstructor
public class OrderStockMovementUsageController {


    private OrderStockMovementUsageService orderStockMovementUsageService;

    @GetMapping("/stock/order/{orderId}/moviments")
    public ResponseEntity<OrderMovementListDTO> getByOrder(
            @PathVariable("orderId") Integer orderId){
        return ResponseEntity.ok(this.orderStockMovementUsageService.getByOrder(orderId));
    }

    @GetMapping("/stock/movement/{movementId}/orders")
    public ResponseEntity<MovimentOrderListDTO> getByMovement(
            @PathVariable("movementId") Integer movementId){
        return ResponseEntity.ok(this.orderStockMovementUsageService.getByMovement(movementId));
    }


}
