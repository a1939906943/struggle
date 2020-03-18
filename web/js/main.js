var URL = "http://localhost:8081/";
var obj = {};
function callServer({ req, data, success}) {
  $.post({
    url: URL + req + "?req=" + JSON.stringify(data),
    success: function(data) {
      success(data);
    }
  });
}
