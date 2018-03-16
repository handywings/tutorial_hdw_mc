<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>หาสถานที่และค่าพิกัด latitude, longitude ใน Google Maps</title>



<link rel="stylesheet" type="text/css" href="resources/assets/css/jquery-gmaps-latlon-picker.css"/>

</head>


<body>

	<fieldset class="gllpLatlonPicker">
		<input type="text" class="gllpSearchField">
		<input type="button" class="gllpSearchButton" value="search">
		<br/><br/>
		<div class="gllpMap">Google Maps</div>
		<br/>
		lat/lon:
			<input type="text" class="gllpLatitude" value="20"/>
			/
			<input type="text" class="gllpLongitude" value="20"/>
		zoom: <input type="text" class="gllpZoom" value="3"/>
		<input type="button" class="gllpUpdateButton" value="update map">
		<br/>
	</fieldset>

	<div class="code">

<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=AIzaSyC_XL3Tcv19fOT8XX0Po-CdkkHwJx5MQMY" type="text/javascript"></script>
<!-- <script src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;key=AIzaSyC_XL3Tcv19fOT8XX0Po-CdkkHwJx5MQMY"></script> -->
<script type="text/javascript" src="resources/assets/plugins/jquery/jquery-1.12.3.min.js"></script>
<script src="resources/assets/js/jquery-gmaps-latlon-picker.js"></script>

</body>

</html>
