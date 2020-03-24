package com.karljeong.fourtysix.application.login.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karljeong.fourtysix.application.login.service.LoginService;
import com.karljeong.fourtysix.config.security.WebSecurityProvider;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.User;

import resulthandler.ResultDto;
import resulthandler.ResultDto.ResultCodeEnum;

@RestController
@RequestMapping("/v1/api/login")
public class LoginRestController {

	final LoginService loginService;
	final WebSecurityProvider webSecurityProvider;

	LoginRestController(LoginService loginService, WebSecurityProvider webSecurityProvider) {
		this.loginService = loginService;
		this.webSecurityProvider = webSecurityProvider;
	}

	@PostMapping("/f")
	public ResultDto login(HttpServletRequest req, @RequestBody Map<String, Object> requestBody) {
		ResultDto resultDto = new ResultDto();
		String accessToken = (String) requestBody.get("access_token");
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_12);
		try {
			User faebookUser = facebookClient.fetchObject("me", User.class,
					Parameter.with("fields", "email, name, id, picture"));
			if (faebookUser != null) {
				resultDto = webSecurityProvider.googleAuthenticate(faebookUser, req);
			}
		} catch (FacebookException ex) {
			resultDto.setResultMsg("Failed access to Facebook!");
			resultDto.setResultCd(ResultCodeEnum.FAIL_REDIRECT);
			resultDto.setLinkUrl("/login");
		}

		return resultDto;
	}

}
