package com.florin.restaurant.order_item;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.florin.restaurant.model.Menu;
import com.florin.restaurant.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_items", schema = "public")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @Transient
    public double getSubtotal(){
        return this.menu.getPrice() * quantity;
    }

@Transient
    public double getSubtotal(double discount){
        return (this.menu.getPrice() * quantity)-(this.menu.getPrice()*0.2);
    }


    @Transient
    public double getSubtotalWithDiscount(){
        return this.menu.getPrice() * quantity - getDiscountPerMenu();
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public double getDiscountPerMenu() {
        return ((this.menu.getPrice() * quantity) * 0.2);
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public double getDiscountForTotalPrice() {
        return getSubtotal()*0.2;
}
}
