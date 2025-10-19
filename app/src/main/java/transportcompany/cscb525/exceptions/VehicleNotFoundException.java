package transportcompany.cscb525.exceptions;

public class VehicleNotFoundException extends RuntimeException {
  public VehicleNotFoundException(long id) {
    super("Prevoznoto sredstvo #" + id + " ne be otkrito.");
  }
}
