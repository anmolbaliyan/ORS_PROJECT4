package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;



/**
 * Controller class for handling Role operations such as
 * adding, updating, and viewing role details.
 * <p>
 * This servlet is mapped to <b>/ctl/RoleCtl</b> and is responsible
 * for processing role form data, performing validation, and
 * interacting with the RoleModel to persist records.
 * </p>
 *
 * <p>
 * It supports operations like Save, Update, Reset, and Cancel,
 * and follows MVC architecture by coordinating between
 * view (JSP) and model layers.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet("/ctl/RoleCtl")
public class RoleCtl extends BaseCtl {

    /**
     * Validates role input data received from the request.
     * <p>
     * This method checks mandatory fields such as name and description.
     * It also validates the format of the role name.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return true if all inputs are valid, false otherwise
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a RoleBean with request parameters.
     * <p>
     * This method extracts form input values such as id, name,
     * and description and sets them into a RoleBean.
     * It also calls populateDTO() to set common attributes.
     * </p>
     *
     * @param request HttpServletRequest containing user input data
     * @return populated RoleBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        RoleBean bean = new RoleBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests to display role details.
     * <p>
     * If an ID is provided, it retrieves the role record
     * from the database and sets it in the request for display.
     * Otherwise, it forwards to the role form view.
     * </p>
     *
     * @param request  HttpServletRequest containing client request
     * @param response HttpServletResponse used to send response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        RoleModel model = new RoleModel();

        if (id > 0) {
            try {
                RoleBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests for role operations.
     * <p>
     * This method processes different operations:
     * </p>
     * <ul>
     *   <li><b>Save</b>: Adds a new role</li>
     *   <li><b>Update</b>: Updates existing role details</li>
     *   <li><b>Cancel</b>: Redirects to role list page</li>
     *   <li><b>Reset</b>: Reloads the role form</li>
     * </ul>
     *
     * <p>
     * It also handles duplicate role names and sets appropriate
     * success or error messages in the request.
     * </p>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse used to send response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        RoleModel model = new RoleModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            RoleBean bean = (RoleBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Role already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            RoleBean bean = (RoleBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully updated", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Role already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for role operations.
     *
     * @return the path of the role view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.ROLE_VIEW;
    }
}