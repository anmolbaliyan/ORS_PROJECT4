package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.TCPasswordBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.TCPasswordModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet(name = "TCPasswordListCtl", urlPatterns = { "/ctl/TCPasswordListCtl" })

public class TCPasswordListCtl extends BaseCtl {

	// ========================== POPULATE BEAN ==========================

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TCPasswordBean bean = new TCPasswordBean();

		bean.setConfirmCode(DataUtility.getString(request.getParameter("confirmCode")));

		bean.setUserName(DataUtility.getString(request.getParameter("userName")));

		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		return bean;
	}

	// ========================== DO GET ==========================

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TCPasswordBean bean = (TCPasswordBean) populateBean(request);

		TCPasswordModel model = new TCPasswordModel();

		try {

			List<TCPasswordBean> list = model.search(bean, pageNo, pageSize);

			List<TCPasswordBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {

				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.setBean(bean, request);

			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			e.printStackTrace();

			return;
		}
	}

	// ========================== DO POST ==========================

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TCPasswordBean bean = (TCPasswordBean) populateBean(request);

		TCPasswordModel model = new TCPasswordModel();

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TCPASSWORD_CTL, request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					TCPasswordBean deleteBean = new TCPasswordBean();

					for (String id : ids) {

						deleteBean.setId(DataUtility.getInt(id));

						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Data deleted successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TCPASSWORD_LIST_CTL, request, response);

				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TCPASSWORD_LIST_CTL, request, response);

				return;
			}

			list = model.search(bean, pageNo, pageSize);

			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.setBean(bean, request);

			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			e.printStackTrace();

			return;
		}
	}

	// ========================== GET VIEW ==========================

	@Override
	protected String getView() {

		return ORSView.TCPASSWORD_LIST_VIEW;
	}
}