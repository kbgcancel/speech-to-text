<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

	<head>
		<%--<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"></link> --%>	
		<title>Speech-to-Text Demo App</title>
		
		<style>
			div.container {
				border-style: solid;
				border-width: medium;
				height: 500px;
    			width: 600px;
			}
			
			div.phrase {
				border-style: outset;
			}
			
			body {
			    font-family: Helvetica, sans-serif;
			}
					
			label.info {
				font-style: italic;		
				font-weight: lighter;	
				color: blue;
			}
			
			label.speaker {
				text-transform: uppercase; 
				font-weight: bold;
			}
			
			label.phrase {
				text-transform: capitalize;
				word-spacing: 5px;
				text-align: justify;
			}
		</style>
		
	</head>

	<body>
		<h1>SPEECH-TO-TEXT DEMO</h1>
		<form action="home" method="post">
			<table>
				<tr>
					<td>
						<button>Transcribe</button><label class="info"> ${message} </label>
					</td>
				</tr>		
				<tr>
					<td colspan="2">
						<div class="container">							
							<%-- Display dialog --%>
							<c:if test="${not empty results}">
								<c:forEach var="result" items="${results}">
									<div class="phrase">
										<br/>
										<label class="speaker">Speaker ${result.speaker}:</label>&nbsp;&nbsp;&nbsp;<label class="phrase">${result.phrase}.</label><br/>
										<br/>
									</div>
								</c:forEach>
							</c:if>
						</div>	
					</td>
				</tr>
			</table>
		</form>
		<br/>
		
	</body>
</html>