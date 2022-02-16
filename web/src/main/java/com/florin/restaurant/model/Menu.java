package com.florin.restaurant.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "menu", schema = "public")
@Getter
@Setter
public class Menu {

   @Id
   @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   @NotNull
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String url;
    @NotNull
    private Double price;

}
