package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.model.StudentModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Student operations such as
 * adding, updating, and viewing student details.
 * <p>
 * This servlet is mapped to <b>/ctl/StudentCtl</b> and is responsible
 * for processing student form data, performing validation, and
 * interacting with the StudentModel to persist records.
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
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {

    /**
     * Preloads data required for the student form.
     * <p>
     * This method retrieves the list of colleges from CollegeModel
     * and sets it in the request scope for dropdown selection.
     * </p>
     *
     * @param request HttpServletRequest object to store preload data
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void preload(HttpServletRequest request) {
        CollegeModel collegeModel = new CollegeModel();
        try {
            List collegeList = collegeModel.list();
            request.setAttribute("collegeList", collegeList);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates student input data received from the request.
     * <p>
     * This method checks mandatory fields such as first name, last name,
     * mobile number, gender, email, college, and date of birth.
     * It also validates formats like name, phone number, email, and date.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return true if all inputs are valid, false otherwise
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("firstName"))) {
            request.setAttribute("firstName", "Invalid First Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("lastName"))) {
            request.setAttribute("lastName", "Invalid Last Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        } else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", "Mobile No must have 10 digits");
            pass = false;
        } else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", "Invalid Mobile No");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("collegeId"))) {
            request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a StudentBean with request parameters.
     * <p>
     * This method extracts form input values such as id, first name,
     * last name, date of birth, gender, mobile number, email, and
     * collegeId, and sets them into a StudentBean.
     * It also calls populateDTO() to set common attributes.
     * </p>
     *
     * @param request HttpServletRequest containing user input data
     * @return populated StudentBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        StudentBean bean = new StudentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests to display student details.
     * <p>
     * If an ID is provided, it retrieves the student record
     * from the database and sets it in the request for display.
     * Otherwise, it simply forwards to the student form view.
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

        StudentModel model = new StudentModel();

        if (id > 0) {
            try {
                StudentBean bean = model.findByPk(id);
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
     * Handles HTTP POST requests for student operations.
     * <p>
     * This method processes different operations:
     * </p>
     * <ul>
     *   <li><b>Save</b>: Adds a new student</li>
     *   <li><b>Update</b>: Updates existing student details</li>
     *   <li><b>Cancel</b>: Redirects to student list page</li>
     *   <li><b>Reset</b>: Reloads the student form</li>
     * </ul>
     *
     * <p>
     * It also handles duplicate email validation and sets appropriate
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

        StudentModel model = new StudentModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            StudentBean bean = (StudentBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Student added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Email already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            StudentBean bean = (StudentBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Student updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Email already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for student operations.
     *
     * @return the path of the student view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.STUDENT_VIEW;
    }
}