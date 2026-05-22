<%@page import="in.com.rays.proj4.controller.TIotDeviceListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.bean.TIotDeviceBean"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>

<title>IoT Device List</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TIotDeviceBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15px; color: navy;">

			IoT Device List</h1>

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

		<form action="<%=ORSView.TIOTDEVICE_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<TIotDeviceBean> list = (List<TIotDeviceBean>) ServletUtility.getList(request);

			Iterator<TIotDeviceBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">

				<tr>

					<td align="center"><label> <b>Device Name :</b>
					</label> <input type="text" name="deviceName"
						placeholder="Enter Device Name"
						value="<%=ServletUtility.getParameter("deviceName", request)%>">

						&nbsp;&nbsp; <label> <b>Status :</b>
					</label> <input type="text" name="status" placeholder="Enter Status"
						value="<%=ServletUtility.getParameter("status", request)%>">

						&nbsp;&nbsp; <input type="submit" name="operation"
						value="<%=TIotDeviceListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TIotDeviceListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall">
					</th>

					<th width="5%">S.No</th>
					<th width="10%">Device ID</th>
					<th width="20%">Device Name</th>
					<th width="20%">Sensor Type</th>
					<th width="15%">Status</th>
					<th width="15%">Battery Level</th>
					<th width="10%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getDeviceId()%></td>

					<td align="center"><%=bean.getDeviceName()%></td>

					<td align="center"><%=bean.getSensorType()%></td>

					<td align="center"><%=bean.getStatus()%></td>

					<td align="center"><%=bean.getBatteryLevel()%></td>

					<td align="center"><a
						href="TIotDeviceCtl?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=TIotDeviceListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TIotDeviceListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TIotDeviceListCtl.OP_DELETE%>">

					</td>

					<td align="right" style="width: 25%"><input type="submit"
						name="operation" value="<%=TIotDeviceListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
			} else {
			%>

			<table>

				<tr>

					<td align="right"><input type="submit" name="operation"
						value="<%=TIotDeviceListCtl.OP_BACK%>"></td>

				</tr>

			</table>

			<%
			}
			%>

		</form>

	</div>

</body>
</html>