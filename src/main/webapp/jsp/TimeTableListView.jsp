<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.com.rays.proj4.controller.TimeTableListCtl"%>
<%@page import="in.com.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.com.rays.proj4.bean.TimeTableBean"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<html>
<head>
<title>Timetable List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Timetable
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TimeTableBean"
			scope="request"></jsp:useBean>

		<form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="post">
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<TimeTableBean> courseList = (List<TimeTableBean>) request.getAttribute("courseList");
				List<TimeTableBean> subjectList = (List<TimeTableBean>) request.getAttribute("subjectList");

				List<TimeTableBean> list = (List<TimeTableBean>) ServletUtility.getList(request);
				Iterator<TimeTableBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="right"><label><b>Course Name :</b></label><input type="text" name="name" placeholder="Enter Course Name" value="<%=ServletUtility.getParameter("name", request)%>">&emsp;
						<label><b>Subject Name :</b></label> <input type="text" name="name" placeholder="Enter Subject Name" value="<%=ServletUtility.getParameter("name", request)%>">&emsp;
						<label><b>Exam Date :</b></label></td>
					<td align="left"><input type="text" id="update" name="examDate" placeholder="Select Date of Exam"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>">
				</label>&emsp; <input type="submit" name="operation"
					value="<%=TimeTableListCtl.OP_SEARCH%>">&nbsp; <input
						type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th style="width: 5%;"><input type="checkbox" id="selectall" /></th>
					<th style="width: 5%;">S.No</th>
					<th style="width: 13%;">Course Name</th>
					<th style="width: 30%;">Subject Name</th>
					<th style="width: 7%;">Semester</th>
					<th style="width: 10%;">Exam Date</th>
					<th style="width: 15%;">Exam Time</th>
					<th style="width: 10%;">Description</th>
					<th style="width: 5%;">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>
				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getCourseName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getSubjectName()%></td>
					<td style="text-align: center;"><%=bean.getSemester()%></td>
					<%
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
								String date = sdf.format(bean.getExamDate());
					%>
					<td style="text-align: center;"><%=date%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getExamTime()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getDescription()%></td>
					<td style="text-align: center;"><a
						href="TimetableCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table style="width: 100%">
				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_PREVIOUS%>"
						<%=pageNo == 1 ? "" : "disabled" %>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TimeTableListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TimeTableListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=TimeTableListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				}
			%>

		</form>
	</div>
</body>
</html>