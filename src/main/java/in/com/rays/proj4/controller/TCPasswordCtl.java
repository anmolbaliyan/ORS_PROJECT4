package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TCPasswordBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TCPasswordModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TCPasswordCtl", urlPatterns = { "/ctl/TCPasswordCtl" })

public class TCPasswordCtl extends BaseCtl {

	// ========================== VALIDATE ==========================

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("confirmCode"))) {

			request.setAttribute("confirmCode", PropertyReader.getValue("error.require", "Confirm Code"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {

			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("userName"))) {

			request.setAttribute("userName", "Invalid User Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmValue"))) {

			request.setAttribute("confirmValue", PropertyReader.getValue("error.require", "Confirm Value"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {

			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));

			pass = false;
		}

		return pass;
	}

	// ========================== POPULATE BEAN ==========================

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TCPasswordBean bean = new TCPasswordBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setConfirmCode(DataUtility.getString(request.getParameter("confirmCode")));

		bean.setUserName(DataUtility.getString(request.getParameter("userName")));

		bean.setConfirmValue(DataUtility.getString(request.getParameter("confirmValue")));

		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	// ========================== DO GET ==========================

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TCPasswordModel model = new TCPasswordModel();

		if (id > 0) {

			try {

				TCPasswordBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ========================== DO POST ==========================

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		TCPasswordModel model = new TCPasswordModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TCPasswordBean bean = (TCPasswordBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Data added successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Confirm Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TCPasswordBean bean = (TCPasswordBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Data updated successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Confirm Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TCPASSWORD_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TCPASSWORD_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ========================== GET VIEW ==========================

	@Override
	protected String getView() {

		return ORSView.TCPASSWORD_VIEW;
	}
}