package com.example.storage2.DAO;

import com.example.storage2.config.HibernateSessionFactory;
import com.example.storage2.model.Orders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderDAO {

  public void save(Orders orders) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      Transaction transaction = session.beginTransaction();
      session.save(orders);
      transaction.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    }

  }

  @Autowired
  public List<Orders> findAll() {

    String findAll = "FROM Orders ";
    List<Orders> orders = new ArrayList<>();
    SessionFactory instance = HibernateSessionFactory.instance();

    try (Session session = instance.openSession();) {

      Query<Orders> query = session.createQuery(findAll, Orders.class);
      orders = query.list();
      session.close();
      if (orders.size() == 0) {
        return Collections.emptyList();
      }

      return orders;
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  public Optional<Orders> findById(int id) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      return Optional.ofNullable(session.get(Orders.class, id));
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public void update(Orders orders) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      Transaction transaction = session.beginTransaction();
      session.update(orders);
      transaction.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    }

  }

  public void delete(Orders orders) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try ( Session session = instance.openSession();) {
      Transaction transaction = session.beginTransaction();

      session.delete(orders);
      transaction.commit();

    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }


}




