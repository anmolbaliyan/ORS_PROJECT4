package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Subject operations such as
 * adding, updating, and viewing subject details.
 * <p>
 * This servlet is mapped to <b>/ctl/SubjectCtl</b> and is responsible
 * for processing subject form data, performing validation, and
 * interacting with the SubjectModel to persist records.
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
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {

    /**
     * Preloads data required for the subject form.
     * <p>
     * This method retrieves the list of courses from CourseModel
     * and sets it in the request scope for dropdown selection.
     * </p>
     *
     * @param request HttpServletRequest object to store preload data
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void preload(HttpServletRequest request) {
        CourseModel courseModel = new CourseModel();
        try {
            List courseList = courseModel.list();
            request.setAttribute("courseList", courseList);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates subject input data received from the request.
     * <p>
     * This method checks mandatory fields such as subject name,
     * course, and description.
     * If validation fails, appropriate error messages are set
     * in request attributes.
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
            request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a SubjectBean with request parameters.
     * <p>
     * This method extracts form input values such as id, name,
     * courseId, and description and sets them into a SubjectBean.
     * It also calls populateDTO() to set common attributes.
     * </p>
     *
     * @param request HttpServletRequest containing user input data
     * @return populated SubjectBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SubjectBean bean = new SubjectBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests to display subject details.
     * <p>
     * If an ID is provided, it retrieves the subject record
     * from the database and sets it in the request for display.
     * Otherwise, it simply forwards to the subject form view.
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

        SubjectModel model = new SubjectModel();

        if (id > 0) {
            try {
                SubjectBean bean = model.findByPk(id);
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
     * Handles HTTP POST requests for subject operations.
     * <p>
     * This method processes different operations:
     * </p>
     * <ul>
     *   <li><b>Save</b>: Adds a new subject</li>
     *   <li><b>Update</b>: Updates existing subject details</li>
     *   <li><b>Cancel</b>: Redirects to subject list page</li>
     *   <li><b>Reset</b>: Reloads the subject form</li>
     * </ul>
     *
     * <p>
     * It also handles duplicate subject names and sets appropriate
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

        SubjectModel model = new SubjectModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Subject added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject Name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Subject updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject Name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for subject operations.
     *
     * @return the path of the subject view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.SUBJECT_VIEW;
    }
}