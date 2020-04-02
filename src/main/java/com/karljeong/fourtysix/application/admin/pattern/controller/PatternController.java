package com.karljeong.fourtysix.application.admin.pattern.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.karljeong.fourtysix.application.admin.auth.service.AuthService;
import com.karljeong.fourtysix.application.admin.pattern.service.PatternService;
import com.karljeong.fourtysix.common.loadstatic.LoadStatic;

@Controller
@RequestMapping("/admin/pattern")
public class PatternController {

	private final PatternService patternService;
	private final AuthService authService;
	private final LoadStatic loadStatic;

	@Autowired
	PatternController(PatternService patternService, AuthService authService, LoadStatic loadStatic) {
		this.patternService = patternService;
		this.authService = authService;
		this.loadStatic = loadStatic;
	}

	@GetMapping("/viewmain")
	public String viewMain(Model model) {
		return "/view/admin/pattern/pattern";
	}

    @GetMapping("/viewcreate")
    public String viewCreate(Model model) {
        model.addAttribute("authList", authService.findAll());
        return "/view/admin/pattern/patternC";
    }

    @GetMapping("/viewupdate/{patternId}")
    public String viewUpdate(Model model, @PathVariable("patternId") BigInteger patternId) {
        model.addAttribute("mainInfo", patternService.findById(patternId));
        model.addAttribute("authList", authService.findAll());
        return "/view/admin/pattern/patternU";
    }


}
