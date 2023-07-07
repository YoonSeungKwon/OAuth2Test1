package yoon.test.oAuthTest1.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yoon.test.oAuthTest1.dto.SessionMember;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/oauth/base")
    public String loginPage(Model model){
        SessionMember member = (SessionMember) httpSession.getAttribute("user");

        if(member != null){
            model.addAttribute("email", member.getEmail());
            model.addAttribute("name", member.getName());
            model.addAttribute("prpvider", member.getProvider());
        }
        return "result";
    }

}
