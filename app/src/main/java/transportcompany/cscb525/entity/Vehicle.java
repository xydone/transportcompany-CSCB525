package transportcompany.cscb525.entity;

import jakarta.persistence.*;
import transportcompany.cscb525.types.License;
import transportcompany.cscb525.types.TransportType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {
  @ManyToOne()
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "company_id")
  private Company company;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private License license;

  public void setLicense(License license) {
    this.license = license;
  }

  public License getLicense() {
    return license;
  }

  @Column(nullable = false)
  private TransportType type;

  public TransportType getType() {
    return type;
  }

  public void setType(TransportType type) {
    this.type = type;
  }

  @Column(nullable = false)
  private int capacity;

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  // hibernate no arg
  protected Vehicle() {
  }

  public Vehicle(Company company, License license, TransportType transportType, int capacity) {
    this.company = company;
    this.license = license;
    this.type = transportType;
    this.capacity = capacity;
  }
}
