package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TIotDeviceBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TIotDeviceModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TIotDeviceCtl", urlPatterns = { "/ctl/TIotDeviceCtl" })

public class TIotDeviceCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("deviceName"))) {
			request.setAttribute("deviceName", PropertyReader.getValue("error.require", "Device Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("deviceName"))) {
			request.setAttribute("deviceName", "Invalid Device Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("sensorType"))) {
			request.setAttribute("sensorType", PropertyReader.getValue("error.require", "Sensor Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("batteryLevel"))) {
			request.setAttribute("batteryLevel", PropertyReader.getValue("error.require", "Battery Level"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TIotDeviceBean bean = new TIotDeviceBean();

		bean.setDeviceId(DataUtility.getLong(request.getParameter("deviceId")));

		bean.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));

		bean.setSensorType(DataUtility.getString(request.getParameter("sensorType")));

		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		bean.setBatteryLevel(DataUtility.getInt(request.getParameter("batteryLevel")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("deviceId"));

		TIotDeviceModel model = new TIotDeviceModel();

		if (id > 0) {

			try {

				TIotDeviceBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		TIotDeviceModel model = new TIotDeviceModel();

		long id = DataUtility.getLong(request.getParameter("deviceId"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TIotDeviceBean bean = (TIotDeviceBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Device Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Device already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TIotDeviceBean bean = (TIotDeviceBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Device Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Duplicate Record Found", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIOTDEVICE_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIOTDEVICE_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.TIOTDEVICE_VIEW;
	}
}