package controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import form.LoginForm;
import form.RegisterForm;
import user.JpaUserDAO;
import user.User;

@Controller
@RequestMapping("login")
public class LoginController
{
  private JpaUserDAO jpaUserDAO;
    
  @RequestMapping(value="/", method = RequestMethod.GET)
  public View home(Map<String, Object> model)
  {
    model.put("loginURL", "login");
    return new RedirectView("/login/{loginURL}", true);
  }
  
  @RequestMapping(value="/login", method = RequestMethod.GET)
  public String login(Map<String, Object> model)
  {
    model.put("loginForm", new LoginForm());
    return "LoginForm";
  }
  
  @RequestMapping(value="/login", method = RequestMethod.POST)
  public View login(Map<String, Object> model, LoginForm form, HttpSession session)
  { 
    // Get the username and password from the Login form
    String username = form.getUsername();
    String password = form.getPassword();
    
    // Set the user variable to null.
    User user = null;
    
    try
    { // Check to see if the user exists in the database
      user = jpaUserDAO.findUserByUsername(username);
    }
    catch (Exception e)
    { // An exception occurred. The user does not exist.
      session.setAttribute("error", e.getMessage());      
    }
    
    // Need to check if the user is not null. A null occurs only if 
    // the exception occurs where the username did not exist in the
    // database.
    if (user != null)
    { 
      // Check the passwords
      if (user.getPassword().equals(password))
      { 
        // Add the user object to the session
        session.setAttribute("User", user);
        
        // Redirect to the user's dashboard
        model.put("dashboardURL", "dashboard");
        return new RedirectView("/{dashboardURL}", true);
      }
    }
    
    // If login fails, go back to login
    model.put("loginURL", "login");
    return new RedirectView("/login/{loginURL}", true);
  }  
  
  @RequestMapping(value = "register", method = RequestMethod.GET)
  public String register(Map<String, Object> model)
  {
    model.put("registerForm", new RegisterForm());
    return "RegisterForm";
  }

  @RequestMapping(value = "register", method = RequestMethod.POST)
  public View register(Map<String, Object> model, RegisterForm form, HttpSession session)
  {
    // Create a user object and set the username and password
    // from the register form
    User user = new User();
    user.setUsername(form.getUsername());
    user.setPassword(form.getPassword());
    
    try
    { // Add the user in the database
      jpaUserDAO.add(user);
    }
    catch (Exception e)
    { // Exception occurs if the username already exists.
      // Stores exception message in session.
      session.setAttribute("error", e.getMessage());
    }
    
    // Go back to login screen after registering the user.
    model.put("loginURL", "login");
    return new RedirectView("/login/{loginURL}", true);
  }
  
  public void setJpaUserDAO(JpaUserDAO jpaUserDAO)  
  {
    this.jpaUserDAO = jpaUserDAO;
  }
}
