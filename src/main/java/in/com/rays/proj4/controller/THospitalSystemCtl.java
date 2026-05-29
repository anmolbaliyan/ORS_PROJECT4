package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.THospitalSystemBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.THospitalSystemModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "THospitalSystemCtl", urlPatterns = { "/ctl/THospitalSystemCtl" })

public class THospitalSystemCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("patientName"))) {

			request.setAttribute("patientName", PropertyReader.getValue("error.require", "Patient Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("doctorName"))) {

			request.setAttribute("doctorName", PropertyReader.getValue("error.require", "Doctor Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("disease"))) {

			request.setAttribute("disease", PropertyReader.getValue("error.require", "Disease"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomNumber"))) {

			request.setAttribute("roomNumber", PropertyReader.getValue("error.require", "Room Number"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		THospitalSystemBean bean = new THospitalSystemBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));

		bean.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));

		bean.setDisease(DataUtility.getString(request.getParameter("disease")));

		bean.setRoomNumber(DataUtility.getInt(request.getParameter("roomNumber")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		THospitalSystemModel model = new THospitalSystemModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			THospitalSystemBean bean;

			try {

				bean = model.findByPk(id);

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

		THospitalSystemModel model = new THospitalSystemModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			THospitalSystemBean bean = (THospitalSystemBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);

					ServletUtility.setSuccessMessage("Hospital System Updated Successfully", request);

				} else {

					model.add(bean);

					ServletUtility.setSuccessMessage("Hospital System Added Successfully", request);
				}

				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage(bean.getRoomNumber() + " Room Number already exists", request);

				ServletUtility.forward(getView(), request, response);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			THospitalSystemBean bean = (THospitalSystemBean) populateBean(request);

			try {

				model.delete(bean);

				ServletUtility.redirect(ORSView.THOSPITALSYSTEM_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.THOSPITALSYSTEM_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.THOSPITALSYSTEM_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.THOSPITALSYSTEM_VIEW;
	}
}