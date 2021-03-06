package com.karljeong.fourtysix.application.admin.pattern.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karljeong.fourtysix.application.admin.pattern.service.PatternService;
import com.karljeong.fourtysix.database.entity.TbComPattern;
import com.karljeong.fourtysix.resulthandler.DataTableDto;
import com.karljeong.fourtysix.resulthandler.ResultDto;
import com.karljeong.fourtysix.resulthandler.ResultSetter;
import com.karljeong.fourtysix.resulthandler.ResultDto.ResultCodeEnum;

@RestController
@RequestMapping("/v1/api/admin/pattern")
public class PatternRestController {

    private final PatternService patternService;

    @Autowired
	PatternRestController(PatternService patternService) {
		this.patternService = patternService;
	}

	@GetMapping
	public ResultDto readList(@RequestParam(required = false) Map<String, Object> searchRequest,
			final Pageable pageable) {
	    return new ResultSetter(ResultCodeEnum.SUCCESS, null, patternService.readList(searchRequest, pageable), null).getResultDto();
	}

	@GetMapping("/datatable")
	public DataTableDto readListForDataTable(@RequestParam(required = true) MultiValueMap<String, String> formData) {
	    int start = Integer.parseInt(formData.get("start").get(0));
	    int length = Integer.parseInt(formData.get("length").get(0));
	    
	    Page<TbComPattern> retrievedPattern = patternService.readList(new HashMap<String, Object>(), PageRequest.of(start / length , length, Sort.by("uriPattern").ascending().and(Sort.by("createDatetime").descending())));

	    DataTableDto dto = new DataTableDto();
	    dto.setDraw(Integer.parseInt(formData.get("draw").get(0)));
	    dto.setRecordsFiltered((int) retrievedPattern.getTotalElements());
	    dto.setRecordsTotal((int) retrievedPattern.getTotalElements());
	    dto.setData(retrievedPattern.toList());

	    return dto;

	}

    @PostMapping
    public ResultDto create(@RequestBody TbComPattern tbComPattern) {
        TbComPattern createdTbComPattern = patternService.create(tbComPattern);
        return new ResultSetter(ResultCodeEnum.SUCCESS_REDIRECT_ALERT, "Saved Successfully", createdTbComPattern, "/admin/pattern/viewupdate/" + createdTbComPattern.getPatternId()).getResultDto();
    }

    @PostMapping("/{patternId}")
    public ResultDto update(@RequestBody TbComPattern tbComPattern,
            @PathVariable("patternId") BigInteger patternId) {
        TbComPattern updatedTbComPattern = patternService.update(tbComPattern);
        return new ResultSetter(ResultCodeEnum.SUCCESS_REDIRECT_ALERT, "Modified Successfully", updatedTbComPattern, "/admin/pattern/viewupdate/" + updatedTbComPattern.getPatternId()).getResultDto();
    }

    @DeleteMapping("/{patternId}")
    public ResultDto delete(@PathVariable("patternId") BigInteger patternId) {
        patternService.delete(patternId);
        return new ResultSetter(ResultCodeEnum.SUCCESS_REDIRECT_ALERT, "Deleted Successfully", null, "/admin/pattern/viewmain").getResultDto();
    }

}
