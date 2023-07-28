package pt.com.sibs.order.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.com.sibs.order.manager.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
