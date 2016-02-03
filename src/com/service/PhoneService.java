package com.service;

import java.sql.SQLException;

import com.vos.HungupVo;

public interface PhoneService {

	public int hungup(HungupVo vo) throws SQLException;

	public int survey(HungupVo vo) throws SQLException;

	public int link(HungupVo vo) throws SQLException;

}
