package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.UserModel;

public class TestUser {
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {

		// testadd();
		// testDelete();
		// testFindByPk();
		// testUpdate();
		// testSearch();
		// authenticate();

	}

	private static void testSearch() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			List list = new ArrayList();
			// bean.setFirstName("Roshani");
			// bean.setLastName("Bandhiye");
			// bean.setLogin("banti@gmail.com");
			// bean.setId(8L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				/// System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				// System.out.println(bean.getLastLogin());
				// System.out.println(bean.getLock());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws DuplicateRecordException {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			bean = model.findByPk(5L);
			bean.setFirstName("sagar");
			bean.setLastName("goyal");
			bean.setLogin("sagarjain@gmail.com");
			bean.setPassword("sagar123");
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testFindByPk() {
		try {
			UserBean bean = new UserBean();
			long pk = 1L;
			UserModel model = new UserModel();
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			// System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			// System.out.println(bean.getLastLogin());
			// System.out.println(bean.getLock());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() throws ApplicationException {
		UserBean bean = new UserBean();
		bean.setId(1L);
		UserModel model = new UserModel();
		model.delete(bean);

	}

	public static void testadd() {
		try {
			UserBean bean = new UserBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			// bean.setId(1);
			bean.setFirstName("Kapil");
			bean.setLastName("Malviya");
			bean.setLogin("kmalviya30@gmail.com");
			bean.setPassword("kapil@123");
			bean.setDob(sdf.parse("05-07-1997"));
			bean.setRoleId(1L);
			bean.setGender("Male");
			bean.setMobileNo("9407411301");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			UserModel model = new UserModel();

			long pk = model.add(bean);

			System.out.println("record insert at id " + pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void authenticate() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			bean.setLogin("roshani@gmail.com");
			bean.setPassword("Roshani@123");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully login");
			} else {
				System.out.println("Invaliad login Id & password");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}