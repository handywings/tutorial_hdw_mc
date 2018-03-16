

/* =================================================================
    Pie chart
================================================================= */

var ctx = document.getElementById("pie");

var data = {
    labels: [
        "สินค้าทั้งหมด",
        "สินค้าใกล้หมด",
        "สินค้าหมด",
        "สินค้าหมดประกัน"
    ],
    datasets: [{
        data: [50, 167, 218, 95],
        backgroundColor: [
            "#73beff",
            "#f49755",
            "#e97293",
            "#9faec4"
        ]
    }]
};

var myChart = new Chart(ctx, {
    type: 'doughnut',
    data: data
});


/* =================================================================
    Pie chart
================================================================= */

var ctxTest = document.getElementById("pitest");

var dataTest = {
    labels: [
        "สินค้าทั้งหมด",
        "สินค้าใกล้หมด",
        "สินค้าหมด",
        "สินค้าหมดประกัน"
    ],
    datasets: [{
        data: [200, 307, 128, 95],
        backgroundColor: [
            "#73beff",
            "#f49755",
            "#e97293",
            "#9faec4"
        ]
    }]
};

var myChartTest = new Chart(ctxTest, {
    type: 'doughnut',
    data: dataTest
});

/* =================================================================
    Pie chart
================================================================= */

var ctxTestTwo = document.getElementById("pitesttwo");

var dataTest = {
    labels: [
        "สินค้าทั้งหมด",
        "สินค้าใกล้หมด",
        "สินค้าหมด",
        "สินค้าหมดประกัน"
    ],
    datasets: [{
        data: [250, 67, 28, 95],
        backgroundColor: [
            "#73beff",
            "#f49755",
            "#e97293",
            "#9faec4"
        ]
    }]
};

var myChartTestTwo = new Chart(ctxTestTwo, {
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
ctxTestTwo.onclick = function (evt) {
    var activePoints = myChartTestTwo.getElementsAtEvent(evt);
    var chartData = activePoints[0]['_chart'].config.data;
    var idx = activePoints[0]['_index'];

    var value = chartData.datasets[0].data[idx];

    var url = "http://example.com/" + value;
    console.log(url);
    alert(url);
};
