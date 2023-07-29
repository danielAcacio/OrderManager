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
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {

    private ItemService service;

    @Operation(description = "Create a new Item")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Item created."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PostMapping("/item")
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO dto){
        return ResponseEntity.ok(this.service.create(dto));
    }

    @Operation(description = "Update a Item")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Item updated."),
            @ApiResponse( responseCode = "404", description = "Item not found."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PutMapping("/item/{itemId}")
    public ResponseEntity<ItemDTO> updateItem(
            @PathVariable(name = "itemId") Integer itemId,
            @Valid @RequestBody ItemDTO dto){
        return ResponseEntity.ok(this.service.update(itemId,dto));
    }

    @Operation(description = "Delete a Item")
    @ApiResponses({
            @ApiResponse( responseCode = "204", description = "Item deleted."),
            @ApiResponse( responseCode = "404", description = "Item not found."),
            @ApiResponse( responseCode = "409", description = "Item is used in orders or stock movement."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ItemDTO> deleteItem(
            @PathVariable(name = "itemId") Integer itemId){
        this.service.delete(itemId);
        return ResponseEntity.noContent().build();
    }


    @Operation(description = "Get all Items")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Items."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/item")
    public ResponseEntity<List<ItemDTO>> listItem(){
        return ResponseEntity.ok(this.service.getAll());
    }

    @Operation(description = "Get all Items Paged")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Items Paged."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/item/paged")
    public ResponseEntity<Page<ItemDTO>> listItemPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page, size)));
    }


}
