<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="in.com.rays.proj4.bean.TBankingBean"%>

<%@page import="in.com.rays.proj4.controller.TBankingListCtl"%>

<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>

<head>

<title>TBanking List View</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/ORS_Project4/js/checkbox.js"></script>

</head>

<body>

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TBankingBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy;">Banking List</h1>

		<h3>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
			</font>
		</h3>

		<h3>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</h3>

		<form action="<%=ORSView.TBANKING_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<TBankingBean> list = (List<TBankingBean>) ServletUtility.getList(request);

			Iterator<TBankingBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="100%">

				<tr>

					<td align="center"><label><b>Holder Name :</b></label> <input
						type="text" name="holderName" placeholder="Enter Holder Name"
						value="<%=ServletUtility.getParameter("holderName", request)%>">

						<label><b>Account Type :</b></label> <input type="text"
						name="accountType" placeholder="Enter Account Type"
						value="<%=ServletUtility.getParameter("accountType", request)%>">

						<input type="submit" name="operation"
						value="<%=TBankingListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TBankingListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" width="100%">

				<tr style="background-color: lightgray;">

					<th><input type="checkbox" id="selectall"></th>

					<th>S.No</th>

					<th>Account No</th>

					<th>Holder Name</th>

					<th>Balance</th>

					<th>Account Type</th>

					<th>Transaction Id</th>

					<th>Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = it.next();
				%>

				<tr align="center">

					<td><input type="checkbox" class="case" name="ids"
						value="<%=bean.getAccountNo()%>"></td>

					<td><%=index++%></td>

					<td><%=bean.getAccountNo()%></td>

					<td><%=bean.getHolderName()%></td>

					<td><%=bean.getBalance()%></td>

					<td><%=bean.getAccountType()%></td>

					<td><%=bean.getTransactionId()%></td>

					<td><a href="TBankingCtl?id=<%=bean.getAccountNo()%>">
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
						value="<%=TBankingListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TBankingListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=TBankingListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=TBankingListCtl.OP_NEXT%>"
						<%=nextListSize > 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

		</form>

	</div>

</body>

</html>