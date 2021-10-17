package com.example.store.Servlets;

import com.example.store.Data.StoreDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "productServlet", value = "/product")
public class ProductServlet extends HttpServlet {
  public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
    try {
      var store = new StoreDao();
      var id = servletRequest.getParameter("id");
      var prod = store.getProd(id);

      servletRequest.setAttribute("product", prod);
      servletRequest.getRequestDispatcher("WEB-INF/product.jsp").forward(servletRequest, servletResponse);
    } catch (SQLException ignored) {
    }
  }
}
