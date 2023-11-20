package com.sbb.sbb_mission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainContorller {

    @GetMapping("/")
    @ResponseBody
    public String main() {
        return "Hello, SBB!";
    }

}
