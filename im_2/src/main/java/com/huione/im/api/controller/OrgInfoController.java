package com.huione.im.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huione.im.api.common.AjaxJson;
import com.huione.im.api.common.Constants;
import com.huione.im.api.entity.OrgInfo;
import com.huione.im.api.service.OrgInfoService;

@RestController
public class OrgInfoController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private OrgInfoService orgInfoService;

	@RequestMapping(value = "/org/g", method = RequestMethod.GET, consumes = "application/json")
	public AjaxJson getOrgInfo(@RequestParam(name = "oid", required = false) String orgId,
			@RequestParam(name = "pid", required = false) String parentId,
			@RequestParam(name = "up", required = false) String userPhone) {

		if (StringUtils.isEmpty(orgId) && StringUtils.isEmpty(parentId) && StringUtils.isEmpty(userPhone)) {
			parentId = "0";
		}
		AjaxJson j = new AjaxJson();
		try {
			List<OrgInfo> list = orgInfoService.select(orgId, userPhone, parentId);

			Map<String, Object> attrs = new HashMap<String, Object>();
			attrs.put("list", list);
			if (list == null) {
				logger.debug("no data found");
				j.setMsg("未获取数据");
				j.setCode(Constants.RETURN_CODE_404);
			} else {
				logger.debug("data found");
				j.setAttributes(attrs);
				j.setCode(Constants.RETURN_CODE_200);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setMsg("服务器发生异常");
			j.setCode(Constants.RETURN_CODE_404);
		}
		return j;
	}

	@RequestMapping(value = "/org/update", method = RequestMethod.GET, consumes = "application/json")
	public AjaxJson updateOrgInfo(@RequestParam(name = "oid", required = false) String orgId,
			@RequestParam(name = "pid", required = false) String parentId,
			@RequestParam(name = "up") String userPhone,
			@RequestParam(name = "pt", required = false) String position,
			@RequestParam(name = "ep", required = false) String empName,
			@RequestParam(name = "em", required = false) String email) {

		AjaxJson j = new AjaxJson();

		OrgInfo orgInfo = new OrgInfo();
		orgInfo.setParentId(parentId);
		orgInfo.setOrgType("2"); //个人
		orgInfo.setOrgName(userPhone.trim());
		orgInfo.setPosition(position);
		orgInfo.setEmpName(empName);
		orgInfo.setEmail(email);

		try {
			List<OrgInfo> list = orgInfoService.select(orgId, userPhone, parentId);

			if (null != list && list.size() > 0) {
				orgInfoService.update(orgInfo);
			} else {
				orgInfo.setId(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
				orgInfoService.save(orgInfo);
			}

			logger.debug("save org data ok");
			 
			j.setCode(Constants.RETURN_CODE_200);

		} catch (Exception e) {
			logger.error(e.getMessage());
			j.setMsg("服务器发生异常");
			j.setCode(Constants.RETURN_CODE_404);
			e.printStackTrace();
		}
		return j;
	}

	public OrgInfoService getOrgInfoService() {
		return orgInfoService;
	}

	public void setOrgInfoService(OrgInfoService orgInfoService) {
		this.orgInfoService = orgInfoService;
	}

}
