package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.core.events.annotations.LoggedAction;
import pt.com.sibs.order.manager.core.events.annotations.StockOperation;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.repository.OrderRepository;

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


    @Transactional
    @StockOperation(MovementType.OUTPUT)
    @LoggedAction(action = "create")
    public DetailsOrderDTO create(RegisterOrderDTO dto){
        User user = this.userService.getByEmail(dto.getUserEmail());
        Item item = this.itemService.getById(dto.getItem());

        Order order = new Order(null,user, item, dto.getQuantity(), OrderStatus.WAITING_STOCK, LocalDateTime.now(), new ArrayList<>());
        return new DetailsOrderDTO().build(this.repository.save(order));
    }

    @Transactional
    @LoggedAction(action = "update")
    public DetailsOrderDTO update(Integer orderId, RegisterOrderDTO dto){
        Order order = this.getById(orderId);
        User user = this.userService.getByEmail(dto.getUserEmail());
        Item item = this.itemService.getById(dto.getItem());

        order.setItem(item);
        order.setUser(user);
        order.setQuantity(dto.getQuantity());

        return new DetailsOrderDTO().build(this.repository.save(order));
    }

    @Transactional
    @LoggedAction(action = "delete")
    public Order delete(Integer orderId){
        Order order = this.getById(orderId);
        this.repository.delete(order);
        return order;
    }

    public List<Order> getPendentOrders(Item item){
        ArrayList<OrderStatus> status = new ArrayList<>();
        status.add(OrderStatus.WAITING_STOCK);
        return this.repository.findByOrderStatusAndItem(item.getId(),status);
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
