package pt.com.sibs.order.manager.controller.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.interfaces.BuildableDTO;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.controller.dto.interfaces.SimpleDTO;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.StockMovement;
import pt.com.sibs.order.manager.model.enums.MovementType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.awt.print.Pageable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegisterStockMovementDTO implements ParseableDTO<StockMovement> {
    @NotNull(message = "you should to inform a item identifier.")
    @Min(value = 1, message = "you should to inform a valid item identifier.")
    private Integer item;
    @NotNull(message = "you should to inform number of itens.")
    @Min(value = 1, message = "you should to inform a valid number of items.")
    private Integer quantity;


    @Override
    public StockMovement parse() {
        return new StockMovement(null,
                LocalDateTime.now() ,
                Item.builder().id(this.getItem()).build(),
                this.getQuantity(),
                MovementType.INPUT,
                null);
    }
}
