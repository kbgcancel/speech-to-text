<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<!DOCTYPE html>
<html>
	<head>
		<title>Speech-to-Text Demo App</title>
	</head>

	<body>
		SPEECH-TO-TEXT DEMO
		<br/>
		<form action="home" method="post">
			<table>
				<tr>
					<td>
						<!-- <input type="file">&nbsp;<button>Upload</button>&nbsp;<button>Record</button> -->
						<button>Transcribe</button>
					</td>
				</tr>		
				<tr>
					<td colspan="2">
						<textarea rows="35" cols="100"></textarea>
					</td>
				</tr>
			</table>
		</form>
		<br/>
		
	</body>
</html>