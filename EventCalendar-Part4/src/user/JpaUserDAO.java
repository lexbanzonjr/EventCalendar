package user;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaUserDAO implements UserDAO
{
  private EntityManagerFactory factory;
  
  public void add(User user) throws PersistenceException
  {
    EntityManager manager = null;
    EntityTransaction transaction = null;
    
    try
    {
      manager = factory.createEntityManager();
      transaction = manager.getTransaction();
      transaction.begin();
      
      manager.persist(user);
      
      transaction.commit();
    }
    catch (Exception e)
    {
      if(transaction != null && transaction.isActive())
        transaction.rollback();
      System.out.println("JpaUserDAO.add(): (Exception has been thrown) " + e.getLocalizedMessage());
      e.printStackTrace();
    }
    finally
    {
      if (manager != null && manager.isOpen())
        manager.close();
      System.out.println("JpaUserDAO: New user has been added.");
    }
  }

  @Override
  public void createTable()
  {
    /* Only in JDBC */
  }

  @Override
  public User findUserById(int UserId)
  {
    User result = new User();
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      String sql = "select u.id, u.username, u.password "
          + "from " + User.class.getName() + " u";
      List<Object[]> results = em.createQuery(sql).getResultList();
      
      for(Object[] elements : results)
      { // Don't know how to use the getSingleResult(). Did it this way.
        result.setId(Integer.valueOf(String.valueOf(elements[0])));
        result.setUsername(String.valueOf(elements[1]));
        result.setPassword(String.valueOf(elements[2]));
        break;
      }
//      CriteriaBuilder cb = em.getCriteriaBuilder();
//      CriteriaQuery<User> cq = cb.createQuery(User.class);
//      Root<User> user = cq.from(User.class);
//      cq.select(user);
//      cq.where(cb.equal(user.get("Id"), UserId));
//      TypedQuery<User> q = em.createQuery(cq);
//      result = (User) q.getSingleResult();      

      trans.commit();
    }
    catch (Exception e)
    {
      if(trans != null && trans.isActive())
        trans.rollback();
      System.out.println("JpaUserDAO.findUserById(): (Exception has been thrown) " + e.getLocalizedMessage());
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
  public User findUserByUsername(String username) throws Exception
  {
    User result = null;
    EntityManager em = null;
    EntityTransaction trans = null;
    
    try
    {
      em = factory.createEntityManager();
      trans = em.getTransaction();
      trans.begin();
      
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<User> cq = cb.createQuery(User.class);
      Root<User> user = cq.from(User.class);
      cq.select(user);
      cq.where(cb.equal(user.get("username"), username));
      TypedQuery<User> q = em.createQuery(cq);
      result = (User) q.getSingleResult();      
      
      trans.commit();
    }
    catch (Exception e)
    {
      if(trans != null && trans.isActive())
        trans.rollback();
      System.out.println("JpaUserDAO.findUserByUsername(): (Exception has been thrown) " + e.getLocalizedMessage());
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
  public User convertRowToUser(Map<String, Object> row)
  {
    /* Only used in JDBC */
    return null;
  }
  
  public void setFactory(EntityManagerFactory factory)  
  {
    this.factory = factory;
    System.out.println("JpaUserDAO.setFactory(): " + factory.toString());  // For testing
    System.out.println("JpaUserDAO.setFactory(): isOpen() " + factory.isOpen());  // For testing
  }  
}
