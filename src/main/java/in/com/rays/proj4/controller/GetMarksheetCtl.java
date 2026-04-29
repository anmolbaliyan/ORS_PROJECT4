package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.MarksheetBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.MarksheetModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for fetching Marksheet details using Roll Number.
 * <p>
 * This servlet is mapped to <b>/ctl/GetMarksheetCtl</b> and is responsible
 * for validating user input, retrieving marksheet records, and displaying
 * the result to the user.
 * </p>
 *
 * <p>
 * It supports searching a marksheet by Roll Number and displays
 * either the result or an error message if the record is not found.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {

    /**
     * Validates input data for Roll Number.
     * <p>
     * Ensures that the Roll Number field is not empty.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return true if valid, false otherwise
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates MarksheetBean with request parameters.
     * <p>
     * Extracts Roll Number from the request and sets it into the bean.
     * </p>
     *
     * @param request HttpServletRequest containing input data
     * @return populated MarksheetBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        MarksheetBean bean = new MarksheetBean();

        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        return bean;
    }

    /**
     * Handles HTTP GET request.
     * <p>
     * Simply forwards to the Get Marksheet view page.
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

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST request for fetching marksheet.
     * <p>
     * If operation is "GO", it searches the marksheet by Roll Number:
     * </p>
     * <ul>
     *   <li>If found → displays marksheet details</li>
     *   <li>If not found → shows error message</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing user input
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        MarksheetModel model = new MarksheetModel();

        MarksheetBean bean = (MarksheetBean) populateBean(request);

        if (OP_GO.equalsIgnoreCase(op)) {

            try {

                bean = model.findByRollNo(bean.getRollNo());

                if (bean != null) {
                    ServletUtility.setBean(bean, request);
                } else {
                    ServletUtility.setErrorMessage("RollNo Does Not exists", request);
                }

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Get Marksheet.
     *
     * @return path of GetMarksheetView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.GET_MARKSHEET_VIEW;
    }
}