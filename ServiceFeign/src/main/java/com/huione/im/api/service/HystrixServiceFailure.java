package com.huione.im.api.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.huione.im.api.common.AjaxJson;

@Component
public class HystrixServiceFailure implements GroupService {

	@Override
	public AjaxJson getGroupConfigByUserId( String userId, String groupId, String t) {
 		AjaxJson j = new AjaxJson();
		j.setCode("404");
		j.setMsg(" getGroupConfigByUserId service is not available ! userId="+userId+":groupId="+groupId);
		return j;
	}

	@Override
	public AjaxJson saveGroupConfig(String groupId, String userId, String groupName, String userPhone,
			String expiredDate, String dissolveType, String t) {
 		AjaxJson j = new AjaxJson();
		j.setCode("404");
		j.setMsg(" saveGroupConfig service is not available ! userId="+userId+":groupId="+groupId);
		return j;
	}

	@Override
	public AjaxJson updateGroupConfig(String groupId, String userId, String groupName, String userPhone,
			String expiredDate, String dissolveType, String t) {
		AjaxJson j = new AjaxJson();
		j.setCode("404");
		j.setMsg(" updateGroupConfig service is not available ! userId="+userId+":groupId="+groupId);
		return j;
	}

	@Override
	public AjaxJson deleteGroupConfig(String groupId, String userPhone, String t) {
		AjaxJson j = new AjaxJson();
		j.setCode("404");
		j.setMsg(" deleteGroupConfig service is not available ! userId="+userPhone+":groupId="+groupId);
		return j;
	}

}
