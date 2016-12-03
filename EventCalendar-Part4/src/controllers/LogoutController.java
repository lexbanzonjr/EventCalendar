package controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("logout")
public class LogoutController
{

  @RequestMapping("/")
  public View Logout(HttpSession Session, Map<String, Object> model)
  {
    Session.removeAttribute("User");
    model.put("loginURL", "login");
    return new RedirectView("../login/{loginURL}", true);    
  }

}
