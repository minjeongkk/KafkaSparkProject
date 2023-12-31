<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <title>카프카 토픽 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/Main.css">
    <link rel="stylesheet" href="/css/TopicSetting.css">
    <script type="text/javascript" src="/javascript/Main.js"></script>
    <script type="text/javascript" src="/javascript/TopicSetting.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="page_container">
    <div class="row" id="buttons">
        <div class="col-auto">
            <button class="button" id="new" type="button">New</button>
            <button class="button" id="edit" type="button">Edit</button>
            <button class="button" id="delete" type="button">Delete</button>
        </div>
    </div>
    <table class="table" id="TopicListTable">
        <thead class="thead-light">
        <tr class="text-center">
            <th scope="col">Topic Name</th>
            <th scope="col">Monitoring Name</th>
            <th scope="col">IP</th>
            <th scope="col">Port</th>
            <th scope="col">status</th>
        </tr>
        </thead>
        <tbody id="listArea">
        </tbody>
    </table>
</div>

<!-- 생성 팝업창 -->
<div class="popup_layer" id="popup_layer_new" style="display: none;">
    <div class="popup_box">
        <div class="popup_cont">
            <div class="title">New Topic</div>
            <form class="popupForm" id="postForm" action="/saveTopic" method="post">
                <div class="form-group row">
                    <label for="inputTopic" class="col-sm-2 col-form-label"><strong>Topic Name</strong></label>
                    <input type="text" name="topicName" class="inputText" id="inputTopic"/>
                </div>
                <div class="form-group row">
                    <label for="inputMonitoring" class="col-sm-2 col-form-label"><strong>Monitoring
                        Name</strong></label>
                    <input type="text" name="monitoringName" class="inputText" id="inputMonitoring"/>
                </div>
                <div class="form-group row">
                    <label for="inputIP" class="col-sm-2 col-form-label"><strong>IP Address</strong></label>
                    <input type="text" name="ip" class="inputText" id="inputIP"
                           pattern="^([0-9]{1,3}\.){3}[0-9]{1,3}$">
                </div>
                <div class="form-group row">
                    <label for="inputPort" class="col-sm-2 col-form-label"><strong>Port</strong></label>
                    <input type="text" name="port" class="inputText" id="inputPort" pattern="[0-9]+">
                </div>
                <div class="row">
                    <div class="col-auto">
                        <button class="button" id="saveBtn" type="button" role="button" value="Save">Save
                        </button>
                        <button class="button" id="saveCancelBtn" type="button" role="button" value="Cancel">Cancel
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 편집 팝업창 -->
<div class="popup_layer" id="popup_layer_edit" style="display: none;">
    <div class="popup_box">
        <div class="popup_cont">
            <div class="title">Edit Topic</div>
            <form class="popupForm" id="editForm" action="edit/" method="post">
                <div class="form-group row">
                    <label for="inputTopic" class="col-sm-2 col-form-label"><strong>Topic Name</strong></label>
                    <input type="text" name="topicName" class="inputText" id="editTopic"/>
                </div>
                <div class="form-group row">
                    <label for="inputMonitoring" class="col-sm-2 col-form-label"><strong>Monitoring
                        Name</strong></label>
                    <input type="text" name="monitoringName" class="inputText" id="editMonitoring"/>
                </div>
                <div class="form-group row">
                    <label for="inputIP" class="col-sm-2 col-form-label"><strong>IP Address</strong></label>
                    <input type="text" name="ip" class="inputText" id="editIP"
                           pattern="^([0-9]{1,3}\.){3}[0-9]{1,3}$">
                </div>
                <div class="form-group row">
                    <label for="inputPort" class="col-sm-2 col-form-label"><strong>Port</strong></label>
                    <input type="text" name="port" class="inputText" id="editPort" pattern="[0-9]+">
                </div>
                <div class="row">
                    <div class="col-auto">
                        <button class="button" id="editBtn" type="button" role="button" value="Save">Save
                        </button>
                        <button class="button" id="editCancelBtn" type="button" role="button" value="Cancel">Cancel
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>

