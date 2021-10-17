package com.example.store.Servlets;

import com.example.store.Data.StoreDao;
import com.example.store.Models.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "cartServlet", value = "/cart")
public class CartServlet extends HttpServlet {
  private ShoppingCart getCart(HttpServletRequest request) {
    var httpSession = request.getSession(false);
    if (httpSession == null) {
      httpSession = request.getSession(true);
    }

    var shoppingCart = (ShoppingCart) httpSession.getAttribute("cart");

    if (shoppingCart == null) {
      shoppingCart = new ShoppingCart();
      httpSession.setAttribute("cart", shoppingCart);
    }

    return shoppingCart;
  }

  private void updateCart(HttpServletRequest servletRequest, HttpServletResponse servletResponse, ShoppingCart shoppingCart) throws SQLException {
    var storeDao = new StoreDao();
    shoppingCart.resetCart();
    var parameterNames = servletRequest.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      var productId = parameterNames.nextElement();
      try {
        var count = Integer.parseInt(servletRequest.getParameter(productId));
        if (count == 0)
          continue;
        shoppingCart.addWithCount(storeDao.getProd(productId), count);
      } catch (NumberFormatException ignored) {
      }
    }
  }

  public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
    var shoppingCart = getCart(servletRequest);
    servletRequest.setAttribute("cart", shoppingCart);
    servletRequest.getRequestDispatcher("WEB-INF/cart.jsp").forward(servletRequest, servletResponse);
  }

  public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
    var cart = getCart(servletRequest);
    var id = servletRequest.getParameter("productId");

    try {
      if (id == null)
        updateCart(servletRequest, servletResponse, cart);
      else {
        var storeDao = new StoreDao();
        cart.addSingle(storeDao.getProd(id));
      }
    } catch (SQLException ignored) {
    }

    servletRequest.setAttribute("cart", cart);
    servletRequest.getSession().setAttribute("cart", cart);
    servletRequest.getRequestDispatcher("WEB-INF/cart.jsp").forward(servletRequest, servletResponse);
  }
}
