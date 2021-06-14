package com.example.storage2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Orders implements Cloneable {

  @Id
  private int id;
  private double price;
  private int quantity;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "orders_item",
      joinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")
  )
  @JsonIgnoreProperties(value= {"items"})
  @JsonIgnore
  private List<Item> items;
  @JsonFormat (shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime localDateTime;

  public Orders() {
    this.localDateTime = LocalDateTime.now();
  }

  public Orders(int id, double price, int quantity, List<Item> items, LocalDateTime localDateTime) {
    this.id = id;
    if (price > 0) {
      this.price = price;
    } else {
      this.price = 1.0;
    }
    this.price = price;
    if (quantity > 0) {
      this.quantity = quantity;
    } else {
      this.quantity = 1;
    }
    this.items = items;
    this.localDateTime = localDateTime;
  }

  public void setQuantity(int quantity) {
    if (quantity > 0) {
      this.quantity = quantity;
    } else {
      this.quantity = 1;
    }
  }

  @Override
  public Object clone() throws CloneNotSupportedException {

    Orders clone = new Orders();
    clone.setQuantity(getQuantity());
    List<Item> cloned = new ArrayList<>(this.getItems());
    clone.setItems(cloned);
    return clone;
  }
}
