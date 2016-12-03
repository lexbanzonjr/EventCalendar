package user;

import java.util.List;
import java.util.Map;

public interface UserDAO
{
  public void add(User user) throws Exception;
  public void createTable();
  public User findUserById(int UserId);
  public User findUserByUsername(String username) throws Exception;
  
  // Helper methods
  public User convertRowToUser(Map<String, Object> row);
}
