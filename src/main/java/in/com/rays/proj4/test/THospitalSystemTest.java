package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.THospitalSystemBean;
import in.com.rays.proj4.model.THospitalSystemModel;

public class THospitalSystemTest {

	public static THospitalSystemModel model = new THospitalSystemModel();

	public static void main(String[] args) throws Exception {

	    testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		// testFindByRoomNumber();
		//testSearch();
	}

	public static void testAdd() throws Exception {

		THospitalSystemBean bean = new THospitalSystemBean();

		bean.setPatientName("Anmol");
		bean.setDoctorName("Dr Sharma");
		bean.setDisease("Fever");
		bean.setRoomNumber(101);

		

		long pk = model.add(bean);

		System.out.println("Data Added Successfully : " + pk);
	}

	public static void testUpdate() throws Exception {

		THospitalSystemBean bean = model.findByPk(1);

		bean.setPatientName("Rahul");
		bean.setDoctorName("Dr Verma");
		bean.setDisease("Cold");
		bean.setRoomNumber(102);


		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	public static void testDelete() throws Exception {

		THospitalSystemBean bean = new THospitalSystemBean();

		bean.setId(1);

		model.delete(bean);

		System.out.println("Data Deleted Successfully");
	}

	public static void testFindByPk() throws Exception {

		THospitalSystemBean bean = model.findByPk(1);

		if (bean != null) {

			System.out.println(bean.getId());
			System.out.println(bean.getPatientName());
			System.out.println(bean.getDoctorName());
			System.out.println(bean.getDisease());
			System.out.println(bean.getRoomNumber());

		} else {

			System.out.println("Data Not Found");
		}
	}

	public static void testFindByRoomNumber() throws Exception {

		THospitalSystemBean bean = model.findByRoomNumber(101);

		if (bean != null) {

			System.out.println(bean.getId());
			System.out.println(bean.getPatientName());
			System.out.println(bean.getDoctorName());
			System.out.println(bean.getDisease());
			System.out.println(bean.getRoomNumber());

		} else {

			System.out.println("Data Not Found");
		}
	}

	public static void testSearch() throws Exception {

		THospitalSystemBean bean = new THospitalSystemBean();

		bean.setPatientName("A");

		List list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (THospitalSystemBean) it.next();

			System.out.print(bean.getId());
			System.out.print("\t" + bean.getPatientName());
			System.out.print("\t" + bean.getDoctorName());
			System.out.print("\t" + bean.getDisease());
			System.out.println("\t" + bean.getRoomNumber());
		}
	}
}