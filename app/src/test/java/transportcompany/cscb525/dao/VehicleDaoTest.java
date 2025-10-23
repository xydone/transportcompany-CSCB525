package transportcompany.cscb525.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Vehicle;
import transportcompany.cscb525.exceptions.VehicleNotFoundException;
import transportcompany.cscb525.types.TransportType;

@ExtendWith(MockitoExtension.class)
class VehicleDaoTest {

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Query<Vehicle> query;

  @BeforeEach
  void setUp() {
    when(sessionFactory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    SessionFactoryUtil.setSessionFactory(sessionFactory);
  }

  private Company exampleCompany() {
    return new Company("Example company");
  }

  private Vehicle exampleVehicle(Company company) {
    return new Vehicle(company, TransportType.CAR, 10);
  }

  @Test
  void createVehicle() {
    Company company = exampleCompany();
    Vehicle vehicle = exampleVehicle(company);

    VehicleDao.createVehicle(vehicle);

    verify(session).persist(vehicle);
    verify(transaction).commit();
  }

  @Test
  void updateVehicle() {
    Company company = exampleCompany();
    Vehicle vehicle = exampleVehicle(company);

    VehicleDao.updateVehicle(vehicle);

    verify(session).merge(vehicle);
    verify(transaction).commit();
  }

  @Test
  void deleteVehicle() {
    Company company = exampleCompany();
    Vehicle vehicle = exampleVehicle(company);

    VehicleDao.deleteVehicle(vehicle);

    verify(session).remove(vehicle);
    verify(transaction).commit();
  }

  @Test
  void getVehicleById_found() {
    Company company = exampleCompany();
    Vehicle vehicle = exampleVehicle(company);
    when(session.find(Vehicle.class, 1L)).thenReturn(vehicle);

    Vehicle result = VehicleDao.getVehicleById(1L);

    assertEquals(vehicle, result);
    verify(transaction).commit();
  }

  @Test
  void getVehicleById_notFound() {
    when(session.find(Vehicle.class, 1L)).thenReturn(null);

    assertThrows(VehicleNotFoundException.class, () -> VehicleDao.getVehicleById(1L));
  }

  @Test
  void getVehicles() {
    Company company = exampleCompany();
    List<Vehicle> vehicles = List.of(exampleVehicle(company), exampleVehicle(company));

    when(session.createQuery("Select c From Vehicle c", Vehicle.class)).thenReturn(query);
    when(query.getResultList()).thenReturn(vehicles);

    List<Vehicle> result = VehicleDao.getVehicles();

    assertEquals(vehicles, result);
    verify(transaction).commit();
  }

  @Test
  void getVehiclesForCompany() {
    Company company = exampleCompany();
    List<Vehicle> vehicles = List.of(exampleVehicle(company));

    when(session.createQuery(
        "SELECT v FROM Vehicle v WHERE v.company = :company ORDER BY v.id ASC",
        Vehicle.class)).thenReturn(query);
    when(query.setParameter("company", company)).thenReturn(query);
    when(query.getResultList()).thenReturn(vehicles);

    List<Vehicle> result = VehicleDao.getVehiclesForCompany(company);

    assertEquals(vehicles, result);
    verify(transaction).commit();
  }
}
