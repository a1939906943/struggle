var URL = "http://localhost:8081/";
var obj = {};
function callServer({ req, data, success, error }) {
  $.post({
    url: URL + req + "?req=" + JSON.stringify(data),
    success: function(data) {
      success(data);
    },
    error: function(data) {
      error(data);
    }
  });
}
