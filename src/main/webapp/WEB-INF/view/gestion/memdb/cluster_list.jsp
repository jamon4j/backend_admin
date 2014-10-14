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
<div class="path"><p>当前位置：MEMDB管理<span>&gt;</span>cluster列表</p></div>
<div class="main-cont">
    <h3 class="title">cluster列表</h3>

    <div class="set-area">
        <div>
            <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
                <li class="ui-state-default ui-corner-all">
                    <a href="/g/memdb/getclusterlist/${tenantid}/${userId}/${region}">
                        <button>刷新</button>
                    </a>
                </li>
                <li class="ui-state-default ui-corner-all">
                    <button onclick="createCluster('${tenantid}','${userId}','${region}');">创建</button>
                </li>
            </ul>
            <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
                <colgroup>
                </colgroup>
                <thead class="tb-tit-bg">
                <tr>
                    <th>
                        <div class="th-gap">cluster_ID</div>
                    </th>

                    <th>
                        <div class="th-gap">cluster状态</div>
                    </th>
                    <th>
                        <div class="th-gap">cluster名称</div>
                    </th>
                    <th>
                        <div class="th-gap">IP</div>
                    </th>
                    <th>
                        <div class="th-gap">端口</div>
                    </th>
                    <th>
                        <div class="th-gap">缓存类型</div>
                    </th>


                    <th>
                        <div class="th-gap">缓存大小</div>
                    </th>
                    <th>
                        <div class="th-gap">所属机房</div>
                    </th>

                    <th>
                        <div class="th-gap">安全组</div>
                    </th>

                    <th>
                        <div class="th-gap">缓存操作</div>
                    </th>

                </tr>
                </thead>
                <tbody>
                </tbody>

                <c:forEach var="cluster" items="${clusterlist}" varStatus="status">
                    <tr id="tr_${cluster.id}">
                        <td>${cluster.id} </td>
                        <td>${cluster.task_status} </td>
                        <td>${cluster.name} </td>
                        <td>${cluster.vip} </td>
                        <td>${cluster.port} </td>
                        <td>${cluster.protocol} </td>
                        <td>${cluster.size} </td>
                        <td>${area}</td>
                        <td>
                            <p>创建时间：${cluster.securitygroup.created}</p>

                            <p>安全组名称：${cluster.securitygroup.name}</p>

                            <p>安全组描述：${cluster.securitygroup.description}</p>
                            <button onclick="getsecurityrule('${tenantid}','${userId}','${region}','${cluster.securitygroup.id}');">
                                查看安全组规则
                            </button>
                        </td>
                        <td>
                            <p>
                                <button onclick="deleteCluster('${tenantid}','${userId}','${region}','${cluster.id}');">
                                    删除
                                </button>
                            </p>
                            <p>
                                <button onclick="resizeCluster('${tenantid}','${userId}','${region}','${cluster.id}');">
                                    扩容
                                </button>
                            </p>

                            <p>
                                <button onclick="flushdbCluster('${tenantid}','${userId}','${region}','${cluster.id}');">
                                    flushdb
                                </button>
                            </p>

                            <p>
                                <button onclick="setmaxmemorypolicyCluster('${tenantid}','${userId}','${region}','${cluster.id}');">
                                    修改maxmemory-policy
                                </button>
                            </p>
                            <p>
                                <button onclick="getinstances('${tenantid}','${userId}','${region}','${cluster.id}');">
                                    查看instance
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
    <div id="resize_dialog" title="resizeCluster" style="display:none;">
        <fieldset>
            <legend id="resize_legend"></legend>
            <p>size:<input id="resize" name="resize" value=""/></p>
        </fieldset>
    </div>


    <!--修改maxmemory_policy-->
    <div id="resetmaxmemory_policy" title="resetmaxmemory_policy" style="display:none;">
        <p class="validateTips"><b style="color:red"></b></p>
        <fieldset>
            <legend id="resize_legend1"></legend>
            <p>policy:<input id="policy" name="policy" value=""/></p>
        </fieldset>
    </div>

    <!--创建高可用实例-->
    <div id="addcluster_form" title="创建cluster" style="display: none">
        <p class="validateTips"><b style="color:red"></b></p>

        <form>
            <fieldset>
                <p>cluster名称<b>(name必填)</b>：</p>
                <input AUTOCOMPLETE="off" type="text" name="name" id="name" value=""
                       class="text ui-widget-content ui-corner-all"/>

                <p>cluster类型<b>(ha_cluster必填)</b>：</p>
                <input AUTOCOMPLETE="off" type="checkbox" name="ha_cluster" id="ha_cluster" value=""
                       class="text ui-widget-content ui-corner-all"/>


                <p>cluster所用协议<b>(protocol必填)</b>：</p>
                <select name="protocol" id="protocol" class="text ui-widget-content ui-corner-all">
                    <option value="redis">redis</option>

                </select>

                <p>size<b>(size必填)</b>：</p>

                <input AUTOCOMPLETE="off" type="text" name="size" id="size" value=""
                       class="text ui-widget-content ui-corner-all"/>
            </fieldset>
        </form>
    </div>

</body>

<script type="text/javascript">
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

function getinstances(tenantid, userid, region, cluster_id) {
    window.location.href = "/g/memdb/getinstancelist/" + tenantid + "/" + userid + "/" + cluster_id + "/" + region;
}

function getsecurityrule(tenantid, userid, region, groupid) {
    window.location.href = "/g/memdb/clustersecuritygrouprules/" + tenantid + "/" + userid + "/" + region + "/" + groupid;
}
///////////   创建高可用实例  ///////////
function createCluster(tenant_id, userid, region) {

    $("#addcluster_form").dialog({
        autoOpen: false,
        height: 550,
        width: 300,
        modal: true,
        buttons: {
            ok: function () {
                var c = confirm("确定要创建cluster 吗?");
                if (c == false) {
                    return false;
                }
                var bValid = true;
                var name = $("#name");
                var ha_cluster = $("#ha_cluster");
                var protocol = $("#protocol");
                var size = $("#size");
                var tips = $(".validateTips");
                bValid = bValid && checknull(tips, name, "name");
                bValid = bValid && checknull(tips, protocol, "protocol");
                bValid = bValid && checknull(tips, size, "size");
                if (isNaN(size.val())) {
                    bValid = false;
                    alert("请确认size大小为数字！");
                }

                if (bValid) {
                    $.ajax({
                        type: "POST",
                        url: "/g/memdb/addcluster/" + tenant_id + "/" + userid + "/" + region,
                        data: {
                            name: name.val(),
                            ha_cluster: ha_cluster.val(),
                            protocol: protocol.val(),
                            size: size.val()
                        },
                        success: function (data) {
                            var d1 = JSON.parse(data);
                            if (d1.result != "success") {
                                alert(d1.result);
                                window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                                alert("操作失败!")
                                return;
                            }
                            alert("操作成功!");
                            window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("创建 Cluster失败!");
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


function resizeCluster(tenant_id, userid, region, cluster_id) {
    $("#resize_legend").html("resize " + cluster_id);
    $("#resize_dialog").dialog({
        autoOpen: false,
        height: 200,
        width: 400,
        modal: true,
        buttons: {
            ok: function () {
                var c = confirm("确定要 resize 吗?");
                if (c == false) {
                    return false;
                }
                var bValid = true;
                var resize = $("#resize");
                var tips = $(".validateTips");
                bValid = bValid && checknull(tips, resize, "resize");
                if (isNaN(resize.val())) {
                    bValid = false;
                    alert("请确认size大小为数字！");
                }

                if (bValid) {
                    $.ajax({
                        type: "POST",
                        url: "/g/memdb/clusterresize/" + tenant_id + "/" + userid + "/" + cluster_id + "/" + region,
                        data: {
                            resize: resize.val()
                        },
                        success: function (data) {
                            var d1 = JSON.parse(data);
                            if (d1.result != "success") {
                                alert(d1.result);
                                window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                                alert("操作失败!")
                                return;
                            }
                            alert("操作成功!");
                            window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("resize RDS失败!");
                            alert("textStatus:" + textStatus);
                        }
                    });
                }
            },
            cancel: function () {
                $("#resize_dialog").dialog("close");
            }
        }
    });
    $("#resize_dialog").dialog("open");
}

function setmaxmemorypolicyCluster(tenant_id, userid, region, cluster_id) {
    $("#resize_legend").html("resize " + cluster_id);
    $("#resetmaxmemory_policy").dialog({
        autoOpen: false,
        height: 200,
        width: 400,
        modal: true,
        buttons: {
            ok: function () {
                var c = confirm("确定要 resetMaxmemory_policy 吗?");
                if (c == false) {
                    return false;
                }
                var bValid = true;
                var policy = $("#policy");
                var tips = $(".validateTips");
                bValid = bValid && checknull(tips, policy, "policy");
                if (isNaN(policy.val())) {
                    bValid = false;
                    alert("请确认policy大小为数字！");
                }
                if (bValid) {
                    $.ajax({
                        type: "POST",
                        url: "/g/memdb/clustersetmaxmemorypolicy/" + tenant_id + "/" + userid + "/" + cluster_id + "/" + region,
                        data: {
                            policy: policy.val()
                        },
                        success: function (data) {
                            var d1 = JSON.parse(data);
                            if (d1.result != "success") {
                                alert(d1.result);
                                window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                                alert("操作失败!")
                                return;
                            }
                            alert("操作成功!");
                            window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("resize RDS失败!");
//                            alert("XMLHttpRequest.status:" + XMLHttpRequest.status);
//                            alert("XMLHttpRequest.readyState:" + XMLHttpRequest.readyState);
                            alert("textStatus:" + textStatus);
                        }
                    });
                }
            },
            cancel: function () {
                $("#resetmaxmemory_policy").dialog("close");
            }
        }
    });
    $("#resetmaxmemory_policy").dialog("open");
}

function flushdbCluster(tenant_id, userid, region, cluster_id) {
    var c = confirm("确定要 flushdb 吗?");
    if (c == false) {
        return false;
    }
    $.ajax({
        type: "POST",
        url: "/g/memdb/clusterflushdb/" + tenant_id + "/" + userid + "/" + cluster_id + "/" + region,
        data: {},
        success: function (data) {
            var d1 = JSON.parse(data);
            if (d1.result != "success") {
                alert(d1.result);
                window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                alert("操作失败!")
                return;
            }
            alert("操作成功!");
            window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("setmaxmemorypolicyClustercluster失败!");
//            alert("XMLHttpRequest.status:" + XMLHttpRequest.status);
//            alert("XMLHttpRequest.readyState:" + XMLHttpRequest.readyState);
            alert("textStatus:" + textStatus);
        }
    });
}


function deleteCluster(tenant_id, userid, region, cluster_id) {
    var c = confirm("确定要 删除 吗?");
    if (c == false) {
        return false;
    }
    $.ajax({
        type: "GET",
        url: "/g/memdb/deletecluster/" + tenant_id + "/" + userid + "/" + cluster_id + "/" + region,
        success: function (data) {
            var d1 = JSON.parse(data);
            if (d1.result != "success") {
                alert(d1.result);
                window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
                alert("操作失败!")
                return;
            }
            alert("操作成功!");
            window.location.href = "/g/memdb/getclusterlist/" + tenant_id + "/" + userid + "/" + region;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("销毁cluster失败!");
//            alert("XMLHttpRequest.status:" + XMLHttpRequest.status);
//            alert("XMLHttpRequest.readyState:" + XMLHttpRequest.readyState);
            alert("textStatus:" + textStatus);
        }
    });
}
///////////   create rds结束  ///////////
</script>

</html>
