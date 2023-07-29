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
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.service.StockMovementService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class StockMovementController {

    private StockMovementService service;

    @Operation(description = "Create a new Stock Movement to a specific item")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Stock Movement Created."),
            @ApiResponse( responseCode = "404", description = "Item not found."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PostMapping("/stock/movement/add/items")
    public ResponseEntity<DetailsStockMovementDTO> inputStockUnits(@Valid @RequestBody RegisterStockMovementDTO dto){
        return ResponseEntity.ok(this.service.addStockItem(dto));
    }

    @Operation(description = "Update a Stock Movement")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Stock Movement updated."),
            @ApiResponse( responseCode = "404", description = "Item not found."),
            @ApiResponse( responseCode = "409", description = "Stock Movement has conflicts with already associated order."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PutMapping("/stock/movement/{movementId}")
    public ResponseEntity<DetailsStockMovementDTO> updateMovement(
            @PathVariable(name = "movementId") Integer orderId,
            @Valid @RequestBody RegisterStockMovementDTO dto){
        return ResponseEntity.ok(this.service.update(orderId, dto));
    }

    @Operation(description = "Delete a Stock Movement")
    @ApiResponses({
            @ApiResponse( responseCode = "204", description = "Stock Movement deleted."),
            @ApiResponse( responseCode = "404", description = "Stock Movement not found."),
            @ApiResponse( responseCode = "409", description = "Stock Movement has conflicts with already associated order."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @DeleteMapping("/stock/movement/{movementId}")
    public ResponseEntity<DetailsStockMovementDTO> deleteMovement(
            @PathVariable(name = "movementId") Integer orderId){
        this.service.delete(orderId);
        return ResponseEntity.noContent().build();
    }


    @Operation(description = "Get all Stock Movement")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Stock Movement."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/stock/movement")
    public ResponseEntity<List<DetailsStockMovementDTO>> listStockMovement(){
        return ResponseEntity.ok(this.service.getAll());
    }


    @Operation(description = "Get all Stock Movement Paged")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Stock Movement Paged."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/stock/movement/paged")
    public ResponseEntity<Page<DetailsStockMovementDTO>> listStockMovementPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page,size)));
    }

}
