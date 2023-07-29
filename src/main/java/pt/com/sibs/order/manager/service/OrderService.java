package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.core.events.annotations.LoggedAction;
import pt.com.sibs.order.manager.core.events.annotations.StockOperation;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.repository.OrderRepository;
import pt.com.sibs.order.manager.repository.OrderStockMovementUsageRepository;
import pt.com.sibs.order.manager.validators.OrderValidator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository repository;
    private UserService userService;
    private ItemService itemService;
    private StockOrderDispatcherService orderDispatcherService;
    private OrderValidator orderValidator;

    @Transactional
    @LoggedAction(action = "create")
    public DetailsOrderDTO create(RegisterOrderDTO dto){
        Order order = dto.parse();
        order.setUser(this.userService.getByEmail(order.getUser().getEmail()));
        order.setItem(this.itemService.getById(order.getItem().getId()));
        this.repository.save(order);
        this.orderDispatcherService.dispatchPedingOrder(order);
        return new DetailsOrderDTO().build(order);
    }

    @Transactional
    @LoggedAction(action = "update")
    public DetailsOrderDTO update(Integer orderId, RegisterOrderDTO dto){
        Order order = this.getById(orderId);
        this.orderValidator.validateUpdate(order, dto);
        order.setUser(this.userService.getByEmail(dto.getUserEmail()));
        order.setItem(this.itemService.getById(dto.getItem()));
        order.setQuantity(dto.getQuantity());
        this.orderDispatcherService.dispatchPedingOrder(order);
        return new DetailsOrderDTO().build(this.repository.save(order));
    }

    @Transactional
    @LoggedAction(action = "delete")
    public Order delete(Integer orderId){
        Order order = this.getById(orderId);
        this.orderValidator.validateDelete(order);
        this.repository.delete(order);
        return order;
    }


    public List<DetailsOrderDTO> getAll(){
        return this.repository
                .findAll()
                .stream()
                .map(i -> new DetailsOrderDTO().build(i))
                .collect(Collectors.toList());
    }

    public Page<DetailsOrderDTO> getAllPaged(Pageable page){
        return this.repository
                .findAll(page)
                .map(i -> new DetailsOrderDTO().build(i));
    }


    public Order getById(Integer id){
        return this.repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order not found!"));
    }

}
