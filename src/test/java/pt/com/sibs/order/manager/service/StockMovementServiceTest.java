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
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.StockMovement;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.repository.StockMovementRepository;
import pt.com.sibs.order.manager.validators.StockMovementValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StockMovementServiceTest {
    @Mock
    private StockMovementRepository repository;
    @Mock
    private ItemService itemService;
    @Mock
    private StockMovementValidator validator;
    @Captor
    ArgumentCaptor<StockMovement> sCaptor;

    @InjectMocks
    StockMovementService service;

    @Test
    void addStockItem() {
        Item item = new Item(1,"item", new ArrayList<>(), new ArrayList<>());
        StockMovement registeredMovement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());

        RegisterStockMovementDTO rDto = new RegisterStockMovementDTO(1,100);
        Mockito.when(this.itemService.getById(Mockito.any())).thenReturn(item);
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(registeredMovement);
        DetailsStockMovementDTO dto = this.service.addStockItem(rDto);
        Assertions.assertEquals(dto.getItem().getId(), registeredMovement.getItem().getId());
        Assertions.assertEquals(dto.getQuantity(), registeredMovement.getQuantity());
        Assertions.assertEquals(dto.getMovementType(), registeredMovement.getMovementType());
    }

    @Test
    void update() {
        Item item = new Item(1,"item", new ArrayList<>(), new ArrayList<>());
        StockMovement registeredMovement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());

        RegisterStockMovementDTO rDto = new RegisterStockMovementDTO(1,10);
        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.of(registeredMovement));
        Mockito.when(this.itemService.getById(Mockito.any())).thenReturn(item);
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(registeredMovement);
        DetailsStockMovementDTO dto = this.service.update(1,rDto);
        Assertions.assertEquals(dto.getId(), registeredMovement.getId());
        Assertions.assertEquals(dto.getItem().getId(), registeredMovement.getItem().getId());
        Assertions.assertEquals(dto.getQuantity(), registeredMovement.getQuantity());
        Assertions.assertEquals(dto.getMovementType(), registeredMovement.getMovementType());
    }

    @Test
    void delete() {
        StockMovement movement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());


        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(movement));
        this.service.delete(1);
        Mockito.verify(this.repository).delete(sCaptor.capture());
        StockMovement s = sCaptor.getValue();
        Assertions.assertEquals(movement.getId(), s.getId());
        Assertions.assertEquals(movement.getQuantity(),s.getQuantity());
        Assertions.assertEquals(movement.getItem().getId(), s.getItem().getId());
        Assertions.assertEquals(movement.getCreationDate(), s.getCreationDate());
        Assertions.assertEquals(movement.getMovementType(), s.getMovementType());
    }

    @Test
    void deleteError() {
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()-> this.service.delete(1));
    }

    @Test
    void getAll() {
        List<StockMovement> movementList = new ArrayList<>();
        StockMovement movement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());
        movementList.add(movement);
        Mockito.when(this.repository.findAll()).thenReturn(movementList);

        List<DetailsStockMovementDTO> detailsDTOList = this.service.getAll();
        Assertions.assertEquals(movement.getId(), detailsDTOList.get(0).getId());
        Assertions.assertEquals(movement.getQuantity(),detailsDTOList.get(0).getQuantity());
        Assertions.assertEquals(movement.getItem().getId(), detailsDTOList.get(0).getItem().getId());
        Assertions.assertEquals(movement.getCreationDate(), detailsDTOList.get(0).getCreationDate());
        Assertions.assertEquals(movement.getMovementType(), detailsDTOList.get(0).getMovementType());
    }


    @Test
    void getAllPaged() {
        List<StockMovement> movementList = new ArrayList<>();
        StockMovement movement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());
        movementList.add(movement);
        Mockito.when(this.repository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(movementList));

        Page<DetailsStockMovementDTO> detailsDTOList = this.service.getAllPaged(PageRequest.of(0,10));
        Assertions.assertEquals(movement.getId(), detailsDTOList.getContent().get(0).getId());
        Assertions.assertEquals(movement.getQuantity(),detailsDTOList.getContent().get(0).getQuantity());
        Assertions.assertEquals(movement.getItem().getId(), detailsDTOList.getContent().get(0).getItem().getId());
        Assertions.assertEquals(movement.getCreationDate(), detailsDTOList.getContent().get(0).getCreationDate());
        Assertions.assertEquals(movement.getMovementType(), detailsDTOList.getContent().get(0).getMovementType());
    }

}