package com.example.storage2.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Item implements Cloneable {

  @Id
  private int id;
  private String name;
  private double price;

  @Override
  public Object clone() throws CloneNotSupportedException {
    Item clone = new Item();
    clone.setPrice(getPrice());
    clone.setId(getId());
    clone.setName(getName());
    return clone;
  }


}
