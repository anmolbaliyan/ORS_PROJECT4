package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;

public class TestCourse {

	public static CourseModel model = new CourseModel();

	public static void main(String[] args) throws Exception {
		 testadd();
		// testDelete();
		// testFindByName();
		// testFindByPk();
		// testUpdate();
		// testsearch();

	}

	public static void testadd() throws DuplicateRecordException {

		try {
			CourseBean bean = new CourseBean();
			bean.setId(2);
			bean.setName("Bsc");
			bean.setDescription("commerce");
			bean.setDuration("4 Year");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			
			System.out.println("course added successfully " + pk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 3L;
			bean.setId(1);
			model.delete(bean);
			System.out.println("Test Deleted");
			/*
			 * CourseBean deleteBean=model.findByPK(pk); if(deleteBean == null) {
			 * System.out.println("Test Delete fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		try {
			CourseBean bean = new CourseBean();
			bean = model.findByName("B.com");
			if (bean == null) {
				System.out.println("test findBy Name fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 1L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("test findbypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			CourseBean bean = model.findByPk(1L);
			bean.setName("Mca");
			// bean.setDescription("commerce");
			model.update(bean);
			System.out.println("update succ");
			/*
			 * CourseBean updateBean=model.testFindByPK(2L); if(!
			 * "ram".equals(updateBean.getName())){ System.out.println("test update fail");
			 * }
			 */

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testsearch() throws DatabaseException, ApplicationException {
		CourseBean bean = new CourseBean();
		List list = new ArrayList();
		// list=model.search(bean);

		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CourseBean) it.next();
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
		}
	}

}