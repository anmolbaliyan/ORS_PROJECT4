<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="in.com.rays.proj4.bean.TProductBean"%>

<%@page import="in.com.rays.proj4.controller.TProductListCtl"%>

<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>

<head>

<title>TProduct List View</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TProductBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy;">Product List</h1>

		<h3>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
			</font>
		</h3>

		<h3>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</h3>

		<form action="<%=ORSView.TPRODUCT_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<TProductBean> list = (List<TProductBean>) ServletUtility.getList(request);

			Iterator<TProductBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="100%">

				<tr>

					<td align="center"><label> <b>Product Name :</b>
					</label> <input type="text" name="productName"
						placeholder="Enter Product Name"
						value="<%=ServletUtility.getParameter("productName", request)%>">

						&emsp; <label> <b>Brand :</b>
					</label> <input type="text" name="brand" placeholder="Enter Brand"
						value="<%=ServletUtility.getParameter("brand", request)%>">

						&emsp; <input type="submit" name="operation"
						value="<%=TProductListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TProductListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" width="100%">

				<tr style="background-color: lightgray;">

					<th><input type="checkbox" id="selectall"></th>

					<th>S.No</th>

					<th>Product Name</th>

					<th>Brand</th>

					<th>Price</th>

					<th>Quantity</th>

					<th>Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getProductId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getProductName()%></td>

					<td align="center"><%=bean.getBrand()%></td>

					<td align="center"><%=bean.getPrice()%></td>

					<td align="center"><%=bean.getQuantity()%></td>

					<td align="center"><a
						href="TProductCtl?id=<%=bean.getProductId()%>"> Edit </a></td>

				</tr>

				<%
				}
				%>

			</table>

			<br>

			<table width="100%">

				<tr>

					<td><input type="submit" name="operation"
						value="<%=TProductListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TProductListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TProductListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=TProductListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

		</form>

	</div>

</body>
</html>