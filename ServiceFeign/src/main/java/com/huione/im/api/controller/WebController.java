package com.huione.im.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huione.im.api.common.AjaxJson;
import com.huione.im.api.service.GroupService;

@RestController
public class WebController {
	
	@Autowired GroupService groupService;
	
	@RequestMapping(value = "/grpcfg/g",method = RequestMethod.GET, produces="application/json",consumes="application/json")
	@ResponseBody
	public AjaxJson getGroupInfo(@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gid", required = false) String groupId, @RequestParam("t") String t) {
		return groupService.getGroupConfigByUserId(userId, groupId, t);
	}
	
	@RequestMapping(value = "/grpcfg/save", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	AjaxJson saveGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gn", required = false) String groupName, @RequestParam(name = "up") String userPhone,
			@RequestParam(name = "ed") String expiredDate, @RequestParam(name = "dt") String dissolveType,
			@RequestParam("t") String t) {
		return groupService.saveGroupConfig(groupId, userId, groupName, userPhone, expiredDate, dissolveType, t);
	}

	@RequestMapping(value = "/grpcfg/update", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	AjaxJson updateGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gn", required = false) String groupName, @RequestParam(name = "up") String userPhone,
			@RequestParam(name = "ed") String expiredDate, @RequestParam(name = "dt") String dissolveType,
			@RequestParam("t") String t) {
		return groupService.updateGroupConfig(groupId, userId, groupName, userPhone, expiredDate, dissolveType, t);
	}

	@RequestMapping(value = "/grpcfg/delete", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	AjaxJson deleteGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "up",required=false) String userPhone, @RequestParam("t") String t) {
		return groupService.deleteGroupConfig(groupId, userPhone, t);
	}
}
