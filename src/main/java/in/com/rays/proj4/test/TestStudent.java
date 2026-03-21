package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.StudentModel;

public class TestStudent {

	public static StudentModel model = new StudentModel();

	public static void main(String[] args) throws ParseException {
		testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByEmailId();
		// testSearch();

	}

	public static void testAdd() throws ParseException {

		try {
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			// bean.setId(3L);
			bean.setFirstName("Akshat");
			bean.setLastName("Tiwari");
			bean.setDob(sdf.parse("22/09/1997"));
			bean.setMobileNo("9407411301");
			bean.setEmail("kmalviya301@gmail.com");
			bean.setCollegeId(1L);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);

			System.out.println("student added " + pk);

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		try {
			StudentBean bean = new StudentBean();
			long pk = 20L;

			bean.setId(pk);
			model.delete(bean);
			StudentBean deletebean = model.findByPk(pk);
			if (deletebean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			StudentBean bean = model.findByPk(19L);

			bean.setFirstName("ram");
			bean.setLastName("sharma");
			bean.setCollegeId(5L);
			model.update(bean);
			System.out.println("Test Update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			StudentBean bean = new StudentBean();
			Long pk = 21L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByEmailId() {
		try {
			StudentBean bean = new StudentBean();
			bean = model.findByEmailId("ankit@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By EmailId fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			// bean.setFirstName("ram");
			// bean.setEmail("kmalviya30@gmail.com");
			// bean.setCollegeName("RML Maheshwari");
			bean.setCollegeId(1L);
			list = model.search(bean, 0, 0);
			if (list.size() > 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}