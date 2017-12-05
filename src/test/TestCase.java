package test;

import java.util.List;

import org.junit.Test;

import com.cn.store.dao.impl.ProductDaoImpl;
import com.cn.store.domain.PageBean;
import com.cn.store.domain.Product;
import com.cn.store.service.ProductService;
import com.cn.store.service.impl.ProductServiceImpl;

public class TestCase {
	@Test
	public void test1() {
		ProductDaoImpl pdao = new ProductDaoImpl();
		int tr = pdao.findTotalRecordByCid("2");
		System.out.println(tr);
	}
	@Test
	public void test2() {
		ProductDaoImpl pdao = new ProductDaoImpl();
		List<Product> list = pdao.findAllByCid("1", 0, 10);
		for (Product pro : list) {
			System.out.println(pro);
			
		}
	}
	@Test
	public void test3() {
		 ProductService ps = new ProductServiceImpl();
		PageBean<Product> list = ps.findByCid("1", 1, 10);
		List<Product> data = list.getData();
		System.out.println(list.getTotalPage());
		for (Product pro : data) {
			System.out.println(pro);
		}
	}
}
