package transportcompany.cscb525.service;

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
