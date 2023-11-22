package com.sbb.sbb_mission.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainContorller {

    @GetMapping("/")
    public void main() {
    }

}
