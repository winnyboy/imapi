package com.huione.im.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huione.im.api.service.GroupConfigService;
import com.huione.im.api.common.AjaxJson;
import com.huione.im.api.common.Constants;
import com.huione.im.api.common.DateUtil;
import com.huione.im.api.entity.GroupConfig;

/**
 * 群组信息接口
 * @author winnyboy
 * @since 2017-11-18
 */
@RestController
public class GroupConfigController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
			
	@Resource
	private GroupConfigService groupConfigService;

	@RequestMapping(value = "/grpcfg/g", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	public AjaxJson getGroupConfigByUserId(@RequestParam(name = "uid", required = false) String userId,
			@RequestParam(name = "gid", required = false) String groupId) {
		List<GroupConfig> list = null;
		AjaxJson result = new AjaxJson();
		if(StringUtils.isEmpty(userId) && StringUtils.isEmpty(groupId)) {
			result.setMsg("缺少参数,群id或手机号码至少需要一个");
			result.setCode(Constants.RETURN_CODE_404);
			return result;
		}
		try {
			logger.debug("find user's groups");
			if(!StringUtils.isEmpty(userId)) {
				userId = userId.trim();
			}
			if(!StringUtils.isEmpty(groupId)) {
				groupId = groupId.trim();
			}
			
			String[] groupIds = null;
			if(groupId.indexOf(",")> 0) {
				groupIds = groupId.split(",");
			}else{
				groupIds = new String[]{groupId};
			}
			
			list = groupConfigService.select(userId, groupIds);
			
			Map<String, Object> attrs = new HashMap<String, Object>(15);
			attrs.put("list", list);
			if (list == null) {
				logger.debug("no data found");
				result.setMsg("未获取数据");
				result.setCode(Constants.RETURN_CODE_404);
			} else {
				logger.debug("data found");
				result.setAttributes(attrs);
				result.setCode(Constants.RETURN_CODE_200);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setMsg("服务器发生异常");
			result.setCode(Constants.RETURN_CODE_404);
		}
		

		return result;
	}

	@RequestMapping(value = "/grpcfg/save", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	public AjaxJson saveGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "uid",required=false) String userId, @RequestParam(name = "gn",required=false) String groupName,
			@RequestParam(name = "up") String userPhone, @RequestParam(name = "ed") String expiredDate,
			@RequestParam(name = "dt") String dissolveType) {
		
		if(!StringUtils.isEmpty(userPhone)) {
			userPhone = userPhone.trim();
		}
		if(!StringUtils.isEmpty(groupId)) {
			groupId = groupId.trim();
		}
		
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setGroupId(groupId.trim());
		groupConfig.setUserId(userId);
		groupConfig.setUserPhone(userPhone);
		groupConfig.setGroupName(groupName);
		groupConfig.setExpiredDate(DateUtil.strToDate(expiredDate));
		groupConfig.setDissolveType(dissolveType);
		groupConfig.setId(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
		int result = 0;
		AjaxJson json = new AjaxJson();
		try {
			logger.debug("verify group duplicate");
			String[] groupIds = null;
			if(groupId.indexOf(",")> 0) {
				groupIds = groupId.split(",");
			}else{
				groupIds = new String[]{groupId};
			}
			
			List<GroupConfig> list = groupConfigService.select(userId, groupIds);
			
			if(list!=null && list.size()>0) {
				logger.debug("begin save data");
				json.setMsg("请勿重复保存");
				json.setCode(Constants.RETURN_CODE_404);
			}else{
				logger.debug("begin save data");
				result = groupConfigService.save(groupConfig);
				json.setMsg("保存成功");
				json.setCode(Constants.RETURN_CODE_200);
			}
		} catch (Exception e) {
			logger.error("exception"+e.getMessage());
			json.setMsg("服务器异常");
			json.setCode(Constants.RETURN_CODE_404);
			e.printStackTrace();
		}
 		 
		return json;
	}
	
	@RequestMapping(value = "/grpcfg/update", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	public AjaxJson updateGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "uid",required=false) String userId, @RequestParam(name = "gn",required=false) String groupName,
			@RequestParam(name = "up") String userPhone, @RequestParam(name = "ed") String expiredDate,
			@RequestParam(name = "dt") String dissolveType) {
		
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setGroupId(groupId.trim());
		groupConfig.setUserId(userId);
		groupConfig.setUserPhone(userPhone.trim());
		groupConfig.setGroupName(groupName);
		groupConfig.setExpiredDate(DateUtil.strToDate(expiredDate));
		groupConfig.setDissolveType(dissolveType);
		 
		int result = 0;
		AjaxJson json = new AjaxJson();
		try {
			String[] groupIds = new String[]{groupId};
			List<GroupConfig> list = groupConfigService.select(userId, groupIds);
			
			if(list!=null && list.size()>0) {
				logger.debug("begin update group");
				result = groupConfigService.update(groupConfig);
			}else {
				logger.debug("begin save group");
				result = groupConfigService.save(groupConfig);
			}
			json.setCode(Constants.RETURN_CODE_200);
		} catch (Exception e) {
			logger.error("exception"+e.getMessage());
			json.setMsg("服务器异常");
			json.setCode(Constants.RETURN_CODE_404);
			e.printStackTrace();
		}
 		 
		return json;
	}
	
	@RequestMapping(value = "/grpcfg/delete", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	public AjaxJson deleteGroupConfig(@RequestParam(name = "gid") String groupId,
			@RequestParam(name = "up",required=false) String userPhone) {
		
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setGroupId(groupId.trim());
		groupConfig.setUserPhone(userPhone.trim());
		 
		int result = 0;
		AjaxJson json = new AjaxJson();
		try {
			logger.debug("begin delete group");
			result = groupConfigService.delete(groupConfig);
			json.setCode(Constants.RETURN_CODE_200);
		} catch (Exception e) {
			logger.error("exception"+e.getMessage());
			json.setMsg("操作失败");
			json.setCode(Constants.RETURN_CODE_404);
			e.printStackTrace();
		}
 		 
		return json;
	}

	public GroupConfigService getGroupConfigService() {
		return groupConfigService;
	}

	public void setGroupConfigService(GroupConfigService groupConfigService) {
		this.groupConfigService = groupConfigService;
	}

}
