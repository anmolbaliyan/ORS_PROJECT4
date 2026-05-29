package in.com.rays.proj4.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.T_SmartHomeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.T_SmartHomeModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "T_SmartHomeCtl", urlPatterns = { "/ctl/T_SmartHomeCtl" })
public class T_SmartHomeCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("deviceName"))) {
			request.setAttribute("deviceName", PropertyReader.getValue("error.require", "Device Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomName"))) {
			request.setAttribute("roomName", PropertyReader.getValue("error.require", "Room Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("powerStatus"))) {
			request.setAttribute("powerStatus", PropertyReader.getValue("error.require", "Power Status"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("energyUsage"))) {
			request.setAttribute("energyUsage", PropertyReader.getValue("error.require", "Energy Usage"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		T_SmartHomeBean bean = new T_SmartHomeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));
		bean.setRoomName(DataUtility.getString(request.getParameter("roomName")));
		bean.setPowerStatus(DataUtility.getString(request.getParameter("powerStatus")));
		bean.setEnergyUsage(DataUtility.getInt(request.getParameter("energyUsage")));

		bean.setCreatedBy(request.getParameter("createdBy"));
		bean.setModifiedBy(request.getParameter("modifiedBy"));

		bean.setCreatedDatetime(DataUtility.getTimestamp(request.getParameter("createdDatetime")));

		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		T_SmartHomeModel model = new T_SmartHomeModel();

		if (id > 0) {

			try {

				T_SmartHomeBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		T_SmartHomeModel model = new T_SmartHomeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		T_SmartHomeBean bean = (T_SmartHomeBean) populateBean(request);

		try {

			if (OP_SAVE.equalsIgnoreCase(op)) {

				long pk = model.add(bean);

				bean.setId(pk);

				ServletUtility.setSuccessMessage("Smart Home Device Added Successfully", request);

			} else if (OP_UPDATE.equalsIgnoreCase(op)) {

				model.update(bean);

				ServletUtility.setSuccessMessage("Smart Home Device Updated Successfully", request);

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				model.delete(bean);

				ServletUtility.redirect(ORSView.T_SMARTHOME_LIST_CTL, request, response);
				return;

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.T_SMARTHOME_LIST_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.T_SMARTHOME_CTL, request, response);
				return;
			}

			ServletUtility.setBean(bean, request);

		} catch (Exception e) {

			e.printStackTrace();

			ServletUtility.setErrorMessage(e.getMessage(), request);
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.T_SMARTHOME_VIEW;
	}
}