package pt.com.sibs.order.manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.service.StockMovementService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class StockMovementController {

    private StockMovementService service;

    @PostMapping("/stock/movement/add/items")
    public ResponseEntity<DetailsStockMovementDTO> inputStockUnits(@Valid @RequestBody RegisterStockMovementDTO dto){
        return ResponseEntity.ok(this.service.addStockItem(dto));
    }


    @PutMapping("/stock/movement/{movementId}")
    public ResponseEntity<DetailsStockMovementDTO> updateMovement(
            @PathVariable(name = "movementId") Integer orderId,
            @Valid @RequestBody RegisterStockMovementDTO dto){
        return ResponseEntity.ok(this.service.update(orderId, dto));
    }

    @DeleteMapping("/stock/movement/{movementId}")
    public ResponseEntity<DetailsStockMovementDTO> deleteMovement(
            @PathVariable(name = "movementId") Integer orderId){
        this.service.delete(orderId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/stock/movement")
    public ResponseEntity<List<DetailsStockMovementDTO>> listStockMovement(){
        return ResponseEntity.ok(this.service.getAll());
    }


    @GetMapping("/stock/movement/paged")
    public ResponseEntity<Page<DetailsStockMovementDTO>> listStockMovementPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page,size)));
    }

}
