package transportcompany.cscb525.exceptions;

public class InsufficientVehicleCapacityException extends RuntimeException {
  public InsufficientVehicleCapacityException() {
    super("Prevoznoto sredstvo nqma dostatuchno capacity za tozi prevoz.");
  }
}