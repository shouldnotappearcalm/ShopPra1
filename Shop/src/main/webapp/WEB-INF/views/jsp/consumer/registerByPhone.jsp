<%--
  Created by IntelliJ IDEA.
  User: GZR
  Date: 2017/3/6
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>会员注册</title>
    <link href="${pageContext.request.contextPath}/static/css/register.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/consumer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function(){

            $('#phoneNumber').bind('input propertychange',function(){
                $('#WritePhoneHint').text('');
            });

            $('#username').bind('input propertychange',function(){
                $('#HintSpan').text('');
            });
            var validate=$('#registerForm').validate({
                focusInvalid: false, //当为false时，验证无效时，没有焦点响应
                rules:{
                    username:{
                        required:true,
                        minlength:2,
                        maxlength:16
                    },
                    password:{
                        required:true,
                        minlength:6,
                        maxlength:18
                    },
                    rePassword:{
                        required:true,
                        equalTo:'#password'
                    },
                    name:{
                        required:true
                    },
                    phone:{
                        required:true,
                        isMobile:true
                    },
                    addr:{
                        required:true
                    },
                    checkcode:{
                        required:true
                    }
                },
                messages:{
                    username:{
                        required:"请填写用户名",
                        minlength:"最小长度不能少于2个字符",
                        maxlength:"最大长度不能超过16个字符"
                    },
                    password:{
                        required:"请填写密码",
                        minlength:"字符长度不能小于6",
                        maxlength:"字符长度不能大于18"
                    },
                    rePassword:{
                        required:"请再次输入密码",
                        equalTo:"两次密码不一致"
                    },

                    name:{
                        required:"请填写真实姓名"
                    },
                    phone:{
                        required:"请输入正确手机号码",
                        isMobile:"输入手机号格式有误"
                    },
                    addr:{
                        required:"请输入正确地址"
                    },
                    checkcode:{
                        required:"请输入图片验证码"
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="container register">
    <div class="span24">
        <div class="wrap">
            <div class="main clearfix">
                <div class="title">
                    <strong>会员注册</strong>USER REGISTER
                </div>
                <form id="registerForm" action="${pageContext.request.contextPath }/consumer/phone/register"
                      method="post" novalidate="novalidate">
                    <table>
                        <tbody>
                        <tr>
                            <th>
                                <span class="requiredField">*</span>用户名:
                            </th>
                            <td>
                                <input type="text" id="username" name="username" class="text" onblur="checkUserName()"
                                       maxlength="20" value="${consumer.username}">
                                <span  style="color:red">${errorMap.username}</span>
                                <span id="HintSpan"></span>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <span class="requiredField">*</span>密&nbsp;&nbsp;码:
                            </th>
                            <td>
                                <input type="password" id="password" name="password" class="text" maxlength="20"
                                       autocomplete="off" value="${consumer.password}">
                                <span  style="color:red">${errorMap.password}</span>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <span class="requiredField">*</span>确认密码:
                            </th>
                            <td>
                                <input type="password" name="rePassword" class="text" maxlength="20" autocomplete="off">
                            </td>
                        </tr>
                        <%--<tr>
                            <th>
                                <span class="requiredField">*</span>E-mail:
                            </th>
                            <td>
                                <input type="text" id="email" name="email" class="text" maxlength="200">
                            </td>
                        </tr>--%>
                        <tr>
                            <th>
                                姓名:
                            </th>
                            <td>
                                <input type="text" name="name" value="${consumer.name}" class="text" maxlength="200">
                                <span  style="color:red">${errorMap.name}</span>
                            </td>
                        </tr>

                        <tr>
                            <th>
                                电话:
                            </th>
                            <td>
                                <input type="text" id="phoneNumber" name="phone" value="${consumer.phone}" class="text"/>
                                <span id="WritePhoneHint" style="color:red">${errorMap.phone}</span>
                                <!-- 模态框（Modal） -->
                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" style="width:450px;">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                <h4 class="modal-title" id="myModalLabel">输入图中验证码</h4>
                                            </div>
                                            <div class="modal-body" style="padding: 0px;">
                                                <div class="input-group">
                                                    <input type="text" id="phoneCheckCode" class="form-control" placeholder="验证码" style="display:inline;width: 200px;height: 40px;margin-right: 0px;" />
                                                    <img id="checkPhoneImg" style="width:200px;height:40px;margin-bottom: auto;margin-top: auto;margin-left: 0px;" class="captchaImage"
                                                         src="${pageContext.request.contextPath}/common/PhoneImgCode/getCode.do"  onclick="changePhoneImage()" title="点击更换验证码">
                                                </div>
                                                <div class="input-group">
                                                    <span style="margin-left: 2px;" id="PhoneHintSpan"></span>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                <button type="button" class="btn btn-primary" onclick="sendPhoneMessage();">提交</button>
                                            </div>
                                        </div><!-- /.modal-content -->
                                    </div><!-- /.modal-dialog -->
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                验证码
                            </th>
                            <td>
                                <input type="text" style="margin-right: 0px;width:110px;" name="phoneCode" value="" class="text"/>
                                <input type="button" id="sendMessageBtn" style="margin-left: 0px;width:110px;" value="发送验证码" onclick="showModal()" class="text"/>
                                <span  style="color:red">${errorMap.phoneCode}</span>
                            </td>

                        </tr>
                        <tr>
                            <th>
                                地址:
                            </th>
                            <td>
                                <input type="text" name="addr" class="text" value="${consumer.addr}" maxlength="200">
                                <span style="color:red">${errorMap.addr}</span>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <span class="requiredField">*</span>验证码:
                            </th>
                            <td>
                                <span class="fieldSet">
                                    <input type="text" id="checkcode" name="checkcode" class="text captcha"
                                           maxlength="5" autocomplete="off"><img id="checkImg" class="captchaImage"
 src="${pageContext.request.contextPath}/common/ConRegImgCode/getCode.do"  onclick="changeImage()" title="点击更换验证码">
                                </span>
                                <span style="color:red">${errorMap.checkcode}</span>
                            </td>
                        </tr>
                        <tr>
                            <th>&nbsp;

                            </th>
                            <td>
                                <input type="submit" style="width: auto" class="submit" value="同意以下协议并注册">
                            </td>
                        </tr>
                        <tr>
                            <th>&nbsp;

                            </th>
                            <td>
                                注册协议
                            </td>
                        </tr>
                        <tr>
                            <th>&nbsp;

                            </th>
                            <td>
                                <div id="agreement" class="agreement" style="height: 200px;">
                                    <p>
                                        尊敬的用户欢迎您注册成为本网站会员。请用户仔细阅读以下全部内容。如用户不同意本服务条款任意内容，请不要注册或使用本网站服务。如用户通过本网站注册程序，即表示用户与本网站已达成协议，自愿接受本服务条款的所有内容。此后，用户不得以未阅读本服务条款内容作任何形式的抗辩。</p>
                                    <p>一、本站服务条款的确认和接纳<br>本网站涉及的各项服务的所有权和运作权归本网站所有。本网站所提供的服务必须按照其发布的服务条款和操作规则严格执行。本服务条款的效力范围及于本网站的一切产品和服务，用户在享受本网站的任何服务时，应当受本服务条款的约束。
                                    </p>
                                    <p>二、服务简介<br>本网站运用自己的操作系统通过国际互联网络为用户提供各项服务。用户必须: 1. 提供设备，如个人电脑、手机或其他上网设备。 2.
                                        个人上网和支付与此服务有关的费用。</p>
                                    <p>三、用户在不得在本网站上发布下列违法信息<br>1. 反对宪法所确定的基本原则的； 2. 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的； 3.
                                        损害国家荣誉和利益的； 4. 煽动民族仇恨、民族歧视，破坏民族团结的； 5. 破坏国家宗教政策，宣扬邪教和封建迷信的； 6.
                                        散布谣言，扰乱社会秩序，破坏社会稳定的； 7. 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的； 8. 侮辱或者诽谤他人，侵害他人合法权益的； 9.
                                        含有法律、行政法规禁止的其他内容的。</p>
                                    <p>四、有关个人资料<br>用户同意: 1. 提供及时、详尽及准确的个人资料。 2. 同意接收来自本网站的信息。 3.
                                        不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。 4. 本网站不公开用户的姓名、地址、电子邮箱和笔名。除以下情况外: a)
                                        用户授权本站透露这些信息。 b) 相应的法律及程序要求本站提供用户的个人资料。</p>
                                    <p>五、服务条款的修改<br>本网站有权在必要时修改服务条款，一旦条款及服务内容产生变动，本网站将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的本网站信息服务。如果用户继续享用本网站信息服务，则视为接受服务条款的变动。
                                    </p>
                                    <p>六、用户隐私制度<br>尊重用户个人隐私是本网站的一项基本政策。所以，本网站一定不会在未经合法用户授权时公开、编辑或透露其注册资料及保存在本网站中的非公开内容，除非有法律许可要求或本网站在诚信的基础上认为透露这些信息在以下四种情况是必要的:
                                        1. 遵守有关法律规定，遵从本网站合法服务程序。 2. 保持维护本网站的商标所有权。 3. 在紧急情况下竭力维护用户个人和社会大众的隐私安全。 4.
                                        符合其他相关的要求。</p>
                                    <p>七、用户的帐号、密码和安全性<br>用户一旦注册成功，将获得一个密码和用户名。用户需谨慎合理的保存、使用用户名和密码。如果你不保管好自己的帐号和密码安全，将负全部责任。另外，每个用户都要对其帐户中的所有活动和事件负全责。你可随时根据指示改变你的密码。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告本网站。
                                        八、 拒绝提供担保
                                        用户明确同意信息服务的使用由用户个人承担风险。本网站不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保，但会在能力范围内，避免出错。</p>
                                    <p>九、有限责任<br>如因不可抗力或其它本站无法控制的原因使本站销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等本站会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失，同时会尽量避免这种损害的发生。
                                    </p>
                                    <p>十、用户信息的储存和限制<br>本站有判定用户的行为是否符合国家法律法规规定及本站服务条款权利，如果用户违背本网站服务条款的规定，本网站有权中断对其提供服务的权利。
                                    </p>
                                    <p>十一、用户管理<br>用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于本站的国家法律、地方法律和国际法律标准的。用户必须遵循: 1.
                                        使用网络服务不作非法用途。 2. 不干扰或混乱网络服务。 3. 遵守所有使用网络服务的网络协议、规定、程序和惯例。
                                        用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输何教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。未经许可而非法进入其它电脑系统是禁止的。
                                        若用户的行为不符合以上提到的服务条款，本站将作出独立判断立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任。用户若在本站上散布和传播反动、色情或其它违反国家法律的信息，本站的系统记录有可能作为用户违反法律的证据。
                                    </p>
                                    <p>十二、通告<br>所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。服务条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。</p>
                                    <p>十三、信息内容的所有权<br>本网站定义的信息内容包括:
                                        文字、软件、声音、相片、录象、图表；在广告中全部内容；本网站为用户提供的其它信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在本网站和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。本站所有的文章版权归原文作者和本站共同所有，任何人需要转载本站的文章，必须征得原文作者或本站授权。
                                    </p>
                                    <p>十四、法律<br>本协议的订立、执行和解释及争议的解决均应适用中华人民共和国的法律。用户和本网站一致同意服从本网站所在地有管辖权的法院管辖。如发生本网站服务条款与中华人民共和国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它条款则依旧保持对用户的约束力。
                                    </p>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="login">
                        <div class="ad">
                            <dl>
                                <dt>
                                    注册即享受
                                </dt>
                                <dd>
                                    正品保障、正规发票
                                </dd>
                                <dd>
                                    货到付款、会员服务
                                </dd>
                                <dd>
                                    自由退换、售后上门
                                </dd>
                            </dl>
                        </div>
                        <dl>
                            <dt>已经拥有账号了？</dt>
                            <dd>
                                立即登录即可体验在线购物！
                                <a href="./会员登录.htm">立即登录</a>
                            </dd>
                        </dl>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function  sendPhoneMessage() {
        $('#PhoneHintSpan').html("");
        //先判断验证码是否已经填写
        var phoneCheckCode=$('#phoneCheckCode').val().trim();
        if(phoneCheckCode==null||phoneCheckCode.length==0||phoneCheckCode==''){
            $('#PhoneHintSpan').html("<font color='red'>验证码为空</font>");
            return;
        }

        var phoneNumber=$('#phoneNumber').val().trim();
        console.log('phone:'+phoneNumber);
        //1.创建异步交互对象
        var xhr = createXmlHttp();
        //2.设计监听，触发回调函数
        xhr.onreadystatechange = function () {
            console.log(xhr.readyState + "," + xhr.status);
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    console.log(xhr.responseText);
                    if(xhr.responseText=='success'){
                        //发送短信成功的处理,关闭弹出框

                        //调用验证码读秒事件
                        setTime($('#sendMessageBtn'));
                        $('#myModal').modal('hide');
                    }else{
                        $('#PhoneHintSpan').html("<font color='red'>"+xhr.responseText+"</font>");
                    }
                }
            }
        }
        //3.打开链接,发送短信
        xhr.open("GET", "/shop/consumer/sendPhoneMessage.do?phoneCheckCode="+phoneCheckCode+"&phoneNumber="+phoneNumber+"&time=" + new Date().getTime(), true);//true,异步
        //4.发送
        xhr.send(null);
    }
    function showModal(){
        var mobile=$('#phoneNumber').val().trim();
        if(!checkPhone(mobile)){
            //手机号错误
            $('#WritePhoneHint').html("<font color='red'>手机号填写写有误</font>")
            return;
        }
        $('#myModal').modal('show');
    }
    
    var countdown=120;
    function setTime(obj) {
        if (countdown == 0) {
            $(obj).removeAttr("disabled");
            $(obj).val("发送验证码");
            countdown = 120;
            return;
        } else {
            $(obj).attr("disabled", true);
            $(obj).val("重新发送(" + countdown + ")");
            countdown--;
        }
        setTimeout(function() {
                    setTime(obj) }
                ,1000)
    }
    function checkPhone(phone){
        //var phone = document.getElementById('phone').value;
        if(!(/^1[34578]\d{9}$/.test(phone))){
            //alert("手机号码有误，请重填");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
