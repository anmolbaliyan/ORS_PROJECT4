<%@page import="in.com.rays.proj4.controller.T_SmartHomeListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>

<html>
<head>
<title>Smart Home List</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/ORS_Project4/js/checkbox.js"></script>

</head>
<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.T_SmartHomeBean"
		scope="request">
	</jsp:useBean>

	<div align="center">

		<h1 align="center" style="color: navy;">Smart Home List</h1>

		<h3>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
			</font>
		</h3>

		<h3>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</h3>

		<form action="<%=ORSView.T_SMARTHOME_LIST_CTL%>" method="post">

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

					<td align="center"><label><b>Device Name :</b></label> <input
						type="text" name="deviceName" placeholder="Enter Device Name"
						value="<%=ServletUtility.getParameter("deviceName", request)%>">

						&nbsp; <label><b>Room Name :</b></label> <input type="text"
						name="roomName" placeholder="Enter Room Name"
						value="<%=ServletUtility.getParameter("roomName", request)%>">

						&nbsp; <input type="submit" name="operation"
						value="<%=T_SmartHomeListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=T_SmartHomeListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" width="100%">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall">
					</th>

					<th width="5%">S.No</th>

					<th width="25%">Device Name</th>

					<th width="25%">Room Name</th>

					<th width="20%">Power Status</th>

					<th width="15%">Energy Usage</th>

					<th width="5%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = (in.com.rays.proj4.bean.T_SmartHomeBean) it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getDeviceName()%></td>

					<td align="center"><%=bean.getRoomName()%></td>

					<td align="center"><%=bean.getPowerStatus()%></td>

					<td align="center"><%=bean.getEnergyUsage()%></td>

					<td align="center"><a
						href="T_SmartHomeCtl?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
				}
				%>

			</table>

			<br>

			<table width="100%">

				<tr>

					<td width="25%"><input type="submit" name="operation"
						value="<%=T_SmartHomeListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" align="center"><input type="submit"
						name="operation" value="<%=T_SmartHomeListCtl.OP_NEW%>"></td>

					<td width="25%" align="center"><input type="submit"
						name="operation" value="<%=T_SmartHomeListCtl.OP_DELETE%>">

					</td>

					<td width="25%" align="right"><input type="submit"
						name="operation" value="<%=T_SmartHomeListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

		</form>

	</div>

</body>
</html>