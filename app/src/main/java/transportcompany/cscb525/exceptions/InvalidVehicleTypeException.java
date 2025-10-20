package transportcompany.cscb525.exceptions;

import transportcompany.cscb525.types.TransportType;

public class InvalidVehicleTypeException extends RuntimeException {
  public InvalidVehicleTypeException(TransportType transportType, TransportType vehicleType) {
    super("Prevoznoto sredstvo " + vehicleType + " e nesuvmestimo s prevoza " + transportType + ".");
  }
}