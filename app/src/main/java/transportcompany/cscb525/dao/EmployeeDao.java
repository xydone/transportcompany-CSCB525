package transportcompany.cscb525.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.validation.Valid;
import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.exceptions.EmployeeNotFoundException;
import transportcompany.cscb525.util.InputUtil;

public class EmployeeDao {
  public static void createEmployee(@Valid Employee employee) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.persist(employee);
      transaction.commit();
    }
  }

  public static void updateEmployee(@Valid Employee employee) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.merge(employee);
      transaction.commit();
    }
  }

  public static void deleteEmployee(@Valid Employee employee) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(employee);
      transaction.commit();
    }
  }

  public static Employee getEmployeeById(long id) {
    Employee employee;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      employee = session.find(Employee.class, id);
      transaction.commit();
    }
    if (employee == null) {
      throw new EmployeeNotFoundException(id);
    }
    return employee;
  }

  public static List<Employee> getEmployees() {
    List<Employee> companies;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      companies = session
          .createQuery("Select c From Employee c", Employee.class)
          .getResultList();
      transaction.commit();
    }
    return companies;
  }

  public static List<Employee> getEmployeesForCompany(Company company) {
    List<Employee> employees;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      employees = session.createQuery(
          "SELECT e FROM Employee e WHERE e.company = :company ORDER BY e.id ASC",
          Employee.class)
          .setParameter("company", company)
          .getResultList();

      transaction.commit();
    }
    return employees;
  }

  public static Employee selectEmployee(Scanner scanner, List<Employee> employeeList) {
    if (employeeList.isEmpty()) {
      System.out.println("Kompaniqta nqma slujiteli.");
      return null;
    }

    System.out.println("List na slujiteli:");
    for (int i = 1; i <= employeeList.size(); i++) {
      Employee e = employeeList.get(i - 1);
      System.out.println("#" + i + ": " + e.getName() + " ; License " + e.getLicense() + " ; " + e.getSalary() + "$");
    }
    System.out.println("Natisnete 0 za da izlezete");

    int employeeNum = InputUtil.readInt(scanner, "Napishete nomerut na slujitelqt: ");
    if (employeeNum == 0 || employeeNum > employeeList.size()) {
      return null;
    }

    return employeeList.get(employeeNum - 1);
  }
}
