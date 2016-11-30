package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcUserDAO extends JdbcDaoSupport implements UserDAO
{
  
  public void add(User user) throws Exception
  {
    // Store the user's username
    String username = user.getUsername();
    
    // Create a null user
    User checkUser =  null;
    
    try
    { // Check to see if the username exists in the database
      checkUser = findUserByUsername(username);
    }
    catch (Exception e)
    { 
      // If the username does exist, the FindUserByUsername()
      // will throw an exception. 
      // Catch the exception, but do nothing
    }
    
    if (checkUser != null)
    { // Throw an error
      throw new Exception("Username already exists");
    }
    
    // Create and execute the sql
    String sql = "INSERT INTO USER (USERNAME, PASSWORD) VALUES (?, ?)";
    
    getJdbcTemplate().update(sql, new Object[] {user.getUsername(), user.getPassword()});
  }
  
  public void createTable()
  {
    String sql = "CREATE TABLE User (ID INT IDENTITY PRIMARY KEY, USERNAME VARCHAR(16), PASSWORD VARCHAR(16))";
    
    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    jdbcTemplate.execute(sql);
  }
  
  public User findUserById(int UserId)
  {
    String sql = "SELECT * FROM USER WHERE ID = ?";
    
    User user = (User)getJdbcTemplate().queryForObject(sql, 
        new Object[]{ UserId }, new UserRowMapper());  
    
    return user;
  }
  
  public User findUserByUsername(String username) throws Exception
  {
    String sql = "SELECT * FROM USER WHERE USERNAME = ?";
    User user;
    try
    {
      user = (User)getJdbcTemplate().queryForObject(sql, 
          new Object[]{ username }, new UserRowMapper());  
    }
    catch (Exception e)
    {
      throw new Exception("Username does not exist");
    }
    
    return user;
  }
  
  public User convertRowToUser(Map<String, Object> row)
  { 
    User user = new User();
    
    user.setId((int)row.get("ID"));
    user.setUsername((String)row.get("USERNAME"));
    user.setPassword((String)row.get("PASSWORD"));
    
    return user;    
  }
}
