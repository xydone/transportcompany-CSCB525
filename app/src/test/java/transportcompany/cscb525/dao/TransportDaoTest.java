package transportcompany.cscb525.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Client;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.entity.Transport;
import transportcompany.cscb525.exceptions.TransportNotFoundException;
import transportcompany.cscb525.types.License;
import transportcompany.cscb525.types.TransportType;

@ExtendWith(MockitoExtension.class)
class TransportDaoTest {

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Query<Transport> query;

  @BeforeEach
  void setUp() {
    when(sessionFactory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    SessionFactoryUtil.setSessionFactory(sessionFactory);
  }

  private Company exampleCompany() {
    return new Company("Example company");
  }

  private Transport exampleTransport(Company company) {
    return new Transport(
        LocalDate.now(),
        LocalDate.now().plusDays(1),
        "Start",
        "End",
        1000L,
        TransportType.TRUCK,
        20,
        company,
        new Employee("Example employee", License.A, company, 1000),
        true,
        new Client("Example client", company));
  }

  @Test
  void createTransport() {
    Company company = exampleCompany();
    Transport transport = exampleTransport(company);

    TransportDao.createTransport(transport);

    verify(session).persist(transport);
    verify(transaction).commit();
  }

  @Test
  void updateTransport() {
    Company company = exampleCompany();
    Transport transport = exampleTransport(company);

    TransportDao.updateTransport(transport);

    verify(session).merge(transport);
    verify(transaction).commit();
  }

  @Test
  void deleteTransport() {
    Company company = exampleCompany();
    Transport transport = exampleTransport(company);

    TransportDao.deleteTransport(transport);

    verify(session).remove(transport);
    verify(transaction).commit();
  }

  @Test
  void getTransportById_found() {
    Company company = exampleCompany();
    Transport transport = exampleTransport(company);
    when(session.find(Transport.class, 1L)).thenReturn(transport);

    Transport result = TransportDao.getTransportById(1L);

    assertEquals(transport, result);
    verify(transaction).commit();
  }

  @Test
  void getTransportById_notFound() {
    when(session.find(Transport.class, 1L)).thenReturn(null);

    assertThrows(TransportNotFoundException.class, () -> TransportDao.getTransportById(1L));
  }

  @Test
  void getTransports() {
    Company company = exampleCompany();
    List<Transport> transports = List.of(
        exampleTransport(company),
        exampleTransport(company));

    when(session.createQuery("Select c From Transport c", Transport.class)).thenReturn(query);
    when(query.getResultList()).thenReturn(transports);

    List<Transport> result = TransportDao.getTransports();

    assertEquals(transports, result);
    verify(transaction).commit();
  }

  @Test
  void getTransportsForCompany() {
    Company company = exampleCompany();
    List<Transport> transports = List.of(exampleTransport(company));

    when(session.createQuery(
        "SELECT t FROM Transport t WHERE t.company = :company ORDER BY t.id ASC",
        Transport.class)).thenReturn(query);
    when(query.setParameter("company", company)).thenReturn(query);
    when(query.getResultList()).thenReturn(transports);

    List<Transport> result = TransportDao.getTransportsForCompany(company);

    assertEquals(transports, result);
    verify(transaction).commit();
  }

  @Test
  void filterTransports() {
    List<Transport> transports = List.of(exampleTransport(exampleCompany()));

    when(session.createQuery(anyString(), eq(Transport.class))).thenReturn(query);
    when(query.setParameter(eq("startPointFilter"), anyString())).thenReturn(query);
    when(query.setParameter(eq("endPointFilter"), anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(transports);

    List<Transport> result = TransportDao.filterTransports("Start", "End");

    assertEquals(transports, result);
    verify(query).setParameter("startPointFilter", "%Start%");
    verify(query).setParameter("endPointFilter", "%End%");
    verify(transaction).commit();
  }

  
}
