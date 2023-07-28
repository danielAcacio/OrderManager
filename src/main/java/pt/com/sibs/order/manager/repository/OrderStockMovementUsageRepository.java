package pt.com.sibs.order.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.sibs.order.manager.model.OrderStockMovementUsage;

import java.util.List;

@Repository
public interface OrderStockMovementUsageRepository extends JpaRepository<OrderStockMovementUsage, Integer> {

    @Query(value = "select distinct o from OrderStockMovementUsage o " +
            " where o.order.id = :orderId ")
    List<OrderStockMovementUsage> getByOrderId(@Param("orderId") Integer orderId);

    @Query(value = "select distinct o from OrderStockMovementUsage o " +
            " where o.stockMovement.id = :movementId ")
    List<OrderStockMovementUsage> getByMovementId(@Param("movementId") Integer movementId);
}
