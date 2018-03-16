/* =================================================================
    Bar chart
================================================================= */

var ctxBar = document.getElementById("bar");

var data = {
    labels: [
        "รายเดือน",
        "รายปี",
        "รายครึ่งปี",
        "โครงการ",
        "โฆษณา (วิทยุ)",
        "โฆษณา (เคเบิล)",
        "กล้องวงจรปิด",
        "INTERNET"

    ],
    datasets: [{
        label: 'จำนวน',
        data: [20, 28, 16, 11, 35, 13, 18, 28],
        backgroundColor: [
            "#e97293",
            "#c277d0",
            "#8384e3",
            "#73beff",
            "#2ecbbe",
            "#56b786",
            "#f49755",
            "#e97293"
        ],
        borderColor: [
            'rgba(255,99,132,1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)'
        ],
        borderWidth: 0,
    }]
};

var myChartBar = new Chart(ctxBar, {
    type: 'horizontalBar',
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
                }
            }],
            xAxes: [{
                gridLines: {
                    color: "rgba(0, 0, 0, 0)",
                }
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
        "สมาชิกใหม่",
        "สมาชิกตัดสาย (ค้างชำระ)",
        "สมาชิกตัดสาย (ไม่ค้างชำระ)",
        "สมาชิกเก่ากลับมาใชใหม่"
    ],
    datasets: [{
        data: [150, 67, 28, 105],
        backgroundColor: [
            "#e97293",
            "#c277d0",
            "#8384e3",
            "#73beff"
        ]
    }]
};

var myChart = new Chart(ctx, {
    type: 'pie',
    data: data
});


/* =================================================================
    Pie chart
================================================================= */

var ctxTest = document.getElementById("pitest");

var dataTest = {
    labels: [
        "รายปี",
        "โฆษณา (วิทยุ)",
        "กล้องวงจรปิด"
    ],
    datasets: [{
        data: [250, 250, 70],
        backgroundColor: [
            "#c277d0",
            "#2ecbbe",
            "#f49755"
        ]
    }]
};

var myChartTest = new Chart(ctxTest, {
    type: 'doughnut',
    data: dataTest
});

ctx.onclick = function (evt) {
    var activePoints = myChart.getElementsAtEvent(evt);
    var chartData = activePoints[0]['_chart'].config.data;
    var idx = activePoints[0]['_index'];

    var value = chartData.datasets[0].data[idx];

    var url = "http://example.com/" + value;
    console.log(url);
    alert(url);
};
ctxTest.onclick = function (evt) {
    var activePoints = myChartTest.getElementsAtEvent(evt);
    var chartData = activePoints[0]['_chart'].config.data;
    var idx = activePoints[0]['_index'];

    var value = chartData.datasets[0].data[idx];

    var url = "http://example.com/" + value;
    console.log(url);
    alert(url);
};
ctxBar.onclick = function (evt) {
    var activePoints = myChartBar.getElementsAtEvent(evt);
    var chartData = activePoints[0]['_chart'].config.data;
    var idx = activePoints[0]['_index'];

    var value = chartData.datasets[0].data[idx];

    var url = "http://example.com/" + value;
    console.log(url);
    alert(url);
};