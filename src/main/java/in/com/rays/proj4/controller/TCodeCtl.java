package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TCodeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TCodeModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TCodeCtl", urlPatterns = { "/ctl/TCodeCtl" })

public class TCodeCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("language"))) {

			request.setAttribute("language", PropertyReader.getValue("error.require", "Language"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("codeSnippet"))) {

			request.setAttribute("codeSnippet", PropertyReader.getValue("error.require", "Code"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("executionTime"))) {

			request.setAttribute("executionTime", PropertyReader.getValue("error.require", "Execution Time"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("outputStatus"))) {

			request.setAttribute("outputStatus", PropertyReader.getValue("error.require", "Status"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TCodeBean bean = new TCodeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setLanguage(DataUtility.getString(request.getParameter("language")));

		bean.setCodeSnippet(DataUtility.getString(request.getParameter("codeSnippet")));

		bean.setExecutionTime(DataUtility.getString(request.getParameter("executionTime")));

		bean.setOutputStatus(DataUtility.getString(request.getParameter("outputStatus")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TCodeModel model = new TCodeModel();

		if (id > 0) {

			try {

				TCodeBean bean = model.findByPk(id);

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

		TCodeModel model = new TCodeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TCodeBean bean = (TCodeBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("TCode Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Language Already Exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TCodeBean bean = (TCodeBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("TCode Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Language Already Exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TCODE_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TCODE_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.TCODE_VIEW;
	}
}