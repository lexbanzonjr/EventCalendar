package event;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcEventDAO extends JdbcDaoSupport implements EventDAO 
{
  // Column Names 
  private String cnCreateDateTime = "CreateDateTime";
  private String cnEndDateTime = "EndDateTime";
  private String cnEventName = "EventName";
  private String cnEventId = "EventId";
  private String cnOwnerId = "OwnerId";
  private String cnStartDateTime = "StartDateTime";
        
  public void add(Event event)
  {
    String sql = "INSERT INTO EVENT (EVENTNAME, OWNERID, STARTDATETIME, ENDDATETIME) VALUES (?, ?, ?, ?)";
    getJdbcTemplate().update(sql, new Object[] {event.getEventName(), event.getOwnerId(), event.getStartDateTime(), 
      event.getEndDateTime()});
  }

  public Event convertRowToEvent(Map row)
  {
    Event event = new Event();
    
    event.setEventId((int)row.get(this.cnEventId));
    event.setCreateDateTime((String)row.get(this.cnCreateDateTime));
    event.setEndDateTime((String)row.get(this.cnEndDateTime));
    event.setEventName((String)row.get(this.cnEventName));
    event.setOwnerId((int)row.get(this.cnOwnerId));
    event.setStartDateTime((String)row.get(this.cnStartDateTime));
    
    return event;    
  }
  
  public void createTable()
  {
    String sql = "CREATE TABLE Event ("
        + this.cnEventId + " int identity primary key, "
        + this.cnCreateDateTime + " varchar(16), "
        + this.cnEndDateTime + " varchar(16), "
        + this.cnEventName + " varchar(32), "
        + this.cnOwnerId + " int, " 
        + this.cnStartDateTime + " varchar(16))";  

    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    jdbcTemplate.execute(sql);
  }
  
  public void deleteTable()
  {
    String sql = "DROP TABLE EVENT";
    
    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    try
    {
      jdbcTemplate.execute(sql);
    }
    catch (Exception  e)
    {
      System.out.println(e.getMessage());
    }
  }
  
  public List<Event> findAll()
  {
    String sql = "SELECT * FROM EVENT ORDER BY TO_DATE(STARTDATETIME, 'MM-DD-YYYY') DESC";
    
    List<Event> list = new ArrayList<Event>();
    
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
    for (Map row : rows)
    {
      Event event = convertRowToEvent(row);
      list.add(event);
    }
    return list;
  }
  
  public List<Event> findAllEventLikedByUserId(int userId) throws Exception
  {
    String sql = "SELECT EVENTID, CREATEDATETIME, ENDDATETIME, EVENTNAME, OWNERID, STARTDATETIME FROM EVENT "
        + "JOIN LIKES ON EVENT.EVENTID=LIKES.EVENTID WHERE LIKES.USERID = ? "
        + "ORDER BY TO_DATE(STARTDATETIME, 'MM-DD-YYYY') DESC";
    
    List<Event> list = new ArrayList<Event>();
    
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] {userId});
    for (Map row : rows)
    {
      Event event = convertRowToEvent(row);
      list.add(event);
    }
    return list;    
  }
  
  public List<Event> findAllCreatedByUserId(int userId)
  {
    String sql = "SELECT * FROM EVENT WHERE OWNERID = ? ORDER BY TO_DATE(STARTDATETIME, 'MM-DD-YYYY') DESC";
    
    List<Event> list = new ArrayList<Event>();
    
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] {userId});
    for (Map row : rows)
    {
      Event event = convertRowToEvent(row);
      list.add(event);
    }
    return list;    
  }
    
  public List<Event> findAllSortedByStartDateTime()
  {
    String sql = "SELECT * FROM EVENT ORDER BY TO_DATE(STARTDATETIME, 'MM-DD-YYYY') DESC";
    
    List<Event> list = new ArrayList<Event>();
    
    List<Map<String,Object>> rows = getJdbcTemplate().queryForList(sql);
    for (Map row : rows)
    {
      Event event = convertRowToEvent(row);
      list.add(event);      
    }
    return list;
  }
  
  public Event findEventById(int eventId)
  {
    return null;
  }

}
