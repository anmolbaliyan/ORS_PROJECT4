package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;

public class TestCollege {

	public static void main(String[] args) throws DuplicateRecordException {

		 testadd();
		// testDelete();
		// testFindByName();
		// testFindByPk();
		// testupdate();
		// testsearch();
		

	}

	

	private static void search() {
		 try {
	            CollegeBean bean = new CollegeBean();
	            List list = new ArrayList();
	            bean.setName("IIT kharagpur");
	            
	            CollegeModel model = new CollegeModel();
	            list = model.search(bean, 0, 0);
	            if (list.size() > 0) {
	                System.out.println("Test Search fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (CollegeBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getName());
	                System.out.println(bean.getAddress());
	                System.out.println(bean.getState());
	                System.out.println(bean.getCity());
	                System.out.println(bean.getPhoneNo());
	                System.out.println(bean.getCreatedBy());
	                System.out.println(bean.getCreatedDatetime());
	                System.out.println(bean.getModifiedBy());
	                System.out.println(bean.getModifiedDatetime());
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
		
	}

	private static void testupdate() {
		try {
			CollegeBean bean = new CollegeBean();
			bean.setName("AU University");
			bean.setAddress("Lonavala");
			bean.setId(5L);
			CollegeModel model = new CollegeModel();
			model.update(bean);
			System.out.println("Record Updated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testFindByPk() {

		try {

			CollegeModel model = new CollegeModel();

			CollegeBean bean = model.findByPk(5L);
			System.out.println(bean.getId());
			System.out.println(bean.getAddress());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testFindByName() {
		try {

			CollegeModel model = new CollegeModel();

			CollegeBean bean = model.findByName("sage");
			System.out.println(bean.getId());
			System.out.println(bean.getAddress());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testDelete() {

		try {
			CollegeBean bean = new CollegeBean();
			bean.setId(15L);
			CollegeModel model = new CollegeModel();
			model.delete(bean);
			System.out.println("record delete");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testadd() throws DuplicateRecordException {
		try {
			CollegeBean bean = new CollegeBean();
		  //  bean.setId(2L);
			bean.setName("JIT");
			bean.setAddress("Borawan");
			bean.setState("mp");
			bean.setCity("Khargone");
			bean.setPhoneNo("767856545465");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			CollegeModel model = new CollegeModel();
			long pk = model.add(bean);
			System.out.println("Test Add succ");
			CollegeBean addedBean = model.findByPk(pk);
			if (addedBean == null) {
				System.out.println("Test ass fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}