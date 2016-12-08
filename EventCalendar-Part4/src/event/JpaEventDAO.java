package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import likes.Likes;

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
    /* Only for jdbc */
  }

  @Override
  public void deleteTable()
  {
    /* Only for jdbc */
  }

  @Override
  public List<Event> findAll()
  {
    List<Event> result = null;
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Event> cq = cb.createQuery(Event.class);
      Root<Event> event = cq.from(Event.class);
      cq.select(event);
      TypedQuery<Event> q = em.createQuery(cq);
      result = (List<Event>) q.getResultList();      
      
      trans.commit();
    }
    catch (Exception e)
    {
      if(trans != null && trans.isActive())
        trans.rollback();
      e.printStackTrace();
    }
    finally
    {
      if (em != null && em.isOpen())
        em.close();
    }    
    return result;
  }

  @Override
  public List<Event> findAllEventLikedByUserId(int userId) throws Exception
  {
    List<Event> result = new ArrayList<Event>();
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      String sql = "select "
          + "e.eventId, "
          + "e.createDateTime, "
          + "e.endDateTime, "
          + "e.ownerId, "
          + "e.startDateTime, "
          + "e.eventName "
          + "from " + Event.class.getName() + " e, " + Likes.class.getName() + " l "
          + "where e.eventId = l.eventId and l.userId = " + userId;
      List<Object[]> results = em.createQuery(sql).getResultList();
      
      for(Object[] elements : results)
      {
        Event event = new Event();
        event.setEventId(Integer.valueOf(String.valueOf(elements[0])));
        event.setCreateDateTime(String.valueOf(elements[1]));
        event.setEndDateTime(String.valueOf(elements[2]));
        event.setOwnerId(Integer.valueOf(String.valueOf(elements[3])));
        event.setStartDateTime(String.valueOf(elements[4]));
        event.setEventName(String.valueOf(elements[5]));
        result.add(event);
      }   
            
      trans.commit();
    }
    catch (Exception e)
    {
      if(trans != null && trans.isActive())
        trans.rollback();
      e.printStackTrace();
    }
    finally
    {
      if (em != null && em.isOpen())
        em.close();
    }    
    return result;
  }

  @Override
  public List<Event> findAllCreatedByUserId(int userId)
  {
    List<Event> result = new ArrayList<Event>();
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      String sql = "select "
          + "e.eventId, "
          + "e.createDateTime, "
          + "e.endDateTime, "
          + "e.ownerId, "
          + "case e.startDateTime when '' then '01-01-1900' else e.startDateTime end, "
          + "case e.endDateTime when '' then '01-01-1900' when '--' then '01-01-1900' else e.endDateTime end "
          + "from " + Event.class.getName() + " e "
          + "where e.ownerId = " + userId;
      List<Object[]> results = em.createQuery(sql).getResultList();
      
      for(Object[] elements : results)
      {
        Event event = new Event();
        event.setEventId(Integer.valueOf(String.valueOf(elements[0])));
        event.setCreateDateTime(String.valueOf(elements[1]));
        event.setEndDateTime(String.valueOf(elements[2]));
        event.setOwnerId(Integer.valueOf(String.valueOf(elements[3])));
        event.setStartDateTime(String.valueOf(elements[4]));
        event.setEventName(String.valueOf(elements[5]));
        result.add(event);
      }   
            
      trans.commit();
    }
    catch (Exception e)
    {
      if(trans != null && trans.isActive())
        trans.rollback();
      e.printStackTrace();
    }
    finally
    {
      if (em != null && em.isOpen())
        em.close();
    }    
    return result;
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
