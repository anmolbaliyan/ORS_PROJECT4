<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="in.com.rays.proj4.controller.TCodeListCtl"%>

<%@page import="in.com.rays.proj4.bean.TCodeBean"%>

<%@page import="in.com.rays.proj4.util.DataUtility"%>

<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>

<head>

<title>TCode List View</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TCodeBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy">TCode List</h1>

		<h3>

			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>

			</font>

		</h3>

		<h3>

			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>

			</font>

		</h3>

		<form action="<%=ORSView.TCODE_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<TCodeBean> list = (List<TCodeBean>) ServletUtility.getList(request);

			Iterator<TCodeBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="100%">

				<tr>

					<td align="center"><label><b>Language :</b></label> <input
						type="text" name="language" placeholder="Enter Language"
						value="<%=ServletUtility.getParameter("language", request)%>">

						&nbsp; <label><b>Status :</b></label> <input type="text"
						name="outputStatus" placeholder="Enter Status"
						value="<%=ServletUtility.getParameter("outputStatus", request)%>">

						&nbsp; <input type="submit" name="operation"
						value="<%=TCodeListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=TCodeListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<%
			if (list != null && list.size() > 0) {
			%>

			<table border="1" width="100%">

				<tr style="background-color: lightgray">

					<th><input type="checkbox" id="selectall"></th>

					<th>S.No</th>

					<th>Language</th>

					<th>Code</th>

					<th>Execution Time</th>

					<th>Status</th>

					<th>Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getLanguage()%></td>

					<td align="center"><%=bean.getCodeSnippet()%></td>

					<td align="center"><%=bean.getExecutionTime()%></td>

					<td align="center"><%=bean.getOutputStatus()%></td>

					<td align="center"><a href="TCodeCtl?id=<%=bean.getId()%>">
							Edit </a></td>

				</tr>

				<%
				}
				%>

			</table>

			<br>

			<table width="100%">

				<tr>

					<td><input type="submit" name="operation"
						value="<%=TCodeListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TCodeListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TCodeListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=TCodeListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
			} else {
			%>

			<table>

				<tr>

					<td><input type="submit" name="operation"
						value="<%=TCodeListCtl.OP_BACK%>"></td>

				</tr>

			</table>

			<%
			}
			%>

		</form>

	</div>

</body>

</html>