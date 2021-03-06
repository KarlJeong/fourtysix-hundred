package com.karljeong.fourtysix.application.user.article.general.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.karljeong.fourtysix.application.user.article.general.service.GeneralService;
import com.karljeong.fourtysix.common.loadstatic.LoadStatic;
import com.karljeong.fourtysix.database.entity.TbArticleGeneral;
import com.karljeong.fourtysix.database.entity.TbArticleGeneralLikePK;
import com.karljeong.fourtysix.utils.PagingUtil;
import com.karljeong.fourtysix.utils.RequestContextUtil;
import com.karljeong.fourtysix.utils.UserUtil;
import com.karljeong.fourtysix.utils.ValidationUtil;

@Controller
@RequestMapping("/b/general")
public class GeneralController {

	private final LoadStatic loadStatic;
	private final GeneralService generalService;

	@Autowired
	GeneralController(LoadStatic loadStatic, GeneralService generalService) {
		this.loadStatic = loadStatic;
		this.generalService = generalService;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/viewmain")
	public String viewMain(Model model, @RequestParam(required = false) Map<String, Object> searchRequest,
			final Pageable pageable) {

		List<Map<String, Object>> articleNumberList = (List<Map<String, Object>>) loadStatic.getSystemCode()
				.get("ARTICLE_NUMBER").get("code");
		if (!ValidationUtil.isValidPageSize(pageable.getPageSize(), articleNumberList)) {
			throw new RuntimeException("Invalid Paging Request.");
		}

		Page<TbArticleGeneral> articleGeneralList = generalService.readList(searchRequest, PageRequest
				.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("articleWriteDatetime").descending()));
		model.addAttribute("articleList", articleGeneralList);
		model.addAttribute("articleNumber", articleNumberList);
		model.addAttribute("paging",
				PagingUtil.getPageList(articleGeneralList.getTotalPages(), articleGeneralList.getNumber()));

		List<Map<String, Object>> generalArticleCategoryList = (List<Map<String, Object>>) loadStatic.getSystemCode()
				.get("ART_GENERAL_CATEGORY").get("code");
		model.addAttribute("generalArticleCategoryList", generalArticleCategoryList);
		model.addAttribute("ARTICLECATEGORYCV", searchRequest.get("ARTICLECATEGORYCV"));

		return "view/article/general/general";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/viewcreate")
	public String viewCreate(Model model) {
		List<Map<String, Object>> generalArticleCategoryList = (List<Map<String, Object>>) loadStatic.getSystemCode()
				.get("ART_GENERAL_CATEGORY").get("code");
		model.addAttribute("generalArticleCategoryList", generalArticleCategoryList);
		return "view/article/general/generalC";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/viewupdate/{articleId}")
	public String viewupdate(Model model, @PathVariable("articleId") BigInteger articleId, HttpServletResponse response)
			throws IOException {
		TbArticleGeneral tbArticleGeneral = generalService.findById(articleId);

		if (UserUtil.hasEditingAuth(tbArticleGeneral.getArticleWriterId())) {
			model.addAttribute("articleInfo", tbArticleGeneral);
			List<Map<String, Object>> generalArticleCategoryList = (List<Map<String, Object>>) loadStatic.getSystemCode()
					.get("ART_GENERAL_CATEGORY").get("code");
			model.addAttribute("generalArticleCategoryList", generalArticleCategoryList);
			return "view/article/general/generalU";
		}
		
		response.sendRedirect(RequestContextUtil.getContextpath() + "/b/general/viewdetail/" + articleId);
		return null;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/viewdetail/{articleId}")
	public String viewUpdate(Model model, @PathVariable("articleId") BigInteger articleId,
			@RequestParam(required = false) Map<String, Object> searchRequest, final Pageable pageable,
			HttpServletRequest request) {
		model.addAttribute("articleInfo", generalService.findById(articleId));

		List<Map<String, Object>> articleNumberList = (List<Map<String, Object>>) loadStatic.getSystemCode()
				.get("ARTICLE_NUMBER").get("code");
		if (!ValidationUtil.isValidPageSize(pageable.getPageSize(), articleNumberList)) {
			throw new RuntimeException("Invalid Paging Request.");
		}

		Page<TbArticleGeneral> articleGeneralList = generalService.readList(searchRequest, pageable);
		model.addAttribute("articleList", articleGeneralList);
		model.addAttribute("articleNumber", articleNumberList);
		model.addAttribute("paging",
				PagingUtil.getPageList(articleGeneralList.getTotalPages(), articleGeneralList.getNumber()));

		TbArticleGeneralLikePK tbArticleGeneralLikePK = new TbArticleGeneralLikePK();
		tbArticleGeneralLikePK.setArticleId(articleId);
		tbArticleGeneralLikePK.setUserInfo(request);
		model.addAttribute("articleLike", generalService.findById(tbArticleGeneralLikePK));

		return "view/article/general/generalR";
	}
}
