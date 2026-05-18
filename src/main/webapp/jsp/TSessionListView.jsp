<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="in.com.rays.proj4.controller.TSessionListCtl"%>
<%@page import="in.com.rays.proj4.bean.TSessionBean"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>

<title>TSession List</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.com.rays.proj4.bean.TSessionBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			TSession List
		</h1>

		<div style="height: 15px; margin-bottom: 12px">

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

		</div>

		<form action="<%=ORSView.TSESSION_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility
					.getInt(request.getAttribute("nextListSize").toString());

			List<TSessionBean> list =
					(List<TSessionBean>) ServletUtility.getList(request);

			Iterator<TSessionBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo"
				value="<%=pageNo%>">

			<input type="hidden" name="pageSize"
				value="<%=pageSize%>">

			<table style="width: 100%">

				<tr>

					<td align="center">

						<label>
							<b>Session Code :</b>
						</label>

						<input type="text" name="sessionCode"
							placeholder="Enter Session Code"
							value="<%=ServletUtility.getParameter("sessionCode", request)%>">

						&emsp;

						<label>
							<b>User Name :</b>
						</label>

						<input type="text" name="userName"
							placeholder="Enter User Name"
							value="<%=ServletUtility.getParameter("userName", request)%>">

						&emsp;

						<label>
							<b>Status :</b>
						</label>

						<input type="text" name="status"
							placeholder="Enter Status"
							value="<%=ServletUtility.getParameter("status", request)%>">

						&emsp;

						<input type="submit" name="operation"
							value="<%=TSessionListCtl.OP_SEARCH%>">

						&nbsp;

						<input type="submit" name="operation"
							value="<%=TSessionListCtl.OP_RESET%>">

					</td>

				</tr>

			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%">
						<input type="checkbox" id="selectall" />
					</th>

					<th width="5%">S.No</th>

					<th width="20%">Session Code</th>

					<th width="25%">User Name</th>

					<th width="25%">Login Time</th>

					<th width="15%">Status</th>

					<th width="5%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = (TSessionBean) it.next();

					SimpleDateFormat sdf =
							new SimpleDateFormat("dd-MM-yyyy");

					String date =
							sdf.format(bean.getLoginTime());
				%>

				<tr>

					<td style="text-align: center;">

						<input type="checkbox"
							class="case"
							name="ids"
							value="<%=bean.getId()%>">

					</td>

					<td style="text-align: center;">
						<%=index++%>
					</td>

					<td style="text-align: center;">
						<%=bean.getSessionCode()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getUserName()%>
					</td>

					<td style="text-align: center;">
						<%=date%>
					</td>

					<td style="text-align: center;">
						<%=bean.getStatus()%>
					</td>

					<td style="text-align: center;">

						<a href="TSessionCtl?id=<%=bean.getId()%>">
							Edit
						</a>

					</td>

				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">

				<tr>

					<td style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=TSessionListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>

					</td>

					<td align="center" style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=TSessionListCtl.OP_NEW%>">

					</td>

					<td align="center" style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=TSessionListCtl.OP_DELETE%>">

					</td>

					<td style="width: 25%" align="right">

						<input type="submit"
							name="operation"
							value="<%=TSessionListCtl.OP_NEXT%>"
							<%=nextListSize != 0 ? "" : "disabled"%>>

					</td>

				</tr>

			</table>

			<%
			} else {
			%>

			<table>

				<tr>

					<td align="right">

						<input type="submit"
							name="operation"
							value="<%=TSessionListCtl.OP_BACK%>">

					</td>

				</tr>

			</table>

			<%
			}
			%>

		</form>

	</div>

</body>
</html>