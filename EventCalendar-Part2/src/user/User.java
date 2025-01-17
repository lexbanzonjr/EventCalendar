package user;

import java.io.Serializable;

public class User implements Serializable
{
  private int Id;
  private String username;
  private String password;
    
  public User()
  {
  }

  public User(String username)
  {
    this.username = username;
  }  
  
  public User(String username, String password)
  {
    this.username = username;
    this.password = password;
  }
    
  public int getId()
  {
    return Id;
  }
  public String getUsername()
  {
    return username;
  }
  public String getPassword()
  {
    return password;
  }
  public void setId(int id)
  {
    Id = id;
  }
  public void setUsername(String username)
  {
    this.username = username;
  }
  public void setPassword(String password)
  {
    this.password = password;
  } 


}
