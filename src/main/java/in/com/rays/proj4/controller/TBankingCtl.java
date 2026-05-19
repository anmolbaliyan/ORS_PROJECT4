package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TBankingBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TBankingModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TBankingCtl", urlPatterns = { "/ctl/TBankingCtl" })

public class TBankingCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("holderName"))) {

			request.setAttribute("holderName", PropertyReader.getValue("error.require", "Holder Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("balance"))) {

			request.setAttribute("balance", PropertyReader.getValue("error.require", "Balance"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("accountType"))) {

			request.setAttribute("accountType", PropertyReader.getValue("error.require", "Account Type"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("transactionId"))) {

			request.setAttribute("transactionId", PropertyReader.getValue("error.require", "Transaction Id"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TBankingBean bean = new TBankingBean();

		bean.setAccountNo(DataUtility.getLong(request.getParameter("id")));
		bean.setHolderName(DataUtility.getString(request.getParameter("holderName")));
		bean.setBalance(DataUtility.getInt(request.getParameter("balance")));
		bean.setAccountType(DataUtility.getString(request.getParameter("accountType")));
		bean.setTransactionId(DataUtility.getInt(request.getParameter("transactionId")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TBankingModel model = new TBankingModel();

		if (id > 0) {

			try {

				TBankingBean bean = model.findByPk(id);

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

		TBankingModel model = new TBankingModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TBankingBean bean = (TBankingBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Banking Data Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Holder Name already exists", request);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TBankingBean bean = (TBankingBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Banking Data Updated Successfully", request);

				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TBANKING_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TBANKING_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.TBANKING_VIEW;
	}
}