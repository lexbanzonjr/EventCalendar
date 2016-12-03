package event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "EventEntity")
@Table(name = "Event")
public class Event implements Serializable
{
  static String pattern = "MM-dd-yyyy";
  
  private String createDateTime;
  private String data;
  private String endDateTime;
  private int eventId;
  private String eventName;
  private boolean like;
  private int ownerId;
  private String ownerName;
  private String startDateTime;
  
  public Event()
  {
    this.like = false;
  }
  
  public Event(String eventName, int ownerId, String startDateTime)
  {
    this.eventName = eventName;
    this.like = false;
    this.ownerId = ownerId;
    this.startDateTime = startDateTime;
  }
  
  public Event(String eventName, int ownerId, String startDateTime, String endDateTime)
  {
    this.eventName = eventName;
    this.like = false;
    this.ownerId = ownerId;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }
  
  @Column(name = "CREATEDATETIME")
  public String getCreateDateTime()
  {
    return createDateTime;
  }  
  
  public String getData()
  {
    return data;
  }
  
  @Column(name = "ENDDATETIME")
  public String getEndDateTime()
  {
    return endDateTime;
  }
  
  @Id
  @Column(name = "EVENTID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int getEventId()
  {
    return eventId;
  }
  
  @Column(name = "EVENTNAME")
  public String getEventName()
  {
    return eventName;
  }
  
  @Column(name = "OWNERID")
  public int getOwnerId()
  {
    return ownerId;
  }  
  
  public String getOwnerName()
  {
    return ownerName;
  }
  
  @Column(name = "STARTDATETIME")
  public String getStartDateTime()
  {
    return startDateTime;
  }
  
  public boolean isLike()
  {
    return like;
  }
  
  public void setCreateDateTime(String createDateTime)
  {
    this.createDateTime = createDateTime;
  }
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  public void setEndDateTime(String endDateTime)
  {
    this.endDateTime = endDateTime;
  }
  
  public void setEventId(int eventId)
  {
    this.eventId = eventId;
  }
  
  public void setEventName(String eventName)
  {
    this.eventName = eventName;
  }
  
  public void setLike(boolean like)
  {
    this.like = like;
  }
  
  public void setOwnerId(int ownerId)
  {
    this.ownerId = ownerId;
  }
  
  public void setOwnerName(String ownerName)
  {
    this.ownerName = ownerName;
  }
  
  public void setStartDateTime(String startDateTime)
  {
    this.startDateTime = startDateTime;
  }  
}
