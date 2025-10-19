package transportcompany.cscb525.exceptions;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException(long id) {
    super("Kompaniqta #" + id + " ne be otkrita.");
  }
}
