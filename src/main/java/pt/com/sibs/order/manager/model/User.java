package pt.com.sibs.order.manager.model;


import lombok.*;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
@Entity
@Table(name = "user_orderer")
public class User implements PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name="user_name", nullable = false)
    private String name;
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User ");
        sb.append("\nUser Number: " +this.getId().toString());
        sb.append("\nUser Name: " + this.getName());
        sb.append("\nUser Email: "+this.getEmail());
        return sb.toString();
    }

}
