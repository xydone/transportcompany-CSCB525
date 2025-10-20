package transportcompany.cscb525.dao;

import jakarta.validation.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.hibernate.*;
import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Client;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.exceptions.ClientNotFoundException;
import transportcompany.cscb525.util.InputUtil;

public class ClientDao {

  public static void createClient(@Valid Client client) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.persist(client);
      transaction.commit();
    }
  }

  public static void updateClient(@Valid Client client) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.merge(client);
      transaction.commit();
    }
  }

  public static void deleteClient(@Valid Client client) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(client);
      transaction.commit();
    }
  }

  public static Client getClientById(long id) {
    Client client;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      client = session.find(Client.class, id);
      transaction.commit();
    }
    if (client == null) {
      throw new ClientNotFoundException(id);
    }
    return client;
  }

  public static List<Client> getClients() {
    List<Client> companies;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      companies = session
          .createQuery("Select c From Client c", Client.class)
          .getResultList();
      transaction.commit();
    }
    return companies;
  }

  public static List<Client> getClientsForCompany(Company company) {
    List<Client> clients;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      clients = session.createQuery(
          "SELECT c FROM Client c WHERE c.company = :company ORDER BY c.id ASC",
          Client.class)
          .setParameter("company", company)
          .getResultList();

      transaction.commit();
    }
    return clients;
  }

}
