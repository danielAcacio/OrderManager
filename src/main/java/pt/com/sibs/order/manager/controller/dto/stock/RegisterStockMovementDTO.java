package pt.com.sibs.order.manager.controller.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.interfaces.SimpleDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegisterStockMovementDTO implements SimpleDTO {
    @NotNull(message = "you should to inform a item identifier.")
    @Min(value = 1, message = "you should to inform a valid item identifier.")
    private Integer item;
    @NotNull(message = "you should to inform number of itens.")
    @Min(value = 1, message = "you should to inform a valid number of items.")
    private Integer quantity;
}
