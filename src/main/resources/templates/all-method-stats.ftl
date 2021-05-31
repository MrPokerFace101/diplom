<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All methods stats</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            let data = google.visualization.arrayToDataTable([
                ['timestamp', 'executionTime'],
                ['1619359201950', 15000],
                ['1619359216950', 1234],
                ['1619359218184', 42]
            ]);
            let options = {
                title: 'randomMethod()',
                legend: { position: 'bottom' }
            };

            let chart = new google.visualization.LineChart(document.getElementById('random_method_chart'));
            chart.draw(data, options);
        }
    </script>
</head>
<body>
<div id="random_method_chart" style="width: 900px; height: 500px"></div>
</body>
</html>