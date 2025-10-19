package transportcompany.cscb525.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
  private String title;
  private Map<Integer, String> options = new LinkedHashMap<>();

  public Menu(String title) {
    this.title = title;
  }

  public void addOption(int number, String description) {
    options.put(number, description);
  }

  public void print() {
    System.out.println("============= " + title + " =============");
    for (Map.Entry<Integer, String> entry : options.entrySet()) {
      System.out.println(entry.getKey() + ". " + entry.getValue());
    }
  }

  public int listen(Scanner scanner, String title) {
    print();
    return InputUtil.readInt(scanner, title);
  }
}
