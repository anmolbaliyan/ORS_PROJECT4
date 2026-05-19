<%@page import="in.com.rays.proj4.controller.ORSView"%>
<%@page import="in.com.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="/ORS_Project4/js/checkbox.js"></script>
<script src="/ORS_Project4/js/datepicker.js"></script>

</head>
<body>
	<%
	UserBean user = (UserBean) session.getAttribute("user");
	%>
	<%
	if (user != null) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>
	<a href=<%=ORSView.MY_PROFILE_CTL%>><b>My Profile</b></a>
	<b>|</b>
	<a href=<%=ORSView.CHANGE_PASSWORD_CTL%>><b>Change Password</b></a>
	<b>|</b>
	<a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a>
	<b>|</b>
	<a href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a>
	<b>|</b>
	<a href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a>
	<b>|</b>
	<a href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a>
	<b>|</b>
	<a href=<%=ORSView.COLLEGE_CTL%>><b>Add College</b></a>
	<b>|</b>
	<a href=<%=ORSView.COLLEGE_LIST_CTL%>><b>College List</b></a>
	<b>|</b>
	<a href=<%=ORSView.STUDENT_CTL%>><b>Add Student</b></a>
	<b>|</b>
	<a href=<%=ORSView.STUDENT_LIST_CTL%>><b>Student List</b></a>
	<b>|</b>
	<a href=<%=ORSView.GET_MARKSHEET_CTL%>><b>Get Marksheet</b></a>
	<b>|</b>
	<a href=<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>><b>Marksheet
			Merit List</b></a>
	<b>|</b>
	<a href=<%=ORSView.MARKSHEET_CTL%>><b>Add Marksheet</b></a>
	<b>|</b>
	<a href=<%=ORSView.MARKSHEET_LIST_CTL%>><b>Marksheet List</b></a>
	<b>|</b>
	<a href=<%=ORSView.COURSE_CTL%>><b>Add Course</b></a>
	<b>|</b>
	<a href=<%=ORSView.COURSE_LIST_CTL%>><b>Course List</b></a>
	<b>|</b>
	<a href=<%=ORSView.SUBJECT_CTL%>><b>Add Subject</b></a>
	<b>|</b>
	<a href=<%=ORSView.SUBJECT_LIST_CTL%>><b>Subject List</b></a>
	<b>|</b>
	<a href=<%=ORSView.TIMETABLE_CTL%>><b>Add Timetable</b></a>
	<b>|</b>
	<a href=<%=ORSView.TIMETABLE_LIST_CTL%>><b>Timetable List</b></a>
	<b>|</b>
	<a href=<%=ORSView.FACULTY_CTL%>><b>Add Faculty</b></a>
	<b>|</b>
	<a href=<%=ORSView.FACULTY_LIST_CTL%>><b>Faculty List</b></a>
	<b>|</b>
	<a href=<%=ORSView.TSESSION_CTL%>><b>Add TSession</b></a>
	<b>|</b>
	<a href=<%=ORSView.TSESSION_LIST_CTL%>><b>TSession List</b></a>
	<b>|</b>
	<a href=<%=ORSView.TCPASSWORD_CTL%>><b>Add TCPassword</b></a>
	<b>|</b>
	<a href=<%=ORSView.TCPASSWORD_LIST_CTL%>><b>TCPassword List</b></a>
	<b>|</b>
	<a href=<%=ORSView.TCODE_CTL%>><b>Add TCode</b></a>
	<b>|</b>
	<a href=<%=ORSView.TCODE_LIST_CTL%>><b>TCode List</b></a>
	<b>|</b>
	<a href=<%=ORSView.TPRODUCT_CTL%>><b>Add TProduct</b></a>
	<b>|</b>
	<a href=<%=ORSView.TPRODUCT_LIST_CTL%>><b>TProduct List</b></a>
	<b>|</b>
	<a href=<%=ORSView.TBANKING_CTL%>><b>Add TBanking</b></a>
	<b>|</b>
	<a href=<%=ORSView.TBANKING_LIST_CTL%>><b>TBanking List</b></a>
	<b>|</b>
	<a target="blank" href="<%=ORSView.JAVA_DOC%>"><b>Java Doc</b></a>
	<b>|</b>
	<a href="<%=ORSView.LOGIN_CTL + "?operation=Logout"%>"><b>Logout</b></a>

	<%
	} else {
	%>
	<h3>Hi, Guest</h3>
	<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> |
	<a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a>
	<%-- <a href="<%=ORSView.USER_REGISTRATION_CTL%>">SignUp</a> --%>
	<%
	}
	%>
	<hr>
	<%@include file="footer.jsp"%>
</body>
</html>