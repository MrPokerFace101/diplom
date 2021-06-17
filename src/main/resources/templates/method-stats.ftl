<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Method stats</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawChart);
        let options = {
            title: ${method.name},
            legend: {position: 'bottom'}
        };

        let dataArray = [
            ['timestamp', 'executionTime'],
            <#list method.stats as stat>
                [${stat.startTime}, ${stat.executionTime}]
            </#list>
        ];

        function drawChart() {
            let chart = new google.visualization.LineChart(document.getElementById('method_chart'));
            chart.draw(dataArray, options);
        }
    </script>
</head>
<body>
<#if stats?? && stats?has_content>
        <div id="method_chart" style="width: 900px; height: 500px"></div>
<#else>
    Nothing found
</#if>
</body>
</html>