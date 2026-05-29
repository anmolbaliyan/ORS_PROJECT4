package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.T_SmartHomeBean;
import in.com.rays.proj4.model.T_SmartHomeModel;

public class T_SmartHomeTest {

	public static void main(String[] args) throws Exception {

		 testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		//testSearch();
	}

	public static void testAdd() throws Exception {

		T_SmartHomeBean bean = new T_SmartHomeBean();

		bean.setDeviceName("Smart Bulb");
		bean.setRoomName("Living Room");
		bean.setPowerStatus("ON");
		bean.setEnergyUsage(120);

		

		T_SmartHomeModel model = new T_SmartHomeModel();

		long pk = model.add(bean);

		System.out.println("Data Inserted Successfully : " + pk);
	}

	public static void testUpdate() throws Exception {

		T_SmartHomeModel model = new T_SmartHomeModel();

		T_SmartHomeBean bean = model.findByPk(1);

		bean.setDeviceName("Smart Fan");
		bean.setRoomName("Bedroom");
		bean.setPowerStatus("OFF");
		bean.setEnergyUsage(80);


		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	public static void testDelete() throws Exception {

		T_SmartHomeBean bean = new T_SmartHomeBean();

		bean.setId(1);

		T_SmartHomeModel model = new T_SmartHomeModel();

		model.delete(bean);

		System.out.println("Data Deleted Successfully");
	}

	public static void testFindByPk() throws Exception {

		T_SmartHomeModel model = new T_SmartHomeModel();

		T_SmartHomeBean bean = model.findByPk(1);

		if (bean != null) {

			System.out.println(bean.getId());
			System.out.println(bean.getDeviceName());
			System.out.println(bean.getRoomName());
			System.out.println(bean.getPowerStatus());
			System.out.println(bean.getEnergyUsage());

		} else {
			System.out.println("Record Not Found");
		}
	}

	public static void testSearch() throws Exception {

		T_SmartHomeBean bean = new T_SmartHomeBean();

		// bean.setDeviceName("Smart");
		// bean.setRoomName("Living");
		// bean.setPowerStatus("ON");
		// bean.setEnergyUsage(120);

		T_SmartHomeModel model = new T_SmartHomeModel();

		List list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (T_SmartHomeBean) it.next();

			System.out.print(bean.getId());
			System.out.print("\t" + bean.getDeviceName());
			System.out.print("\t" + bean.getRoomName());
			System.out.print("\t" + bean.getPowerStatus());
			System.out.println("\t" + bean.getEnergyUsage());
		}
	}
}