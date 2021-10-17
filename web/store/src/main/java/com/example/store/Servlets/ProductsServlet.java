package com.example.store.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "productsServlet", value = "/products")
public class ProductsServlet extends HttpServlet {
  public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
    servletRequest.getRequestDispatcher("WEB-INF/products.jsp").forward(servletRequest, servletResponse);
  }
}
