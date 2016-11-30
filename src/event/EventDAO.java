package event;

import java.util.List;
import java.util.Map;

public interface EventDAO
{
  // Methods
  public void add(Event event);  
  public void createTable();
  public void deleteTable();
  public List<Event> findAll();  
  public List<Event> findAllEventLikedByUserId(int userId) throws Exception;
  public List<Event> findAllCreatedByUserId(int userId);  
  public List<Event> findAllSortedByStartDateTime();    
  public Event findEventById(int eventId);
  
  // Helper methods
  public Event convertRowToEvent(Map row); 
}
