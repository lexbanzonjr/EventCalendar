package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper
{
  public Object mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    User user = new User();
    
    user.setId(rs.getInt("ID"));
    user.setPassword(rs.getString("PASSWORD"));
    user.setUsername(rs.getString("USERNAME"));
    
    return user;
  }
}
