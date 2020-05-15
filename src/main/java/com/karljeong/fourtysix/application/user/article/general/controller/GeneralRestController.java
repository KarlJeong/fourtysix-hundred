package com.karljeong.fourtysix.application.user.article.general.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karljeong.fourtysix.application.user.article.general.service.GeneralService;
import com.karljeong.fourtysix.database.entity.TbArticleGeneral;
import com.karljeong.fourtysix.database.entity.TbArticleGeneralReply;
import com.karljeong.fourtysix.resulthandler.ResultDto;
import com.karljeong.fourtysix.resulthandler.ResultDto.ResultCodeEnum;
import com.karljeong.fourtysix.resulthandler.ResultSetter;

@RestController
@RequestMapping("/v1/api/b/general")
public class GeneralRestController {

	private final GeneralService generalService;

	@Autowired
	GeneralRestController(GeneralService generalService) {
		this.generalService = generalService;
	}

	@GetMapping("/reply/{articleId}/{pageNumber}")
	public ResultDto readReplyList(@PathVariable("articleId") BigInteger articleId, @PathVariable("articleId") int pageNumber) {
		Page<TbArticleGeneralReply> retrievedTbArticleGeneralReply = generalService.readReplyList(articleId, pageNumber);
		return new ResultSetter(ResultCodeEnum.SUCCESS, retrievedTbArticleGeneralReply).getResultDto();
	}

	@PostMapping
	public ResultDto save(@RequestBody TbArticleGeneral tbArticleGeneral) {
		TbArticleGeneral createTbArticleGeneral = generalService.create(tbArticleGeneral);
		return new ResultSetter(ResultCodeEnum.SUCCESS_REDIRECT, "Saved Successfully", createTbArticleGeneral,
				"/b/general/viewdetail/" + createTbArticleGeneral.getArticleId()).getResultDto();
	}

	@PostMapping("/reply")
	public ResultDto reply(@RequestBody TbArticleGeneralReply tbArticleGeneralReply) {
		TbArticleGeneralReply createTbArticleReplyGeneral = generalService.reply(tbArticleGeneralReply);
		return new ResultSetter(ResultCodeEnum.SUCCESS_REDIRECT, "Saved Successfully", createTbArticleReplyGeneral,
				"/b/general/viewdetail/" + createTbArticleReplyGeneral.getArticleId()).getResultDto();
	}

}
