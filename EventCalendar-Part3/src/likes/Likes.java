package likes;

import java.io.Serializable;

public class Likes implements Serializable 
{
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
  
  public int getUserId()
  {
    return userId;
  }
  public int getEventId()
  {
    return eventId;
  }
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  public void setEventId(int eventId)
  {
    this.eventId = eventId;
  }
  

}