<%--
  Created by IntelliJ IDEA.
  User: GZR
  Date: 2017/6/22
  Time: 7:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0080)http://localhost:8080/mango/login.jhtml?redirectUrl=%2Fmango%2Fcart%2Flist.jhtml -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>会员登录</title>
    <link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container login">
    <div class="span12">
        <div class="ad">
            <img src="${pageContext.request.contextPath}/static/image/login.jpg" width="500" height="330" alt="会员登录"
                 title="会员登录">
        </div>
    </div>
    <div class="span12 last">
        <div class="wrap">
            <div class="main">
                <div class="title">
                    <strong>会员登录</strong>USER LOGIN
                </div>
                <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post" novalidate="novalidate">
                    <table>
                        <tbody>
                        <tr>
                            <th>
                                用户名/E-mail:
                            </th>
                            <td>
                                <input type="text" id="username" name="username" class="text" maxlength="20">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                密&nbsp;&nbsp;码:
                            </th>
                            <td>
                                <input type="password" id="password" name="password" class="text" maxlength="20"
                                       autocomplete="off">
                            </td>
                        </tr>
                        <tr>
                            <th>
                                验证码:
                            </th>
                            <td>
										<span class="fieldSet">
											<input type="text" id="captcha" name="captcha" class="text captcha"
                                                   maxlength="4" autocomplete="off"><img id="captchaImage"
                                                                                         class="captchaImage"
                                                                                         src="./image/captcha.jhtml"
                                                                                         title="点击更换验证码">
										</span>
                            </td>
                        </tr>
                        <tr>
                            <th>&nbsp;

                            </th>
                            <td>
                                <label>
                                    <input type="checkbox" id="isRememberUsername" name="isRememberUsername"
                                           value="true">记住用户名
                                </label>
                                <label>
                                    &nbsp;&nbsp;<a>找回密码</a>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <th>&nbsp;

                            </th>
                            <td>
                                <input type="submit" class="submit" value="登 录">
                            </td>
                        </tr>
                        <tr class="register">
                            <th>&nbsp;

                            </th>
                            <td>
                                <dl>
                                    <dt>还没有注册账号？</dt>
                                    <dd>
                                        立即注册即可体验在线购物！
                                        <a href="./会员注册.htm">立即注册</a>
                                    </dd>
                                </dl>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
</html>
