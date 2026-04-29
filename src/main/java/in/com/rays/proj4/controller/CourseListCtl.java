package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for managing Course List operations.
 * <p>
 * This servlet is mapped to <b>/ctl/CourseListCtl</b> and is responsible
 * for handling course search, pagination, deletion, and navigation actions.
 * </p>
 *
 * <p>
 * It interacts with the CourseModel to retrieve and manipulate course records
 * and forwards data to the view layer for display.
 * </p>
 *
 * <p>
 * Supported operations include:
 * </p>
 * <ul>
 *   <li><b>Search</b>: Filter course records</li>
 *   <li><b>Next/Previous</b>: Pagination</li>
 *   <li><b>New</b>: Redirect to course form</li>
 *   <li><b>Delete</b>: Remove selected records</li>
 *   <li><b>Reset</b>: Reload list page</li>
 *   <li><b>Back</b>: Redirect to list view</li>
 * </ul>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {

    /**
     * Preloads course list data.
     * <p>
     * Loads all courses and sets them as a request attribute
     * for use in dropdowns or filters.
     * </p>
     *
     * @param request HttpServletRequest
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
     * Populates CourseBean with request parameters.
     *
     * @param request HttpServletRequest containing input parameters
     * @return populated CourseBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CourseBean bean = new CourseBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));

        return bean;
    }

    /**
     * Handles HTTP GET request.
     * <p>
     * Initializes pagination and retrieves the first page of course records.
     * Sets list data and forwards to the course list view.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        CourseBean bean = (CourseBean) populateBean(request);
        CourseModel model = new CourseModel();

        try {

            List<CourseBean> list = model.search(bean, pageNo, pageSize);
            List<CourseBean> next = model.search(bean, pageNo + 1, pageSize);

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
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Handles HTTP POST request for course list operations.
     * <p>
     * Processes search, pagination, deletion, reset, and navigation actions.
     * </p>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        CourseBean bean = (CourseBean) populateBean(request);
        CourseModel model = new CourseModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            // Handle Search & Pagination
            if (OP_SEARCH.equalsIgnoreCase(op)
                    || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
            }

            // Redirect to Course Form
            else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
                return;
            }

            // Delete Records
            else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    CourseBean deletebean = new CourseBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                    }

                    ServletUtility.setSuccessMessage("Course deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            // Reset Page
            else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
                return;
            }

            // Back Navigation
            else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
                return;
            }

            // Fetch Updated List
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
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Returns the view page for Course List.
     *
     * @return path of CourseListView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.COURSE_LIST_VIEW;
    }
}