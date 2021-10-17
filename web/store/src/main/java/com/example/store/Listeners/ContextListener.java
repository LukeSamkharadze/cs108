package com.example.store.Listeners;

import com.example.store.Data.StoreDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    try {
      servletContextEvent.getServletContext().setAttribute("products", new StoreDao().getProds());
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {
  }
}
