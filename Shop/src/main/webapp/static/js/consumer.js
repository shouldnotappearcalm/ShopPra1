/**
 * Created by GZR on 2017/3/8.
 */
function checkUserName() {
    var username = $('#username').val().trim();
    if(username==null||username==''){
        $('#HintSpan').text('');
        return;
    }
    //1.创建异步交互对象
    var xhr = createXmlHttp();
    //2.设计监听，触发回调函数
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState + "," + xhr.status);
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                console.log(xhr.responseText);
                $('#HintSpan').html(xhr.responseText);
            }
        }
    }
    //3.打开链接
    xhr.open("GET", "/shop/consumer/"+username+"/findByName.do?time=" + new Date().getTime(), true);//true,异步
    //4.发送
    xhr.send(null);
}
function createXmlHttp() {
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}
function changeImage() {
    /* var img1 = document.getElementById("checkImg");
     console.log(new Date().getTime());
     img1.src="${pageContext.request.contextPath}/checkImg.action?"+new Date().getTime();*/
    $('#checkImg').attr("src", "/shop/common/ConRegImgCode/getCode.do?abc=" + new Date().getTime());
}
function changePhoneImage() {
    $('#checkPhoneImg').attr("src", "/shop/common/PhoneImgCode/getCode.do?abc=" + new Date().getTime());
}