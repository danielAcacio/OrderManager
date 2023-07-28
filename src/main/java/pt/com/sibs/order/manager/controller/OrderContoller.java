package pt.com.sibs.order.manager.controller;

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

    @PostMapping("/order")
    public ResponseEntity<DetailsOrderDTO> createOrder(@Valid @RequestBody RegisterOrderDTO dto){
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<DetailsOrderDTO> updateOrder(
            @PathVariable(name = "orderId") Integer orderId,
            @Valid @RequestBody RegisterOrderDTO dto){
        return ResponseEntity.ok(this.service.update(orderId,dto));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<DetailsOrderDTO> deleteOrder(
            @PathVariable(name = "orderId") Integer orderId){
        this.service.delete(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/order")
    public ResponseEntity<List<DetailsOrderDTO>> listOrder(){
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/order/paged")
    public ResponseEntity<Page<DetailsOrderDTO>> listOrderPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page, size)));
    }

}
