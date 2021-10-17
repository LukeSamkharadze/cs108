package AccountManagerPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "logInServlet", value = "/logger")
public class ServletLogIn extends HttpServlet {

  protected void doGet(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException, ServletException {
    doPost(servletRequest, response);
  }

  protected void doPost(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");
    var userName = servletRequest.getParameter("userName");
    var password = servletRequest.getParameter("password");
    if (userName == null || password == null) {
      servletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, response);
      return;
    }
    var accountManager = (AccountManager) getServletContext().getAttribute(AccountManager.NAME);
    var illegalInput = accountManager.isIllegalInput(userName, password);
    if (!(illegalInput)) {
      servletRequest.setAttribute("userName", userName);
      servletRequest.getRequestDispatcher("/welcome.jsp").forward(servletRequest, response);
      return;
    }
    servletRequest.getRequestDispatcher("/wrong.jsp").forward(servletRequest, response);
  }
}
