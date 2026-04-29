package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling College List operations such as
 * searching, pagination, and deletion of college records.
 * <p>
 * This servlet is mapped to <b>/ctl/CollegeListCtl</b> and is responsible
 * for fetching college data from the model and displaying it in list view.
 * It also supports pagination (Next/Previous), search functionality,
 * and bulk deletion of records.
 * </p>
 *
 * <p>
 * It follows MVC architecture and interacts with CollegeModel
 * to retrieve and manipulate data, and forwards it to JSP view.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "CollegeListCtl", urlPatterns = { "/ctl/CollegeListCtl" })
public class CollegeListCtl extends BaseCtl {

    /**
     * Preloads data required for the view.
     * <p>
     * This method loads the list of all colleges and sets it
     * as a request attribute for use in dropdowns or filters.
     * </p>
     *
     * @param request HttpServletRequest object
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
     * Populates CollegeBean with request parameters.
     * <p>
     * Extracts values such as name, city, and id from the request
     * and sets them into the bean for search/filter operations.
     * </p>
     *
     * @param request HttpServletRequest containing input data
     * @return populated CollegeBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CollegeBean bean = new CollegeBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCity(DataUtility.getString(request.getParameter("city")));
        bean.setId(DataUtility.getLong(request.getParameter("collegeId")));

        return bean;
    }

    /**
     * Handles HTTP GET requests.
     * <p>
     * Initializes pagination and fetches the first page of college records.
     * Also prepares next page data to control pagination buttons.
     * </p>
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        CollegeBean bean = (CollegeBean) populateBean(request);
        CollegeModel model = new CollegeModel();

        try {
            List<CollegeBean> list = model.search(bean, pageNo, pageSize);
            List<CollegeBean> next = model.search(bean, pageNo + 1, pageSize);

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
     * Handles HTTP POST requests for list operations.
     * <p>
     * Supported operations:
     * </p>
     * <ul>
     *   <li><b>Search</b> - Filter college records</li>
     *   <li><b>Next</b> - Move to next page</li>
     *   <li><b>Previous</b> - Move to previous page</li>
     *   <li><b>New</b> - Redirect to add new college</li>
     *   <li><b>Delete</b> - Delete selected records</li>
     *   <li><b>Reset</b> - Reload list page</li>
     *   <li><b>Back</b> - Redirect to list page</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse object
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

        CollegeBean bean = (CollegeBean) populateBean(request);
        CollegeModel model = new CollegeModel();

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
                ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    CollegeBean deletebean = new CollegeBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                    }
                    ServletUtility.setSuccessMessage("Data is deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
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
     * Returns the view page for college list.
     *
     * @return path of the college list JSP page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.COLLEGE_LIST_VIEW;
    }
}