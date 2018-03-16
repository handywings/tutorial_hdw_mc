<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>หาสถานที่และค่าพิกัด latitude, longitude ใน Google Maps</title>

<script type="text/javascript">
	function showResult(str) {
		if (str.length == 0) {
			document.getElementById("livesearch").innerHTML = "";
			document.getElementById("livesearch").style.border = "0px";
			return;
		}
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("livesearch").innerHTML = xmlhttp.responseText;
				document.getElementById("livesearch").style.border = "1px solid #A5ACB2";
			}
		}
		xmlhttp.open("GET", "102PlaceSuphanburiGeoRSS.php?q=" + str, true);
		xmlhttp.send();

	}
</script>

<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=AIzaSyC_XL3Tcv19fOT8XX0Po-CdkkHwJx5MQMY" type="text/javascript"></script>

<script type="text/javascript">
	function load() {
		if (GBrowserIsCompatible()) {
			var map = new GMap2(document.getElementById("map"));
			// map.addControl(new GSmallMapControl());
			// map.addControl(new GMapTypeControl());
			map.setUIToDefault();
			var center = new GLatLng(14.46523, 100.13137);
			map.setCenter(center, 15);
			geocoder = new GClientGeocoder();
			var marker = new GMarker(center, {
				draggable : true
			});
			map.addOverlay(marker);
			document.getElementById("lat").innerHTML = center.lat().toFixed(5);
			document.getElementById("lng").innerHTML = center.lng().toFixed(5);

			GEvent.addListener(marker, "dragend", function() {
				var point = marker.getPoint();
				map.panTo(point);
				document.getElementById("lat").innerHTML = point.lat().toFixed(
						5);
				document.getElementById("lng").innerHTML = point.lng().toFixed(
						5);

			});

			GEvent.addListener(map, "moveend", function() {
				map.clearOverlays();
				var center = map.getCenter();
				var marker = new GMarker(center, {
					draggable : true
				});
				map.addOverlay(marker);
				document.getElementById("lat").innerHTML = center.lat()
						.toFixed(5);
				document.getElementById("lng").innerHTML = center.lng()
						.toFixed(5);

				GEvent.addListener(marker, "dragend", function() {
					var point = marker.getPoint();
					map.panTo(point);
					document.getElementById("lat").innerHTML = point.lat()
							.toFixed(5);
					document.getElementById("lng").innerHTML = point.lng()
							.toFixed(5);

				});

			});

		}
	}

	function showAddress(address) {
		var map = new GMap2(document.getElementById("map"));
		map.setUIToDefault();
		// map.addControl(new GSmallMapControl());
		// map.addControl(new GMapTypeControl());
		if (geocoder) {
			geocoder.getLatLng(address, function(point) {
				if (!point) {
					alert(address + " not found");
				} else {
					document.getElementById("lat").innerHTML = point.lat()
							.toFixed(5);
					document.getElementById("lng").innerHTML = point.lng()
							.toFixed(5);
					map.clearOverlays()
					map.setCenter(point, 15);
					var marker = new GMarker(point, {
						draggable : true
					});
					map.addOverlay(marker);

					GEvent.addListener(marker, "dragend", function() {
						var pt = marker.getPoint();
						map.panTo(pt);
						document.getElementById("lat").innerHTML = pt.lat()
								.toFixed(5);
						document.getElementById("lng").innerHTML = pt.lng()
								.toFixed(5);
					});

					GEvent.addListener(map, "moveend", function() {
						map.clearOverlays();
						var center = map.getCenter();
						var marker = new GMarker(center, {
							draggable : true
						});
						map.addOverlay(marker);
						document.getElementById("lat").innerHTML = center.lat()
								.toFixed(5);
						document.getElementById("lng").innerHTML = center.lng()
								.toFixed(5);

						GEvent.addListener(marker, "dragend", function() {
							var pt = marker.getPoint();
							map.panTo(pt);
							document.getElementById("lat").innerHTML = pt.lat()
									.toFixed(5);
							document.getElementById("lng").innerHTML = pt.lng()
									.toFixed(5);
						});

					});

				}
			});
		}
	}
</script>
</head>


<body onload="load()" onunload="GUnload()">

	<table border width="100% height="100%">
		<tr height="100%">
			<td align="center" valign="top"><div
					style="overflow: auto; height: 550px; width: 200px;">
					<h5>พิมพ์ชื่อสถานที่ที่ต้องการค้นหา พิมพ์ - เพื่อแสดงทั้งหมด</h5>

					<form>
						<input type="text" size="20" onkeyup="showResult(this.value)" />
						<div id="livesearch"></div>
					</form>
					<h5>ทดสอบระบบ AJAX Live Search ข้อมูล GeoRSS</h5>
					<h5>ใน server โยธาธิการและผังเมืองจังหวัดสุพรรณบุรี</h5>
					<h5>ซ้อนบน Google Map และ Google Earth</h5>
				</div></td>
			<td align="center" valign="baseline"><h5>พิมพ์ชื่อสถานที่หรือค่าพิกัด
					latitude,longitudeในช่องล่าง แล้วกดปุ่ม "คลิก!" หรือ

					ลากบอลลูนไปที่สถานที่ที่ต้องการหาค่าพิกัด</h5>
				<form action="#"
					onsubmit="showAddress(this.address.value); return false">

					<input type="text" size="37" name="address" value="สุพรรณบุรี" />
					<input type="submit" value="คลิก! ค้นหาใน server Google Map" />

				</form> <h10> <align="left">

				<table bgcolor="#FFFFCC" width="550">
					<td>เส้นรุ้ง ละติจูด Latitude =</td>
					<td id="lat"></td>
					<td>, เส้นแวง ลองจิจูด Longitude =</td>
					<td id="lng"></td>

				</table></h10>
				<div id="map" style="height: 470px">
					<br />
				</div></td>
		</tr>
	</table>



</body>

</html>
