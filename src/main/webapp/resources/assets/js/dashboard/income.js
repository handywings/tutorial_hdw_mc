/* =================================================================
    Bar chart
================================================================= */

var ctxBar = document.getElementById("bar");

var data = {
    labels: [
        "เคเบิล",
        " INTERNET",
        "เคเบิล / INTERNET",
        "กล้องวงจรปิด",
        "โฆษณา (วิทยุ)",
        "โฆษณา (เคเบิล)"
    ],
    datasets: [{
        label: 'จำนวน',
        data: [20, 28, 16, 10, 35, 13],
        backgroundColor: [
            "#e97293",
            "#c277d0",
            "#8384e3",
            "#73beff",
            "#2ecbbe",
            "#56b786"
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
        "คนเก็บเงิน",
        "ชำระที่ออฟฟิศ",
        "ชำระผ่านธนาคาร",
        "ชำระผ่านบคัรเครดิต"
    ],
    datasets: [{
        data: [85, 67, 28, 56],
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
        "ลูกค้า DIGITAL",
        "ลูกค้า INTERNET",
        "ลูกค้าวงจรปิด"
    ],
    datasets: [{
        data: [250, 67, 28],
        backgroundColor: [
            "#e97293",
            "#c277d0",
            "#73beff"
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