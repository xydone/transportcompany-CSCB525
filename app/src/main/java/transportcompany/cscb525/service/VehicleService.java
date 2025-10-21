package transportcompany.cscb525.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import transportcompany.cscb525.entity.Vehicle;
import transportcompany.cscb525.types.TransportType;
import transportcompany.cscb525.util.InputUtil;

public class VehicleService {
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

  public static void saveToFile(List<Vehicle> list, String path) {
    try (FileWriter writer = new FileWriter(path)) {
      for (Vehicle vehicle : list) {
        String line = String.format("Vehicle #%d; %s; %d %s; Company \"%s\"",
            vehicle.getId(),
            vehicle.getType(),
            vehicle.getCapacity(),
            vehicle.getType() == TransportType.TRUCK ? "kg" : "people",
            vehicle.getCompany().getName());
        writer.write(line);

        writer.write("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
