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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Column(nullable = false)
    private License license;

    public void setLicense(License license) {
        this.license = license;
    }

    public License getLicense() {
        return license;
    }

    @ManyToOne()
    @JoinColumn(name = "company_id")
    private Company company;

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    @Column(nullable = false)
    private long salary;

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public long getSalary() {
        return salary;
    }

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
