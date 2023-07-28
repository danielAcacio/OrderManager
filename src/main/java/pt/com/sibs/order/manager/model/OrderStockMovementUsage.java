package pt.com.sibs.order.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="order_stock_usage")
public class OrderStockMovementUsage implements PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_stock_usage_id")
    private Integer id;
    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @JoinColumn(name = "stock_movement_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private StockMovement stockMovement;
    @Column(name= "quantity")
    private Integer quantity;
}
