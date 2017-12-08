package com.huione.im.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huione.im.api.dao.OrgInfoDao;
import com.huione.im.api.entity.OrgInfo;

@Service
public class OrgInfoService {

	@Autowired
	private OrgInfoDao orgInfoDao;
 
	public List<OrgInfo> select(String orgId, String userPhone, String parentId){
		OrgInfo orgInfo = new OrgInfo();
		orgInfo.setOrgName(userPhone);
		orgInfo.setParentId(parentId);
		orgInfo.setId(orgId);
		return orgInfoDao.select(orgInfo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int save(OrgInfo orgInfo) {
		return orgInfoDao.save(orgInfo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int update(OrgInfo orgInfo) {
		return orgInfoDao.update(orgInfo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int delete(OrgInfo orgInfo) {
		return orgInfoDao.delete(orgInfo);
	}
}
