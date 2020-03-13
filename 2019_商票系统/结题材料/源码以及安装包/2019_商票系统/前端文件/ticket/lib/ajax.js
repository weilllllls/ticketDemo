
//get请求
function getRequest(url){
    var res;
    $.ajax({
        type: "get",
        dataType: "json",
        async: false,
        'contentType': 'application/json',
        contentType:"application/json;charset=utf-8",
        url: url,
        success: function (result) {
            console.log(result);
            res = result;
        },
        error: function (result) {
            console.log(result);
            res = result;
        }
     });
     return res;
}

//post请求
function postRequest(url,json){
    var res;
    $.ajax({
        type: "post",
        dataType: "json",
        async: false,
        contentType:"application/json;charset=utf-8",
        'contentType': 'application/json',
        'data':JSON.stringify(json),
        url: url,
        success: function (result) {
            console.log(result);
            res = result;
        },
        error: function (result) {
            console.log("报错"+result);
            res = result;
        }
     });
     return res;
}