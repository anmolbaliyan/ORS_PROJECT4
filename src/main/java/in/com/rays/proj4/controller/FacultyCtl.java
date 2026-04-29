package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.FacultyBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.FacultyModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Faculty operations such as
 * adding, updating, and viewing faculty details.
 * <p>
 * This servlet is mapped to <b>/ctl/FacultyCtl</b> and is responsible
 * for processing faculty form data, performing validation, and
 * interacting with the FacultyModel to persist records.
 * </p>
 *
 * <p>
 * It also preloads reference data like College, Course, and Subject
 * to populate dropdowns in the UI.
 * </p>
 *
 * <p>
 * Supported operations:
 * </p>
 * <ul>
 *   <li><b>Save</b>: Add new faculty</li>
 *   <li><b>Update</b>: Update existing faculty</li>
 *   <li><b>Cancel</b>: Redirect to faculty list</li>
 *   <li><b>Reset</b>: Reload faculty form</li>
 * </ul>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {

    /**
     * Preloads reference data required for Faculty form.
     * <p>
     * Loads College, Subject, and Course lists and sets them
     * as request attributes for dropdown population in the view.
     * </p>
     *
     * @param request HttpServletRequest
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void preload(HttpServletRequest request) {

        CollegeModel collegeModel = new CollegeModel();
        SubjectModel subjectModel = new SubjectModel();
        CourseModel courseModel = new CourseModel();

        try {

            List collegeList = collegeModel.list();
            request.setAttribute("collegeList", collegeList);

            List subjectList = subjectModel.list();
            request.setAttribute("subjectList", subjectList);

            List courseList = courseModel.list();
            request.setAttribute("courseList", courseList);

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates faculty form input.
     * <p>
     * Ensures required fields are filled and validates formats
     * such as name, email, phone number, and date.
     * </p>
     *
     * @param request HttpServletRequest containing user input
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

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
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

        if (DataValidator.isNull(request.getParameter("collegeId"))) {
            request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("subjectId"))) {
            request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates FacultyBean with request parameters.
     *
     * @param request HttpServletRequest containing form data
     * @return populated FacultyBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        FacultyBean bean = new FacultyBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET request.
     * <p>
     * Retrieves faculty data by ID (if provided) and forwards it to the view.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        FacultyModel model = new FacultyModel();

        if (id > 0) {
            try {
                FacultyBean bean = model.findByPk(id);
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
     * Handles HTTP POST request for faculty operations.
     * <p>
     * Processes Save, Update, Cancel, and Reset operations.
     * </p>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        FacultyModel model = new FacultyModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            FacultyBean bean = (FacultyBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Faculty added successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Email already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            FacultyBean bean = (FacultyBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Faculty updated successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Email already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Faculty form.
     *
     * @return path of FacultyView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.FACULTY_VIEW;
    }
}