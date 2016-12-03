package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventDataSource
{	
	private List<EventItem> eventList;
	
	public EventDataSource()
	{
		eventList = new ArrayList<EventItem>();		
	}
	
	public boolean add(EventItem event)
	{
		eventList.add(event);
		return true;
	}
	
	public List<EventItem> findAll()
	{		
		return eventList;
	}
	
	public List<EventItem> findAllCreatedByUserId(long userId)
	{
	  List<EventItem> userEvents = new ArrayList<EventItem>();
	  for (int index = 0; index < eventList.size(); index++)
	  {
	    EventItem event = eventList.get(index);
	    if (event.getOwnerId() == userId)
	      userEvents.add(event);
	  }
	  return userEvents;
	}
	
	public List<EventItem> findAllLikedEventsByUserId(long userId)
	{
	  List<EventItem> userLikes = new ArrayList<EventItem>();
    for (int index = 0; index < eventList.size(); index++)
    {
      EventItem event = eventList.get(index);
      if (event.findLikeByUserId(userId) > -1)
        userLikes.add(event);
    }
    return userLikes;	  
	}
	
	public List<EventItem> findAllSortedByDate()
	{
		List<EventItem> temp = eventList;
		Collections.sort(temp);
		return temp;
	}
	
	public EventItem findEventItemById(long eventId)
	{
	  for (int index = 0; index < eventList.size(); index++)
	  {
	    EventItem event = eventList.get(index); 
	    if (event.getEventId() == eventId)
	      return event;
	  }
	  return null;
	}
	
	public boolean update(EventItem event)
	{
		return true;
	}
	
	public boolean remove(EventItem event)
	{
		return true;
	}
}
