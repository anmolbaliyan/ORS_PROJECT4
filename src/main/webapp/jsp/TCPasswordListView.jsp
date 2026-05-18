<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="in.com.rays.proj4.bean.TCPasswordBean"%>

<%@page import="in.com.rays.proj4.controller.TCPasswordListCtl"%>

<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>

<head>

<title>TCPassword List</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TCPasswordBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">

			TCPassword List</h1>

		<div style="height: 15px; margin-bottom: 12px">

			<h3>

				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>

				</font>

			</h3>

			<h3>

				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>

				</font>

			</h3>

		</div>

		<form action="<%=ORSView.TCPASSWORD_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<TCPasswordBean> list = (List<TCPasswordBean>) ServletUtility.getList(request);

			Iterator<TCPasswordBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">

				<tr>

					<td align="center"><label> <b>Confirm Code :</b>
					</label> <input type="text" name="confirmCode"
						placeholder="Enter Confirm Code"
						value="<%=ServletUtility.getParameter("confirmCode", request)%>">

						&emsp; <label> <b>User Name :</b>
					</label> <input type="text" name="userName" placeholder="Enter User Name"
						value="<%=ServletUtility.getParameter("userName", request)%>">

						&emsp; <input type="submit" name="operation"
						value="<%=TCPasswordListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=TCPasswordListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th width="5%">S.No</th>

					<th width="20%">Confirm Code</th>

					<th width="25%">User Name</th>

					<th width="25%">Confirm Value</th>

					<th width="15%">Status</th>

					<th width="5%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = (TCPasswordBean) it.next();
				%>

				<tr>

					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>

					<td style="text-align: center;"><%=index++%></td>

					<td style="text-align: center;"><%=bean.getConfirmCode()%></td>

					<td style="text-align: center;"><%=bean.getUserName()%></td>

					<td style="text-align: center;"><%=bean.getConfirmValue()%></td>

					<td style="text-align: center;"><%=bean.getStatus()%></td>

					<td style="text-align: center;"><a
						href="TCPasswordCtl?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=TCPasswordListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TCPasswordListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TCPasswordListCtl.OP_DELETE%>">

					</td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=TCPasswordListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
			} else {
			%>

			<table>

				<tr>

					<td align="right"><input type="submit" name="operation"
						value="<%=TCPasswordListCtl.OP_BACK%>"></td>

				</tr>

			</table>

			<%
			}
			%>

		</form>

	</div>

</body>

</html>