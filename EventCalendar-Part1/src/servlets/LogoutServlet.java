package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
    name = "logoutServlet",
    urlPatterns = "/logout"
)

public class LogoutServlet extends HttpServlet
{

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // Create session if not yet created
    HttpSession session = request.getSession();
    
    // Set LoginUserItem to null
    session.setAttribute("LoginUserItem", null);
    
    // Go back to login
    response.sendRedirect("login?message=logout");
  }  
}
