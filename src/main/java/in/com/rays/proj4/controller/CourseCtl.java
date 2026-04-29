package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Course operations such as
 * adding, updating, and viewing course details.
 * <p>
 * This servlet is mapped to <b>/ctl/CourseCtl</b> and is responsible
 * for processing course form data, performing validation, and
 * interacting with the CourseModel to persist records.
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
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {

    /**
     * Validates course input data received from the request.
     * <p>
     * This method checks mandatory fields such as name,
     * duration, and description, and also validates
     * the format of the course name.
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

        if (DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a CourseBean with request parameters.
     * <p>
     * This method extracts form input values such as id, name,
     * duration, and description and sets them into a CourseBean.
     * It also calls populateDTO() to set common attributes.
     * </p>
     *
     * @param request HttpServletRequest containing user input data
     * @return populated CourseBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CourseBean bean = new CourseBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests to display course details.
     * <p>
     * If an ID is provided, it retrieves the course record
     * from the database and sets it in the request for display.
     * Otherwise, it forwards to the course form view.
     * </p>
     *
     * @param request  HttpServletRequest containing client request
     * @param response HttpServletResponse used to send response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     * @author Anmol Kumar Baliyan
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        CourseModel model = new CourseModel();

        if (id > 0) {
            try {
                CourseBean bean = model.findByPk(id);
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
     * Handles HTTP POST requests for course operations.
     * <p>
     * This method processes different operations:
     * </p>
     * <ul>
     *   <li><b>Save</b>: Adds a new course</li>
     *   <li><b>Update</b>: Updates existing course details</li>
     *   <li><b>Cancel</b>: Redirects to course list page</li>
     *   <li><b>Reset</b>: Reloads the course form</li>
     * </ul>
     *
     * <p>
     * It also handles duplicate course records and sets appropriate
     * success or error messages in the request.
     * </p>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse used to send response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     * @author Anmol Kumar Baliyan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        CourseModel model = new CourseModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            CourseBean bean = (CourseBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Course added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            CourseBean bean = (CourseBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Course updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for course operations.
     *
     * @return the path of the course view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.COURSE_VIEW;
    }
}