<%@page import="in.com.rays.proj4.controller.T_SmartHomeCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Smart Home Device</title>
</head>
<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.T_SmartHomeBean"
		scope="request">
	</jsp:useBean>

	<form action="<%=ORSView.T_SMARTHOME_CTL%>" method="post">

		<div align="center">

			<h1 style="color: navy">

				<%
				if (bean.getId() > 0) {
				%>
				Update Smart Home Device
				<%
				} else {
				%>
				Add Smart Home Device
				<%
				}
				%>

			</h1>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Device Name <span style="color: red">*</span>
					</th>

					<td><input type="text" name="deviceName"
						placeholder="Enter Device Name"
						value="<%=DataUtility.getStringData(bean.getDeviceName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("deviceName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Room Name <span style="color: red">*</span>
					</th>

					<td><input type="text" name="roomName"
						placeholder="Enter Room Name"
						value="<%=DataUtility.getStringData(bean.getRoomName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("roomName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Power Status <span style="color: red">*</span>
					</th>

					<td><input type="text" name="powerStatus"
						placeholder="Enter Power Status"
						value="<%=DataUtility.getStringData(bean.getPowerStatus())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("powerStatus", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Energy Usage <span style="color: red">*</span>
					</th>

					<td><input type="text" name="energyUsage"
						placeholder="Enter Energy Usage"
						value="<%=bean.getEnergyUsage() == 0 ? "" : bean.getEnergyUsage()%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("energyUsage", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<%
					if (bean.getId() > 0) {
					%>

					<td colspan="2"><input type="submit" name="operation"
						value="<%=T_SmartHomeCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=T_SmartHomeCtl.OP_CANCEL%>"></td>

					<%
					} else {
					%>

					<td colspan="2"><input type="submit" name="operation"
						value="<%=T_SmartHomeCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=T_SmartHomeCtl.OP_RESET%>"></td>

					<%
					}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>