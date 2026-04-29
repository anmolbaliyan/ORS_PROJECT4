package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling College operations such as
 * adding, updating, and viewing college details.
 * <p>
 * This servlet is mapped to <b>/ctl/CollegeCtl</b> and is responsible
 * for processing college form data, performing validation, and
 * interacting with the CollegeModel to persist records.
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
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {

    /**
     * Validates college input data received from the request.
     * <p>
     * This method checks mandatory fields such as name, address,
     * state, city, and phone number. It also validates the format
     * of name and phone number.
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

        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("state"))) {
            request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        } else if (!DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", "Phone No must have 10 digits");
            pass = false;
        } else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", "Invalid Phone No");
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a CollegeBean with request parameters.
     * <p>
     * This method extracts form input values such as id, name,
     * address, state, city, and phone number and sets them into a CollegeBean.
     * It also calls populateDTO() to set common attributes.
     * </p>
     *
     * @param request HttpServletRequest containing user input data
     * @return populated CollegeBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CollegeBean bean = new CollegeBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setAddress(DataUtility.getString(request.getParameter("address")));
        bean.setState(DataUtility.getString(request.getParameter("state")));
        bean.setCity(DataUtility.getString(request.getParameter("city")));
        bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests to display college details.
     * <p>
     * If an ID is provided, it retrieves the college record
     * from the database and sets it in the request for display.
     * Otherwise, it forwards to the college form view.
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

        CollegeModel model = new CollegeModel();

        if (id > 0) {
            try {
                CollegeBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests for college operations.
     * <p>
     * This method processes different operations:
     * </p>
     * <ul>
     *   <li><b>Save</b>: Adds a new college</li>
     *   <li><b>Update</b>: Updates existing college details</li>
     *   <li><b>Cancel</b>: Redirects to college list page</li>
     *   <li><b>Reset</b>: Reloads the college form</li>
     * </ul>
     *
     * <p>
     * It also handles duplicate college names and sets appropriate
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

        CollegeModel model = new CollegeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            CollegeBean bean = (CollegeBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            CollegeBean bean = (CollegeBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully updated", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for college operations.
     *
     * @return the path of the college view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.COLLEGE_VIEW;
    }
}