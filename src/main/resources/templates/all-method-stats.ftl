<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All methods stats</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        let statDataArray = []

        <#list stats as method>
        let options = {
            title: ${method.name},
            legend: {position: 'bottom'}
        }
        let array = [
            ['timestamp', 'executionTime'],
            <#list method.stats as stat>
            [${stat.startTime}, ${stat.executionTime}],
            </#list>
        ]
        let dataObject = {
            options: options,
            dataArray: array
        }
        statDataArray.push(dataObject)
        </#list>

        function drawChart() {
            for (let i = 0; i < statDataArray.length; i++) {
                let data = google.visualization.arrayToDataTable(statDataArray[i].dataArray);
                let options = statDataArray[i].options;

                let chart = new google.visualization.LineChart(document.getElementById(statDataArray[i].options.title + '_chart'));
                chart.draw(data, options);
            }
        }
    </script>
</head>
<body>
<#if stats?? && stats?has_content>
    <#list stats as method>
        <div id="${method.name}_chart" style="width: 900px; height: 500px"></div>
    </#list>
<#else>
    Nothing found
</#if>
</body>
</html>