<%@page import="in.com.rays.proj4.controller.TProductCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>TProduct View</title>
</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TProductBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.TPRODUCT_CTL%>" method="post">

		<div align="center">

			<h1 style="color: navy">

				<%
				if (bean != null && bean.getProductId() > 0) {
				%>

				Update Product

				<%
				} else {
				%>

				Add Product

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

			<input type="hidden" name="id" value="<%=bean.getProductId()%>">

			<table>

				<tr>
					<th align="left">Product Name<span style="color: red">*</span>
					</th>

					<td><input type="text" name="productName"
						placeholder="Enter Product Name"
						value="<%=DataUtility.getStringData(bean.getProductName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("productName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Brand<span style="color: red">*</span>
					</th>

					<td><input type="text" name="brand" placeholder="Enter Brand"
						value="<%=DataUtility.getStringData(bean.getBrand())%>"></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("brand", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Price<span style="color: red">*</span>
					</th>

					<td><input type="text" name="price" placeholder="Enter Price"
						value="<%=bean.getPrice()%>"></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("price", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Quantity<span style="color: red">*</span>
					</th>

					<td><input type="text" name="quantity"
						placeholder="Enter Quantity" value="<%=bean.getQuantity()%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<td>
						<%
						if (bean != null && bean.getProductId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=TProductCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TProductCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=TProductCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TProductCtl.OP_RESET%>"> <%
 }
 %>

					</td>
				</tr>

			</table>

		</div>

	</form>

</body>
</html>