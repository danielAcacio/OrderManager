package pt.com.sibs.order.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.com.sibs.order.manager.model.StockItemCounter;

import java.util.List;

@Repository
public interface StockItemCounterRepository extends JpaRepository<StockItemCounter, Integer> {

    List<StockItemCounter> findByItemIdEquals(Integer id);
}
