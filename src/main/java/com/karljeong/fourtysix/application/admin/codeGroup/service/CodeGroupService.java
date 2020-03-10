package com.karljeong.fourtysix.application.admin.codeGroup.service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.karljeong.fourtysix.database.entity.TbComCode;
import com.karljeong.fourtysix.database.entity.TbComCodeGroup;
import com.karljeong.fourtysix.database.repository.TbComCodeGroupRepository;
import com.karljeong.fourtysix.database.repository.TbComCodeRepository;
import com.karljeong.fourtysix.database.specification.TbComCodeGroupSpec;
import com.karljeong.fourtysix.database.specification.TbComCodeGroupSpec.SearchKey;

@Service
public class CodeGroupService {

	TbComCodeGroupRepository tbComCodeGroupRepository;
	TbComCodeRepository tbComCodeRepository;

	CodeGroupService(TbComCodeGroupRepository tbComCodeGroupRepository, TbComCodeRepository tbComCodeRepository) {
		this.tbComCodeGroupRepository = tbComCodeGroupRepository;
		this.tbComCodeRepository = tbComCodeRepository;
	}

	public Page<TbComCodeGroup> readList(Map<String, Object> searchRequest, Pageable pageable) {
		Map<SearchKey, Object> searchKeys = new HashMap<>();
		for (String key : searchRequest.keySet()) {
			if (searchRequest.get(key) != null && !"".equals(searchRequest.get(key))) {
				if (!Arrays.asList(new String[] { "SIZE", "PAGE", "SORT" }).contains(key.toUpperCase())) {
					searchKeys.put(SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
				}
			}
		}
		Page<TbComCodeGroup> comCodeGroupList = searchKeys.isEmpty() ? tbComCodeGroupRepository.findAll(pageable)
				: tbComCodeGroupRepository.findAll(TbComCodeGroupSpec.searchWithKeys(searchKeys), pageable);

		for (TbComCodeGroup tbComCodeGroup : comCodeGroupList) {
			tbComCodeGroup.setTbComCodes(tbComCodeRepository.findByCodeGroupId(tbComCodeGroup.getCodeGroupId()));
		}

		return comCodeGroupList;
	}

	public TbComCodeGroup create(TbComCodeGroup tbComCodeGroup) {
		tbComCodeGroup.setCreateUserId(BigInteger.valueOf(11111));
		TbComCodeGroup save = tbComCodeGroupRepository.save(tbComCodeGroup);
		List<TbComCode> tbComCodes = tbComCodeGroup.getTbComCodes();
		for (TbComCode tbComCode : tbComCodes) {
			tbComCodeRepository.saveCodeGroupId(BigInteger.valueOf(11111), tbComCode.getCodeId(),
					tbComCodeGroup.getCodeGroupId());
		}
		return save;
	}

	public TbComCodeGroup update(TbComCodeGroup tbComCodeGroup) {
		tbComCodeGroup.setUpdateUserId(BigInteger.valueOf(11111));
		TbComCodeGroup save = tbComCodeGroupRepository.save(tbComCodeGroup);
		List<TbComCode> tbComCodes = tbComCodeGroup.getTbComCodes();

		if (tbComCodes.size() > 0) {
			tbComCodeRepository.deleteCodeGroupId(BigInteger.valueOf(11111), tbComCodeGroup.getCodeGroupId());

			for (TbComCode tbComCode : tbComCodes) {
				tbComCodeRepository.saveCodeGroupId(BigInteger.valueOf(11111), tbComCode.getCodeId(),
						tbComCodeGroup.getCodeGroupId());
			}
		}
		return save;

	}

	public TbComCodeGroup findById(BigInteger codeGroupId) {
		TbComCodeGroup tbComCodeGroup = tbComCodeGroupRepository.findById(codeGroupId);
		tbComCodeGroup.setTbComCodes(tbComCodeRepository.findByCodeGroupId(codeGroupId));
		return tbComCodeGroup;
	}
}
