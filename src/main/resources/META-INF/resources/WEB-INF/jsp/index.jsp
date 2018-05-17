<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Speech-to-Text Demo App</title>
	</head>

	<body>
		Speech-to-Text Demo App
		<br/>
		<table>
			<tr>
				<td>
					<button>Record Audio</button>&nbsp;<button>Play Sample Audio</button>
				</td>
			</tr>		
			<tr>
				<td colspan="2">
					<textarea rows="35" cols="100" x-webkit-speech></textarea>
				</td>
			</tr>
		</table>
		<br/>
	</body>
</html>