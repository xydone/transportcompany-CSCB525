package transportcompany.cscb525.dto;

import java.util.List;

import transportcompany.cscb525.entity.Company;

public class CompanyDto {
  private Company company;
  private long profit;

  public CompanyDto(Company company, long profit) {
    this.company = company;
    this.profit = profit;
  }

  public Company getCompany() {
    return company;
  }

  public long getProfit() {
    return profit;
  }

  public static void printList(List<CompanyDto> list) {
    for (int i = 1; i <= list.size(); i++) {
      CompanyDto c = list.get(i - 1);
      System.out
          .println("#" + i + ": " + c.getCompany().getName() + ", "
              + c.getProfit() + "$");
    }
  }

}
