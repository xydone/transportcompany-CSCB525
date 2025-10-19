package transportcompany.cscb525.dao;

import java.util.List;

import org.hibernate.*;

import jakarta.validation.Valid;
import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.exceptions.CompanyNotFoundException;

public class CompanyDao {
  public static void save(@Valid Company company) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      session.persist(company);
      transaction.commit();
    }
  }

  public static void createCompany(@Valid Company company) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.persist(company);
      transaction.commit();
    }
  }

  public static Company getCompanyById(long id) {
    Company company;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      company = session.find(Company.class, id);
      transaction.commit();
    }
    if (company == null) {
      throw new CompanyNotFoundException(id);
    }
    return company;
  }

  public static List<Company> getCompanies() {
    List<Company> companies;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      companies = session
          .createQuery("Select c From Company c", Company.class)
          .getResultList();
      transaction.commit();
    }
    return companies;
  }

  public static void updateCompany(Company company) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.merge(company);
      transaction.commit();
    }
  }

  public static void deleteCompany(Company company) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(company);
      transaction.commit();
    }
  }
}
