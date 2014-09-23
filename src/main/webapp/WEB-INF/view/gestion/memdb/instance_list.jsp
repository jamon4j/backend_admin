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
<div class="path"><p>当前位置：MEMDB管理<span>&gt;</span>instance列表</p></div>
<div class="main-cont">
    <h3 class="title">cluster列表</h3>

    <div class="set-area">
        <div>
            <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
                <li class="ui-state-default ui-corner-all">
                    <a href="/g/memdb/getinstancelist/${tenantid}/${userId}/${cluster_id}/${region}">
                        <button>刷新</button>
                    </a>
                </li>
                <li class="ui-state-default ui-corner-all">
                    <button onclick="createInstance('${tenantid}','${userId}','${region}');">创建</button>
                </li>
            </ul>
            <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
                <colgroup>
                </colgroup>
                <thead class="tb-tit-bg">
                <tr>
                    <th>
                        <div class="th-gap">instacn_ID</div>
                    </th>

                    <th>
                        <div class="th-gap">名称</div>
                    </th>
                    <th>
                        <div class="th-gap">状态</div>
                    </th>

                    <th>
                        <div class="th-gap">IP</div>
                    </th>
                    <th>
                        <div class="th-gap">端口</div>
                    </th>
                    <th>
                        <div class="th-gap">角色</div>
                    </th>

                    <th>
                        <div class="th-gap">缓存类型</div>
                    </th>

                    <th>
                        <div class="th-gap">flavor</div>
                    </th>


                    <th>
                        <div class="th-gap">缓存操作</div>
                    </th>

                </tr>
                </thead>
                <tbody>
                </tbody>

                <c:forEach var="instance" items="${instancelist}" varStatus="status">
                    <tr id="tr_${cluster.id}">
                        <td>${instance.id} </td>
                        <td>${instance.name} </td>
                        <td>${instance.status} </td>
                        <td>${instance.ip} </td>
                        <td>${instance.port} </td>
                        <td>${instance.role} </td>
                        <td>${instance.datastore.type} </td>
                        <td>
                            <p>flavor(name)：${instance.flavordisplay.name}</p>

                            <p>flavor(ram)：${instance.flavordisplay.ram}M</p>

                            <p>flavor(vcpus)：${instance.flavordisplay.vcpus}个</p>

                            <p>flavor(id)：${instance.flavordisplay.id}</p>
                        </td>
                        <td>
                            <p>
                                <button onclick="deleteInstance('${tenantid}','${userId}','${region}','${cluster.id}');">
                                    failover
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
    <div id="addcluster_form" title="创建cluster" style="display: none">
        <p class="validateTips"><b style="color:red"></b></p>

        <form>
            <fieldset>
                <p>role<b>(role必填)</b>：</p>
                <select name="role" id="role" class="text ui-widget-content ui-corner-all">
                    <option value="PROXY">PROXY</option>

                </select>

                <p>请选择flavor<b>(protocol必填)</b>：</p>
                <select name="flavor_id" id="flavor_id" class="text ui-widget-content ui-corner-all">
                    <option value="">请选择</option>

                </select>

            </fieldset>
        </form>
    </div>

</body>

<script type="text/javascript">

    var data = {cluster_id: "${cluster_id}"};
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
    function createInstance(tenant_id, userid, region) {


        getFavorList(tenant_id, userid, region);
        $("#addcluster_form").dialog({
            autoOpen: false,
            height: 350,
            width: 300,
            modal: true,
            buttons: {
                ok: function () {
                    var c = confirm("确定要创建instance 吗?");

                    if (c == false) {
                        return false;
                    }
                    var bValid = true;
                    var role = $("#role");
                    var flavor_id = $("#flavor_id");
                    var tips = $(".validateTips");
                    bValid = bValid && checknull(tips, role, "role");
                    bValid = bValid && checknull(tips, flavor_id, "flavor_id");
                    var parturl = tenant_id + "/" + userid + "/" + data.cluster_id + "/" + region;
                    if (bValid) {
                        $.ajax({
                            type: "POST",
                            url: "/g/memdb/addinstance/" + parturl,
                            data: {
                                role: role.val(),
                                flavor_id: flavor_id.val()

                            },
                            success: function (data) {

                                var d1 = JSON.parse(data);
                                if (d1.result != "success") {
                                    alert(d1.result);
                                    window.location.href = "/g/memdb/getinstancelist/" + parturl;
                                    alert("操作失败!")
                                    return;
                                }
                                alert("操作成功!");
                                window.location.href = "/g/memdb/getinstancelist/" + parturl;
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


    function getFavorList(tenant_id, userid, region) {
        var parturl = tenant_id + "/" + userid + "/" + region;

        $.ajax({
            type: "GET",
            url: "/g/memdb/flavorlist/" + parturl,
            success: function (data) {
                var d1 = JSON.parse(data);
                var data = eval("(" + d1.data + ")");
                if (d1.code == 0) {
                    $('#flavor_id').html("");
                    if (data) {

                        for (i in  data) {

                            $('#flavor_id').append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                        }
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        })
    }


    function deleteInstance(tenant_id, userid, region, del_instance_id) {
        var c = confirm("确定要 failover 吗?");
        var parturl = tenant_id + "/" + userid + "/" + data.cluster_id + "/" + region;
        if (c == false) {
            return false;
        }
        $.ajax({
            type: "POST",
            url: "/g/memdb/failureoverinstance/" + parturl,
            data: {
                old_instance_id:del_instance_id

            },
            success: function (data) {
                var d1 = JSON.parse(data);
                if (d1.result != "success") {
                    window.location.href = "/g/memdb/getinstancelist/" + parturl;
                    alert("操作失败!")
                    return;
                }
                alert("操作成功!");
                window.location.href = "/g/memdb/getinstancelist/" + parturl;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("failover instance 失败!");
                alert("textStatus:" + textStatus);
            }
        });
    }
    ///////////   create rds结束  ///////////
</script>

</html>
