package in.com.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TBankingBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TBankingModel;

public class TBankingTest {

	public static void main(String[] args) throws Exception {

		testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		// testFindByName();
		// testSearch();

	}

	private static void testAdd() throws ApplicationException, DuplicateRecordException {

		TBankingBean bean = new TBankingBean();

		bean.setHolderName("Anmol");
		bean.setBalance(50000);
		bean.setAccountType("Saving");
		bean.setTransactionId(1001);

		TBankingModel model = new TBankingModel();

		long pk = model.add(bean);

		System.out.println("Data Added Successfully : " + pk);

	}

	private static void testUpdate() throws Exception {

		TBankingModel model = new TBankingModel();

		TBankingBean bean = model.findByPk(1);

		bean.setHolderName("Rahul");
		bean.setBalance(80000);
		bean.setAccountType("Current");
		bean.setTransactionId(2001);

		model.update(bean);

		System.out.println("Data Updated Successfully");

	}

	private static void testDelete() throws Exception {

		TBankingBean bean = new TBankingBean();

		bean.setAccountNo(1);

		TBankingModel model = new TBankingModel();

		model.delete(bean);

		System.out.println("Data Deleted Successfully");

	}

	private static void testFindByPk() throws Exception {

		TBankingModel model = new TBankingModel();

		TBankingBean bean = model.findByPk(1);

		System.out.println(bean.getAccountNo());
		System.out.println(bean.getHolderName());
		System.out.println(bean.getBalance());
		System.out.println(bean.getAccountType());
		System.out.println(bean.getTransactionId());

	}

	private static void testFindByName() throws Exception {

		TBankingModel model = new TBankingModel();

		TBankingBean bean = model.findByName("Anmol");

		System.out.println(bean.getAccountNo());
		System.out.println(bean.getHolderName());
		System.out.println(bean.getBalance());
		System.out.println(bean.getAccountType());
		System.out.println(bean.getTransactionId());

	}

	private static void testSearch() {

		try {

			TBankingBean bean = new TBankingBean();

			TBankingModel model = new TBankingModel();

			List list = new ArrayList();

			bean.setAccountType("Saving");

			list = model.search(bean, 1, 10);

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TBankingBean) it.next();

				System.out.println(bean.getAccountNo());
				System.out.println(bean.getHolderName());
				System.out.println(bean.getBalance());
				System.out.println(bean.getAccountType());
				System.out.println(bean.getTransactionId());

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}