package com.huione.im.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huione.im.api.common.AjaxJson;

@FeignClient(name = "API-CLIENT",fallback=HystrixServiceFailure.class)
public interface GroupService {

	@RequestMapping(value = "/grpcfg/g", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	AjaxJson getGroupConfigByUserId(@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gid", required = false) String groupId, @RequestParam("t") String t);

	@RequestMapping(value = "/grpcfg/save", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	AjaxJson saveGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gn", required = false) String groupName, @RequestParam(name = "up") String userPhone,
			@RequestParam(name = "ed") String expiredDate, @RequestParam(name = "dt") String dissolveType,
			@RequestParam("t") String t);

	@RequestMapping(value = "/grpcfg/update", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	AjaxJson updateGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gn", required = false) String groupName, @RequestParam(name = "up") String userPhone,
			@RequestParam(name = "ed") String expiredDate, @RequestParam(name = "dt") String dissolveType,
			@RequestParam("t") String t);

	@RequestMapping(value = "/grpcfg/delete", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	AjaxJson deleteGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "up",required=false) String userPhone, @RequestParam("t") String t);
}
