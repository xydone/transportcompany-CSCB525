package transportcompany.cscb525.entity;

import jakarta.persistence.*;
import transportcompany.cscb525.types.License;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(nullable = false)
    private License license;

    @ManyToOne()
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private long salary;

    // hibernate no arg
    protected Employee() {
    }

    public Employee(String name, License license, Company company, long salary) {
        this.name = name;
        this.license = license;
        this.company = company;
        this.salary = salary;
    }
}
