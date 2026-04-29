package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.com.rays.proj4.util.ServletUtility;

/**
 * Front Controller Filter for handling authentication and request interception.
 * <p>
 * This filter is mapped to <b>/ctl/*</b> and <b>/doc/*</b> URLs and acts as a
 * centralized control point in the application.
 * </p>
 *
 * <p>
 * It ensures that only authenticated users can access protected resources.
 * If the user session is not available, it redirects the user to the login page.
 * Otherwise, the request is forwarded to the next resource in the chain.
 * </p>
 *
 * <p>
 * This implementation follows the Front Controller design pattern
 * to manage request flow and session validation.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {

    /**
     * Intercepts incoming requests and checks user session.
     * <p>
     * If the session does not contain a logged-in user,
     * it forwards the request to the login page with an error message.
     * Otherwise, it allows the request to proceed further in the filter chain.
     * </p>
     *
     * @param req   ServletRequest object
     * @param resp  ServletResponse object
     * @param chain FilterChain to pass request to next resource
     * @throws IOException
     * @throws ServletException
     * @author Anmol Kumar Baliyan
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Fctl Do filter");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session.getAttribute("user") == null) {

            ServletUtility.setErrorMessage("Your Session has been Expired... Please Login Again", request);

            String uri = request.getRequestURI();
            request.setAttribute("uri", uri);

            System.out.println("URI " + uri);

            ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
            return;

        } else {
            // Continue request processing
            chain.doFilter(req, resp);
        }
    }

    /**
     * Initializes the filter.
     *
     * @param conf FilterConfig object
     * @throws ServletException
     * @author Anmol Kumar Baliyan
     */
    public void init(FilterConfig conf) throws ServletException {
    }

    /**
     * Destroys the filter instance.
     *
     * @author Anmol Kumar Baliyan
     */
    public void destroy() {
    }
}