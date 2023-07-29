package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.core.email.EmailService;
import pt.com.sibs.order.manager.core.events.EventPublisher;
import pt.com.sibs.order.manager.core.events.LoggingEvent;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.core.events.StockMovementEvent;
import pt.com.sibs.order.manager.model.*;
import pt.com.sibs.order.manager.repository.OrderRepository;
import pt.com.sibs.order.manager.repository.OrderStockMovementUsageRepository;
import pt.com.sibs.order.manager.repository.StockItemCounterRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StockOrderDispatcherService {
    private StockItemCounterRepository repository;
    private OrderRepository orderRepository;
    private OrderStockMovementUsageRepository orderSctockMovementUsage;
    private EmailService emailService;

    private EventPublisher publisher;

    @EventListener(value = {StockMovementEvent.class})
    @Transactional
    public void stockMovementEventDispatcher(StockMovementEvent event){
         Item item = (Item)event.getSource();
         List<Order> pendentOrders = this.getPendentOrders(item);
         this.dispatchOrders(pendentOrders);
    }


    public void dispatchOrders(List<Order> pendentOrders) {
        pendentOrders
        .stream()
        .forEach(orderToResolve->{
            this.dispatchPedingOrder(orderToResolve);
            if(orderToResolve.getOrderStatus().equals(OrderStatus.COMPLETED)){
                publisher.publishLogEvent("update",orderToResolve);
            }
        });



    }

    public void dispatchPedingOrder(Order orderToResolve){
        List<StockItemCounter> counters = this.repository.findByItemIdEquals(orderToResolve.getItem().getId());
        counters.stream().filter(c->c.getQuantity()>0).forEach(c->{
            Integer remainTocomplete = orderToResolve.getRemainUnitsToFullFill();
            if(remainTocomplete <= 0){
                return;
            }else{
                Integer quantity = remainTocomplete>c.getQuantity()?
                        c.getQuantity():remainTocomplete;
                createOrderMovement(orderToResolve, c, quantity);
            }
        });

        if(orderToResolve.getMovements()!=null &&
                !orderToResolve.getMovements().isEmpty()){
            this.orderSctockMovementUsage.saveAll(orderToResolve.getMovements());
            this.orderSctockMovementUsage.flush();
        }

        if(orderToResolve.getRemainUnitsToFullFill()==0){
            orderToResolve.setOrderStatus(OrderStatus.COMPLETED);
            this.emailService.sendEmailOrderCompleted(orderToResolve);
        }else{
            orderToResolve.setOrderStatus(OrderStatus.WAITING_STOCK);
        }
    }

    @Transactional
    private void createOrderMovement(Order order, StockItemCounter counter, Integer quantity){
        OrderStockMovementUsage usage = new OrderStockMovementUsage();
        StockMovement mov = new StockMovement();
        mov.setId(counter.getId());

        usage.setOrder(order);
        usage.setStockMovement(mov);
        usage.setQuantity(quantity);

        order.getMovements().add(usage);
        counter.setQuantity(counter.getQuantity()-quantity);
    }

    public List<Order> getPendentOrders(Item item){
        ArrayList<OrderStatus> status = new ArrayList<>();
        status.add(OrderStatus.WAITING_STOCK);
        return this.orderRepository.findByOrderStatusAndItem(item.getId(),status);
    }

}
