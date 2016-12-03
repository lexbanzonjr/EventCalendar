package likes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "LikesEntity")
@Table(name = "Likes")
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
  
  public int getEventId()
  {
    return eventId;
  }

  public int getLikesId()
  {
	return likesId;
  }

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
