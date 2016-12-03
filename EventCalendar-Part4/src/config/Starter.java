package config;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;

import event.JdbcEventDAO;
import likes.JdbcLikesDAO;
import user.JdbcUserDAO;

@Controller
public class Starter extends JdbcDaoSupport
{
  private JdbcEventDAO jdbcEventDAO;
  private JdbcUserDAO jdbcUserDAO;
  private JdbcLikesDAO jdbcLikesDAO;

  public void start()
  {
    try
    { // Create event table
      jdbcEventDAO.createTable();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    
    try
    { // Create user table
      jdbcUserDAO.createTable();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    
    try
    { // Create likes table
      jdbcLikesDAO.createTable();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    
    
  }

  public void setJdbcEventDAO(JdbcEventDAO jdbcEventDAO)
  {
    this.jdbcEventDAO = jdbcEventDAO;
  }
  
  public void setJdbcLikesDAO(JdbcLikesDAO jdbcLikesDAO)
  {
    this.jdbcLikesDAO = jdbcLikesDAO;
  }
  
  public void setJdbcUserDAO(JdbcUserDAO jdbcUserDAO)  
  {
    this.jdbcUserDAO = jdbcUserDAO;
  }  
}
