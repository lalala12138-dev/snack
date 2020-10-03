package com.yc.snack.product.service;

import java.util.List;
import java.util.Map;

import com.yc.snack.product.bean.GoodsInfo;

/**
 * 商品信息 业务接口
 * @author 38929
 * @date 2020年6月18日
 */
public interface IGoodsInfoService {

	public int add(GoodsInfo gf);
	
	public int update(GoodsInfo gf);
	
	public GoodsInfo findByGid(String gno);
	
	public Map<String, Object> findByPage(Map<String,Object> map);
	
	public Map<String, Object> findByFirst(Map<String,Object> map);
	
	public List<GoodsInfo> finds(Map<String,Object> map);
}
