package com.karljeong.fourtysix.application.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/viewMain")
    public String viewMain(Model model) {
        return "";
    }
}