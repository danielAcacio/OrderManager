package pt.com.sibs.order.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderContoller {

    private OrderService service;

    @Operation(description = "Create A new Order")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Order created."),
            @ApiResponse( responseCode = "404", description = "Item Or User Orderer(email search) not found."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PostMapping("/order")
    public ResponseEntity<DetailsOrderDTO> createOrder(@Valid @RequestBody RegisterOrderDTO dto){
        return ResponseEntity.ok(this.service.create(dto));
    }

    @Operation(description = "Update a Order")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Order updated."),
            @ApiResponse( responseCode = "404", description = "Item Or User Orderer(email search) not found."),
            @ApiResponse( responseCode = "409", description = "Order has conflict with already alocated stock movements."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PutMapping("/order/{orderId}")
    public ResponseEntity<DetailsOrderDTO> updateOrder(
            @PathVariable(name = "orderId") Integer orderId,
            @Valid @RequestBody RegisterOrderDTO dto){
        return ResponseEntity.ok(this.service.update(orderId,dto));
    }

    @Operation(description = "Delete a Order")
    @ApiResponses({
            @ApiResponse( responseCode = "204", description = "Order deleted."),
            @ApiResponse( responseCode = "404", description = "Order not found."),
            @ApiResponse( responseCode = "409", description = "Orders complete or patially complete cannot be deleted."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<DetailsOrderDTO> deleteOrder(
            @PathVariable(name = "orderId") Integer orderId){
        this.service.delete(orderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Get all Orders")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Orders."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/order")
    public ResponseEntity<List<DetailsOrderDTO>> listOrder(){
        return ResponseEntity.ok(this.service.getAll());
    }

    @Operation(description = "Get all Orders Paged")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Orders Paged."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/order/paged")
    public ResponseEntity<Page<DetailsOrderDTO>> listOrderPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page, size)));
    }

}
