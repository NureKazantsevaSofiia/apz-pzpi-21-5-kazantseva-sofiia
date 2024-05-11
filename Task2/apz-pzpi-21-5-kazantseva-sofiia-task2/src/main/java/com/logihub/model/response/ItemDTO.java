package com.logihub.model.response;

import com.logihub.model.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    private Long itemId;

    private String name;

    private Double weight;

    private Double price;

    private Long amount;

    private Double totalWeight;

    private Double totalPrice;

    public ItemDTO(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.weight = item.getWeight();
        this.price = item.getPrice();
        this.amount = item.getAmount();
        this.totalWeight = item.getTotalWeight();
        this.totalPrice = item.getTotalPrice();
    }
}
