<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.controller.THospitalSystemListCtl"%>

<html>
<head>

<title>Hospital System List</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.com.rays.proj4.bean.THospitalSystemBean" scope="request">
	</jsp:useBean>

	<div align="center">

		<h1 style="color: navy;">Hospital System List</h1>

		<h3>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
			</font>
		</h3>

		<h3>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</h3>

		<form action="<%=ORSView.THOSPITALSYSTEM_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);

			Iterator it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="100%">

				<tr>

					<td align="center"><label> <b>Patient Name :</b>
					</label> <input type="text" name="patientName"
						placeholder="Enter Patient Name"
						value="<%=ServletUtility.getParameter("patientName", request)%>">

						&nbsp; <label> <b>Doctor Name :</b>
					</label> <input type="text" name="doctorName"
						placeholder="Enter Doctor Name"
						value="<%=ServletUtility.getParameter("doctorName", request)%>">

						&nbsp; <input type="submit" name="operation"
						value="<%=THospitalSystemListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=THospitalSystemListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" width="100%" style="border-collapse: collapse;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall">
					</th>

					<th width="5%">S.No</th>

					<th width="20%">Patient Name</th>

					<th width="20%">Doctor Name</th>

					<th width="20%">Disease</th>

					<th width="15%">Room Number</th>

					<th width="5%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = (in.com.rays.proj4.bean.THospitalSystemBean) it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getPatientName()%></td>

					<td align="center"><%=bean.getDoctorName()%></td>

					<td align="center"><%=bean.getDisease()%></td>

					<td align="center"><%=bean.getRoomNumber()%></td>

					<td align="center"><a style="text-decoration: none;"
						href="THospitalSystemCtl?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
				}
				%>

			</table>

			<br>

			<table width="100%">

				<tr>

					<td width="25%"><input type="submit" name="operation"
						value="<%=THospitalSystemListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" align="center"><input type="submit"
						name="operation" value="<%=THospitalSystemListCtl.OP_NEW%>">

					</td>

					<td width="25%" align="center"><input type="submit"
						name="operation" value="<%=THospitalSystemListCtl.OP_DELETE%>">

					</td>

					<td width="25%" align="right"><input type="submit"
						name="operation" value="<%=THospitalSystemListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

		</form>

	</div>

</body>
</html>