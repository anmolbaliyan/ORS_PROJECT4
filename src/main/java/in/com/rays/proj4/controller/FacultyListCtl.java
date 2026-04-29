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
import in.com.rays.proj4.model.FacultyModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for managing Faculty List operations.
 * <p>
 * This servlet is mapped to <b>/ctl/FacultyListCtl</b> and is responsible
 * for handling faculty search, pagination, deletion, and navigation actions.
 * </p>
 *
 * <p>
 * It interacts with the FacultyModel to retrieve and manipulate faculty records
 * and forwards data to the view layer for display.
 * </p>
 *
 * <p>
 * Supported operations include:
 * </p>
 * <ul>
 *   <li><b>Search</b>: Filter faculty records</li>
 *   <li><b>Next/Previous</b>: Pagination</li>
 *   <li><b>New</b>: Redirect to faculty form</li>
 *   <li><b>Delete</b>: Remove selected records</li>
 *   <li><b>Reset</b>: Reload list page</li>
 *   <li><b>Back</b>: Redirect to list view</li>
 * </ul>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {

    /**
     * Populates FacultyBean with request parameters.
     * <p>
     * Extracts search fields such as first name, last name, and email.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return populated FacultyBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        FacultyBean bean = new FacultyBean();

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));

        return bean;
    }

    /**
     * Handles HTTP GET request.
     * <p>
     * Initializes pagination and retrieves the first page of faculty records.
     * Sets list data and forwards to the faculty list view.
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

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        FacultyBean bean = (FacultyBean) populateBean(request);
        FacultyModel model = new FacultyModel();

        try {

            List<FacultyBean> list = model.search(bean, pageNo, pageSize);
            List<FacultyBean> next = model.search(bean, pageNo + 1, pageSize);

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
        }
    }

    /**
     * Handles HTTP POST request for faculty list operations.
     * <p>
     * Processes different operations such as search, pagination,
     * deletion, reset, and navigation.
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

        FacultyBean bean = (FacultyBean) populateBean(request);
        FacultyModel model = new FacultyModel();

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

            // Redirect to Add Faculty page
            else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
                return;
            }

            // Delete Selected Records
            else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    FacultyBean deletebean = new FacultyBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                    }

                    ServletUtility.setSuccessMessage("Faculty is deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            // Reset Page
            else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
                return;
            }

            // Back Navigation
            else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
                return;
            }

            // Fetch updated list
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
     * Returns the view page for Faculty List.
     *
     * @return path of FacultyListView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.FACULTY_LIST_VIEW;
    }
}