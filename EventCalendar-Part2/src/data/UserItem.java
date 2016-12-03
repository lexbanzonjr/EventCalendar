package data;

public class UserItem
{
	private static long count = 0;
	private long Id;
	private String username;
	private String password;
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public long getId()
	{
		return Id;
	}	

	public void setId(long Id)
	{
		this.Id = Id;
	}
	
	public static UserItem getNew(String username, String password)
	{
		UserItem userItem = new UserItem();
		userItem.setId(count);
		userItem.setUsername(username);
		userItem.setPassword(password);
		++count;
				
		return userItem;
	}	
}
