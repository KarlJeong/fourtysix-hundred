package com.karljeong.fourtysix.config.security;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import com.karljeong.fourtysix.application.signin.service.SigninService;
import com.karljeong.fourtysix.common.dto.ResultDto;
import com.karljeong.fourtysix.common.dto.ResultDto.ResultCodeEnum;
import com.karljeong.fourtysix.database.entity.TbComAuth;
import com.karljeong.fourtysix.database.entity.TbComUser;
import com.restfb.types.User;

@Component
public class WebSecurityProvider implements AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	final SigninService signinService;

	WebSecurityProvider(SigninService signinService) {
		this.signinService = signinService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info(" :: Login with Spring Security Begins :: ");
		TbComUser tbComUser = this.getUserInfo(authentication.getName(), (String) authentication.getCredentials());
		if (tbComUser == null) {
			throw new BadCredentialsException("Invalid Id or Password.");
		}

		int isBanned = this.getUserBanInfo(tbComUser.getUserId());

		if (isBanned > 0) {
			throw new BadCredentialsException("Banned User.");
		}

		List<TbComAuth> tbComAuths = this.findAuthsInfoByUserId(tbComUser.getUserId());

		return new UsernamePasswordAuthenticationToken(tbComUser, null, this.getAuthorities(tbComAuths));
	}

	public ResultDto facebookAuthenticate(User faebookUser, HttpServletRequest req) {
		SecurityContext sc = SecurityContextHolder.getContext();

		TbComUser tbComUser = this.getUserInfo(faebookUser.getEmail());
		ResultDto resultDto = new ResultDto();
		if (tbComUser == null) {
			resultDto.setResultMsg("Signup First Please!");
			resultDto.setResultCd(ResultCodeEnum.FAIL_REDIRECT);
			resultDto.setLinkUrl("/login/f");
			return resultDto;
		}

		int isBanned = this.getUserBanInfo(tbComUser.getUserId());

		if (isBanned > 0) {
			resultDto.setResultMsg("Banned User");
			resultDto.setResultCd(ResultCodeEnum.FAIL_REDIRECT);
			resultDto.setLinkUrl("/login/f");
			return resultDto;
		}

		List<TbComAuth> tbComAuths = this.findAuthsInfoByUserId(tbComUser.getUserId());

		sc.setAuthentication(new UsernamePasswordAuthenticationToken(tbComUser, null, this.getAuthorities(tbComAuths)));
		HttpSession session = req.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
		resultDto.setData(tbComUser);
		resultDto.setResultCd(ResultCodeEnum.SUCCESS_REDIRECT);
		resultDto.setLinkUrl("/admin/codegroup/viewmain");
		return resultDto;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private ArrayList<SimpleGrantedAuthority> getAuthorities(List<TbComAuth> tbComAuths) {
		ArrayList<SimpleGrantedAuthority> authrotyList = new ArrayList<SimpleGrantedAuthority>();
		tbComAuths.forEach(auth -> authrotyList.add(new SimpleGrantedAuthority("ROLE_" + auth.getAuthCode())));
		return authrotyList;
	}

	private TbComUser getUserInfo(String loginId, String password) {
		// return loginService.findByLoginIdAndLoginPassword(loginId,
		// EncryptUtil.encryptWithSha256(password));
		return signinService.findByLoginIdAndLoginPassword(loginId, password);
	}

	private TbComUser getUserInfo(String loginId) {
		return signinService.findByLoginId(loginId);
	}

	private int getUserBanInfo(BigInteger userId) {
		return signinService.findBannedInfoByUserId(userId);
	}

	private List<TbComAuth> findAuthsInfoByUserId(BigInteger userId) {
		return signinService.findAuthsInfoByUserId(userId);
	}

}
