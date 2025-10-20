package transportcompany.cscb525.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import transportcompany.cscb525.entity.Client;
import transportcompany.cscb525.util.InputUtil;

public class ClientService {
  public static Optional<Client> selectClient(Scanner scanner, List<Client> clientList) {
    if (clientList.isEmpty()) {
      System.out.println("Kompaniqta nqma clienti.");
      return Optional.empty();
    }

    System.out.println("List na klienti:");
    for (int i = 1; i <= clientList.size(); i++) {
      Client c = clientList.get(i - 1);
      System.out.println("#" + i + ": " + c.getName());
    }
    System.out.println("Natisnete 0 za da izlezete");

    int clientNum = InputUtil.readInt(scanner, "Napishete nomerut na klientut: ");
    if (clientNum == 0 || clientNum > clientList.size()) {
      return Optional.empty();
    }

    return Optional.of(clientList.get(clientNum - 1));
  }
}
