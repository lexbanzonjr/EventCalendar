package likes;

import java.util.List;

public interface LikesDAO
{
  public void add(Likes likes);
  public void createTable();
  public List<Likes> findAllLikesByUserId(int userId); 
  public Likes findLike(int userId, int eventId) throws Exception; 
  public void remove(Likes likes);
}
