package pt.com.sibs.order.manager.controller.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.interfaces.BuildableDTO;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.model.Item;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ItemDTO implements ParseableDTO<Item>, BuildableDTO<Item> {
    private Integer id;
    @NotBlank(message = "You should to send an item name!")
    private String name;

    @Override
    public Item parse() {
        return Item
                .builder()
                .id(this.getId())
                .name(this.getName())
                .build();
    }

    @Override
    public ItemDTO build(Item item) {
        this.setId(item.getId());
        this.setName(item.getName());
        return this;
    }
}
