package in.com.rays.proj4.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TSessionBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TSessionModel;

public class TSessionTest {

	public static void main(String[] args) throws DuplicateRecordException, ParseException {

		testAdd();
		// testDelete();
		// testFindBySessionCode();
		// testFindByPk();
		// testUpdate();
		// testSearch();

	}

	// ========================== TEST SEARCH ==========================

	private static void testSearch() {

		try {

			TSessionBean bean = new TSessionBean();

			List list = new ArrayList();

			bean.setSessionCode("S001");

			TSessionModel model = new TSessionModel();

			list = model.search(bean, 0, 0);

			if (list.size() == 0) {

				System.out.println("No Record Found");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TSessionBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getSessionCode());
				System.out.println(bean.getUserName());
				System.out.println(bean.getLoginTime());
				System.out.println(bean.getStatus());

				System.out.println("-----------------------------------");
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST UPDATE ==========================

	private static void testUpdate() {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			TSessionBean bean = new TSessionBean();

			bean.setId(1L);
			bean.setSessionCode("S001");
			bean.setUserName("Anmol");
			bean.setLoginTime(sdf.parse("2002-02-02"));
			bean.setStatus("ACTIVE");

			TSessionModel model = new TSessionModel();

			model.update(bean);

			System.out.println("Record Updated Successfully");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST FIND BY PK ==========================

	private static void testFindByPk() {

		try {

			TSessionModel model = new TSessionModel();

			TSessionBean bean = model.findByPk(1L);

			if (bean != null) {

				System.out.println(bean.getId());
				System.out.println(bean.getSessionCode());
				System.out.println(bean.getUserName());
				System.out.println(bean.getLoginTime());
				System.out.println(bean.getStatus());

			} else {

				System.out.println("Record Not Found");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST FIND BY SESSION CODE ==========================

	private static void testFindBySessionCode() {

		try {

			TSessionModel model = new TSessionModel();

			TSessionBean bean = model.findBySessionCode("S001");

			if (bean != null) {

				System.out.println(bean.getId());
				System.out.println(bean.getSessionCode());
				System.out.println(bean.getUserName());
				System.out.println(bean.getLoginTime());
				System.out.println(bean.getStatus());

			} else {

				System.out.println("Record Not Found");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST DELETE ==========================

	private static void testDelete() {

		try {

			TSessionBean bean = new TSessionBean();

			bean.setId(1L);

			TSessionModel model = new TSessionModel();

			model.delete(bean);

			System.out.println("Record Deleted Successfully");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST ADD ==========================

	private static void testAdd() throws DuplicateRecordException, ParseException {

		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			TSessionBean bean = new TSessionBean();

			bean.setSessionCode("S001");
			bean.setUserName("Anmol");
			bean.setLoginTime(sdf.parse("2002-02-02"));
			bean.setStatus("ACTIVE");

			TSessionModel model = new TSessionModel();

			long pk = model.add(bean);

			System.out.println("Record Added Successfully");

			TSessionBean addedBean = model.findByPk(pk);

			if (addedBean == null) {

				System.out.println("Test Add Failed");
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
}