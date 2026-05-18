package in.com.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TProductBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TProductModel;

public class TProductTest {

	public static void main(String[] args) throws Exception {

		testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		// testFindByName();
		// testSearch();

	}

	private static void testAdd() throws ApplicationException, DuplicateRecordException {

		TProductBean bean = new TProductBean();

		bean.setProductName("Laptop");
		bean.setBrand("Dell");
		bean.setPrice(55000);
		bean.setQuantity(10);

		TProductModel model = new TProductModel();

		long pk = model.add(bean);

		System.out.println("Data Added Successfully : " + pk);

	}

	private static void testUpdate() throws Exception {

		TProductModel model = new TProductModel();

		TProductBean bean = model.findByPk(1);

		bean.setProductName("Mobile");
		bean.setBrand("Samsung");
		bean.setPrice(25000);
		bean.setQuantity(5);

		model.update(bean);

		System.out.println("Data Updated Successfully");

	}

	private static void testDelete() throws Exception {

		TProductBean bean = new TProductBean();

		bean.setProductId(1);

		TProductModel model = new TProductModel();

		model.delete(bean);

		System.out.println("Data Deleted Successfully");

	}

	private static void testFindByPk() throws Exception {

		TProductModel model = new TProductModel();

		TProductBean bean = model.findByPk(1);

		System.out.println(bean.getProductId());
		System.out.println(bean.getProductName());
		System.out.println(bean.getBrand());
		System.out.println(bean.getPrice());
		System.out.println(bean.getQuantity());

	}

	private static void testFindByName() throws Exception {

		TProductModel model = new TProductModel();

		TProductBean bean = model.findByName("Laptop");

		System.out.println(bean.getProductId());
		System.out.println(bean.getProductName());
		System.out.println(bean.getBrand());
		System.out.println(bean.getPrice());
		System.out.println(bean.getQuantity());

	}

	private static void testSearch() {

		try {

			TProductBean bean = new TProductBean();

			TProductModel model = new TProductModel();

			List list = new ArrayList();

			bean.setBrand("Dell");

			list = model.search(bean, 1, 10);

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TProductBean) it.next();

				System.out.println(bean.getProductId());
				System.out.println(bean.getProductName());
				System.out.println(bean.getBrand());
				System.out.println(bean.getPrice());
				System.out.println(bean.getQuantity());

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}