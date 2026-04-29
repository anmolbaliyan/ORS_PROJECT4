package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.RecordNotFoundException;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Change Password functionality.
 * <p>
 * This servlet is mapped to <b>/ctl/ChangePasswordCtl</b> and is responsible
 * for validating user input, processing password change requests,
 * and interacting with the UserModel to update the password.
 * </p>
 *
 * <p>
 * It supports operations such as:
 * </p>
 * <ul>
 *   <li><b>Save</b>: Change the current user's password</li>
 *   <li><b>Change My Profile</b>: Redirect to profile page</li>
 * </ul>
 *
 * <p>
 * It ensures proper validation like password strength, matching confirmation,
 * and verifying old password before updating.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {

    /**
     * Operation constant for redirecting to My Profile page.
     */
    public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

    /**
     * Validates password change input fields.
     * <p>
     * This method checks:
     * </p>
     * <ul>
     *   <li>Old password is not empty</li>
     *   <li>New password is valid and strong</li>
     *   <li>New and confirm passwords match</li>
     *   <li>Old and new passwords are different</li>
     * </ul>
     *
     * @param request HttpServletRequest containing input parameters
     * @return true if all inputs are valid, false otherwise
     * 
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("oldPassword"))) {
            request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
            pass = false;
        } else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Old and New passwords should be different");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
            pass = false;
        } else if (!DataValidator.isPasswordLength(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Password should be 8 to 12 characters");
            pass = false;
        } else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Must contain uppercase, lowercase, digit & special character");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
            pass = false;
        }

        if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", "New and confirm passwords not matched");
            pass = false;
        }

        return pass;
    }

    /**
     * Populates UserBean with password-related request parameters.
     * <p>
     * Sets old password and confirm password into the bean.
     * Also calls populateDTO() for common attributes.
     * </p>
     *
     * @param request HttpServletRequest containing input data
     * @return populated UserBean object
     * 
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests.
     * <p>
     * Simply forwards the request to the Change Password view page.
     * </p>
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     * 
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests for password change operations.
     * <p>
     * Performs the following:
     * </p>
     * <ul>
     *   <li>Validates old password</li>
     *   <li>Updates password if valid</li>
     *   <li>Refreshes session user data</li>
     *   <li>Handles errors such as invalid old password</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse object
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     * 
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        String newPassword = (String) request.getParameter("newPassword");

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            try {
                boolean flag = model.changePassword(id, bean.getPassword(), newPassword);

                if (flag == true) {
                    bean = model.findByLogin(user.getLogin());
                    session.setAttribute("user", bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Password has been changed Successfully", request);
                }

            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage("Old Password is Invalid", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
            return;
        }

        ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
    }

    /**
     * Returns the view page for change password.
     *
     * @return the path of the change password JSP page
     * 
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.CHANGE_PASSWORD_VIEW;
    }
}