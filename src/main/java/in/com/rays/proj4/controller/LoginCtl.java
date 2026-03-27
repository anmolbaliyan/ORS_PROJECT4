package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl {

	public static final String OP_SIGN_IN = "Sign In";
	public static final String OP_SIGN_UP = "Sign Up";
	public static final String OP_LOG_OUT = "Logout";

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(op)) {
			HttpSession session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("Logout Successful!", request);
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();
		HttpSession session = request.getSession();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {
					session.setAttribute("user", bean);
					RoleBean rolebean = role.findByPk(bean.getRoleId());
					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}
					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					return;
				} else {
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}
}
