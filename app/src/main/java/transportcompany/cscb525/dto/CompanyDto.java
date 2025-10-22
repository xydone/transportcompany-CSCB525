package transportcompany.cscb525.dto;

import java.util.List;

import transportcompany.cscb525.entity.Company;

public class CompanyDto {
  private Company company;
  private long profit;
  private long transportCount;

  public CompanyDto(Company company, long profit, long transportCount) {
    this.company = company;
    this.profit = profit;
    this.transportCount = transportCount;
  }

  public Company getCompany() {
    return company;
  }

  public long getProfit() {
    return profit;
  }

  public long getTransportCount() {
    return transportCount;
  }

  /**
   * printTotals determines if the total sum of companies, transports, profits is
   * printed at the end. Made toggleable as it might not be desireable behaviour
   * everywhere
   */
  public static void printList(List<CompanyDto> list, boolean printTotals) {
    long totalCompanies = list.size();
    long totalTransports = 0;
    long totalProfits = 0;
    for (int i = 1; i <= list.size(); i++) {
      CompanyDto dto = list.get(i - 1);
      long transportCount = dto.getTransportCount();
      totalTransports += transportCount;
      long profit = dto.getProfit();
      totalProfits += profit;

      System.out
          .println("#" + i + ": " + dto.getCompany().getName() + ", " + transportCount + " prevoza, "
              + dto.getProfit() + "$");
    }
    if (printTotals) {
      System.out
          .println("TOTAL: " + totalCompanies + " kompanii, " + totalTransports + " prevoza, " + totalProfits + "$");
    }
  }

}
