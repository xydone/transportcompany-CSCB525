package transportcompany.cscb525.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import transportcompany.cscb525.types.TransportType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "transports")
public class Transport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false)
  private LocalDate departure;

  @Column(nullable = false)
  private LocalDate arrival;

  @Column(name = "start_point", nullable = false)
  @NotBlank(message = "Nachalnata tochka e zaduljitelna.")
  private String startPoint;

  @Column(name = "end_point", nullable = false)
  @NotBlank(message = "Destinaciqta e zaduljitelna.")
  private String endPoint;

  @Column(nullable = false)
  @PositiveOrZero(message = "Cenata trqbva da bude polojitelno chislo ili nula")
  private long price;

  @Column(nullable = false)
  private TransportType transport;

  // in the case that this is a cargo transport, this represents the weight of the
  // cargo
  // in the case that this is a passenger transport, this represents the amount of
  // people in the vehicle
  @Column(nullable = false)
  private int size;

  @ManyToOne()
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToOne()
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  // true - paid
  // false - not paid
  @Column(nullable = false)
  private boolean status;

  @ManyToOne
  @JoinColumn(name = "vehicle_id")
  private Vehicle vehicle;

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  // hibernate no arg
  protected Transport() {
  }

  public Transport(LocalDate departure, LocalDate arrival, String startPoint, String endPoint, long price,
      TransportType transport, int size,
      Company company,
      Employee employee, boolean status, Client client) {
    this.departure = departure;
    this.arrival = arrival;
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.price = price;
    this.transport = transport;
    this.size = size;
    this.company = company;
    this.employee = employee;
    this.status = status;
    this.client = client;
  }
}