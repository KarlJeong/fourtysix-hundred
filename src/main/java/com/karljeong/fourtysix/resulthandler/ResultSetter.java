package com.karljeong.fourtysix.resulthandler;

import com.karljeong.fourtysix.resulthandler.ResultDto.ResultCodeEnum;

public class ResultSetter {

	private ResultDto resultDto;

	public ResultSetter(ResultCodeEnum resultCd, Object data) {
		resultDto = new ResultDto();
		this.resultDto.setResultCd(resultCd);
		this.resultDto.setData(data);
	}

	public ResultSetter(ResultCodeEnum resultCd, String resultMsg, Object data, String linkUrl) {
		resultDto = new ResultDto();
		this.resultDto.setResultCd(resultCd);
		this.resultDto.setResultMsg(resultMsg);
		this.resultDto.setData(data);
		this.resultDto.setLinkUrl(linkUrl);
	}

	public ResultDto getResultDto() {
		return resultDto;
	}

	public void setResultDto(ResultDto resultDto) {
		this.resultDto = resultDto;
	}
}
