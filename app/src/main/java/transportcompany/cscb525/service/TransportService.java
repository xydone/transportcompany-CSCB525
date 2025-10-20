package transportcompany.cscb525.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import transportcompany.cscb525.entity.Client;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.entity.Transport;
import transportcompany.cscb525.entity.Vehicle;
import transportcompany.cscb525.exceptions.InsufficientVehicleCapacityException;
import transportcompany.cscb525.exceptions.InvalidEmployeeLicenseException;
import transportcompany.cscb525.exceptions.InvalidVehicleTypeException;
import transportcompany.cscb525.types.TransportType;
import transportcompany.cscb525.util.InputUtil;

public class TransportService {
  public static Transport createTransport(LocalDate departure, LocalDate arrival,
      String startPoint, String endPoint,
      long price, TransportType transportType, int size,
      Company company, Employee employee,
      Vehicle vehicle, boolean status, Client client) {

    if (vehicle.getType() != transportType) {
      throw new InvalidVehicleTypeException(transportType, vehicle.getType());
    }

    if (!employee.hasLicenseFor(transportType)) {
      throw new InvalidEmployeeLicenseException();
    }

    if (vehicle.getCapacity() < size) {
      throw new InsufficientVehicleCapacityException();
    }

    Transport t = new Transport(departure, arrival, startPoint, endPoint, price,
        transportType, size, company, employee, status, client);
    t.setVehicle(vehicle);

    return t;
  }

  public static Transport selectTransport(Scanner scanner, List<Transport> transportList) {
    if (transportList.isEmpty()) {
      System.out.println("Kompaniqta nqma prevozi.");
      return null;
    }

    System.out.println("List na prevozi:");
    for (int i = 1; i <= transportList.size(); i++) {
      Transport t = transportList.get(i - 1);
      System.out.println("#" + i + ": " + t.getStartPoint() + " -> " + t.getEndPoint() + ", platen: " + t.getStatus());
    }
    System.out.println("Natisnete 0 za da izlezete");

    int transportNum = InputUtil.readInt(scanner, "Napishete nomerut na slujitelqt: ");
    if (transportNum == 0 || transportNum > transportList.size()) {
      return null;
    }

    return transportList.get(transportNum - 1);
  }
}
