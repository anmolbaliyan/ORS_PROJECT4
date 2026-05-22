<%@page import="in.com.rays.proj4.controller.TIotDeviceCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>IoT Device</title>
</head>
<body>

	<form action="<%=ORSView.TIOTDEVICE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TIotDeviceBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15px; color: navy">

				<%
				if (bean != null && bean.getDeviceId() > 0) {
				%>
				Update
				<%
				} else {
				%>
				Add
				<%
				}
				%>

				IoT Device

			</h1>

			<div style="height: 15px; margin-bottom: 12px">

				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

			</div>

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
					<th align="left">Device ID <span style="color: red">*</span>
					</th>

					<td><input type="text" name="deviceId"
						placeholder="Enter Device ID" value="<%=bean.getDeviceId()%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("deviceId", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Device Name <span style="color: red">*</span>
					</th>

					<td><input type="text" name="deviceName"
						placeholder="Enter Device Name"
						value="<%=DataUtility.getStringData(bean.getDeviceName())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("deviceName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Sensor Type <span style="color: red">*</span>
					</th>

					<td><input type="text" name="sensorType"
						placeholder="Enter Sensor Type"
						value="<%=DataUtility.getStringData(bean.getSensorType())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("sensorType", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status <span style="color: red">*</span>
					</th>

					<td><input type="text" name="status"
						placeholder="Enter Status"
						value="<%=DataUtility.getStringData(bean.getStatus())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Battery Level <span style="color: red">*</span>
					</th>

					<td><input type="text" name="batteryLevel"
						placeholder="Enter Battery Level"
						value="<%=bean.getBatteryLevel()%>"></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("batteryLevel", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>

					<th></th>

					<%
					if (bean != null && bean.getDeviceId() > 0) {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TIotDeviceCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=TIotDeviceCtl.OP_CANCEL%>"></td>

					<%
					} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TIotDeviceCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=TIotDeviceCtl.OP_RESET%>">

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