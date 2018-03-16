

/* =================================================================
    Pie chart
================================================================= */




/* =================================================================
    Pie chart
================================================================= */

var ctxTest = document.getElementById("pitest");

var dataTest = {
    labels: [
        "ลูกค้า DIGITAL",
        "ซ่อมสัญญาณ",
        "เสริมจุด"
    ],
    datasets: [{
        data: [250, 67, 28],
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