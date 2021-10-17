package AccountManagerPackage;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.*;

@WebServlet(name = "registrationServlet", value = "/registration")
public class ServletRegistration extends HttpServlet {
  protected void doGet(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException, ServletException {
    doPost(servletRequest, response);
  }

  protected void doPost(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");
    var userName = servletRequest.getParameter("userName");
    var password = servletRequest.getParameter("password");
    servletRequest.setAttribute("userName", userName); // set attribute
    var accountManager = (AccountManager) getServletContext().getAttribute(AccountManager.NAME);
    var hasAccount = accountManager.doesAlreadyHaveAccount(userName);
    if (!(hasAccount)) {
      accountManager.addAccount(userName, password);
      servletRequest.getRequestDispatcher("/welcome.jsp").forward(servletRequest, response);
      return;
    }
    servletRequest.getRequestDispatcher("/used.jsp").forward(servletRequest, response);
  }
}
