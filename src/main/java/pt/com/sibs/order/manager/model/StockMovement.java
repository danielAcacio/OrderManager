package pt.com.sibs.order.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.interfaces.StockItemHandler;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity @Table(name = "stock_movement")
public class StockMovement implements StockItemHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_movement_id")
    private Integer id;
    @Column(name="creation_date", nullable = false)
    private LocalDateTime creationDate;
    @JoinColumn(name="item_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @OneToMany(mappedBy = "stockMovement", fetch = FetchType.LAZY)
    private List<OrderStockMovementUsage> usages;


    public Integer getItensAvaiable(){
        AtomicReference<Integer> itens = new AtomicReference<>(this.quantity);
        usages.forEach(u-> itens.updateAndGet(v -> v - quantity));

        return itens.get();
    }


    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("YYYY/MM/dd hh:mm:ss");
        StringBuilder sb = new StringBuilder("StockMovement");
        sb.append("\nStock Movement Number:" +this.getId().toString());
        sb.append("\nItem:"+this.getItem().getName());
        sb.append("\nQuantity: "+ this.getQuantity());
        sb.append("\nMovement Type: "+ this.getMovementType().name());
        sb.append("\nCreated at: " + fmt.format(this.creationDate));
        return sb.toString();
    }


}
