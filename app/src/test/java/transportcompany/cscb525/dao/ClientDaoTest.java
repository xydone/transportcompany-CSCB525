package transportcompany.cscb525.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import transportcompany.cscb525.configuration.SessionFactoryUtil;
import transportcompany.cscb525.entity.Client;
import transportcompany.cscb525.entity.Company;
import transportcompany.cscb525.exceptions.ClientNotFoundException;

@ExtendWith(MockitoExtension.class)
class ClientDaoTest {

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @Mock
  private SessionFactory sessionFactory;

  @BeforeEach
  void setUp() {
    when(sessionFactory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    SessionFactoryUtil.setSessionFactory(sessionFactory);
  }

  @Test
  void createClient() {
    Client client = new Client("Client #1", new Company("Example company"));

    ClientDao.createClient(client);

    verify(session).persist(client);
    verify(transaction).commit();
  }

  @Test
  void updateClient() {
    Client client = new Client("Client #1", new Company("Example company"));

    ClientDao.updateClient(client);

    verify(session).merge(client);
    verify(transaction).commit();
  }

  @Test
  void deleteClient() {
    Client client = new Client("Client #1", new Company("Example company"));

    ClientDao.deleteClient(client);

    verify(session).remove(client);
    verify(transaction).commit();
  }

  @Test
  void getClientById_found() {
    Client client = new Client("Client #1", new Company("Example company"));
    when(session.find(Client.class, 1L)).thenReturn(client);

    Client result = ClientDao.getClientById(1);

    assertEquals(client, result);
    verify(transaction).commit();
  }

  @Test
  void getClientById_notFound() {
    when(session.find(Client.class, 1L)).thenReturn(null);

    assertThrows(ClientNotFoundException.class, () -> ClientDao.getClientById(1L));
  }

}