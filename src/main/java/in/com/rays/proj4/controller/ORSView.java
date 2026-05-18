package in.com.rays.proj4.controller;

/**
 * ORSView interface contains all view (JSP) and controller (URL)
 * constants used in the application.
 * <p>
 * This interface acts as a centralized place for managing navigation
 * between different pages and controllers in the ORS (Online Result System).
 * It helps avoid hardcoding URLs across the application and improves
 * maintainability.
 * </p>
 *
 * <p>
 * All JSP view paths and controller mappings are defined here,
 * following MVC architecture.
 * </p>
 *
 * <b>Example:</b>
 * <ul>
 *   <li>USER_VIEW → points to User JSP page</li>
 *   <li>USER_CTL → points to User Controller URL</li>
 * </ul>
 *
 * @author Anmol Kumar Baliyan
 */
public interface ORSView {

    /**
     * Application context path.
     * Used as a prefix for all controller URLs.
     */
    public String APP_CONTEXT = "/ORS_Project4";

    /**
     * Base folder for all JSP pages.
     */
    public String PAGE_FOLDER = "/jsp";
    
    
    /**
     * java doc
     */
    public String JAVA_DOC = APP_CONTEXT + "/doc/index.html";

    /** Welcome Page */
    public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
    public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

    /** User Registration */
    public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
    public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

    /** Login */
    public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
    public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

    /** Marksheet (Public) */
    public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
    public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

    /** Marksheet Merit List */
    public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
    public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

    /** User */
    public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
    public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

    public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
    public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

    /** Role */
    public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
    public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

    public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
    public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

    /** College */
    public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
    public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

    public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
    public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

    /** Student */
    public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
    public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

    public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
    public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

    /** Marksheet (Admin) */
    public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
    public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

    public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
    public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

    /** Course */
    public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
    public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

    public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
    public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

    /** Subject */
    public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
    public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

    public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
    public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

    /** TimeTable */
    public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
    public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";

    public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
    public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";

    /** Faculty */
    public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
    public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

    public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
    public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

    /** Password Management */
    public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
    public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

    public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
    public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

    public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
    public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

    /** Error Handling */
    public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
    public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";
    
    public String TSESSION_VIEW = PAGE_FOLDER + "/TSessionView.jsp";
    public String TSESSION_CTL = APP_CONTEXT + "/ctl/TSessionCtl";

    public String TSESSION_LIST_VIEW = PAGE_FOLDER + "/TSessionListView.jsp";
    public String TSESSION_LIST_CTL = APP_CONTEXT + "/ctl/TSessionListCtl";
    
    public String TCPASSWORD_VIEW = PAGE_FOLDER + "/TCPasswordView.jsp";
    public String TCPASSWORD_CTL = APP_CONTEXT + "/ctl/TCPasswordCtl";

    public String TCPASSWORD_LIST_VIEW = PAGE_FOLDER + "/TCPasswordListView.jsp";
    public String TCPASSWORD_LIST_CTL = APP_CONTEXT + "/ctl/TCPasswordListCtl";
    
    public String TCODE_VIEW = PAGE_FOLDER + "/TCodeView.jsp";
    public String TCODE_CTL = APP_CONTEXT + "/ctl/TCodeCtl";

    public String TCODE_LIST_VIEW = PAGE_FOLDER + "/TCodeListView.jsp";
    public String TCODE_LIST_CTL = APP_CONTEXT + "/ctl/TCodeListCtl";
    
    public String TPRODUCT_VIEW = PAGE_FOLDER + "/TProductView.jsp";
    public String TPRODUCT_CTL = APP_CONTEXT + "/ctl/TProductCtl";

    public String TPRODUCT_LIST_VIEW = PAGE_FOLDER + "/TProductListView.jsp";
    public String TPRODUCT_LIST_CTL = APP_CONTEXT + "/ctl/TProductListCtl";

}