package pt.com.sibs.order.manager.controller.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.interfaces.SimpleDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegisterOrderDTO implements SimpleDTO {
    @NotNull(message = "you should to inform a item identifier.")
    @Min(value = 1, message = "you should to inform a valid item identifier.")
    private Integer item;

    @NotNull(message = "you should to inform number of itens.")
    @Min(value = 1, message = "you should to inform a valid number of items.")
    private Integer quantity;

    @NotBlank(message = "You should to inform an orderer email.")
    @Email(message= "You should to inform a valid orderer email.")
    private String userEmail;

}
