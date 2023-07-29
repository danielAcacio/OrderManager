package pt.com.sibs.order.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.sibs.order.manager.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailLike(String email);

    @Query("select u from User u where " +
            " u.id != :id and u.email = :email")
    List<User> findByIdAndEmail(@Param("id") Integer id,@Param("email") String email);
}
