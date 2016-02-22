package com.service.imp;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.dao.PhoneDao;
import com.service.PhoneService;
import com.vos.HungupVo;

@Service
public class PhoneServiceImp implements PhoneService {

	private PhoneDao phoneDao;

	public PhoneDao getPhoneDao() {
		return phoneDao;
	}

	public void setPhoneDao(PhoneDao phoneDao) {
		this.phoneDao = phoneDao;
	}

	@Override
	public int hungup(HungupVo vo) throws SQLException {
		// TODO Auto-generated method stub
		return phoneDao.hungup(vo);
	}

	@Override
	public int survey(HungupVo vo) throws SQLException {
		// TODO Auto-generated method stub
		return phoneDao.survey(vo);
	}

	@Override
	public int link(HungupVo vo) throws SQLException {
		// TODO Auto-generated method stub
		return phoneDao.link(vo);
	}

}
