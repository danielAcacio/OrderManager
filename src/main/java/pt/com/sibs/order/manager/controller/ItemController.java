package pt.com.sibs.order.manager.controller;

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

    @PostMapping("/item")
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO dto){
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<ItemDTO> updateItem(
            @PathVariable(name = "itemId") Integer itemId,
            @Valid @RequestBody ItemDTO dto){
        return ResponseEntity.ok(this.service.update(itemId,dto));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ItemDTO> deleteItem(
            @PathVariable(name = "itemId") Integer itemId){
        this.service.delete(itemId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/item")
    public ResponseEntity<List<ItemDTO>> listItem(){
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/item/paged")
    public ResponseEntity<Page<ItemDTO>> listItemPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page, size)));
    }


}
