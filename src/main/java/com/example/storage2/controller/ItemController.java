package com.example.storage2.controller;


import com.example.storage2.DAO.ItemDAO;
import com.example.storage2.DAO.OrderDAO;
import com.example.storage2.model.Item;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *
 * Create another rest controller which will return the requested Item at the lowest price
 * (the number of items must be reduced in storage). If there are not enough items,
 * return all available
 *
 * */
@RestController
@RequestMapping("item")
public class ItemController {

  private final OrderDAO orderDAO;
  private final ItemDAO itemDao;

  @Autowired
  public ItemController(ItemDAO itemDAO, OrderDAO orderDAO) {
    this.itemDao = itemDAO;
    this.orderDAO = orderDAO;
  }


  @GetMapping()
  public List<Item> getAll() {
    return itemDao.findAll();
  }

  @GetMapping("{id}")
  public Optional<Item> getItem(@PathVariable int id) {
    return itemDao.findById(id);
  }

  @GetMapping("{itemName}")
  public Item getItemAtTheLowestPrice(@PathVariable String itemName) {

    Item min = itemDao.findAll().stream()
        .filter(e -> e.getName() != null && e.getName().equals(itemName))
        .min(Comparator.comparing(Item::getPrice)).orElseThrow(NoSuchElementException::new);

    return min;
  }

  @PostMapping
  public Item saveItem(@RequestBody @NonNull Item Item) {
    itemDao.save(Item);
    return Item;
  }


  @PutMapping("{id}")
  public Item updateItem(@PathVariable int id, @NonNull @RequestBody Item Item) {

    itemDao.update(Item);

    return itemDao.findById(Item.getId()).orElseThrow(IllegalAccessError::new);
  }


  @DeleteMapping("{id}")
  public void delete(@PathVariable int id) {

    Item Item = itemDao.findById(id).orElseThrow(NoSuchElementException::new);

    itemDao.delete(Item);

  }


}
