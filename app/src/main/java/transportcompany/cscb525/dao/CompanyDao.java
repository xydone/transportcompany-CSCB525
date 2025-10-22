package transportcompany.cscb525.dao;

import java.util.List;

import org.hibernate.*;
import org.hibernate.query.Query;

import jakarta.validation.Valid;
import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.dto.CompanyDto;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.exceptions.CompanyNotFoundException;

public class CompanyDao {

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

  /**
   * all params are nullable
   */
  public static List<CompanyDto> filterCompanies(String name, Long profits, boolean isSortAsc) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      // WHERE 1=1 is needed as further queries have to assume they can either be the
      // first or not the first filter applied
      StringBuilder hql = new StringBuilder("""
              SELECT new transportcompany.cscb525.dto.CompanyDto(
                  c,
                  COALESCE((SELECT SUM(t.price) FROM Transport t WHERE t.company = c), 0)
                  - COALESCE((SELECT SUM(e.salary) FROM Employee e WHERE e.company = c), 0),
                  (SELECT COUNT(t) FROM Transport t WHERE t.company = c)
              )
              FROM Company c
              WHERE 1 = 1
          """);

      if (name != null && !name.isBlank()) {
        hql.append(" AND LOWER(c.name) LIKE LOWER(:nameFilter)");
      }

      if (profits != null) {
        hql.append("""
            AND (
            COALESCE((SELECT SUM(t.price) FROM Transport t WHERE t.company = c), 0)
            - COALESCE((SELECT SUM(e.salary) FROM Employee e WHERE e.company = c), 0)
            ) >= :minProfit
            """);
      }

      // order by profit descending
      hql.append("""
              ORDER BY (
                  COALESCE((SELECT SUM(t.price) FROM Transport t WHERE t.company = c), 0)
                  - COALESCE((SELECT SUM(e.salary) FROM Employee e WHERE e.company = c), 0)
              )
          """);

      hql.append(isSortAsc ? "ASC" : "DESC");

      Query<CompanyDto> query = session.createQuery(hql.toString(), CompanyDto.class);

      if (name != null && !name.isBlank()) {
        query.setParameter("nameFilter", "%" + name + "%");
      }
      if (profits != null) {
        query.setParameter("minProfit", profits);
      }

      List<CompanyDto> results = query.getResultList();
      transaction.commit();

      return results;
    }
  }

}
