<%@page import="in.com.rays.proj4.controller.TBankingCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>TBanking View</title>
</head>

<body>

	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.TBANKING_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.com.rays.proj4.bean.TBankingBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="color: navy;">

				<%
				if (bean.getAccountNo() > 0) {
				%>

				Update Banking

				<%
				} else {
				%>

				Add Banking

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

			<input type="hidden" name="id" value="<%=bean.getAccountNo()%>">

			<table>

				<tr>
					<th align="left">Holder Name<span style="color: red">*</span>
					</th>

					<td><input type="text" name="holderName"
						placeholder="Enter Holder Name"
						value="<%=DataUtility.getStringData(bean.getHolderName())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("holderName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Balance<span style="color: red">*</span>
					</th>

					<td><input type="text" name="balance"
						placeholder="Enter Balance" value="<%=bean.getBalance()%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("balance", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Account Type<span style="color: red">*</span>
					</th>

					<td><input type="text" name="accountType"
						placeholder="Enter Account Type"
						value="<%=DataUtility.getStringData(bean.getAccountType())%>">
					</td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("accountType", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Transaction Id<span style="color: red">*</span>
					</th>

					<td><input type="text" name="transactionId"
						placeholder="Enter Transaction Id"
						value="<%=bean.getTransactionId()%>"></td>

					<td><font color="red"> <%=ServletUtility.getErrorMessage("transactionId", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>

					<td>
						<%
						if (bean.getAccountNo() > 0) {
						%> <input type="submit" name="operation"
						value="<%=TBankingCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TBankingCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=TBankingCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TBankingCtl.OP_RESET%>"> <%
 }
 %>

					</td>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>