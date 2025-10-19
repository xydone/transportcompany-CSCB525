package transportcompany.cscb525.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(long id) {
    super("Slujitel #" + id + " ne be otkrit.");
  }
}
