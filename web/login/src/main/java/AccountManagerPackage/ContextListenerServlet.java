package AccountManagerPackage;

import javax.servlet.*;
import javax.servlet.annotation.*;

@WebListener
public class ContextListenerServlet implements ServletContextListener {
  public void contextInitialized(ServletContextEvent sce) {
    AccountManager accMan = new AccountManager();
    ServletContext sc = sce.getServletContext();
    sc.setAttribute(AccountManager.NAME, accMan);
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }
}
