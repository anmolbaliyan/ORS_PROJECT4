package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Role list operations such as
 * search, pagination, deletion, and navigation.
 * <p>
 * This servlet is mapped to <b>/ctl/RoleListCtl</b> and is responsible
 * for displaying role records with filtering options like role name.
 * </p>
 *
 * <p>
 * It supports pagination (Next/Previous), deletion of records,
 * and navigation to other modules.
 * This class follows MVC architecture by coordinating between
 * view (JSP) and model layers.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet("/ctl/RoleListCtl")
public class RoleListCtl extends BaseCtl {

    /**
     * Populates a RoleBean with request parameters.
     * <p>
     * This method extracts search criteria such as role name
     * and roleId from the request and sets them into a RoleBean.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return populated RoleBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        RoleBean bean = new RoleBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setId(DataUtility.getLong(request.getParameter("roleId")));

        return bean;
    }

    /**
     * Handles HTTP GET requests to display the role list.
     * <p>
     * This method initializes pagination parameters, retrieves role
     * records based on search criteria, and forwards the request to the
     * role list view. It also prepares data for next page navigation.
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

        RoleBean bean = (RoleBean) populateBean(request);
        RoleModel model = new RoleModel();

        try {
            List<RoleBean> list = model.search(bean, pageNo, pageSize);
            List<RoleBean> next = model.search(bean, pageNo + 1, pageSize);

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
            return;
        }
    }

    /**
     * Handles HTTP POST requests for role list operations.
     * <p>
     * This method processes different operations:
     * </p>
     * <ul>
     *   <li><b>Search</b>: Filters role records</li>
     *   <li><b>Next/Previous</b>: Handles pagination</li>
     *   <li><b>New</b>: Redirects to another module</li>
     *   <li><b>Delete</b>: Deletes selected role records</li>
     *   <li><b>Reset</b>: Reloads role list page</li>
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

        RoleBean bean = (RoleBean) populateBean(request);
        RoleModel model = new RoleModel();

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
                ServletUtility.redirect(ORSView.USER_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    RoleBean deletebean = new RoleBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Data is deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
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
            return;
        }
    }

    /**
     * Returns the view page for the role list.
     *
     * @return the path of the role list view page
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.ROLE_LIST_VIEW;
    }
}