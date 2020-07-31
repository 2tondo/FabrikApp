package it.Fabrik.FabrikApp.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("home")
    public String home(Model model){
        return "home";
    }

}
