package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.UserItem;
import user.JdbcUserDAO;
import user.User;
import data.UserDataSource;

@WebServlet(
   name = "RegisterServlet",
   urlPatterns = "/register"
)
public class RegisterServlet extends HttpServlet
{
  private static JdbcUserDAO jdbcUserDAO;
  private static final long serialVersionUID = 1L;

  UserDataSource userDataSource = new UserDataSource();
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // Create session if not yet created
    HttpSession session = request.getSession();
    
    // Check if user is logged in
    UserItem loginUserItem = (UserItem)session.getAttribute("LoginUserItem");
    if (loginUserItem != null) 
    {
      response.sendRedirect("calendar");
      return;
    }
    
    request.getRequestDispatcher("WEB-INF/jsp/view/viewRegisterNewUser.jsp").forward(request, response);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Set the user variable to null.
    User user = new User(username, password);    

    // Create session if not yet created
    HttpSession session = request.getSession();
    
    String message;
    try
    { // Add the user in the database
      jdbcUserDAO.add(user);
      message = "CreatedNewUserSuccess";
    }
    catch (Exception e)
    { // Exception occurs if the username already exists.
      // Stores exception message in session.
      System.out.println(e.getMessage());
      message = "Duplicate";
    }
    
    response.sendRedirect("login?message=" + message);  	
  }
  
  public void setJdbcUserDAO(JdbcUserDAO jdbcUserDAO)  
  {
    this.jdbcUserDAO = jdbcUserDAO;
  }  
}
