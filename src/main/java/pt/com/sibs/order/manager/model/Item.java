package pt.com.sibs.order.manager.model;

import lombok.*;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@Builder
@Entity @Table(name="item")

public class Item implements PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_id")
    private Integer id;
    @Column(name = "item_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "item")
    private List<Order> orderList;
    @OneToMany(mappedBy = "item")
    private List<StockMovement> movementList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Item ");
        sb.append("\nItem Number: " +this.getId().toString());
        sb.append("\nItem Name: " + this.getName());
        return sb.toString();
    }
}
