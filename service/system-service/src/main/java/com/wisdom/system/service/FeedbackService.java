/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.system.service;

import com.wisdom.common.dto.system.SuggestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class FeedbackService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public SuggestionDto addSuggestion(String userId, String suggestion) {

		SuggestionDto suggestionDto=new SuggestionDto();
		suggestionDto.setUserId(userId);
		suggestionDto.setSuggestion(suggestion);
		this.mongoTemplate.insert(suggestionDto, "suggestion");
		return suggestionDto;
	}

}
