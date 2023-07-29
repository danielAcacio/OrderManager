package pt.com.sibs.order.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Entity
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Subselect("with output_items as ( " +
        " select stock_movement_id,  sum(u.quantity) as quantity " +
        " from order_stock_usage u " +
        " group by stock_movement_id) " +
        " select sm.stock_movement_id, sm.item_id, sm.quantity - coalesce(oi.quantity,0) as item_stock " +
        " from stock_movement sm  " +
        " left join output_items oi on oi.stock_movement_id=sm.stock_movement_id " +
        " where sm.quantity - coalesce(oi.quantity,0)>0" +
        " group by 1,2,3 ")
public class StockItemCounter {

    @Id
    @Column(name = "stock_movement_id")
    private Integer id;

    @Column(name="item_id")
    private Integer itemId;

    @Column(name="item_stock")
    private Integer quantity;
}
