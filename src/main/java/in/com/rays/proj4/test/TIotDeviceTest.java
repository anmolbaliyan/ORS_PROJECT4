package in.com.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TIotDeviceBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TIotDeviceModel;

public class TIotDeviceTest {

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {

		testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		// testSearch();
	}

	public static void testAdd() {

		try {

			TIotDeviceBean bean = new TIotDeviceBean();

			bean.setDeviceName("Smart Temperature Sensor");
			bean.setSensorType("Temperature");
			bean.setStatus("Active");
			bean.setBatteryLevel(95);

			TIotDeviceModel model = new TIotDeviceModel();

			long pk = model.add(bean);

			System.out.println("Record inserted at ID : " + pk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws DuplicateRecordException {

		try {

			TIotDeviceModel model = new TIotDeviceModel();

			TIotDeviceBean bean = model.findByPk(1);

			bean.setDeviceName("Updated Sensor");
			bean.setSensorType("Humidity");
			bean.setStatus("Inactive");
			bean.setBatteryLevel(80);

			model.update(bean);

			System.out.println("Record Updated Successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {

		TIotDeviceBean bean = new TIotDeviceBean();

		bean.setDeviceId(1);

		TIotDeviceModel model = new TIotDeviceModel();

		model.delete(bean);

		System.out.println("Record Deleted Successfully");
	}

	public static void testFindByPk() {

		try {

			long pk = 1;

			TIotDeviceModel model = new TIotDeviceModel();

			TIotDeviceBean bean = model.findByPk(pk);

			if (bean == null) {
				System.out.println("Record Not Found");
				return;
			}

			System.out.println("Device Id : " + bean.getDeviceId());

			System.out.println("Device Name : " + bean.getDeviceName());

			System.out.println("Sensor Type : " + bean.getSensorType());

			System.out.println("Status : " + bean.getStatus());

			System.out.println("Battery Level : " + bean.getBatteryLevel());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {

			TIotDeviceBean bean = new TIotDeviceBean();

			TIotDeviceModel model = new TIotDeviceModel();

			List list = new ArrayList();

			// bean.setDeviceName("Smart");
			// bean.setSensorType("Temperature");
			// bean.setStatus("Active");

			list = model.search(bean, 1, 10);

			if (list.size() <= 0) {
				System.out.println("No Record Found");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (TIotDeviceBean) it.next();

				System.out.println(bean.getDeviceId());
				System.out.println(bean.getDeviceName());
				System.out.println(bean.getSensorType());
				System.out.println(bean.getStatus());
				System.out.println(bean.getBatteryLevel());

				System.out.println("-------------------------");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}