package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TProductBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TProductModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TProductCtl", urlPatterns = { "/ctl/TProductCtl" })

public class TProductCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("productName"))) {

			request.setAttribute("productName", PropertyReader.getValue("error.require", "Product Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("brand"))) {

			request.setAttribute("brand", PropertyReader.getValue("error.require", "Brand"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {

			request.setAttribute("price", PropertyReader.getValue("error.require", "Price"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {

			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TProductBean bean = new TProductBean();

		bean.setProductId(DataUtility.getInt(request.getParameter("id")));
		bean.setProductName(DataUtility.getString(request.getParameter("productName")));
		bean.setBrand(DataUtility.getString(request.getParameter("brand")));
		bean.setPrice(DataUtility.getInt(request.getParameter("price")));
		bean.setQuantity(DataUtility.getInt(request.getParameter("quantity")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = DataUtility.getInt(request.getParameter("id"));

		TProductModel model = new TProductModel();

		if (id > 0) {

			try {

				TProductBean bean = model.findByPk(id);

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

		TProductModel model = new TProductModel();

		int id = DataUtility.getInt(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TProductBean bean = (TProductBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Product Added Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Product Name already exists", request);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TProductBean bean = (TProductBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setSuccessMessage("Product Updated Successfully", request);

				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TPRODUCT_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TPRODUCT_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.TPRODUCT_VIEW;
	}
}