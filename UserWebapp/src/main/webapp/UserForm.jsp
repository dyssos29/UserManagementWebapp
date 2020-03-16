<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>User Web Application</title>
		<link href="static/css/DatePicker.css" rel="stylesheet" />
		<script src="static/js/DatePicker.js"></script>
	</head>
	<body>
		<h1>User Web Application</h1>
		<a href="index.jsp"><h4>Home</h4></a>
		<a href="view"><h4>Display Users</h4></a>
		<form action="add" method="post">
		<p>Please fill in this form to be registered as a user.</p>
			<fieldset>
				<legend><b>User Information</b></legend>
					<label for="fname">First Name</label>
					<input type="text" placeholder="Enter your first name" name="fname" required>
					<br>
					<label for="lname">Last Name</label>
					<input type="text" placeholder="Enter your last name" name="lname" required>
					<br>
					<label for="cars">Provide your gender:</label>
					<select id="gender" name="gender">
					  <option value="male">M</option>
					  <option value="female">F</option>
					</select>
					<br>
					<label for="birthday">Birthday:</label>
					<input type="text" id="birthday" name="birthday" required>
					<br>
					<label for="homeAddress">Home Address</label>
					<input type="text" placeholder="Enter your home addresss" name="homeAddress">
					<br>
					<label for="workAddress">Work Address</label>
					<input type="text" placeholder="Enter your work addresss" name="workAddress">
					<br>
					<input type="reset">
					<br>
					<br>
					<input type="submit" value="Register">
			</fieldset>
		</form>
		<script type="text/javascript">
			const input = document.getElementById('birthday');
			const datepicker = new TheDatepicker.Datepicker(input);
			datepicker.render();

			datepicker.options.setMaxDate(new Date());
		</script>
	</body>
</html>