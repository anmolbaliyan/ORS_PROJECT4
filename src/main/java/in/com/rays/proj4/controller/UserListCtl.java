package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * 
 * Controller class for handling user list operations such as search,
 * 
 * pagination, deletion, and navigation.
 * 
 * <p>
 * 
 * This servlet is mapped to <b>/ctl/UserListCtl</b> and is responsible for
 * 
 * displaying user records with support for filtering and pagination.
 * 
 * It also provides functionality to delete users and navigate between pages.
 * 
 * </p>
 *
 * 
 * 
 * <p>
 * 
 * This class extends {@code BaseCtl} and utilizes common controller
 * 
 * functionalities such as request processing, validation, and forwarding.
 * 
 * </p>
 *
 * 
 * 
 * @author Anmol Kumar Baliyan
 * 
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {

	/**
	 * 
	 * Preloads data required for the view.
	 * 
	 * <p>
	 * 
	 * This method loads the list of roles from the RoleModel and sets it
	 * 
	 * in the request scope so that it can be used for dropdown selection
	 * 
	 * in the user list view.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest object to store preload data
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel roleModel = new RoleModel();
		try {
			List roleList = roleModel.list();
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Populates a UserBean with request parameters.
	 * 
	 * <p>
	 * 
	 * This method extracts search parameters such as first name, login,
	 * 
	 * and roleId from the request and sets them into a UserBean object.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest containing input parameters
	 * 
	 * @return populated UserBean object
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		return bean;
	}

	/**
	 * 
	 * Handles HTTP GET requests to display the user list.
	 * 
	 * <p>
	 * 
	 * This method initializes pagination parameters, retrieves user data
	 * 
	 * based on search criteria, and forwards the request to the user list view.
	 * 
	 * It also prepares the next page data to support pagination controls.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest containing client request
	 * 
	 * @param response HttpServletResponse used to send response
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input/output error occurs
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		try {
			List<UserBean> list = model.search(bean, pageNo, pageSize);
			List<UserBean> next = model.search(bean, pageNo + 1, pageSize);

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

	/**
	 * 
	 * Handles HTTP POST requests for user list operations.
	 * 
	 * <p>
	 * 
	 * This method processes various operations such as:
	 * 
	 * </p>
	 * 
	 * <ul>
	 * 
	 * <li><b>Search</b>: Filters users based on input criteria</li>
	 * 
	 * <li><b>Next/Previous</b>: Handles pagination navigation</li>
	 * 
	 * <li><b>New</b>: Redirects to user creation page</li>
	 * 
	 * <li><b>Delete</b>: Deletes selected user records</li>
	 * 
	 * <li><b>Reset/Back</b>: Reloads the user list page</li>
	 * 
	 * </ul>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It updates the request attributes with user data and forwards the
	 * 
	 * request to the user list view.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest containing form data
	 * 
	 * @param response HttpServletResponse used to send response
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input/output error occurs
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					UserBean deletebean = new UserBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("User deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
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

	/**
	 * 
	 * Returns the view page for the user list.
	 * 
	 * <p>
	 * 
	 * This method provides the path of the user list JSP page defined
	 * 
	 * in ORSView.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the path of the user list view page
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}
}
