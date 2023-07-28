package pt.com.sibs.order.manager.controller.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.model.Order;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DetailsOrderDTO implements ParseableDTO<Order> {

    private Integer id;
    @NotNull
    @Valid
    private UserDTO orderer;
    @NotNull
    @Valid
    private ItemDTO item;
    @NotNull
    @Min(value = 1)
    private Integer quantity;
    @NotNull
    private OrderStatus orderStatus;

    private Integer currentCompletion;

    private LocalDateTime creationDate;

    @Override
    public Order parse() {
        return new Order(this.getId(),
                            this.getOrderer().parse(),
                            this.getItem().parse(),
                            this.getQuantity(),
                            this.getOrderStatus(),
                            this.getCreationDate(),
                            new ArrayList<>());
    }

    @Override
    public DetailsOrderDTO build(Order order) {
        this.setId(order.getId());
        this.setItem(new ItemDTO().build(order.getItem()));
        this.setQuantity(order.getQuantity());
        this.setOrderer(new UserDTO().build(order.getUser()));
        this.setOrderStatus(order.getOrderStatus());
        this.setCreationDate(order.getCreationDate());
        this.setCurrentCompletion(this.getQuantity()- order.getRemainUnitsToFullFill());
        return this;
    }
}
