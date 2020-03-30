package com.yc.fresh.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.fresh.entity.CartInfo;
import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.service.ICartInfoService;

@RestController
@RequestMapping("/cart")
public class CartInfoController {
	@Autowired
	private ICartInfoService service;
	
	private MemberInfo getMemberInfo(HttpSession session) {
		Object obj = session.getAttribute("loginMember");
		if (obj == null) { // 说明没有登录
			return null;
		}
		return (MemberInfo) obj;
	}

	@RequestMapping("/getInfo")
	public List<Map<String, Object>> getInfo(HttpSession session) {
		MemberInfo mf = this.getMemberInfo(session);
		if (mf == null) { // 说明没有登录
			return Collections.emptyList();
		}
		return service.findByMno(mf.getMno()); // 根据会员编号，查询购物车信息
	}
	
	@RequestMapping("/update")
	public int update(CartInfo cf) {
		cf.setNum(1);
		return service.update(cf);
	}
	
	@RequestMapping("/updates")
	public int updates(CartInfo cf) {
		return service.update(cf);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/add")
	public String add(HttpSession session, CartInfo cf) {
		String cno = service.add(cf);
		if (!"0".equals(cno)) { // 说明加入购物车成功
			Object obj = session.getAttribute("cartInfos");
			if (obj != null) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cno", cno);
				map.put("gno", cf.getGno());
				list.add(map);
				session.setAttribute("cartInfos", list);
			}
		}
		return cno; 
	}
	
	@RequestMapping("/finds")
	public List<CartInfo> finds(HttpSession session) {
		MemberInfo mf = this.getMemberInfo(session);
		if (mf == null) { // 说明没有登录
			return null;
		}
		return service.finds(mf.getMno());
	}
	
	@RequestMapping("/del")
	public int del(String cno, HttpSession session) {
		return service.deleteByCno(cno);
	}
	
	@RequestMapping("/findByCnos")
	public List<CartInfo> findByCnos(String cnos) {
		return service.findByCnos(cnos.split(","));
	}
}
