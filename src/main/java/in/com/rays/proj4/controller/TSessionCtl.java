package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TSessionBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TSessionModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TSessionCtl", urlPatterns = { "/ctl/TSessionCtl" })

public class TSessionCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("sessionCode"))) {

			request.setAttribute("sessionCode",
					PropertyReader.getValue("error.require", "Session Code"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {

			request.setAttribute("userName",
					PropertyReader.getValue("error.require", "User Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("userName"))) {

			request.setAttribute("userName", "Invalid User Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("loginTime"))) {

			request.setAttribute("loginTime",
					PropertyReader.getValue("error.require", "Login Time"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {

			request.setAttribute("status",
					PropertyReader.getValue("error.require", "Status"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TSessionBean bean = new TSessionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSessionCode(DataUtility.getString(request.getParameter("sessionCode")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setLoginTime(DataUtility.getDate(request.getParameter("loginTime")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TSessionModel model = new TSessionModel();

		if (id > 0) {

			try {

				TSessionBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		TSessionModel model = new TSessionModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TSessionBean bean = (TSessionBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("TSession Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Session Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TSessionBean bean = (TSessionBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("TSession Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Session Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TSESSION_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TSESSION_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.TSESSION_VIEW;
	}
}