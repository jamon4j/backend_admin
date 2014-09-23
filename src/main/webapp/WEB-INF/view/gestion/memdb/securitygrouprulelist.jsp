<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../inc/jsCssIncludeHeader.jspf" %>
<head>
    <%@include file="../inc/meta.jspf" %>
    <title>memdb - 高可用列表</title>
    <style type="text/css">
        label, input {
        }

        input.text {
            margin-bottom: 12px;
            width: 80%;
            padding: .4em;
        }

        select {
            margin-bottom: 12px;
            width: 80%;
            padding: .4em;
        }

        fieldset {
            padding: 0;
            border: 0;
            margin-top: 25px;
        }

        div#users-contain {
            width: 350px;
            margin: 20px 0;
        }

        div#users-contain table {
            margin: 1em 0;
            border-collapse: collapse;
            width: 100%;
        }

        div#users-contain table td, div#users-contain table th {
            border: 1px solid #eee;
            padding: .6em 10px;
            text-align: left;
        }

        .ui-dialog .ui-state-error {
            padding: .3em;
        }

        .validateTips {
            border: 1px solid transparent;
            padding: 0.3em;
        }

        ul#icons {
            margin: 0;
            padding: 0;
        }

        ul#icons li {
            margin: 2px;
            position: relative;
            padding: 4px 0;
            cursor: pointer;
            float: left;
            list-style: none;
        }

        ul#icons span.ui-icon {
            float: left;
            margin: 0 4px;
        }

    </style>
</head>
<body class="main-body">
<div class="path"><p>当前位置：MEMDB管理<span>&gt;</span>securityrule列表</p></div>
<div class="main-cont">
    <h3 class="title">cluster列表</h3>

    <div class="set-area">
        <div>
            <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
                <li class="ui-state-default ui-corner-all">
                    <a href="/g/memdb/clustersecuritygrouprules/${tenantid}/${userId}/${region}/${group_id}">
                        <button>刷新</button>
                    </a>
                </li>
                <li class="ui-state-default ui-corner-all">
                    <button onclick="createRule('${tenantid}','${userId}','${region}');">创建</button>
                </li>
            </ul>
            <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
                <colgroup>
                </colgroup>
                <thead class="tb-tit-bg">
                <tr>
                    <th>
                        <div class="th-gap">id</div>
                    </th>

                    <th>
                        <div class="th-gap">类型</div>
                    </th>
                    <th>
                        <div class="th-gap">from_port</div>
                    </th>

                    <th>
                        <div class="th-gap">to_port</div>
                    </th>
                    <th>
                        <div class="th-gap">cidr</div>
                    </th>

                    <th>
                        <div class="th-gap">操作</div>
                    </th>

                </tr>
                </thead>
                <tbody>
                </tbody>

                <c:forEach var="rule" items="${rulelist}" varStatus="status">
                    <tr id="tr_${rule.id}">
                        <td>${rule.id} </td>
                        <td>${rule.protocol} </td>
                        <td>${rule.from_port} </td>
                        <td>${rule.to_port} </td>
                        <td>${rule.cidr} </td>
                        <td>
                            <p>
                                <button onclick="deleteRule('${tenantid}','${userId}','${region}','${rule.id}');">
                                    删除
                                </button>
                            </p>

                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </div>

    <div id="failover_dialog" title="failoverRDS" style="display:none;">
        <fieldset>
            <legend id="failover_legend">failoverRDS</legend>
            <p class="validateTips"><b style="color:red"></b></p>

            <p>force_host:<input id="failover_force_host" name="failover_force_host" value=""/></p>
        </fieldset>
    </div>
    <!---->


    <!--修改maxmemory_policy-->
    <div id="resetmaxmemory_policy" title="resetmaxmemory_policy" style="display:none;">
        <p class="validateTips"><b style="color:red"></b></p>
        <fieldset>
            <legend id="resize_legend1"></legend>
            <p>policy:<input id="policy" name="policy" value=""/></p>
        </fieldset>
    </div>

    <!--创建高可用实例-->
    <div id="addcluster_form" title="创建rule" style="display: none">
        <p class="validateTips"><b style="color:red"></b></p>

        <form>
            <fieldset>
                <p>to_port<b>(to_port)</b>：</p>
                <input AUTOCOMPLETE="off" type="text" name="to_port" id="to_port" value=""
                       class="text ui-widget-content ui-corner-all"/>

                <p>from_port<b>(from_port)</b>：</p>
                <input AUTOCOMPLETE="off" type="text" name="from_port" id="from_port" value=""
                       class="text ui-widget-content ui-corner-all"/>

                <p>cidr<b>(cidr)</b>：</p>
                <input AUTOCOMPLETE="off" type="text" name="cidr" id="cidr" value="0.0.0.0/0"
                       class="text ui-widget-content ui-corner-all"/>


            </fieldset>
        </form>
    </div>

</body>

<script type="text/javascript">

    var data = {cluster_id: "${group_id}"};
    var j = jQuery.noConflict(true);
    function checknull(tips, o, n) {
        if (o.val() == null || o.val() == "") {
            alert(n + " 不能为空!!!");
            o.addClass("ui-state-error");
            updateTips(tips, n + " 不能为空!!!");
            return false;
        } else {
            return true;
        }
    }

    function updateTips(tips, t) {
        tips.text(t).addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }
    ///////////   创建高可用实例  ///////////
    function createRule(tenant_id, userid, region) {


        $("#addcluster_form").dialog({
            autoOpen: false,
            height: 350,
            width: 300,
            modal: true,
            buttons: {
                ok: function () {
                    var c = confirm("确定要创建securityrule吗?");
                    if (c == false) {
                        return false;
                    }
                    var bValid = true;
                    var from_port = $("#from_port");
                    var to_port = $("#to_port");
                    var cidr = $("#cidr");
                    var tips = $(".validateTips");
                    bValid = bValid && checknull(tips, from_port, "from_port");
                    bValid = bValid && checknull(tips, to_port, "to_port");
                    bValid = bValid && checknull(tips, cidr, "cidr");
                    var parturl = tenant_id + "/" + userid + "/" + region;
                    if (bValid) {
                        $.ajax({
                            type: "POST",
                            url: "/g/memdb/clustersecuritygroupruleadd/" + parturl,
                            data: {
                                group_id: "${group_id}",
                                protocol: "tcp",
                                from_port: from_port.val(),
                                to_port: to_port.val(),
                                cidr: cidr.val()

                            },
                            success: function (data) {

                                var d1 = JSON.parse(data);
                                if (d1.result != "success") {
                                    alert(d1.result);
                                    window.location.href = "/g/memdb/clustersecuritygrouprules/" + parturl + "/" + "${group_id}";
                                    alert("操作失败!")
                                    return;
                                }
                                alert("操作成功!");
                                window.location.href = "/g/memdb/clustersecuritygrouprules/" + parturl + "/${group_id}";
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("创建 Cluster失败!");
                                alert("XMLHttpRequest.status:" + XMLHttpRequest.status);
                                alert("XMLHttpRequest.readyState:" + XMLHttpRequest.readyState);
                                alert("textStatus:" + textStatus);
                            }
                        });
                    }
                },
                cancel: function () {
                    $("#addcluster_form").dialog("close");
                }
            }
        });
        $("#addcluster_form").dialog("open");
    }


    function deleteRule(tenant_id, userid, region, rule_id) {
        var c = confirm("确定要 删除安全组 吗?");
        var parturl = tenant_id + "/" + userid + "/" + region + "/";
        if (c == false) {
            return false;
        }
        $.ajax({
            type: "POST",
            url: "/g/memdb/clustersecuritygroupRuleDelete/" + parturl,
            data: {
                security_group_rule_id: rule_id

            },
            success: function (data) {
                var d1 = JSON.parse(data);
                if (d1.result != "success") {
                    window.location.href = "/g/memdb/clustersecuritygrouprules/" + parturl + "/${group_id}";
                    alert("操作失败!")
                    return;
                }
                alert("操作成功!");
                window.location.href = "/g/memdb/clustersecuritygrouprules/" + parturl + "/${group_id}";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("failover instance 失败!");
                alert("XMLHttpRequest.status:" + XMLHttpRequest.status);
                alert("XMLHttpRequest.readyState:" + XMLHttpRequest.readyState);
                alert("textStatus:" + textStatus);
            }
        });
    }
    ///////////   create rds结束  ///////////
</script>

</html>
