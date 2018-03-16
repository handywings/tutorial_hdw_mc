
/* =================================================================
Bar chart
================================================================= */
var ctx = document.getElementById("pie");
	var data = {
	    labels: ["เก็บได้", "เก็บไม่ได้", "หนี้ศูนย์"],
	    datasets: [{
	        label: 'จำนวน',
	        data: value,
	        backgroundColor: [
	            "#2ecbbe",
	            "#f49755",
	            "#9faec4"
	        ],
	        borderColor: [
	            'rgba(255,99,132,1)',
	            'rgba(54, 162, 235, 1)',
	            'rgba(75, 192, 192, 1)'
	        ],
	        borderWidth: 0,
	    }]
	};	

	var myChartBar = new Chart(ctxBar, {
	    type: 'bar',
	    data: data,
	    options: {
	        legend: {
	            display: false
	        },
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                },
	                gridLines: {
	                    color: "rgba(0, 0, 0, 0)",
	                },
	                fontSize: 40
	            }],
	            xAxes: [{
	                gridLines: {
	                    color: "rgba(0, 0, 0, 0)",
	                },
	                fontSize: 40
	            }]
	        }
	    }
	});


/* =================================================================
    Pie chart
================================================================= */
	var ctx = document.getElementById("pie");
	var data = {
	    labels: [
	        "รายเดือน",
	        "รายครึ่งปี",
	        "รายปี"
	    ],
	    datasets: [{
	        data: value,
	        backgroundColor: [
	            "#e97293",
	            "#c277d0",
	            "#8384e3"
	        ]
	    }]
	};

	var myChart = new Chart(ctx, {
	    type: 'doughnut',
	    data: data
	});

//ctx.onclick = function (evt) {
//    var activePoints = myChart.getElementsAtEvent(evt);
//    var chartData = activePoints[0]['_chart'].config.data;
//    var idx = activePoints[0]['_index'];
//
//    var value = chartData.datasets[0].data[idx];
//
//    var url = "http://example.com/" + value;
//    console.log(url);
////    alert(url);
//};
//
//ctxBar.onclick = function (evt) {
//    var activePoints = myChartBar.getElementsAtEvent(evt);
//    var chartData = activePoints[0]['_chart'].config.data;
//    var idx = activePoints[0]['_index'];
//
//    var value = chartData.datasets[0].data[idx];
//
//    var url = "http://example.com/" + value;
//    console.log(url);
//    alert(url);
//};