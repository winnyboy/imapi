package com.huione.im.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huione.im.api.dao.GroupConfigDao;
import com.huione.im.api.entity.GroupConfig;

@Service
public class GroupConfigService {
	
	@Autowired
	private GroupConfigDao groupConfigDao;
 
	public List<GroupConfig> select(String userPhone, String[] groupIds){
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setUserPhone(userPhone);
		groupConfig.setGroupIds(groupIds);
		return groupConfigDao.select(groupConfig);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int save(GroupConfig groupConfig) {
		return groupConfigDao.save(groupConfig);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int update(GroupConfig groupConfig) {
		return groupConfigDao.update(groupConfig);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public int delete(GroupConfig groupConfig) {
		return groupConfigDao.delete(groupConfig);
	}

	public GroupConfigDao getGroupConfigDao() {
		return groupConfigDao;
	}

	public void setGroupConfigDao(GroupConfigDao groupConfigDao) {
		this.groupConfigDao = groupConfigDao;
	}
	
	
}

