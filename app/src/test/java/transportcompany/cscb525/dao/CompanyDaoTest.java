package transportcompany.cscb525.dao;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.dto.CompanyDto;
import transportcompany.cscb525.entity.Company;

@ExtendWith(MockitoExtension.class)
public class CompanyDaoTest {
  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Query<CompanyDto> query;

  @BeforeEach
  void setUp() {
    when(sessionFactory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    SessionFactoryUtil.setSessionFactory(sessionFactory);
  }

  @Test
  void createCompany() {
    Company company = new Company("Example company");

    CompanyDao.createCompany(company);

    verify(session).persist(company);
    verify(transaction).commit();
  }

  @Test
  void updateCompany() {
    Company company = new Company("Example company");

    CompanyDao.updateCompany(company);

    verify(session).merge(company);
    verify(transaction).commit();
  }

  @Test
  void deleteCompany() {
    Company company = new Company("Example company");

    CompanyDao.deleteCompany(company);

    verify(session).remove(company);
    verify(transaction).commit();
  }

  @Test
  void filterCompanies() {
    List<CompanyDto> expectedResults = new ArrayList<>();

    when(session.createQuery(anyString(), eq(CompanyDto.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(expectedResults);

    List<CompanyDto> actualResults = CompanyDao.filterCompanies(null, null, true);

    verify(session).createQuery(argThat(hql -> !hql.contains(":nameFilter") &&
        !hql.contains(":minProfit") &&
        hql.contains("ASC")), eq(CompanyDto.class));

    verify(query, never()).setParameter(anyString(), any());

    verify(query).getResultList();
    verify(transaction).commit();
    assertSame(expectedResults, actualResults);
  }

  @Test
  void filterCompanies_everythingOn() {
    String nameFilter = "Example";
    Long minProfit = 1000L;
    boolean isSortAsc = false;
    List<CompanyDto> expectedResults = new ArrayList<>();

    when(session.createQuery(anyString(), eq(CompanyDto.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(expectedResults);

    List<CompanyDto> actualResults = CompanyDao.filterCompanies(nameFilter, minProfit, isSortAsc);

    verify(session).createQuery(argThat(hql -> hql.contains(":nameFilter") &&
        hql.contains(":minProfit") &&
        hql.contains("DESC")), eq(CompanyDto.class));

    verify(query).setParameter("nameFilter", "%" + nameFilter + "%");
    verify(query).setParameter("minProfit", minProfit);

    verify(query).getResultList();
    verify(transaction).commit();
    assertSame(expectedResults, actualResults);
  }

}
