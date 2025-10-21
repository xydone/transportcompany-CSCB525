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

  public long getId() {
    return id;
  }

  @Column(nullable = false)
  private LocalDate departure;

  public LocalDate getDeparture() {
    return departure;
  }

  @Column(nullable = false)
  private LocalDate arrival;

  public LocalDate getArrival() {
    return arrival;
  }

  @Column(name = "start_point", nullable = false)
  @NotBlank(message = "Nachalnata tochka e zaduljitelna.")
  private String startPoint;

  public String getStartPoint() {
    return startPoint;
  }

  @Column(name = "end_point", nullable = false)
  @NotBlank(message = "Destinaciqta e zaduljitelna.")
  private String endPoint;

  public String getEndPoint() {
    return endPoint;
  }

  @Column(nullable = false)
  @PositiveOrZero(message = "Cenata trqbva da bude polojitelno chislo ili nula")
  private long price;

  public long getPrice() {
    return price;
  }

  @Column(nullable = false)
  private TransportType transport;

  public TransportType getTransportType() {
    return transport;
  }

  // in the case that this is a cargo transport, this represents the weight of the
  // cargo
  // in the case that this is a passenger transport, this represents the amount of
  // people in the vehicle
  @Column(nullable = false)
  private int size;

  public int getSize() {
    return size;
  }

  @ManyToOne()
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "company_id")
  private Company company;

  public Company getCompany() {
    return company;
  }

  @ManyToOne()
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  public Employee getEmployee() {
    return employee;
  }

  // true - paid
  // false - not paid
  @Column(nullable = false)
  private boolean status;

  public boolean getStatus() {
    return status;
  }

  public void flipStatus() {
    this.status = !this.status;
  }

  @ManyToOne
  @JoinColumn(name = "vehicle_id")
  private Vehicle vehicle;

  public Vehicle getvehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public Client getclient() {
    return client;
  }

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