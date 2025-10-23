package transportcompany.cscb525.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.exceptions.EmployeeNotFoundException;
import transportcompany.cscb525.types.License;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {
  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private SessionFactory sessionFactory;

    @Mock
  private Query<Employee> query;

  @BeforeEach
  void setUp() {
    when(sessionFactory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    SessionFactoryUtil.setSessionFactory(sessionFactory);
  }

    @Test
  void createEmployee() {
    Employee employee = new Employee("Example employee", License.A, new Company("Example company"), 1000);

    EmployeeDao.createEmployee(employee);

    verify(session).persist(employee);
    verify(transaction).commit();
  }

  @Test
  void updateEmployee() {
    Employee employee = new Employee("Example employee", License.A, new Company("Example company"), 1000);

    EmployeeDao.updateEmployee(employee);

    verify(session).merge(employee);
    verify(transaction).commit();
  }

  @Test
  void deleteEmployee() {
    Employee employee = new Employee("Example employee", License.A, new Company("Example company"), 1000);

    EmployeeDao.deleteEmployee(employee);

    verify(session).remove(employee);
    verify(transaction).commit();
  }

  @Test
  void getEmployeeById_found() {
    Employee employee = new Employee("Example employee", License.A, new Company("Example company"), 1000);
    when(session.find(Employee.class, 1L)).thenReturn(employee);

    Employee result = EmployeeDao.getEmployeeById(1L);

    assertEquals(employee, result);
    verify(transaction).commit();
  }

  @Test
  void getEmployeeById_notFound() {
    when(session.find(Employee.class, 1L)).thenReturn(null);

    assertThrows(EmployeeNotFoundException.class, () -> EmployeeDao.getEmployeeById(1L));
  }

  @Test
  void getEmployees() {
    List<Employee> employees = List.of(
        new Employee("Example employee", License.A, new Company("Example company"), 1000),
        new Employee("Example employee", License.A, new Company("Example company"), 1000)
    );

    when(session.createQuery("Select c From Employee c", Employee.class)).thenReturn(query);
    when(query.getResultList()).thenReturn(employees);

    List<Employee> result = EmployeeDao.getEmployees();

    assertEquals(employees, result);
    verify(transaction).commit();
  }

  @Test
  void getEmployeesForCompany() {
    Company company = new Company("Example company");
    List<Employee> employees = List.of(new Employee("Example employee", License.A, company, 1000));
    when(session.createQuery(
        "SELECT e FROM Employee e WHERE e.company = :company ORDER BY e.id ASC",
        Employee.class)).thenReturn(query);
    when(query.setParameter("company", company)).thenReturn(query);
    when(query.getResultList()).thenReturn(employees);

    List<Employee> result = EmployeeDao.getEmployeesForCompany(company);

    assertEquals(employees, result);
    verify(transaction).commit();
  }

  @Test
  void filterEmployees() {
    List<Employee> employees = List.of(new Employee("Example employee", License.A, new Company("Example company"), 1000));

    when(session.createQuery(anyString(), eq(Employee.class))).thenReturn(query);
    when(query.setParameter(eq("minSalary"), any())).thenReturn(query);
    when(query.setParameter(eq("license"), any())).thenReturn(query);
    when(query.getResultList()).thenReturn(employees);

    List<Employee> result = EmployeeDao.filterEmployees(3000L, License.B, true);

    assertEquals(employees, result);
    verify(query).setParameter("minSalary", 3000L);
    verify(query).setParameter("license", License.B);
    verify(transaction).commit();
  }


}
