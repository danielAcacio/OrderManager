package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.repository.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {
    private ItemRepository itemRepository;

    @Transactional
    public ItemDTO create(ItemDTO dto){
        Item item = dto.parse();
        return new ItemDTO().build(this.itemRepository.save(item));
    }

    @Transactional
    public ItemDTO update(Integer itemId, ItemDTO dto){
        Item item = this.getById(itemId);
        item.setName(dto.getName());
        return new ItemDTO().build(this.itemRepository.save(item));
    }

    @Transactional
    public Item delete(Integer itemId){
        Item item = this.getById(itemId);
        this.itemRepository.delete(item);
        return item;
    }

    public List<ItemDTO> getAll(){
        return this.itemRepository
                .findAll()
                .stream()
                .map(i -> new ItemDTO().build(i))
                .collect(Collectors.toList());
    }

    public Page<ItemDTO> getAllPaged(Pageable page){
        return this.itemRepository
                .findAll(page)
                .map(i -> new ItemDTO().build(i));
    }

    public Item getById(Integer id){
        return this.itemRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Item not found!"));
    }



}
