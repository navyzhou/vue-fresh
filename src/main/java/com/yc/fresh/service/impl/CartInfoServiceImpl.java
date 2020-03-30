package com.yc.fresh.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.CartInfo;
import com.yc.fresh.mapper.ICartInfoMapper;
import com.yc.fresh.service.ICartInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class CartInfoServiceImpl implements ICartInfoService{
	@Autowired
	private ICartInfoMapper mapper;
	
	@Override
	public String add(CartInfo cf) {
		if (StringUtil.checkNull(String.valueOf(cf.getGno()), String.valueOf(cf.getMno()), String.valueOf(cf.getNum()))) {
			return "0";
		}
		String cno = UUID.randomUUID().toString().replace("-", "");
		cf.setCno(cno);
		if (mapper.add(cf) > 0) {
			return cno;
		}
		return "0";
	}

	@Override
	public List<Map<String, Object>> findByMno(int mno) {
		return mapper.findByMno(mno);
	}

	@Override
	public List<CartInfo> finds(int mno) {
		return mapper.finds(mno);
	}

	@Override
	public int delete(String[] cnos) {
		if (cnos == null || cnos.length <= 0) {
			return -1;
		}
		return mapper.delete(cnos);
	}

	@Override
	public int deleteByCno(String cno) {
		if (StringUtil.checkNull(cno)) {
			return -1;
		}
		return mapper.deleteByCno(cno);
	}

	@Override
	public int update(CartInfo cf) {
		return mapper.update(cf);
	}

	@Override
	public List<CartInfo> findByCnos(String[] cnos) {
		return mapper.findByCnos(cnos);
	}
}
