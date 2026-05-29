<%@page import="in.com.rays.proj4.controller.THospitalSystemCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Hospital System</title>
</head>

<body>

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.com.rays.proj4.bean.THospitalSystemBean"
		scope="request">
	</jsp:useBean>

	<form action="<%=ORSView.THOSPITALSYSTEM_CTL%>"
		method="post">

		<div align="center">

			<h1 style="color: navy;">

				<%
				if (bean != null && bean.getId() > 0) {
				%>

				Update Hospital System

				<%
				} else {
				%>

				Add Hospital System

				<%
				}
				%>

			</h1>

			<h3>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<input type="hidden"
				name="id"
				value="<%=bean.getId()%>">

			<input type="hidden"
				name="createdBy"
				value="<%=bean.getCreatedBy()%>">

			<input type="hidden"
				name="modifiedBy"
				value="<%=bean.getModifiedBy()%>">

			<input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden"
				name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>

					<th align="left">
						Patient Name<span style="color: red">*</span>
					</th>

					<td>

						<input type="text"
							name="patientName"
							placeholder="Enter Patient Name"
							value="<%=DataUtility.getStringData(bean.getPatientName())%>">

					</td>

					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("patientName", request)%>
						</font>
					</td>

				</tr>

				<tr>

					<th align="left">
						Doctor Name<span style="color: red">*</span>
					</th>

					<td>

						<input type="text"
							name="doctorName"
							placeholder="Enter Doctor Name"
							value="<%=DataUtility.getStringData(bean.getDoctorName())%>">

					</td>

					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("doctorName", request)%>
						</font>
					</td>

				</tr>

				<tr>

					<th align="left">
						Disease<span style="color: red">*</span>
					</th>

					<td>

						<input type="text"
							name="disease"
							placeholder="Enter Disease"
							value="<%=DataUtility.getStringData(bean.getDisease())%>">

					</td>

					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("disease", request)%>
						</font>
					</td>

				</tr>

				<tr>

					<th align="left">
						Room Number<span style="color: red">*</span>
					</th>

					<td>

						<input type="text"
							name="roomNumber"
							placeholder="Enter Room Number"
							value="<%=bean.getRoomNumber()%>">

					</td>

					<td>
						<font color="red">
							<%=ServletUtility.getErrorMessage("roomNumber", request)%>
						</font>
					</td>

				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>

					<th></th>

					<%
					if (bean != null && bean.getId() > 0) {
					%>

					<td>

						<input type="submit"
							name="operation"
							value="<%=THospitalSystemCtl.OP_UPDATE%>">

						<input type="submit"
							name="operation"
							value="<%=THospitalSystemCtl.OP_CANCEL%>">

					</td>

					<%
					} else {
					%>

					<td>

						<input type="submit"
							name="operation"
							value="<%=THospitalSystemCtl.OP_SAVE%>">

						<input type="submit"
							name="operation"
							value="<%=THospitalSystemCtl.OP_RESET%>">

					</td>

					<%
					}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>