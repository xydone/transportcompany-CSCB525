package transportcompany.cscb525.entity;

import jakarta.persistence.*;
import transportcompany.cscb525.types.TransportType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "transports")
public class Transport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false)

  private String departure;
  @Column(nullable = false)

  private String arrival;
  @Column(nullable = false)

  private long price;
  @Column(nullable = false)

  private TransportType transport;
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

  @Column(nullable = false)
  private boolean status;

  @Column
  @OneToMany
  private List<Client> clients;

    // hibernate no arg
    protected Transport() {
    }

  public Transport(String departure, String arrival, long price, TransportType transport, int size, Company company,
      Employee employee, boolean status, List<Client> clients) {
    this.departure = departure;
    this.arrival = arrival;
    this.price = price;
    this.transport = transport;
    this.size = size;
    this.company = company;
    this.employee = employee;
    this.status = status;
    this.clients = clients;
  }
}