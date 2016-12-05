package event;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class JpaEventDAO implements EventDAO
{
  private EntityManagerFactory factory;

  @Override
  public void add(Event event)
  {
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      em.persist(event);
      
      trans.commit();
    }
    catch (Exception e)
    {
      if(trans != null && trans.isActive())
        trans.rollback();
      System.out.println("JpaEventDAO.add(): (Exception has been thrown) " + e.getLocalizedMessage());
      e.printStackTrace();
    }
    finally
    {
      if (em != null && em.isOpen())
        em.close();
      System.out.println("JpaEventDAO: New event has been added.");
    }    
  }

  @Override
  public void createTable()
  {
    
  }

  @Override
  public void deleteTable()
  {
    
  }

  @Override
  public List<Event> findAll()
  {
    return null;
  }

  @Override
  public List<Event> findAllEventLikedByUserId(int userId) throws Exception
  {
    return null;
  }

  @Override
  public List<Event> findAllCreatedByUserId(int userId)
  {
    return null;
  }

  @Override
  public List<Event> findAllSortedByStartDateTime()
  {
    return null;
  }

  @Override
  public Event findEventById(int eventId)
  {
    return null;
  }

  @Override
  public Event convertRowToEvent(Map row)
  {
    return null;
  }
  
  public void setFactory(EntityManagerFactory factory)  
  {
    this.factory = factory;
    System.out.println("JpaEventDAO.setFactory(): " + factory.toString());  // For testing
    System.out.println("JpaEventDAO.setFactory(): isOpen() " + factory.isOpen());  // For testing
  }  
}
