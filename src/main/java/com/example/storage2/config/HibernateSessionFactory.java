package com.example.storage2.config;

import com.example.storage2.model.Item;
import com.example.storage2.model.Orders;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

  private static SessionFactory sessionFactory;

  private HibernateSessionFactory() {
  }

  public static SessionFactory instance() {
    if (sessionFactory == null) {

      try {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Orders.class);
        configuration.addAnnotatedClass(Item.class);

        StandardServiceRegistryBuilder settings = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(settings.build());
      } catch (HibernateException e) {
        e.printStackTrace();
      }
    }

    return sessionFactory;
  }
}


