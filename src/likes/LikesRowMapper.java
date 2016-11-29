package likes;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LikesRowMapper implements RowMapper
{

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    Likes likes = new Likes();
    
    likes.setEventId(rs.getInt("EVENTID"));
    likes.setUserId(rs.getInt("USERID"));
    
    return likes;
  }

}
