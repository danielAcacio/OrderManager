package pt.com.sibs.order.manager.controller.dto.stock;

import lombok.*;
import pt.com.sibs.order.manager.controller.dto.interfaces.BuildableDTO;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.StockMovement;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DetailsStockMovementDTO implements ParseableDTO<StockMovement>, BuildableDTO<StockMovement> {

    private Integer id;
    @Valid
    private ItemDTO item;
    @NotNull
    @Min(value = 0)
    private Integer quantity;

    private MovementType movementType;

    private LocalDateTime creationDate;

    @Override
    public StockMovement parse() {
        return new StockMovement(this.getId(),
                                this.getCreationDate(),
                                this.getItem().parse(),
                                this.getQuantity(),
                                this.getMovementType(),
                                null);
    }

    @Override
    public DetailsStockMovementDTO build(StockMovement stockMovement) {
        this.setId(stockMovement.getId());
        this.setItem(new ItemDTO().build(stockMovement.getItem()));
        this.setQuantity(stockMovement.getQuantity());
        this.setMovementType(stockMovement.getMovementType());
        this.setCreationDate(stockMovement.getCreationDate());
        return this;
    }
}
