package transportcompany.cscb525.util;

import java.util.Scanner;

public class InputUtil {

  public static int readInt(Scanner scanner, String prompt) {
    while (true) {
      if (prompt != null && !prompt.isBlank()) {
        System.out.print(prompt + '\n');
      }

      if (scanner.hasNextInt()) {
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
      } else {
        System.out.println("Trqbva da bude int.");
        scanner.nextLine();
      }
    }
  }

  public static long readLong(Scanner scanner, String prompt) {
    while (true) {
      if (prompt != null && !prompt.isBlank()) {
        System.out.print(prompt + '\n');
      }

      if (scanner.hasNextInt()) {
        long value = scanner.nextInt();
        scanner.nextLine();
        return value;
      } else {
        System.out.println("Trqbva da bude long.");
        scanner.nextLine();
      }
    }
  }

  public static String readString(Scanner scanner, String prompt) {
    while (true) {
      if (prompt != null && !prompt.isBlank()) {
        System.out.print(prompt + '\n');
      }

      String input = scanner.nextLine().trim();

      if (!input.isEmpty()) {
        return input;
      } else {
        System.out.println("Trqbva da bude tekst (ne moje da e empty).");
      }
    }
  }
}
