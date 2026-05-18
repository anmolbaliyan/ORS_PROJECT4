package in.com.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TCodeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TCodeModel;

public class TCodeTest {

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {

		testAdd();
		// testDelete();
		// testFindByPk();
		// testUpdate();
		// testSearch();
		// testFindByLanguage();

	}

	// ========================== TEST ADD ==========================

	public static void testAdd() {

		try {

			TCodeBean bean = new TCodeBean();

			bean.setLanguage("Java");
			bean.setCodeSnippet("System.out.println(\"Hello World\");");
			bean.setExecutionTime("2 Seconds");
			bean.setOutputStatus("Success");

			TCodeModel model = new TCodeModel();

			long pk = model.add(bean);

			System.out.println("Record inserted at id : " + pk);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST UPDATE ==========================

	private static void testUpdate() throws DuplicateRecordException {

		try {

			TCodeModel model = new TCodeModel();

			TCodeBean bean = model.findByPk(1L);

			bean.setLanguage("Python");
			bean.setCodeSnippet("print('Hello')");
			bean.setExecutionTime("1 Second");
			bean.setOutputStatus("Executed");

			model.update(bean);

			System.out.println("Record Updated Successfully");

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST DELETE ==========================

	public static void testDelete() throws ApplicationException {

		TCodeBean bean = new TCodeBean();

		bean.setId(1L);

		TCodeModel model = new TCodeModel();

		model.delete(bean);

		System.out.println("Record Deleted Successfully");
	}

	// ========================== TEST FIND BY PK ==========================

	private static void testFindByPk() {

		try {

			TCodeBean bean = new TCodeBean();

			long pk = 1L;

			TCodeModel model = new TCodeModel();

			bean = model.findByPk(pk);

			if (bean == null) {

				System.out.println("Test Find By PK Fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getLanguage());
			System.out.println(bean.getCodeSnippet());
			System.out.println(bean.getExecutionTime());
			System.out.println(bean.getOutputStatus());

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST FIND BY LANGUAGE ==========================

	private static void testFindByLanguage() {

		try {

			TCodeModel model = new TCodeModel();

			TCodeBean bean = model.findByLanguage("Java");

			if (bean != null) {

				System.out.println(bean.getId());
				System.out.println(bean.getLanguage());
				System.out.println(bean.getCodeSnippet());
				System.out.println(bean.getExecutionTime());
				System.out.println(bean.getOutputStatus());

			} else {

				System.out.println("No Record Found");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ========================== TEST SEARCH ==========================

	private static void testSearch() {

		try {

			TCodeBean bean = new TCodeBean();

			TCodeModel model = new TCodeModel();

			List list = new ArrayList();

			bean.setLanguage("Java");

			list = model.search(bean, 1, 10);

			if (list.size() < 0) {

				System.out.println("Test Search Fail");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TCodeBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getLanguage());
				System.out.println(bean.getCodeSnippet());
				System.out.println(bean.getExecutionTime());
				System.out.println(bean.getOutputStatus());
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}
}