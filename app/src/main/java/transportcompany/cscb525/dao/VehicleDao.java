package transportcompany.cscb525.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.validation.Valid;
import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Vehicle;
import transportcompany.cscb525.exceptions.VehicleNotFoundException;
import transportcompany.cscb525.util.InputUtil;

public class VehicleDao {
  public static void createVehicle(@Valid Vehicle vehicle) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.persist(vehicle);
      transaction.commit();
    }
  }

  public static void updateVehicle(@Valid Vehicle vehicle) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.merge(vehicle);
      transaction.commit();
    }
  }

  public static void deleteVehicle(@Valid Vehicle vehicle) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(vehicle);
      transaction.commit();
    }
  }

  public static Vehicle getVehicleById(long id) {
    Vehicle vehicle;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      vehicle = session.find(Vehicle.class, id);
      transaction.commit();
    }
    if (vehicle == null) {
      throw new VehicleNotFoundException(id);
    }
    return vehicle;
  }

  public static List<Vehicle> getVehicles() {
    List<Vehicle> companies;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      companies = session
          .createQuery("Select c From Vehicle c", Vehicle.class)
          .getResultList();
      transaction.commit();
    }
    return companies;
  }

  public static List<Vehicle> getVehiclesForCompany(Company company) {
    List<Vehicle> vehicles;
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();

      vehicles = session.createQuery(
          "SELECT v FROM Vehicle v WHERE v.company = :company ORDER BY v.id ASC",
          Vehicle.class)
          .setParameter("company", company)
          .getResultList();

      transaction.commit();
    }
    return vehicles;
  }

  public static Vehicle selectVehicle(Scanner scanner, List<Vehicle> vehicleList) {
    if (vehicleList.isEmpty()) {
      System.out.println("Kompaniqta nqma prevozni sredstva.");
      return null;
    }

    System.out.println("List na prevozni sredstva:");
    for (int i = 1; i <= vehicleList.size(); i++) {
      Vehicle v = vehicleList.get(i - 1);
      System.out.println(
          "#" + i + "; Type: " + v.getType() + " ; Capacity: " + v.getCapacity());
    }
    System.out.println("Natisnete 0 za da izlezete");

    int vehicleNum = InputUtil.readInt(scanner, "Napishete nomerut na prevoznoto stredstvo: ");
    if (vehicleNum == 0 || vehicleNum > vehicleList.size()) {
      return null;
    }

    return vehicleList.get(vehicleNum - 1);
  }

}
