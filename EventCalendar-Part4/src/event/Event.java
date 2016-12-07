package event;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import likes.Likes;

@Entity
@Table
public class Event implements Serializable
{
  static String pattern = "MM-dd-yyyy";
  
  private String createDateTime;
  private String data;
  private String endDateTime;
  private int eventId;
  private String eventName;
  private boolean liked;
  private int ownerId;
  private String ownerName;
  private String startDateTime;
  
  public Event()
  {
    this.liked = false;
  }
  
  public Event(String eventName, int ownerId, String startDateTime)
  {
    this.eventName = eventName;
    this.liked = false;
    this.ownerId = ownerId;
    this.startDateTime = startDateTime;
  }
  
  public Event(String eventName, int ownerId, String startDateTime, String endDateTime)
  {
    this.eventName = eventName;
    this.liked = false;
    this.ownerId = ownerId;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }
  
  @Basic
  @Column
  public String getCreateDateTime()
  {
    return createDateTime;
  }  
  
  public String getData()
  {
    return data;
  }
  
  @Basic
  @Column
  public String getEndDateTime()
  {
    return endDateTime;
  }
  
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int getEventId()
  {
    return eventId;
  }
  
  @Basic
  @Column
  public String getEventName()
  {
    return eventName;
  }
  
  @Basic
  @Column
  public int getOwnerId()
  {
    return ownerId;
  }  
  
  public String getOwnerName()
  {
    return ownerName;
  }
  
  @Basic
  @Column
  public String getStartDateTime()
  {
    return startDateTime;
  }
  
  public boolean isLiked()
  {
    return liked;
  }
  
  public void setCreateDateTime(String createDateTime)
  {
    this.createDateTime = createDateTime;
  }
  
  @Transient
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

  @Transient
  public void setLiked(boolean liked)
  {
    this.liked = liked;
  }
  
  public void setOwnerId(int ownerId)
  {
    this.ownerId = ownerId;
  }
  
  @Transient
  public void setOwnerName(String ownerName)
  {
    this.ownerName = ownerName;
  }
  
  public void setStartDateTime(String startDateTime)
  {
    this.startDateTime = startDateTime;
  }  
}
