package pt.com.sibs.order.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select o from Order o where " +
            " o.item.id = :itemId and " +
            " o.orderStatus in :orderStatus " +
            " order by o.creationDate ASC")
    List<Order> findByOrderStatusAndItem(@Param("itemId") Integer itemId,
                                         @Param("orderStatus")List<OrderStatus> orderStatus);

    List<Order> findByOrderStatusEquals(OrderStatus orderStatus);

}
