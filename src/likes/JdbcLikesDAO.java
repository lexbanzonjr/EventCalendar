
package likes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcLikesDAO extends JdbcDaoSupport implements LikesDAO
{
  public void add(Likes likes)
  {
    String sql ="INSERT INTO LIKES (USERID, EVENTID) VALUES (?, ?)";
    getJdbcTemplate().update(sql, new Object[] {likes.getUserId(), likes.getEventId()});
  }
  
  public void createTable()
  {
    String sql = "CREATE TABLE Likes (UserId int, EventId int)";    
    
    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    jdbcTemplate.execute(sql);
  }
  
  public List<Likes> findAllLikesByUserId(int userId)
  {
    String sql = "SELECT * FROM LIKES WHERE USERID = " + userId;
    
    List<Likes> list = new ArrayList<Likes>();
    
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
    for (Map row : rows)
    {
      Likes likes = new Likes();
      likes.setEventId((int)row.get("UserId"));
      likes.setUserId((int)row.get("EventId"));
      list.add(likes);
    }
    
    return list;
  }
  
  public Likes findLike(int userId, int eventId) throws Exception
  {
    String sql = "SELECT * FROM LIKES WHERE USERID = ? AND EVENTID = ?";
    Likes like = null;
    
    try
    {
      like = (Likes) getJdbcTemplate().queryForObject(sql, new Object[] { userId, eventId }, new LikesRowMapper());
    }
    catch (Exception e)
    {
      // This only occurs when no records of likes in database.
      // We leave this here to prevent an error from showing
    }
    
    return like;
  }
  
  public void remove(Likes like)
  {
    int userId = like.getUserId();
    int eventId = like.getEventId();
    
    String sql = "DELETE FROM LIKES WHERE USERID = " + userId + " AND EVENTID = " + eventId;
    
    JdbcTemplate jdbcTemplate = getJdbcTemplate();
    jdbcTemplate.execute(sql);    
  }
}
