$.ajax({
    url: "getAllTopic",
    success: function (result) {
        console.log(result);
        $("#listArea").empty();
        var html = "";
        result.forEach(function (item) {
            html += "<tr onclick='tableClick(" + item.id + ")' id='" + item.id + "'> <td>" + item.topicName + "</td><td>"
                + item.monitoringName + "</td><td>" + item.ip + "</td><td>" + item.port + "</td><td>" + item.status + "</td></tr>";
        })
        $("#listArea").append(html);
    }
})

var num = "";
function tableClick(no){
    console.log(no);
    num = no;
}
$(document).ready(function () {
    $("#new").click(function () {
        $("#popup_layer_new").css("display", "block");
        $("form").submit(function(e) {
            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);

            $.ajax({
                type: "POST",
                url: "saveTopic",
                data: form.serialize(), // serializes the form's elements.
                dataType: 'json',
                success: function(json)
                {
                    console.log("저장 성공");
                    $("#popup_layer_new").css("display", "none");
                    $("#page").load("TopicSetting");
                }
            });
        });
    });
    $("#delete").click(function(){
        $.ajax({
            url: "delete/"+num,
            type: "GET",
            success: function (result) {
                alert("삭제되었습니다.");
                $("#page").load("TopicSetting");
            }
        })
    });
    $("#edit").click(function(){
        $("#popup_layer_edit").css("display", "block");
        $.ajax({
            url: "edit/"+num,
            type : "GET",
            success: function (result) {
                console.log(result);
                $("#editTopic").val(result.topicName);
                $("#editMonitoring").val(result.monitoringName);
                $("#editIP").val(result.ip);
                $("#editPort").val(result.port);

            }
        })
        $("form").submit(function(e) {
            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            var url = form.attr('action');

            $.ajax({
                type: "POST",
                url: "edit/"+num,
                data: form.serialize(), // serializes the form's elements.
                dataType: 'json',
                success: function(json)
                {
                    console.log("수정 성공");
                    $("#popup_layer_edit").css("display", "none");
                    $("#page").load("TopicSetting");
                }
            });
        });
    });
});