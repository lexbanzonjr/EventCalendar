package likes;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Likes implements Serializable 
{
  private int likesId;
  private int userId;
  private int eventId;
  
  public Likes()
  {
  }
  
  public Likes(int userId, int eventId)
  {
    this.userId = userId;
    this.eventId = eventId;
  }
  
  @Basic
  @Column
  public int getEventId()
  {
    return eventId;
  }

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int getLikesId()
  {
	return likesId;
  }

  @Basic
  @Column
  public int getUserId()
  {
    return userId;
  }
    
  public void setLikesId(int likesId) 
  {
	this.likesId = likesId;
  }

  public void setEventId(int eventId)
  {
    this.eventId = eventId;
  }

  public void setUserId(int userId)
  {
    this.userId = userId;
  }
}
