package pt.com.sibs.order.manager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.repository.ItemRepository;
import pt.com.sibs.order.manager.validators.ItemValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository repository;
    @Mock
    private ItemValidator validator;
    @Captor
    ArgumentCaptor<Item> iCaptor;

    @InjectMocks
    private ItemService service;


    @Test
    void create() {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(new Item(1, "item", new ArrayList<>(), new ArrayList<>()));
        ItemDTO dtoInitial= new ItemDTO(null, "item");
        ItemDTO processedDTO = this.service.create(dtoInitial);
        Assertions.assertEquals(1, processedDTO.getId());
        Assertions.assertEquals(dtoInitial.getName(), processedDTO.getName());

    }

    @Test
    void update() {
        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(
                Optional.of(new Item(1, "item", new ArrayList<>(), new ArrayList<>())));

        Mockito.when(this.repository.save(Mockito.any())).thenReturn(new Item(1, "item", new ArrayList<>(), new ArrayList<>()));
        ItemDTO dtoInitial= new ItemDTO(1, "item");
        ItemDTO processedDTO = this.service.update(1,dtoInitial);
        Assertions.assertEquals(1, processedDTO.getId());
        Assertions.assertEquals(dtoInitial.getName(), processedDTO.getName());
    }

    @Test
    void delete() {
        Item item = new Item(1, "item", new ArrayList<>(), new ArrayList<>());
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(item));
        this.service.delete(1);
        Mockito.verify(this.repository).delete(iCaptor.capture());
        Item  i = iCaptor.getValue();
        Assertions.assertEquals(item.getId(), i.getId());
        Assertions.assertEquals(item.getName(), i.getName());
    }

    @Test
    void deleteError() {
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()-> this.service.delete(1));
    }

    @Test
    void getAll() {
        List<Item> itemList = new ArrayList<>();
        Item item = new Item(1, "item", new ArrayList<>(), new ArrayList<>());
        itemList.add(item);
        Mockito.when(this.repository.findAll()).thenReturn(itemList);

        List<ItemDTO> itemDTOList = this.service.getAll();
        Assertions.assertEquals(item.getId(), itemDTOList.get(0).getId());
        Assertions.assertEquals(item.getName(),itemDTOList.get(0).getName());
    }

    @Test
    void getAllPaged() {
        List<Item> itemList = new ArrayList<>();
        Item item = new Item(1, "item", new ArrayList<>(), new ArrayList<>());
        itemList.add(item);
        Mockito.when(this.repository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(itemList));

        Page<ItemDTO> itemDTOList = this.service.getAllPaged(PageRequest.of(1,10));
        Assertions.assertEquals(item.getId(), itemDTOList.getContent().get(0).getId());
        Assertions.assertEquals(item.getName(),itemDTOList.getContent().get(0).getName());

    }

    @Test
    void getById() {
    }
}