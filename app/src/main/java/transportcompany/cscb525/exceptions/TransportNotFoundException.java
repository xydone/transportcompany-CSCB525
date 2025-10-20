package transportcompany.cscb525.exceptions;

public class TransportNotFoundException extends RuntimeException {
  public TransportNotFoundException(long id) {
    super("Transporta #" + id + " ne be otkrit.");
  }
}
