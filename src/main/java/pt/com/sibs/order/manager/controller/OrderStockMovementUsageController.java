package pt.com.sibs.order.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Get One Order and its Stock Movement list")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Get One Order and its Stock Movement list."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/stock/order/{orderId}/moviments")
    public ResponseEntity<OrderMovementListDTO> getByOrder(
            @PathVariable("orderId") Integer orderId){
        return ResponseEntity.ok(this.orderStockMovementUsageService.getByOrder(orderId));
    }

    @Operation(description = "Get One Stock Movement and its Order list")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Get One Stock Movement and its Order list"),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/stock/movement/{movementId}/orders")
    public ResponseEntity<MovimentOrderListDTO> getByMovement(
            @PathVariable("movementId") Integer movementId){
        return ResponseEntity.ok(this.orderStockMovementUsageService.getByMovement(movementId));
    }


}
