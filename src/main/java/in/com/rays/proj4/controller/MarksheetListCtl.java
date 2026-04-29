package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.MarksheetBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.MarksheetModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for managing Marksheet list operations such as
 * searching, viewing, deleting, and pagination.
 * <p>
 * This servlet is mapped to <b>/ctl/MarksheetListCtl</b> and is responsible
 * for handling user interactions related to marksheet records.
 * </p>
 *
 * <p>
 * It supports operations like Search, Next, Previous, New, Delete,
 * Reset, and Back. It follows MVC architecture by coordinating
 * between view (JSP) and model layers.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {

    /**
     * Populates a MarksheetBean with request parameters.
     * <p>
     * Extracts search criteria such as roll number and name
     * from the request and sets them into the bean.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return populated MarksheetBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        MarksheetBean bean = new MarksheetBean();

        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
        bean.setName(DataUtility.getString(request.getParameter("name")));

        return bean;
    }

    /**
     * Handles HTTP GET request to display marksheet list.
     * <p>
     * Initializes pagination and retrieves marksheet records
     * based on search criteria.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        MarksheetBean bean = (MarksheetBean) populateBean(request);
        MarksheetModel model = new MarksheetModel();

        try {
            List<MarksheetBean> list = model.search(bean, pageNo, pageSize);
            List<MarksheetBean> next = model.search(bean, pageNo + 1, pageSize);

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
     * Handles HTTP POST request for marksheet operations.
     * <p>
     * Supported operations:
     * </p>
     * <ul>
     *   <li><b>Search</b>: Filters records</li>
     *   <li><b>Next</b>: Moves to next page</li>
     *   <li><b>Previous</b>: Moves to previous page</li>
     *   <li><b>New</b>: Redirects to marksheet form</li>
     *   <li><b>Delete</b>: Deletes selected records</li>
     *   <li><b>Reset</b>: Reloads list</li>
     *   <li><b>Back</b>: Redirects to list</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
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

        MarksheetBean bean = (MarksheetBean) populateBean(request);
        MarksheetModel model = new MarksheetModel();

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
                ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    MarksheetBean deletebean = new MarksheetBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Marksheet is deleted successfully", request);
                    }

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
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
     * Returns the view page for Marksheet List.
     *
     * @return path of MarksheetListView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.MARKSHEET_LIST_VIEW;
    }
}
