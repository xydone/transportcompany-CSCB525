package transportcompany.cscb525.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.validation.Valid;
import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Transport;
import transportcompany.cscb525.exceptions.TransportNotFoundException;

public class TransportDao {
  public static void createTransport(@Valid Transport transport) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.persist(transport);
      transaction.commit();
    }
  }

  public static void updateTransport(@Valid Transport transport) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.merge(transport);
      transaction.commit();
    }
  }

  public static void deleteTransport(@Valid Transport transport) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(transport);
      transaction.commit();
    }
  }

  public static Transport getTransportById(long id) {
    Transport transport;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      transport = session.find(Transport.class, id);
      transaction.commit();
    }
    if (transport == null) {
      throw new TransportNotFoundException(id);
    }
    return transport;
  }

  public static List<Transport> getTransports() {
    List<Transport> companies;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      companies = session
          .createQuery("Select c From Transport c", Transport.class)
          .getResultList();
      transaction.commit();
    }
    return companies;
  }

  public static List<Transport> getTransportsForCompany(Company company) {
    List<Transport> transports;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      transports = session.createQuery(
          "SELECT t FROM Transport t WHERE t.company = :company ORDER BY t.id ASC",
          Transport.class)
          .setParameter("company", company)
          .getResultList();

      transaction.commit();
    }
    return transports;
  }

  /**
   * all params are nullable
   */
  public static List<Transport> filterTransports(String startPoint, String endPoint) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      // WHERE 1=1 is needed as further queries have to assume they can either be the
      // first or not the first filter applied
      StringBuilder hql = new StringBuilder("""
              FROM Transport t
              WHERE 1 = 1
          """);

      if (startPoint != null && !startPoint.isBlank()) {
        hql.append(" AND LOWER(t.startPoint) LIKE LOWER(:startPointFilter)");
      }
      if (endPoint != null && !endPoint.isBlank()) {
        hql.append(" AND LOWER(t.endPoint) LIKE LOWER(:endPointFilter)");
      }

      Query<Transport> query = session.createQuery(hql.toString(), Transport.class);

      if (startPoint != null && !startPoint.isBlank()) {
        query.setParameter("startPointFilter", "%" + startPoint + "%");
      }
      if (endPoint != null && !endPoint.isBlank()) {
        query.setParameter("endPointFilter", "%" + endPoint + "%");
      }

      List<Transport> results = query.getResultList();
      transaction.commit();

      return results;
    }
  }

}
