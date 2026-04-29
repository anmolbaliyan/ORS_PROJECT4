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
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Subject list operations such as
 * search, pagination, deletion, and navigation.
 * <p>
 * This servlet is mapped to <b>/ctl/SubjectListCtl</b> and is responsible
 * for displaying subject records with filtering options like subject name,
 * course name, and description.
 * </p>
 *
 * <p>
 * It also supports pagination (Next/Previous), deletion of records,
 * and navigation to the subject form.
 * This class follows MVC architecture by coordinating between
 * view (JSP) and model layers.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {

    /**
     * Preloads data required for the subject list view.
     * <p>
     * This method retrieves subject and course lists from their respective
     * models and sets them into the request scope for use in dropdown filters.
     * </p>
     *
     * @param request HttpServletRequest object to store preload data
     * @author Anmol Kumar Baliyan
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
     * Populates a SubjectBean with request parameters.
     * <p>
     * This method extracts search criteria such as name, course name,
     * description, courseId, and subjectId from the request and sets
     * them into a SubjectBean object.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return populated SubjectBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SubjectBean bean = new SubjectBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setId(DataUtility.getLong(request.getParameter("subjectId")));

        return bean;
    }

    /**
     * Handles HTTP GET requests to display the subject list.
     * <p>
     * This method initializes pagination parameters, retrieves subject
     * records based on search criteria, and forwards the request to the
     * subject list view. It also prepares data for next page navigation.
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

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        SubjectBean bean = (SubjectBean) populateBean(request);
        SubjectModel model = new SubjectModel();

        try {
            List<SubjectBean> list = model.search(bean, pageNo, pageSize);
            List<SubjectBean> next = model.search(bean, pageNo + 1, pageSize);

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
     * Handles HTTP POST requests for subject list operations.
     * <p>
     * This method processes different operations based on user action:
     * </p>
     * <ul>
     *   <li><b>Search</b>: Filters subject records</li>
     *   <li><b>Next/Previous</b>: Handles pagination</li>
     *   <li><b>New</b>: Redirects to subject creation page</li>
     *   <li><b>Delete</b>: Deletes selected subject records</li>
     *   <li><b>Reset/Back</b>: Reloads subject list page</li>
     * </ul>
     *
     * <p>
     * It updates request attributes with the result list and pagination
     * details and forwards the request to the view.
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

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        SubjectBean bean = (SubjectBean) populateBean(request);
        SubjectModel model = new SubjectModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    SubjectBean deletebean = new SubjectBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Data is deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
                return;
            }

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
     * Returns the view page for the subject list.
     *
     * @return the path of the subject list view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.SUBJECT_LIST_VIEW;
    }
}