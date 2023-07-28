package pt.com.sibs.order.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity @Table(name="item")
public class Item implements PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_id")
    private Integer id;
    @Column(name = "item_name", nullable = false)
    private String name;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Item ");
        sb.append("\nItem Number: " +this.getId().toString());
        sb.append("\nItem Name: " + this.getName());
        return sb.toString();
    }
}
