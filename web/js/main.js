// 后台项目地址
var URL = "http://localhost:8081/";

// 封装ajax
// req: controller请求地址
// data: 发送的数据 没有不传
// success：成功回调函数
function callServer({ req, data, success}) {
  $.post({
    url: URL + req + "?req=" + JSON.stringify(data),
    success: function(data) {
      success(data);
    }
  });
}
