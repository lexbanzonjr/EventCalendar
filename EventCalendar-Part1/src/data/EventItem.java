package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class EventItem implements Comparable<EventItem>
{
  //static String pattern = "MM-dd-yyyy HH:mm a";
  static String pattern = "MM-dd-yyyy";
  
  private Date createDateTime;
  private Date endDateTime;
  private static long eventCount = 0;
  private long eventId;
  private String eventName;
  private Vector<Long> likes = new Vector<Long>();
  private long ownerId;
  private Date startDateTime;

  @Override
  public int compareTo(EventItem o)
  {
  	return getStartDateTime().compareTo(o.getStartDateTime());
  }
  
  public void addLikeByUserId(long e)
  {
    this.likes.add(e);
  }
  
  public long findLikeByUserId(long e)
  {
    for (long item : likes)
    {
      if (item == e)
        return e;
    }
    return -1;
  }
  
  public String constructLikeForHTML(long e, String redirect)
  {
    if (findLikeByUserId(e) > -1)
      return "<a href=\"calendar?action=dislikeEvent&redirect="+ redirect + "&eventId=" + this.getEventId() + "\">Disike</a>";
    else
      return "<a href=\"calendar?action=likeEvent&redirect=" + redirect + "&eventId=" + this.getEventId() + "\">Like</a>";  
  }
  
	public Date getCreateDateTime()
	{
		return createDateTime;
	}

	public Date getEndDateTime()
	{
		return endDateTime;
	}

	public long getEventId()
	{
		return eventId;
	}

	public String getEventName()
	{
		return eventName;
	}
	
	public long getOwnerId()
	{
		return ownerId;
	}
	
	public Date getStartDateTime()
	{
		return startDateTime;
	}
	
	public void removeLikeByUserId(long e)
	{
    for (int index = 0; index < likes.size(); index++)
    {
      if (likes.get(index) == e)
        likes.remove(index);
    }	  
	}
	
	public void setCreateDateTime(String createDateTime)
	{
  	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
  	try
		{
			this.createDateTime = formatter.parse(createDateTime);
		} 
  	catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setEndDateTime(String endDateTime)
	{
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    if (endDateTime.isEmpty())
      return;
    try
    {
      this.endDateTime = formatter.parse(endDateTime);
    } 
    catch (ParseException e)
    {
      e.printStackTrace();
    }
	}
	
	public void setEventId(long eventId)
	{
		this.eventId = eventId;
	}
	
	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}
	
	public void setOwnerId(long ownerId)
	{
		this.ownerId = ownerId;
	}
	
	public void setStartDateTime(String startDateTime)
	{
  	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
  	try
		{
			this.startDateTime = formatter.parse(startDateTime);
		} 
  	catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public static EventItem getNew(long ownerId)
  {
  	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
  	String createDateTime = formatter.format(new Date());  	
  	
  	EventItem eventItem = new EventItem();
  	eventItem.setEventId(eventCount);
  	eventItem.setCreateDateTime(createDateTime);
  	eventItem.setOwnerId(ownerId);
  	++eventCount;
  	return eventItem;  	
  }
}
