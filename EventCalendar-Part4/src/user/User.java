package user;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table
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
    
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int getId()
  {
    return Id;
  }
  
  @Basic
  @Column(name = "USERNAME", unique = true)
  public String getUsername()
  {
    return username;
  }
  
  @Basic
  @Column(name = "PASSWORD")
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
