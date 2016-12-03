package data;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource
{
	static List<UserItem> userList = new ArrayList<UserItem>();
	
	public boolean add(UserItem user)
	{
		userList.add(user);
		return true;
	}
	
	public List<UserItem> findAll()
	{
		return userList;
	}
	
	public UserItem findUserItemByUsername(String username)
	{
		for (UserItem userItem : userList)
		{
			if (userItem.getUsername().toUpperCase().equals(username.toUpperCase()))
				return userItem;
		}
		return null;
	}
	
	public String findUsernameById(int Id)
	{
		return userList.get(Id).getUsername();
	}
	
	public boolean remove(UserItem user)
	{
		return true;
	}
	
	public boolean update(UserItem user)
	{
		return true;
	}
}
