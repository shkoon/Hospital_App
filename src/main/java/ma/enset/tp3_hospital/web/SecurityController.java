package ma.enset.tp3_hospital.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/notAuthorized")
    public String notAuthorized(){

        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
