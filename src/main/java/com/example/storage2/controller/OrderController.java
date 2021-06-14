package com.example.storage2.controller;

import com.example.storage2.DAO.ItemDAO;
import com.example.storage2.DAO.OrderDAO;
import com.example.storage2.model.Orders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

  private final OrderDAO orderDAO;
  private final ItemDAO itemDAO;

  @Autowired
  public OrderController(ItemDAO itemDAO, OrderDAO orderDAO) {
    this.itemDAO = itemDAO;
    this.orderDAO = orderDAO;
  }


  //every 10 seconds to delete old orders
  @Scheduled(cron = "*/10 * * * * *")
  private void checkIfActual() {

    if (orderDAO.findAll().size() == 0) {
      return;
    }

    orderDAO.findAll().removeIf(e -> {
      LocalDateTime key = e.getLocalDateTime();

      if (key.getHour() == LocalDateTime.now().getHour()) {
        return (LocalDateTime.now().getMinute() - key.getMinute()) > 10;
      } else {
        int diff = 60 - key.getMinute();
        return (diff + key.getMinute()) > 10;
      }

    });
  }


  @GetMapping()
  public List<Orders> getAll() {
    return orderDAO.findAll();
  }

  @GetMapping("{id}")
  public Optional<Orders> getOrder(@PathVariable int id) {
    return orderDAO.findById(id);
  }

  @PostMapping
  public Orders saveOrder(@RequestBody @NonNull Orders orders) {

    orderDAO.save(orders);

    return orders;
  }


  // adding item with id {itemId} to order
  @PostMapping("{itemId}")
  public Orders saveOrder(@RequestBody @NonNull Orders orders, @PathVariable int itemId) {

    orders.getItems().add(itemDAO.findById(itemId).orElseThrow(NoSuchElementException::new));

    return orders;

  }


  @PutMapping("{id}")
  public Orders updateOrder(@PathVariable int id, @NonNull @RequestBody Orders orders) {

    orderDAO.update(orders);

    return orderDAO.findById(orders.getId()).orElseThrow(IllegalAccessError::new);

  }


  @DeleteMapping("{id}")
  public void delete(@PathVariable int id) {

    Orders orders = orderDAO.findById(id).orElseThrow(NoSuchElementException::new);

    orderDAO.delete(orders);

  }

}
