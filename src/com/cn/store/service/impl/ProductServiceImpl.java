package com.cn.store.service.impl;

import java.util.List;

import com.cn.store.dao.ProductDao;
import com.cn.store.dao.impl.ProductDaoImpl;
import com.cn.store.domain.PageBean;
import com.cn.store.domain.Product;
import com.cn.store.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao pdao = new ProductDaoImpl();
	@Override
	public List<Product> findByHot() {
		return pdao.findByHot();
	}

	@Override
	public List<Product> findByNew() {
		return pdao.findByNew();
	}

	@Override
	public Product findById(String pid) {
		return pdao.findById(pid);
	}

	@Override
	public PageBean<Product> findByCid(String cid, int pageNumber, int pageSize) {
		int totalReord = pdao.findTotalRecordByCid(cid);
		PageBean<Product> pageBean = new PageBean<>(pageNumber,pageSize,totalReord) ;
		List<Product> data = pdao.findAllByCid(cid, pageBean.getStartIndex(), pageSize);
		pageBean.setData(data);
		return pageBean;
	}

	@Override
	public PageBean<Product> findByPage(int pageNumber, int pageSize) {
		int totalRecord = pdao.findTotalRecord();
		PageBean<Product> pageBean = new PageBean<Product>(pageNumber,pageSize,totalRecord);
		List<Product> data = pdao.findAll(pageBean.getPageNumber(), pageBean.getPageSize());
		pageBean.setData(data);
		return pageBean;
	}

	@Override
	public void save(Product product) {
		pdao.save(product);
		
	}

}
