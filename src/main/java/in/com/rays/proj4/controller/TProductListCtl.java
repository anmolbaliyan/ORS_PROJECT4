package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TProductBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.TProductModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TProductListCtl", urlPatterns = { "/ctl/TProductListCtl" })

public class TProductListCtl extends BaseCtl {

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TProductBean bean = new TProductBean();

		bean.setProductName(DataUtility.getString(request.getParameter("productName")));
		bean.setBrand(DataUtility.getString(request.getParameter("brand")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TProductBean bean = (TProductBean) populateBean(request);

		TProductModel model = new TProductModel();

		try {

			List list = model.search(bean, pageNo, pageSize);

			List next = model.search(bean, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TProductBean bean = (TProductBean) populateBean(request);

		TProductModel model = new TProductModel();

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)) {

				pageNo = 1;

			} else if (OP_NEXT.equalsIgnoreCase(op)) {

				pageNo++;

			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

				pageNo--;

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TPRODUCT_CTL, request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null) {

					for (String id : ids) {

						TProductBean deleteBean = new TProductBean();

						deleteBean.setProductId(DataUtility.getInt(id));

						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Product Deleted Successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TPRODUCT_LIST_CTL, request, response);

				return;
			}

			list = model.search(bean, pageNo, pageSize);

			List next = model.search(bean, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {

		return ORSView.TPRODUCT_LIST_VIEW;
	}
}