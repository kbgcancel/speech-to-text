<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Static Content -->
<link rel="stylesheet" href="/resources/css/styles.css" type="text/css"></link>

<title>[DEMO]</title>

</head>

<body>
	<form method="post" action="/transcribe" enctype="multipart/form-data">
		<table>
			<thead>
				<tr>
					<td><img alt="" src="/resources/img/speech-recognition.png"></td>
				</tr>
				<tr>
					<td style="text-align:center"><label class="title">Speech-to-Text Conversion</label><br>
						<label class="desc">This demo uses speech recognition
							capabilities to convert speech into text.</label></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><label class="info"> ${message} </label></td>
				</tr>
				<tr>
					<td><input type="file" name="file" /><input type="submit"
						value="Transcribe" /><label class="error">${error} </label></td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="container">
							<%-- Display dialog --%>
							<c:if test="${not empty results}">
								<c:forEach var="result" items="${results}">
									<div class="phrase">
										<br /> <label class="speaker">Speaker
											${result.speaker}:</label>&nbsp;&nbsp;&nbsp;<label class="phrase">${result.phrase}.</label><br />
										<br />
									</div>
								</c:forEach>
							</c:if>
						</div>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td><h5>This Application is intended for demonstration
							purposes only.</h5></td>
					<td><h5>Copyright &copy; 2018 Advanced World Solutions.
							All Rights Reserved.</h5></td>
				</tr>
			</tfoot>
		</table>
	</form>
	<br />

</body>
</html>