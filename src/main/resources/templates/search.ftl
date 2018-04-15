<html>
<script type="text/javascript" src="../assets/js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="../assets/js/amazeui.min.js"></script>
<script type="text/javascript" src="../assets/js/app.js"></script>
<script type="text/javascript" src="../assets/js/blockUI.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
<head>
    <style>
        .imgstyle {
            margin: 15% auto 0 auto;
            height: 92px;
            width: 274px;
        }

        .inputstyle {
            height: 40px;
            width: 50%;
            margin-left: 25%;
            margin-right: 24%;
            margin-top: 2%;
            margin-bottom: 0;
            font-size: 20px;
            font-family: "隶书";
        }

        .buttonstyle {
            height: 35px;
            width: 8%;
            margin-top: 2%;
            margin-left: 46%;
            margin-right: 45%;
            margin-bottom: 0;
            border: none;
        }
    </style>
</head>
<body>
<div class="imgstyle">
    <img style="height: 100%;width: 100%;" src="../file/elgoog.png"/>
</div>
<input id="tags" name="tags" type ="text" class="inputstyle" onkeypress="keypress()" placeholder="  在elgoog上搜索" />
<button class="buttonstyle" name="buttonSearch" id="buttonSearch" type="submit" onclick="search();return false;">
    <p style="margin: auto auto auto auto">elgoog 搜索</p>
</button>
</body>
<script>
    function keypress() {
        if (event.keyCode == 13)
            search();
    }

    function search() {

        var content = $("#tags").val();

        $.ajax({
            type: "post",
            url: "/beginSearch",
            timeout: 800000,
            dataType: "json",
            data: {
                "content": content,
            },

            success: function (data) {
                window.location.href = "searchResult";
            },
            error: function () {
                alert("请求出错")
            }
        })
    }
</script>
</html>
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
                        // alert("成功")
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