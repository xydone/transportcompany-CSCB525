package transportcompany.cscb525.service;

import java.time.LocalDate;

import transportcompany.cscb525.entity.Client;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.entity.Transport;
import transportcompany.cscb525.entity.Vehicle;
import transportcompany.cscb525.exceptions.InsufficientVehicleCapacityException;
import transportcompany.cscb525.exceptions.InvalidEmployeeLicenseException;
import transportcompany.cscb525.exceptions.InvalidVehicleTypeException;
import transportcompany.cscb525.types.TransportType;

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
}
