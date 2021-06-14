package com.example.storage2.DAO;

import com.example.storage2.config.HibernateSessionFactory;
import com.example.storage2.model.Item;
import com.example.storage2.model.Orders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ItemDAO {


  public void save(Item item) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      Transaction transaction = session.beginTransaction();
      session.save(item);
      transaction.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    }

  }

  public List<Item> findAll() {

    String findAll = "FROM " + Item.class.getSimpleName();
    List<Item> items = new ArrayList<>();
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {

      Query<Item> query = session.createQuery(findAll, Item.class);
      items = query.list();
      return items;
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  public Optional<Item> findById(int id) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      return Optional.ofNullable(session.get(Item.class, id));
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public void update(Item item) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      Transaction transaction = session.beginTransaction();
      session.update(item);
      transaction.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    }

  }

  public void delete(Item item) {
    SessionFactory instance = HibernateSessionFactory.instance();
    try (
        Session session = instance.openSession();
    ) {
      Transaction transaction = session.beginTransaction();

      session.delete(item);
      transaction.commit();

    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }


}
