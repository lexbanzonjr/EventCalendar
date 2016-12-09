package likes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class JpaLikesDAO implements LikesDAO
{
  private EntityManagerFactory factory;

  @Override
  public void add(Likes likes)
  {
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      em.persist(likes);
      
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
  }

  @Override
  public void createTable()
  {
    /* Only for jdbc */
  }

  @Override
  public List<Likes> findAllLikesByUserId(int userId)
  {
    List<Likes> listLikes = new ArrayList<Likes>();
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      String sql = "select "
          + "l.likesId, "
          + "l.userId, "
          + "l.eventId, "
          + "from " + Likes.class.getName() + " l "
          + "where l.userId = " + userId;
      List<Object[]> result = em.createQuery(sql).getResultList();
      
      for (Object[] elements : result)
      {
        Likes likes = new Likes();
        likes.setLikesId(Integer.valueOf(String.valueOf(elements[0])));
        likes.setUserId(Integer.valueOf(String.valueOf(elements[1])));
        likes.setEventId(Integer.valueOf(String.valueOf(elements[2])));
        listLikes.add(likes);        
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
    
    return listLikes;
  }

  @Override
  public Likes findLike(int userId, int eventId) throws Exception
  {
    Likes likes = null;
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      String sql = "select "
          + "l.likesId, "
          + "l.userId, "
          + "l.eventId "
          + "from " + Likes.class.getName() + " l "
          + "where l.userId = " + userId + " and l.eventId = " + eventId;
      List<Object[]> results = em.createQuery(sql).getResultList();
      
      if (results.size() > 1)
        throw new Exception("Returned more than 1 record.");
        
      for (Object[] elements : results)
      { 
        likes = new Likes();
        likes.setLikesId(Integer.valueOf(String.valueOf(elements[0])));
        likes.setUserId(Integer.valueOf(String.valueOf(elements[1])));
        likes.setEventId(Integer.valueOf(String.valueOf(elements[2])));
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
    
    return likes;
  }

  @Override
  public void remove(Likes likes)
  {        
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      Likes tmpLikes = findLike(likes.getUserId(), likes.getEventId());
      
      if (tmpLikes == null)
        throw new Exception("No like found to remove.");
      
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      em.remove(em.contains(tmpLikes) ? tmpLikes : em.merge(tmpLikes));
      
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
  }

  public void setFactory(EntityManagerFactory factory)
  {
    this.factory = factory;
  }
}
