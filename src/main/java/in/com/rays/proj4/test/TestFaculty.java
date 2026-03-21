package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.FacultyBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.FacultyModel;

public class TestFaculty {

	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) throws DuplicateRecordException, ApplicationException {
		//testadd();
		// testDelete();
		// testUpdate();
		 testFindByPk();
		// testFindByEmail();
		 //testsearch();

	}

	public static void testadd() throws DuplicateRecordException {

		try {
			FacultyBean bean = new FacultyBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// bean.setId(1);
			System.out.println("2222222222");
			bean.setFirstName("Rohan");
			bean.setLastName("karma");
			bean.setGender("male");
			bean.setEmail("rohan@gmail.com");
			bean.setMobileNo("9087654329");
			bean.setCollegeId(1);
			bean.setCollegeName("rpl");
			bean.setCourseId(1);
			bean.setCourseName("m.com");
			bean.setDob(sdf.parse("22/09/1999"));
			bean.setSubjectId(1);
			bean.setSubjectName("maths");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		try {
			FacultyBean bean = new FacultyBean();
			long pk = 1L;

			bean.setId(pk);
			model.delete(bean);
			FacultyBean deletebean = model.findByPk(pk);
			if (deletebean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			FacultyBean bean = model.findByPk(1L);
			bean.setFirstName("akash");
			// bean.setDescription("commerce");
			model.update(bean);
			System.out.println("update succ");

			/*
			 * FacultyBean updateBean=model.findByPK(2L);
			 * if(!"ram".equals(updateBean.getFirstName())){
			 * System.out.println("test update fail"); }
			 */

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 1L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("test findbypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmail());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByEmail() {
		try {
			FacultyBean bean = new FacultyBean();
			bean = model.findByEmail("ram@gmail.com");
			if (bean != null) {
				System.out.println("Test Find By Email fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmail());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testsearch() throws ApplicationException {
		FacultyBean bean = new FacultyBean();
		List list = new ArrayList();
		//list=model.search(bean);
		
		Iterator it = list.iterator();
		while(it.hasNext()) {
			bean= (FacultyBean) it.next();
			
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmail());
			System.out.println(bean.getMobileNo());
			
		}
	}

}