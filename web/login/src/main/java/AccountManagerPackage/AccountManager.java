package AccountManagerPackage;

import java.util.*;

public class AccountManager {
  public static final String NAME = "AccountManagerPackage";
  private final HashMap<String, String> accounts;

  public AccountManager() {
    accounts = new HashMap<>();
  }

  public boolean isIllegalInput(String userName, String password) {
    return accounts.isEmpty() ||
           (!(accounts.containsKey(userName))) ||
           (!(accounts.get(userName).equals(password)));
  }

  public void addAccount(String userName, String password) {
    if (accounts.containsKey(userName)) return;
    accounts.put(userName, password);
  }

  public boolean doesAlreadyHaveAccount(String userName) {
    return accounts.containsKey(userName);
  }
}
