package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.TimeTableBean;
import in.com.rays.proj4.model.TimeTableModel;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * 
 * Controller class for handling TimeTable operations such as
 * 
 * adding, updating, and viewing timetable details.
 * 
 * <p>
 * 
 * This servlet is mapped to <b>/ctl/TimeTableCtl</b> and is responsible
 * 
 * for processing timetable form data, performing validation, and
 * 
 * interacting with the TimeTableModel to persist records.
 * 
 * </p>
 *
 * 
 * 
 * <p>
 * 
 * It ensures that duplicate timetable entries are not created by
 * 
 * performing multiple validation checks before saving or updating data.
 * 
 * The class follows MVC architecture by coordinating between
 * 
 * view (JSP) and model layers.
 * 
 * </p>
 *
 * 
 * 
 * @author Anmol Kumar Baliyan
 * 
 */
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {

	/**
	 * 
	 * Preloads data required for the timetable form.
	 * 
	 * <p>
	 * 
	 * This method retrieves subject and course lists from their respective
	 * 
	 * models and sets them into the request scope for populating dropdowns
	 * 
	 * in the timetable view.
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

		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Validates timetable input data received from the request.
	 * 
	 * <p>
	 * 
	 * This method checks mandatory fields such as semester, exam date,
	 * 
	 * exam time, description, course, and subject.
	 * 
	 * It also validates the date format and ensures that exams are not
	 * 
	 * scheduled on Sundays.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * If validation fails, appropriate error messages are set in request
	 * 
	 * attributes and the method returns false.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest containing input parameters
	 * 
	 * @return true if all inputs are valid, false otherwise
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Date of Exam"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Date of Exam"));
			pass = false;
		} else if (DataValidator.isSunday(request.getParameter("examDate"))) {
			request.setAttribute("examDate", "Exam should not be on Sunday");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
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
	 * 
	 * Populates a TimeTableBean with request parameters.
	 * 
	 * <p>
	 * 
	 * This method extracts form input values such as id, semester,
	 * 
	 * description, exam time, exam date, courseId, and subjectId,
	 * 
	 * and sets them into a TimeTableBean object.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It also calls populateDTO() to set common attributes.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest containing user input data
	 * 
	 * @return populated TimeTableBean object
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * 
	 * Handles HTTP GET requests to display timetable details.
	 * 
	 * <p>
	 * 
	 * If an ID is provided, it retrieves the corresponding timetable
	 * 
	 * record from the database and sets it in the request for display.
	 * 
	 * Otherwise, it simply forwards to the timetable form view.
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();

		if (id > 0) {
			try {
				TimeTableBean bean = model.findByPk(id);
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
	 * 
	 * Handles HTTP POST requests for timetable operations.
	 * 
	 * <p>
	 * 
	 * This method processes different operations:
	 * 
	 * </p>
	 * 
	 * <ul>
	 * 
	 * <li><b>Save</b>: Adds a new timetable entry after checking for
	 * duplicates</li>
	 * 
	 * <li><b>Update</b>: Updates existing timetable data after validation</li>
	 * 
	 * <li><b>Cancel</b>: Redirects to timetable list page</li>
	 * 
	 * <li><b>Reset</b>: Reloads the timetable form</li>
	 * 
	 * </ul>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It performs multiple checks to ensure no duplicate timetable exists
	 * 
	 * based on course, subject, semester, exam date, and exam time.
	 * 
	 * Appropriate success or error messages are set in the request.
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		TimeTableModel model = new TimeTableModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			TimeTableBean bean1;
			TimeTableBean bean2;
			TimeTableBean bean3;

			try {
				bean1 = model.checkByCourseName(bean.getCourseId(), bean.getExamDate());

				bean2 = model.checkBySubjectName(bean.getCourseId(), bean.getSubjectId(), bean.getExamDate());

				bean3 = model.checkBySemester(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate());

				if (bean1 == null && bean2 == null && bean3 == null) {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Timetable added successfully", request);
				} else {
					bean = (TimeTableBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Timetable already exist!", request);
				}
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Timetable already exist!", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			TimeTableBean bean4;

			try {

				bean4 = model.checkByExamTime(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate(), bean.getExamTime(), bean.getDescription());

				if (id > 0 && bean4 == null) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Timetable updated successfully", request);
				} else {
					bean = (TimeTableBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Timetable already exist!", request);
				}
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Timetable already exist!", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * 
	 * Returns the view page for timetable operations.
	 * 
	 * <p>
	 * 
	 * This method provides the path of the timetable JSP page
	 * 
	 * defined in ORSView.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the path of the timetable view page
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}
}