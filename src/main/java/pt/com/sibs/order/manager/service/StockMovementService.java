package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.core.events.annotations.LoggedAction;
import pt.com.sibs.order.manager.core.events.annotations.StockOperation;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.StockMovement;
import pt.com.sibs.order.manager.repository.StockMovementRepository;
import pt.com.sibs.order.manager.validators.StockMovementValidator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockMovementService {
    private StockMovementRepository repository;
    private ItemService itemService;
    private StockMovementValidator validator;


    @Transactional
    @StockOperation(MovementType.INPUT)
    @LoggedAction(action = "create")
    public DetailsStockMovementDTO addStockItem(RegisterStockMovementDTO dto){
        StockMovement movement = dto.parse();
        movement.setItem(this.itemService.getById(dto.getItem()));
        this.repository.save(movement);
        this.repository.flush();
        return new DetailsStockMovementDTO().build(movement);
    }

    @Transactional
    @LoggedAction(action = "update")
    @StockOperation(MovementType.INPUT)
    public DetailsStockMovementDTO update(Integer id, RegisterStockMovementDTO dto){
        StockMovement stockMovement = this.getById(id);
        this.validator.validateUpdate(stockMovement, dto);

        Item item = this.itemService.getById(dto.getItem());
        stockMovement.setItem(item);
        stockMovement.setQuantity(dto.getQuantity());
        stockMovement.setCreationDate(LocalDateTime.now());

        return new DetailsStockMovementDTO().build(this.repository.save(stockMovement));
    }

    @Transactional
    @LoggedAction(action = "delete")
    public StockMovement delete(Integer id){
        StockMovement stockMovement = this.getById(id);
        this.validator.validateDelete(stockMovement);
        this.repository.delete(stockMovement);
        return stockMovement;
    }

    public List<DetailsStockMovementDTO> getAll(){
        return this.repository
                .findAll()
                .stream()
                .map(i -> new DetailsStockMovementDTO().build(i))
                .collect(Collectors.toList());
    }

    public Page<DetailsStockMovementDTO> getAllPaged(Pageable page){
        return this.repository
                .findAll(page)
                .map(i -> new DetailsStockMovementDTO().build(i));
    }

    public StockMovement getById(Integer id){
        return this.repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Stock Movement not found!"));
    }

}
