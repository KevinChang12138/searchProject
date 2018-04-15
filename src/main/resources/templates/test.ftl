<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>jQuery UI 自动完成（Autocomplete） - 默认功能</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.9.1.js"></script>
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
    <script>
        $(function () {
            $("#tags").autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "/doSearch",
                        dataType: "json",
                        data: {
                            name: request.term
                        },
                        success: function (data) {
                            var dataObj = data.obj; // 表示处理后的JSON数据
                            response(dataObj);
                        },
                        error: function () {
                            alert("失败")
                        }
                    })
                },
                minLength: 2
            });
        })
    </script>
</head>
<body>

<div class="ui-widget">
    <label for="tags">标签：</label>
    <input id="tags">
</div>


</body>
</html>