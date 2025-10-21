package transportcompany.cscb525.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import transportcompany.cscb525.entity.Employee;
import transportcompany.cscb525.util.InputUtil;

public class EmployeeService {
  public static Employee selectEmployee(Scanner scanner, List<Employee> employeeList) {
    if (employeeList.isEmpty()) {
      System.out.println("Kompaniqta nqma slujiteli.");
      return null;
    }

    System.out.println("List na slujiteli:");
    printEmployees(employeeList);
    System.out.println("Natisnete 0 za da izlezete");

    int employeeNum = InputUtil.readInt(scanner, "Napishete nomerut na slujitelqt: ");
    if (employeeNum == 0 || employeeNum > employeeList.size()) {
      return null;
    }

    return employeeList.get(employeeNum - 1);
  }

  public static void printEmployees(List<Employee> employeeList) {
    for (int i = 1; i <= employeeList.size(); i++) {
      Employee e = employeeList.get(i - 1);
      System.out.println("#" + i + ": " + e.getName() + " ; License " + e.getLicense() + " ; " + e.getSalary() + "$");
    }
  }

  public static void saveToFile(List<Employee> list, String path) {
    try (FileWriter writer = new FileWriter(path)) {
      for (Employee employee : list) {
        String line = String.format("Employee #%d; %s; License %s; %d$; Company \"%s\"",
            employee.getId(),
            employee.getName(),
            employee.getLicense().name(),
            employee.getSalary(),
            employee.getCompany().getName());
        writer.write(line);

        writer.write("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
