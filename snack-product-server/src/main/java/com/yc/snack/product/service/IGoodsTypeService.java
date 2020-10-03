package com.yc.snack.product.service;

import java.util.List;

import com.yc.snack.product.bean.GoodsType;

/**
 * 商品类型的业务接口
 * @author lalala
 *
 */
public interface IGoodsTypeService {
	
	public int add(GoodsType type);
	
	public int update(GoodsType type);
	
	public List<GoodsType> findAll();
	
	public List<GoodsType> finds();
}
