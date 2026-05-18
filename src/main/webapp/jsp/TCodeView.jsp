<%@page import="in.com.rays.proj4.controller.TCodeCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>

<head>
<title>TCode View</title>
</head>

<body>

	<form action="<%=ORSView.TCODE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TCodeBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="color: navy">

				<%
				if (bean != null && bean.getId() > 0) {
				%>

				Update TCode

				<%
				} else {
				%>

				Add TCode

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

					<th align="left">Language <span style="color: red">*</span>
					</th>

					<td><input type="text" name="language"
						placeholder="Enter Language"
						value="<%=DataUtility.getStringData(bean.getLanguage())%>">

					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("language", request)%>

					</font></td>

				</tr>

				<tr>

					<th align="left">Code <span style="color: red">*</span>
					</th>

					<td><textarea rows="5" cols="22" name="codeSnippet"
							placeholder="Enter Code"><%=DataUtility.getStringData(bean.getCodeSnippet())%></textarea>

					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("codeSnippet", request)%>

					</font></td>

				</tr>

				<tr>

					<th align="left">Execution Time <span style="color: red">*</span>
					</th>

					<td><input type="text" name="executionTime"
						placeholder="Enter Execution Time"
						value="<%=DataUtility.getStringData(bean.getExecutionTime())%>">

					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("executionTime", request)%>

					</font></td>

				</tr>

				<tr>

					<th align="left">Status <span style="color: red">*</span>
					</th>

					<td><input type="text" name="outputStatus"
						placeholder="Enter Status"
						value="<%=DataUtility.getStringData(bean.getOutputStatus())%>">

					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("outputStatus", request)%>

					</font></td>

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

					<td><input type="submit" name="operation"
						value="<%=TCodeCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TCodeCtl.OP_CANCEL%>"></td>

					<%
					} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=TCodeCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TCodeCtl.OP_RESET%>"></td>

					<%
					}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>

</html>