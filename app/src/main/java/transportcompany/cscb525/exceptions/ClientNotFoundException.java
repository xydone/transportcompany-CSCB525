package transportcompany.cscb525.exceptions;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(long id) {
    super("Klientut #" + id + " ne be otkrit.");
  }
}
