package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.MarksheetBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.MarksheetModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for displaying the Marksheet Merit List.
 * <p>
 * This servlet is mapped to <b>/ctl/MarksheetMeritListCtl</b> and is responsible
 * for fetching and displaying the top-ranking students based on marks.
 * </p>
 *
 * <p>
 * It retrieves the merit list using MarksheetModel and supports
 * pagination. It also provides navigation back to the welcome page.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {

    /**
     * Handles HTTP GET request to display the merit list.
     * <p>
     * This method retrieves a paginated list of top-performing students
     * and sets it in the request for rendering in the view.
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

        MarksheetModel model = new MarksheetModel();

        try {
            List<MarksheetBean> list = model.getMeritList(pageNo, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Handles HTTP POST request for navigation operations.
     * <p>
     * Currently supports:
     * </p>
     * <ul>
     *   <li><b>Back</b>: Redirects to the welcome page</li>
     * </ul>
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

        if (OP_BACK.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
            return;
        }
    }

    /**
     * Returns the view page for Marksheet Merit List.
     *
     * @return path of MarksheetMeritListView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.MARKSHEET_MERIT_LIST_VIEW;
    }
}
