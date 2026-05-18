<%@page import="in.com.rays.proj4.controller.TSessionCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>TSession View</title>
</head>

<body>

	<form action="<%=ORSView.TSESSION_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.com.rays.proj4.bean.TSessionBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
				if (bean != null && bean.getId() > 0) {
				%>

				Update

				<%
				} else {
				%>

				Add

				<%
				}
				%>

				TSession
			</h1>

			<div style="height: 15px; margin-bottom: 12px">

				<h3 align="center">
					<font color="red">
						<%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="green">
						<%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

			</div>

			<input type="hidden" name="id"
				value="<%=bean.getId()%>">

			<input type="hidden" name="createdBy"
				value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>">

			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>

					<th align="left">
						Session Code<span style="color: red">*</span>
					</th>

					<td>
						<input type="text" name="sessionCode"
							placeholder="Enter Session Code"
							value="<%=DataUtility.getStringData(bean.getSessionCode())%>">
					</td>

					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("sessionCode", request)%>
						</font>
					</td>

				</tr>

				<tr>

					<th align="left">
						User Name<span style="color: red">*</span>
					</th>

					<td>
						<input type="text" name="userName"
							placeholder="Enter User Name"
							value="<%=DataUtility.getStringData(bean.getUserName())%>">
					</td>

					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("userName", request)%>
						</font>
					</td>

				</tr>

				<tr>

					<th align="left">
						Login Time<span style="color: red">*</span>
					</th>

					<td>
						<input type="text" id="udate" name="loginTime"
							placeholder="Enter Login Time"
							value="<%=DataUtility.getDateString(bean.getLoginTime())%>">
					</td>

					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("loginTime", request)%>
						</font>
					</td>

				</tr>

				<tr>

					<th align="left">
						Status<span style="color: red">*</span>
					</th>

					<td>
						<input type="text" name="status"
							placeholder="Enter Status"
							value="<%=DataUtility.getStringData(bean.getStatus())%>">
					</td>

					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("status", request)%>
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

					<td align="left" colspan="2">

						<input type="submit" name="operation"
							value="<%=TSessionCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
							value="<%=TSessionCtl.OP_CANCEL%>">

					</td>

					<%
					} else {
					%>

					<td align="left" colspan="2">

						<input type="submit" name="operation"
							value="<%=TSessionCtl.OP_SAVE%>">

						<input type="submit" name="operation"
							value="<%=TSessionCtl.OP_RESET%>">

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