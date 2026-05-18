package in.com.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TCPasswordBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TCPasswordModel;

public class TCPasswordTest {

	public static void main(String[] args)
			throws ApplicationException, DuplicateRecordException {

		testAdd();
		// testDelete();
		// testFindByPk();
		// testUpdate();
		// testSearch();
		// testFindByCode();
	}

	// ========================== ADD ==========================

	public static void testAdd() {

		try {

			TCPasswordBean bean = new TCPasswordBean();

			bean.setConfirmCode("CP001");
			bean.setUserName("Anmol");
			bean.setConfirmValue("YES");
			bean.setStatus("ACTIVE");

			TCPasswordModel model = new TCPasswordModel();

			long pk = model.add(bean);

			System.out.println("Record inserted at id : " + pk);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== DELETE ==========================

	public static void testDelete()
			throws ApplicationException {

		TCPasswordBean bean = new TCPasswordBean();

		bean.setId(1L);

		TCPasswordModel model = new TCPasswordModel();

		model.delete(bean);

		System.out.println("Record deleted successfully");
	}

	// ========================== FIND BY PK ==========================

	public static void testFindByPk() {

		try {

			TCPasswordBean bean = new TCPasswordBean();

			long pk = 1L;

			TCPasswordModel model = new TCPasswordModel();

			bean = model.findByPk(pk);

			if (bean == null) {

				System.out.println("Record not found");

			} else {

				System.out.println(bean.getId());
				System.out.println(bean.getConfirmCode());
				System.out.println(bean.getUserName());
				System.out.println(bean.getConfirmValue());
				System.out.println(bean.getStatus());
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	// ========================== UPDATE ==========================

	public static void testUpdate()
			throws DuplicateRecordException {

		try {

			TCPasswordModel model = new TCPasswordModel();

			TCPasswordBean bean = model.findByPk(1L);

			bean.setConfirmCode("CP100");
			bean.setUserName("Kapil");
			bean.setConfirmValue("NO");
			bean.setStatus("INACTIVE");

			model.update(bean);

			System.out.println("Record updated successfully");

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	// ========================== SEARCH ==========================

	public static void testSearch() {

		try {

			TCPasswordBean bean = new TCPasswordBean();

			TCPasswordModel model = new TCPasswordModel();

			List list = new ArrayList();

			// bean.setConfirmCode("CP");
			// bean.setUserName("Anmol");
			// bean.setStatus("ACTIVE");

			list = model.search(bean, 1, 10);

			if (list.size() < 0) {

				System.out.println("Search failed");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TCPasswordBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getConfirmCode());
				System.out.println(bean.getUserName());
				System.out.println(bean.getConfirmValue());
				System.out.println(bean.getStatus());

				System.out.println("------------------------");
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	// ========================== FIND BY CODE ==========================

	public static void testFindByCode() {

		try {

			TCPasswordModel model = new TCPasswordModel();

			TCPasswordBean bean =
					model.findByCode("CP001");

			if (bean != null) {

				System.out.println(bean.getId());
				System.out.println(bean.getConfirmCode());
				System.out.println(bean.getUserName());
				System.out.println(bean.getConfirmValue());
				System.out.println(bean.getStatus());

			} else {

				System.out.println("Record not found");
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
}