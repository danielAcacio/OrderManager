package pt.com.sibs.order.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.model.interfaces.StockItemHandler;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name ="stock_order")
public class Order implements StockItemHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
    @Column(name="creation_date", nullable = false)
    private LocalDateTime creationDate;
    @OneToMany(mappedBy = "order")
    private List<OrderStockMovementUsage> movements;


    public Integer getRemainUnitsToFullFill(){
        AtomicReference<Integer> alredyInserted = new AtomicReference<>(0);
        movements.stream().forEach(movement-> alredyInserted.updateAndGet(v -> v + movement.getQuantity()));

        return  this.quantity - alredyInserted.get();

    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("YYYY/MM/dd hh:mm:ss");

        StringBuilder sb = new StringBuilder("Order");
        sb.append("\nOrder Number:" +this.getId().toString());
        sb.append("\nOrderer Email: " +this.getUser().getEmail());
        sb.append("\nItem: "+this.getItem().getName());
        sb.append("\nQuantity: "+ this.getQuantity());
        sb.append("\nOrder Status: "+ this.getOrderStatus().name());
        sb.append("\nCreated at: " + fmt.format(this.getCreationDate()));

        return sb.toString();
    }

}
