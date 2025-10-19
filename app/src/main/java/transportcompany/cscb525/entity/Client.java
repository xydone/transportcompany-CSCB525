package transportcompany.cscb525.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String name;

  @ManyToOne()
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "company_id")
  private Company company;

  // hibernate no arg
  protected Client() {
  }

  public Client(String name, Company company) {
    this.name = name;
    this.company = company;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
