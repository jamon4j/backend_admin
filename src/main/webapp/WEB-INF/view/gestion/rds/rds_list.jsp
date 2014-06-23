<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
    <%@include file="../inc/meta.jspf"%>
    <%
		String userId = (String)request.getAttribute("userId");
		String instance_id = (String)request.getAttribute("instance_id");
 	%>
    <title>RDS - RDS列表</title>
	<style>
			label, input {  }
			input.text { margin-bottom:12px; width:80%; padding: .4em; }
			select {margin-bottom:12px; width:80%; padding: .4em; }
			fieldset { padding:0; border:0; margin-top:25px; }
			div#users-contain { width: 350px; margin: 20px 0; }
			div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
			div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
			.ui-dialog .ui-state-error { padding: .3em; }
			.validateTips { border: 1px solid transparent; padding: 0.3em; }
            ul#icons {margin: 0; padding: 0;}
            ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
            ul#icons span.ui-icon {float: left; margin: 0 4px;}
	</style>
   	<script language="javascript">
   		var j = jQuery.noConflict(true);
           	$(function(){
                    $( "#slider-range-min" ).slider({
                        range: "min",
                        value: 20,
                        min: 5,
                        max: 200,
                        slide: function( event, ui ) {
                            $( "#create_root_disk" ).val(ui.value);
                        }
                    });
                    $( "#create_root_disk" ).val($( "#slider-range-min" ).slider( "value" ));
        	   	});

   		function checknull( tips, o, n ) {
			if ( o.val() == null || o.val() == "" ) {
				alert( n + " 不能为空!!!" );
				o.addClass( "ui-state-error" );
				updateTips( tips, n + " 不能为空!!!" );
				return false;
			} else {
				return true;
			}
		}
		function updateTips( tips, t ) {
			tips.text( t ).addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}

    ///////////   securityDetail详情开始  ///////////
    		function securityDetail(id){
    	  		$( "#securityDetail_"+id ).dialog({
    				autoOpen: false,
    				postion: "center",
    				modal: true,
    				height:"700",
    				width:"900",
    				show: {
    					effect: "blind",
    					duration: 200

    				}
    			});
    			$( "#securityDetail_"+id ).dialog("open");
    	   	}
    ///////////   securityDetail详情结束  ///////////

    ///////////   security开始  ///////////
            function security(user_id,securityGroupId){
             var security_dialog = $("#security_dialog");
                 security_dialog.html("");
                security_dialog.dialog({
                    autoOpen: false,
                    postion: "center",
                    modal: true,
                    height:"700",
                    width:"900",
                    show: {
                        effect: "blind",
                        duration: 200

                    }
                });
                    $.ajax({
                        type: "GET",
                        url : "/g/user/rds/secrityGroup/",
                        data : {
                            username:user_id,
                            securityGroupId:securityGroupId
                        },
                        success : function(data) {
                              var d1 = JSON.parse(data);
                               if(d1.result != "success"){
                                   alert(d1.result);
                                   return;
                               }
                               if(d1.content != null){
                                   security_dialog.append("<p>id:"+d1.content.id+"</p>");
                                   security_dialog.append("<p>名称:"+d1.content.name+"</p>");
                                    for(var i=0;i<d1.content.links.length;i++){
                                        security_dialog.append("<p>&nbsp;&nbsp;<b>link</b></p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;href: "+d1.content.links[i].href+"</p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;rel: "+d1.content.links[i].rel+"</p>");
                                    }
                                    for(var i=0;i<d1.content.rules.length;i++){
                                        security_dialog.append("<p>&nbsp;&nbsp;<b>rule</b></p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;id: "+d1.content.rules[i].id+"</p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;to_port: "+d1.content.rules[i].to_port+"</p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;cidr: "+d1.content.rules[i].cidr+"</p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;from_port: "+d1.content.rules[i].from_port+"</p>");
                                        security_dialog.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;protocol: "+d1.content.rules[i].protocol+"</p>");
                                    }
                                    security_dialog.append("<p>描述:"+d1.content.description+"</p>");
                                    security_dialog.append("<p>创建时间:"+d1.content.created+"</p>");
                                    security_dialog.append("<p>更新时间:"+d1.content.updated+"</p>");
                               }else{
                                     security_dialog.append("<p>没有信息</p>");
                               }
                        },
                        error : function(XMLHttpRequest,textStatus,errorThrown) {
                            alert("失败!");
                            alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                            alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                            alert("textStatus:"+textStatus);
                        }
                    });
                security_dialog.dialog("open");
            }
    ///////////   security结束  ///////////

    ///////////   backup开始  ///////////
            function backup(userid,instance_id){
                $( "#backup_dialog").dialog({
                    autoOpen: false,
                    postion: "center",
                    modal: true,
                    height:"700",
                    width:"900",
                    show: {
                        effect: "blind",
                        duration: 200
                    }
                });
                var backup_dialog = $("#backup_dialog");
                backup_dialog.html("");
                $.ajax({
                    type: "GET",
                    url : "/g/user/rds/backup/",
                    data : {
                        username:userid,
                        instance_id:instance_id
                    },
                    success : function(data) {
                            var d1;
                            try{
                                d1 = JSON.parse(data);
                            }catch(err){
                               //alert("错误:"+ err);
                               backup_dialog.append("<p>暂无备份.</p>");
                            }
                           if(d1.result != "success"){
                               alert(d1.result);
                               return;
                           }
                        if(d1.content.length>0){
                            backup_dialog.append("<form>");
                            backup_dialog.append("<fieldset>");
                            for(var i=0;i<d1.content.length;i++){
                                backup_dialog.append("<div id='backup_div_"+d1.content[i].id+"'>");
                                backup_dialog.append("<p></p>");
                                    backup_dialog.append("<legend>id: " + d1.content[i].id + "</legend>");
                                    backup_dialog.append("<p>name: " + d1.content[i].name + "</p>");
                                    backup_dialog.append("<p>描述: " + d1.content[i].description + "</p>");
                                    backup_dialog.append("<p>status: " + d1.content[i].status + "</p>");
                                    backup_dialog.append("<p>locationRef: " + d1.content[i].locationRef + "</p>");
                                    backup_dialog.append("<p>size: " + d1.content[i].size + "</p>");
                                    backup_dialog.append("<p>type: " + d1.content[i].type + "</p>");
                                    backup_dialog.append("<p>db_type: " + d1.content[i].db_type + "</p>");
                                    backup_dialog.append("<p>instance_id: " + d1.content[i].instance_id + "</p>");
                                    backup_dialog.append("<p>group_id: " + d1.content[i].group_id + "</p>");
                                    backup_dialog.append("<p>创建时间: " + d1.content[i].created + "</p>");
                                    backup_dialog.append("<p>更新时间: " + d1.content[i].updated + "</p>");
                                    backup_dialog.append("<p>更新时间: " + d1.content[i].updated + "</p>");
                                    if("snapshot" == d1.content[i].type){
                                        backup_dialog.append("<button id='del_backup_button_"+d1.content[i].id+"'>删除备份</button>");
                                        $("#del_backup_button_"+d1.content[i].id).attr("onclick","delete_backup('"+userid+"','"+d1.content[i].id+"');");
                                    }
                                backup_dialog.append("<p></p>");
                                backup_dialog.append("<br/>");
                                backup_dialog.append("<br/>");
                                backup_dialog.append("<br/>");
                                backup_dialog.append("<hr/>");
                                backup_dialog.append("</div>");
                            }
                            backup_dialog.append("</fieldset>");
                            backup_dialog.append("</form>");
                        }else{
                            backup_dialog.append("<p>暂无备份.</p>");
                        }
                    },
                    error : function(XMLHttpRequest,textStatus,errorThrown) {
                        alert("失败!");
                        alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                        alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                        alert("textStatus:"+textStatus);
                    }
                });
                $( "#backup_dialog").dialog("open");
            }
    ///////////   backup结束  ///////////


    function delete_backup(userid,backup_id){
        var c= confirm("确定要 delete backup "+ backup_id +"吗?");
         if(c== false){
             return false;
         }
         $.ajax({
             type: "POST",
             url : "/g/user/rds/backup/delete",
             data : {
                 username:userid,
                 backup_id:backup_id
             },
             success : function(data) {
                   var d1 = JSON.parse(data);
                    if(d1.result != "success"){
                        alert(d1.result);
                        return;
                    }
                    alert("操作成功!");
                    $("#backup_div_"+backup_id).remove();
             },
             error : function(XMLHttpRequest,textStatus,errorThrown) {
                 alert("失败!");
                 alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                 alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                 alert("textStatus:"+textStatus);
                 return false;
             }
         });
    }

    ///////////   backup_config开始  ///////////
            function backup_config(user_id,instance_id){
                var backup_config_dialog = $("#backup_config_dialog");
                backup_config_dialog.html("");
                backup_config_dialog.dialog({
                    autoOpen: false,
                    postion: "center",
                    modal: true,
                    height:"700",
                    width:"900",
                    show: {
                        effect: "blind",
                        duration: 200

                    }
                });
                $.ajax({
                    type: "GET",
                    url : "/g/user/rds/backupConfig/",
                    data : {
                        username:user_id,
                        instance_id:instance_id
                    },
                    success : function(data) {
                          var d1 = JSON.parse(data);
                           if(d1.result != "success"){
                               alert(d1.result);
                               return;
                           }
                        if(d1.content != null){
                            backup_config_dialog.append("<p>duration: "+d1.content.duration+"</p>");
                            backup_config_dialog.append("<p>autobackup_at: "+d1.content.autobackup_at+"</p>");
                            backup_config_dialog.append("<p>expire_after: "+d1.content.expire_after+"</p>");
                        }else{
                            backup_config_dialog.append("<p>没有信息.</p>");
                        }
                    },
                    error : function(XMLHttpRequest,textStatus,errorThrown) {
                        alert("失败!");
                        alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                        alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                        alert("textStatus:"+textStatus);
                    }
                });
                backup_config_dialog.dialog("open");
            }
    ///////////   backup_config结束  ///////////

 	</script>
</head>

<body class="main-body">
<div class="path"><p>当前位置：RDS管理<span>&gt;</span>rds列表</p></div>
<div class="main-cont">
    <h3 class="title">rds列表</h3>
    <form action="/g/user/rdslist/"  method="post"  style="display:inline-block;" >
        user_id：<input type="text" name="user_id" value="" />
        <%--group：<input type="text" name="group" value="" size="50"/>--%>
        instance_id：<input type="text" name="instance_id" value="" size="50"/>
        <input class="ui-state-default ui-corner-all" type="submit" name="submit" value="查  找"  />
    </form>
    <div class="set-area">
        <div>rds列表，rds数量：<c:out value="${rdsGroupDTO.instanceSize}"></c:out>
        <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
            <li class="ui-state-default ui-corner-all">
                <a href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>"><button >刷新</button></a>
            </li>
            <%--<li class="ui-state-default ui-corner-all" onclick="editrds();">
                <button></button>
            </li>--%>
            <li class="ui-state-default ui-corner-all">
                <button  onclick="addrds('<%=userId %>',false);">创建</button>
            </li>
            <li class="ui-state-default ui-corner-all">
                <button  onclick="addrdsFromBackup('<%=userId %>');">从备份创建</button>
            </li>
        </ul>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th >
                    <div class="th-gap">复制组ID</div>
                </th>
                <th>
                    <div class="th-gap">实例名称</div>
                </th>
                <th >
                    <div class="th-gap">实例类型</div>
                </th>
                <th >
                    <div class="th-gap">实例ID</div>
                </th>
                <th>
                    <div class="th-gap">租户ID</div>
                </th>
                 <th>
                    <div class="th-gap">状态</div>
                </th>
                <!-- <th >
                    <div class="th-gap">复制组ID</div>
                </th> -->
                <th>
                    <div class="th-gap">虚拟机IP</div>
                </th>
                <th >
                    <div class="th-gap">VIP</div>
                </th>
                <th>
                    <div class="th-gap">创建时间</div>
                </th>
                <th >
                    <div class="th-gap">更新时间</div>
                </th>
                <th >
                    <div class="th-gap">安全组信息</div>
                </th>
                <th>
                    <div class="th-gap">备份列表</div>
                </th>
                 <th>
                     <div class="th-gap">备份配置</div>
                 </th>
                 <th>
                      <div class="th-gap">操作</div>
                  </th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            </table>
             <c:forEach var="rdsGroups" items="${rdsGroupDTO.rdsGroups}" varStatus="status">
              <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
                  <colgroup>
                  </colgroup>
                <tr>
                    <td rowspan="${fn:length(rdsGroups.rdsInstances) + 1}">${rdsGroups.group}</td>
                </tr>
                    <c:forEach var="rds" items="${rdsGroups.rdsInstances}" varStatus="status">


                        <tr id="tr_${rds.id}">
                            <td>${rds.name} </td>
                            <td>${rds.type} </td>
                            <td>${rds.id} </td>
                            <td><%=userId %></td>
                            <td>
                            <p><b>status</b></p> <p>[${rds.status}]</p>
                            <hr/>
                            <p><b>service_status</b></p><p>[${rds.service_status}]</p>
                            <hr/>
                            <p><b>server_status</b></p> <p>[${rds.server_status}]</p>
                            <hr/>
                            <p><b>task_status</b></p><p>[${rds.task_status}]</p>
                            </td>
                            <!--<td>${rds.group} </td>-->
                            <td><c:forEach var="i" items="${rds.ip}" varStatus="status">
                                <p>ip:${i}</p>
                            </c:forEach></td>
                            <td>${rds.vip} </td>
                            <td>${rds.created}</td>
                            <td>${rds.updated}</td>
                            <td><button class="ui-state-default ui-corner-all" onclick="security('<%=userId %>','${rds.security_group}')">安全组信息</button>
                            </td>
                            <td><button class="ui-state-default ui-corner-all" onclick="backup('<%=userId %>','${rds.id}')">备份列表</button>
                            </td>
                            <td><button class="ui-state-default ui-corner-all" onclick="backup_config('<%=userId %>','${rds.id}')">备份配置</button>
                            </td>
                            <td>
                                <p><button onclick="removeInstance('<%=userId %>','${rds.id}');">删除</button></p>
                                <c:choose>
                                       <c:when test="${rds.type eq 'MASTER' || rds.type eq 'STANDBY'}">
                                        <p><button onclick="migrate('<%=userId %>','${rds.id}');">迁移</button></p>
                                        <p><button onclick="failover('<%=userId %>','${rds.id}');">failover</button></p>
                                       </c:when>
                                </c:choose>
                                <p><button onclick="resize('<%=userId %>','${rds.id}','${rds.flavor.vcpus}','${rds.flavor.disk}','${rds.flavor.ram}');">resize</button></p>
                                <p><button onclick="resetAdminPassword('<%=userId %>','${rds.id}');">修改密码</button></p>
                                <c:choose>
                                       <c:when test="${rds.type eq 'SINGLE'}">
                                          <p><button onclick="upgrade('<%=userId %>','${rds.id}');">升级</button></p>
                                       </c:when>
                                </c:choose>
                                <p><button onclick="create_backup('<%=userId %>','${rds.id}');">创建备份</button></p>
                            </td>
                        </tr>
                    </c:forEach>
			</c:forEach>
            </table>
    </div>
</div>


 <%-- 隐藏区 开始 --%>

    <div id="create_backup_dialog" title="create_backup" style="display:none;">
        <form>
            <fieldset>
                <legend id="create_backup_legend">创建备份</legend>
                <p class="validateTips"><b style="color:red"></b></p>
                <p>备份名称:<input id="create_backup_name" name="create_backup_name" value=""/></p>
                 <p>备份类型：</p>
                  <select name="create_backup_type" id="create_backup_type"  class="text ui-widget-content ui-corner-all">
                       <option value="snapshot">手动</option>
                       <option value="autobackup">自动</option>
                  </select>
                <p>description:<input id="create_backup_description" name="create_backup_description" value=""/></p>
            </fieldset>
        </form>
    </div>

    <div id="migrate_dialog" title="migrateRDS" style="display:none;">
        <fieldset>
            <legend id="migrate_legend"></legend>
            <p class="validateTips"><b style="color:red"></b></p>
            <div id="migrate_backup_id_div">
                <p>备份列表：</p>
                   <select name="migrate_backup_id" id="migrate_backup_id"  class="text ui-widget-content ui-corner-all">
                        <option value="">不选择</option>
                   </select>
            </div>
            <p>host:<input id="migrate_host" name="migrate_host" value=""/></p>
            <p>内存:<input id="migrate_ram" name="migrate_ram" value=""/></p>
            <p>CPU:<input id="migrate_vcpus" name="migrate_vcpus" value=""/></p>
            <p>硬盘:<input id="migrate_disk" name="migrate_disk" value=""/></p>
        </fieldset>
    </div>

    <div id="failover_dialog" title="failoverRDS" style="display:none;">
        <fieldset>
            <legend id="failover_legend">failoverRDS</legend>
            <p class="validateTips"><b style="color:red"></b></p>
            <p>force_host:<input id="failover_force_host" name="failover_force_host" value=""/></p>
        </fieldset>
    </div>

    <div id="backup_config_dialog" title="backup_config详情" style="display:none"></div>

    <div id="security_dialog" title="security详情" style="display:none"></div>

    <div id="backup_dialog" title="backup_dialog" style="display:none">
    </div>

    <div id="resize_dialog" title="resizeRDS" style="display:none;">
        <fieldset>
            <legend id="resize_legend"></legend>
            <p class="validateTips"><b style="color:red"></b></p>
            <p>内存:<input id="resize_ram" name="resize_ram" value=""/></p>
            <p>CPU:<input id="resize_vcpus" name="resize_vcpus" value=""/></p>
            <p>硬盘:<input id="resize_disk" name="resize_disk" value=""/></p>
        </fieldset>
    </div>

    <div id="resetAdminPassword_dialog" title="resetAdminPassword" style="display:none;">
        <p class="validateTips"><b style="color:red"></b></p>
        <form>
            <fieldset>
                <legend>resetAdminPassword</legend>
                <p>新的超管密码:<input AUTOCOMPLETE="off" type="passowrd"  id="reset_admin_password" name="reset_admin_password" value="" AUTOCOMPLETE="off"/></p>
                <p>再次输入超管密码:<input AUTOCOMPLETE="off" type="passowrd" id="reset_admin_password2" name="reset_admin_password2" value="" AUTOCOMPLETE="off"/></p>
            </fieldset>
        </form>
    </div>

<div id="addrds_form" title="创建RDS" style="display: none">
    <p class="validateTips"><b style="color:red"></b></p>
	<form>
		<fieldset>


            <p>租户ID<b>(必填)</b>：</p>
            <input AUTOCOMPLETE="off" type="text" name="create_name" id="create_user"  value="" class="text ui-widget-content ui-corner-all" />

			<p>名称<b>(必填)</b>：</p>
			<input AUTOCOMPLETE="off" type="text" name="create_name" id="create_name"  value="" class="text ui-widget-content ui-corner-all" />
			<div id="create_backup_id_div"  style="display: none">
                <p>备份列表：</p>
                   <select name="create_backup_id" id="create_backup_id"  class="text ui-widget-content ui-corner-all">
                   </select>
            </div>
			<p>类型：</p>
           <select name="create_type" id="create_type"  class="text ui-widget-content ui-corner-all" >
               <option value="SG">测试</option>
               <option value="HA">高可用</option>
               <option value="RR">只读</option>
           </select>
           <p>数据库类型：</p>
           <select name="create_service_type" id="create_service_type" class="text ui-widget-content ui-corner-all" >
               <option value="mysql">mysql</option>
           </select>
           <p>超管用户名<b>(必填)</b>：</p>
            <input AUTOCOMPLETE="off"  type="text" name="create_admin_user" id="create_admin_user" value="" class="text ui-widget-content ui-corner-all" />
           <p>超管密码<b>(必填)</b>：</p>
            <input AUTOCOMPLETE="off"  type="password" name="create_admin_password" id="create_admin_password" value="" class="text ui-widget-content ui-corner-all" />
           <p>再次输入超管密码<b>(必填)</b>：</p>
           <input AUTOCOMPLETE="off"  type="password" name="create_admin_password2" id="create_admin_password2" value="" class="text ui-widget-content ui-corner-all" />
            <p>数据库端口<b>(必填)</b>：</p>
            <input AUTOCOMPLETE="off"  type="text" name="create_port" id="create_port" value="" class="text ui-widget-content ui-corner-all" />
            <p>cpu核数（如：1、4、8）</p>
            <input AUTOCOMPLETE="off"  type="text" name="create_vcpu" id="create_vcpu" value="" class="text ui-widget-content ui-corner-all" />
            <p><label for="create_root_disk">系统盘大小<b>(必填,单位:<input type="text" name="create_root_disk" id="create_root_disk" style="width:28px;border:0;color: #f6931f; font-weight: bold;display: inline-block;" />GB)</b></label></p>
            <div id="slider-range-min" style="width:81%;"></div>
            <p>内存大小(如：512、2048)：</p>
            <input AUTOCOMPLETE="off"  type="text" name="create_ram" id="create_ram" value="" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
</div>


 <%-- 隐藏区 结束 --%>
 <script>
        		///////////   create_backup 开始  ///////////
     	   	function create_backup(userid,instance_id){

                var tips = $( ".validateTips" );
                $("#create_backup_legend").html("create_backup " + instance_id);
     		   	$( "#create_backup_dialog").dialog({
     				autoOpen: false,
     				height: 350,
     				width: 700,
     				modal: true,
     				buttons: {
     					"提交": function() {
                            var c= confirm("确定要 create_backup 吗?");
                              if(c== false){
                                  return false;
                              }
     						var bValid = true;
     						create_backup_type = $( "#create_backup_type");
     						create_backup_name = $( "#create_backup_name");
     					    //tips = $( ".validateTips" );

     						bValid = bValid && checknull( tips, create_backup_name, "create_backup_name");
     						if(bValid){
     							$.ajax({
     								type: "POST",
     								url : "/g/user/rds/backup/",
     								data : {
                                         username:userid,
                                         instance_id:instance_id,
                                         backup_name:create_backup_name.val(),
                                         type:create_backup_type.val()
     								},
     								success : function(data) {
     									  var d1 = JSON.parse(data);
                                            if(d1.result != "success"){
                                                alert(d1.result);
                                                //window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                                return;
                                            }
                                            alert("操作成功!");
                                            //window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
     								},
     								error : function(XMLHttpRequest,textStatus,errorThrown) {
     									alert("create_backup 失败!");
     									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
     									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
     									alert("textStatus:"+textStatus);
     								}
     							});
     						}
     					},
     					"取消": function() {
     						$("#create_backup_dialog").dialog( "close" );
     					}
     				},
     				close: function() {

                        if (tips)
                        {
                            tips.empty();
                    }
     				}
     			});
     	   		$( "#create_backup_dialog" ).dialog("open");
     	   	}
     	   	///////////   create_backup 结束  ///////////
 </script>


<script>
       		///////////   resize rds开始  ///////////
    	   	function resize(userid,instance_id,resize_vcpus,resize_disk,resize_ram){
    	   	    $("#resize_legend").html("resize "+instance_id);

    	   	    $( "#resize_vcpus").val(resize_vcpus);
                $( "#resize_disk").val(resize_disk);
                $( "#resize_ram").val(resize_ram);

    		   	$( "#resize_dialog").dialog({
    				autoOpen: false,
    				height: 350,
    				width: 700,
    				modal: true,
    				buttons: {
    					"修改": function() {
                           var c= confirm("确定要 resize 吗?");
                             if(c== false){
                                 return false;
                             }
    						var bValid = true;
    						resize_vcpus = $( "#resize_vcpus");
    						resize_disk = $( "#resize_disk");
    						resize_ram = $("#resize_ram");
    					    tips = $( ".validateTips" );

    						bValid = bValid && checknull( tips, resize_vcpus, "resize_vcpus");
    						bValid = bValid && checknull( tips, resize_disk, "resize_disk");
    						bValid = bValid && checknull( tips, resize_ram, "resize_ram");
                            if(isNaN(resize_disk.val())){
                                bValid = false;
                                alert("请确认磁盘大小为数字！");
                            }
                            if(isNaN(resize_vcpus.val())){
                                bValid = false;
                                alert("请确认cpu核数为数字！");
                            }
                            if(isNaN(resize_ram.val())){
                                bValid = false;
                                alert("请确认内存大小为数字！");
                            }
    						if(bValid){
    							$.ajax({
    								type: "POST",
    								url : "/g/user/rds/resize/",
    								data : {
                                        username:userid,
                                        instance_id:instance_id,
                                        ram:resize_ram.val(),
                                        vcpus:resize_vcpus.val(),
                                        disk:resize_disk.val()
    								},
    								success : function(data) {
    									  var d1 = JSON.parse(data);
                                           if(d1.result != "success"){
                                               alert(d1.result);
                                               window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                               return;
                                           }
                                           alert("操作成功!");
                                           window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
    								},
    								error : function(XMLHttpRequest,textStatus,errorThrown) {
    									alert("resize RDS失败!");
    									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
    									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
    									alert("textStatus:"+textStatus);
    								}
    							});
    						}
    					},
    					"取消": function() {
    						$("#resize_dialog").dialog( "close" );
    					}
    				}
    			});
    	   		$( "#resize_dialog" ).dialog("open");
    	   	}
    	   	///////////   resize rds结束  ///////////
</script>

<script>
       		///////////   resetAdminPassword 开始  ///////////
    	   	function resetAdminPassword(userid,instance_id){
                var tips = $( ".validateTips" );
    		   	$( "#resetAdminPassword_dialog").dialog({
    				autoOpen: false,
    				height: 350,
    				width: 700,
    				modal: true,
    				buttons: {
    					"修改": function() {
                           var c= confirm("确定要 resetAdminPassword 吗?");
                             if(c== false){
                                 return false;
                             }
    						var bValid = true;
    						reset_admin_password = $( "#reset_admin_password");
    						reset_admin_password2 = $( "#reset_admin_password2");


    						bValid = bValid && checknull( tips, reset_admin_password, "reset_admin_password");
    						bValid = bValid && checknull( tips, reset_admin_password2, "reset_admin_password2");
    						if(! (reset_admin_password.val()==reset_admin_password2.val())){
                                bValid = false;
                                alert("请确认两次输入的密码相同！");
                            }
    						if(bValid){
    							$.ajax({
    								type: "POST",
    								url : "/g/user/rds/resetAdminPassword/",
    								data : {
                                        username:userid,
                                        instance_id:instance_id,
                                        password:reset_admin_password.val()
    								},
    								success : function(data) {
    									  var d1 = JSON.parse(data);
                                           if(d1.result != "success"){
                                               alert(d1.result);
                                               window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                               return;
                                           }
                                           alert("操作成功!");
                                           window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
    								},
    								error : function(XMLHttpRequest,textStatus,errorThrown) {
    									alert("resetAdminPassword 失败!");
    									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
    									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
    									alert("textStatus:"+textStatus);
    								}
    							});
    						}
    					},
    					"取消": function() {
    						$("#resetAdminPassword_dialog").dialog( "close" );
    					}
    				},
    				close: function() {

                              tips.empty();

    				}
    			});
    	   		$( "#resetAdminPassword_dialog" ).dialog("open");
    	   	}
    	   	///////////   resetAdminPassword 结束  ///////////
</script>

<script>
            function addrdsFromBackup(userid){
                $( "#create_backup_id_div").show();
                var create_backup_id = $("#create_backup_id");
                create_backup_id.html("");
                 $.ajax({
                    type: "GET",
                    url : "/g/user/rds/backup/",
                    data : {
                        username:userid
                    },
                    success : function(data) {
                          var d1 = JSON.parse(data);
                           if(d1.result != "success"){
                               alert(d1.result);
                               return;
                           }
                        if(d1.content.length>0){
                            for(var i=0;i<d1.content.length;i++){
                                create_backup_id.append("<option value='"+d1.content[i].id+"'>" + d1.content[i].name + "</option>");
                            }
                        }else{
                           create_backup_id.append("<option value=''>暂无备份.</option>");
                        }
                           addrds(userid,true);
                    },
                    error : function(XMLHttpRequest,textStatus,errorThrown) {
                        alert("失败!");
                        alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                        alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                        alert("textStatus:"+textStatus);
                    }
                });
            }

       		///////////   创建rds开始  ///////////
    	   	function addrds(userid,addrdsFromBackup){
    	   	    if(addrdsFromBackup == null || addrdsFromBackup == false){
    	   	        $( "#create_backup_id_div").hide();
    	   	    }
    		   	$( "#addrds_form" ).dialog({
    				autoOpen: false,
    				height: 800,
    				width: 700,
    				modal: true,
    				buttons: {
    					"创建": function() {
    					    var c= confirm("确定要 创建 吗?");
                             if(c== false){
                                 return false;
                             }
    						var bValid = true;
                           var create_user=$("#create_user");
    						var create_name = $( "#create_name" );
    						create_type = $( "#create_type" );
    						create_service_type = $( "#create_service_type" );
    						create_admin_user = $( "#create_admin_user" );
    						create_admin_password = $( "#create_admin_password" );
    						create_admin_password2 = $( "#create_admin_password2" );
    						create_port = $( "#create_port" );
    						create_vcpu = $( "#create_vcpu" );
    						create_root_disk = $( "#create_root_disk" );
    						create_ram = $("#create_ram");
    					//	allFields = $( [] ).add( create_name ).add( create_imageRef ).add( create_count ).add( create_security_groups ).add( create_adminPass ),
    						tips = $( ".validateTips" );


    					//	allFields.removeClass( "ui-state-error" );
                            bValid = bValid&& checknull( tips, create_user, "create_user");
    						bValid = bValid && checknull( tips, create_name, "create_name");
    						bValid = bValid && checknull( tips, create_type, "create_type");
    						bValid = bValid && checknull( tips, create_service_type, "create_service_type");
    						bValid = bValid && checknull( tips, create_admin_user, "create_admin_user");
    						bValid = bValid && checknull( tips, create_admin_password, "create_admin_password");
    						bValid = bValid && checknull( tips, create_admin_password2, "create_admin_password2");
    						bValid = bValid && checknull( tips, create_port, "create_port");
    						bValid = bValid && checknull( tips, create_vcpu, "create_vcpu");
    						bValid = bValid && checknull( tips, create_root_disk, "create_root_disk");
    						bValid = bValid && checknull( tips, create_ram, "create_ram");
                            if(! (create_admin_password.val()==create_admin_password2.val())){
                                bValid = false;
                                alert("请确认两次输入的密码相同！");
                            }
                            if(isNaN(create_port.val())){
                                bValid = false;
                                alert("请确认数据库端口为数字！");
                            }
                            if(isNaN(create_vcpu.val())){
                                bValid = false;
                                alert("请确认cpu核数为数字！");
                            }
                            if(isNaN(create_ram.val())){
                                bValid = false;
                                alert("请确认内存大小为数字！");
                            }

                            var postdata={"user_id":create_user.val(),
                                "rds":{
                                    "name":create_name.val(),
                                    "type":create_type.val(),
                                    "service_type":create_service_type.val(),
                                    "extend":{
                                        "admin_user":create_admin_user.val(),
                                        "admin_password":create_admin_password.val(),
                                        "port":create_port.val()
                                    },
                                    "flavor":{
                                        "ram":create_ram.val(),
                                        "vcpus":create_vcpu.val(),
                                        "disk":create_root_disk.val()
                                    }
                                }
                            }
                            if(addrdsFromBackup)
                            {
                                postdata=={"user_id":create_user.val(),
                                    "rds":{
                                        "name":create_name.val(),
                                        "type":create_type.val(),
                                        "service_type":create_service_type.val(),
                                        "extend":{
                                            "admin_user":create_admin_user.val(),
                                            "admin_password":create_admin_password.val(),
                                            "port":create_port.val()
                                        },
                                        "flavor":{
                                            "ram":create_ram.val(),
                                            "vcpus":create_vcpu.val(),
                                            "disk":create_root_disk.val()
                                        },
                                        "restorePoint":{
                                            "backupRef":$("#create_backup_id").val()
                                        }
                                    }
                                }
                            }

                            var jsonData = JSON.stringify(postdata);
    						if(bValid){
    							$.ajax({
    								type: "POST",
    								url : "/g/user/createrds/",
    								contentType: "application/json; charset=utf-8",
    								data : jsonData,
    								success : function(data) {
    									  var d1 = JSON.parse(data);
                                           //alert(d1.result);
                                           if(d1.result != "success"){
                                               alert(d1.result);
                                               window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                               return;
                                           }
                                           alert("操作成功!");
                                           window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
    								},
    								error : function(XMLHttpRequest,textStatus,errorThrown) {
    									alert("创建RDS失败!");
    									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
    									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
    									alert("textStatus:"+textStatus);
    								}
    							});
    						$("#addrds_form").dialog( "close" );
    						}
    					},
    					"取消": function() {
    						$("#addrds_form").dialog( "close" );
    					}
    				},
    				close: function() {
    					//$("#create_imageRef").empty();
    					//$("#create_imageRef").append("<option value=''>请选择镜像</option>");
    					//$("#create_zone").empty();
    					//$("#create_zone").append("<option value=''>请选择zone</option>");
    					//$("#create_security_groups").empty();
    				}
    			});
    	   		$( "#addrds_form" ).dialog("open");
    	   	}
    	   	///////////   创建rds结束  ///////////
</script>

<script>

    	   	///////////   upgrade 开始  ///////////
            function upgrade(userid,instance_id){
                    var c= confirm("确定要 upgrade"+instance_id+"吗?");
                     if(c== false){
                         return false;
                     }
                    $.ajax({
                        type: "POST",
                        url : "/g/user/rds/upgrade/",
                        data : {
                            username:userid,
                            instance_id:instance_id
                        },
                        success : function(data) {
                              var d1 = JSON.parse(data);
                               //alert(d1.result);
                               if(d1.result != "success"){
                                   alert(d1.result);
                                   return;
                               }
                               alert("操作成功!");
                               window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                        },
                        error : function(XMLHttpRequest,textStatus,errorThrown) {
                            alert("失败!");
                            alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                            alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                            alert("textStatus:"+textStatus);
                        }
                    });
            }
            ///////////   upgrade 结束  ///////////

            ///////////   migrate rds开始  ///////////
                function migrate(userid,instance_id){
                    $("#migrate_legend").html("migrate " + instance_id);
                    var migrate_backup_id = $("#migrate_backup_id");
                        migrate_backup_id.html("");
                        migrate_backup_id.html("<option value=''>不选择</option>");
                         $.ajax({
                            type: "GET",
                            url : "/g/user/rds/backup/",
                            data : {
                                username:userid
                            },
                            success : function(data) {
                                  var d1 = JSON.parse(data);
                                   if(d1.result != "success"){
                                       alert(d1.result);
                                       return;
                                   }
                                if(d1.content.length>0){
                                    for(var i=0;i<d1.content.length;i++){
                                        migrate_backup_id.append("<option value='"+d1.content[i].id+"'>" + d1.content[i].name + "</option>");
                                    }
                                }else{
                                }
                                migrate2(userid,instance_id);
                            },
                            error : function(XMLHttpRequest,textStatus,errorThrown) {
                                alert("失败!");
                                alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                                alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                                alert("textStatus:"+textStatus);
                            }
                        });
                }

                function migrate2(userid,instance_id){
                    $( "#migrate_dialog").dialog({
                        autoOpen: false,
                        height: 350,
                        width: 700,
                        modal: true,
                        buttons: {
                            "提交": function() {
                               var c= confirm("确定要 migrate 吗?");
                                 if(c== false){
                                     return false;
                                 }
                                var bValid = true;
                                var migrate_host = $( "#migrate_host");
                                var migrate_vcpus = $( "#migrate_vcpus");
                                var migrate_disk = $( "#migrate_disk");
                                var migrate_ram = $("#migrate_ram");
                                var migrate_backup_id = $("#migrate_backup_id");

                                if(migrate_disk.val()!= "" &&  isNaN(migrate_disk.val())){
                                    bValid = false;
                                    alert("请确认磁盘大小为数字！");
                                }
                                if(migrate_vcpus.val()!= "" && isNaN(migrate_vcpus.val())){
                                    bValid = false;
                                    alert("请确认cpu核数为数字！");
                                }
                                if(migrate_ram.val()!= "" && isNaN(migrate_ram.val())){
                                    bValid = false;
                                    alert("请确认内存大小为数字！");
                                }
                                if(bValid){
                                    $.ajax({
                                        type: "POST",
                                        url : "/g/user/rds/migrate/",
                                        data : {
                                            username:userid,
                                            instance_id:instance_id,
                                            ram:migrate_ram.val(),
                                            vcpus:migrate_vcpus.val(),
                                            disk:migrate_disk.val(),
                                            host:migrate_host.val(),
                                            backup_id:migrate_backup_id.val()
                                        },
                                        success : function(data) {
                                              var d1 = JSON.parse(data);
                                               if(d1.result != "success"){
                                                   alert(d1.result);
                                                   window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                                   return;
                                               }
                                               alert("操作成功!");
                                               window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                        },
                                        error : function(XMLHttpRequest,textStatus,errorThrown) {
                                            alert("migrate RDS失败!");
                                            alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                                            alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                                            alert("textStatus:"+textStatus);
                                        }
                                    });
                                }
                            },
                            "取消": function() {
                                $("#migrate_dialog").dialog( "close" );
                            }
                        }
                    });
                    $( "#migrate_dialog" ).dialog("open");
                }
                ///////////   migrate rds结束  ///////////


            ///////////   failover 开始  ///////////
            function failover(userid,instance_id){
            $("#failover_legend").html("failover " + instance_id);
             $( "#failover_dialog").dialog({
                autoOpen: false,
                height: 350,
                width: 700,
                modal: true,
                buttons: {
                    "提交": function() {
                            var c= confirm("确定要 failover"+instance_id+"吗?");
                            if(c== false){
                                return false;
                            }
                            $.ajax({
                                type: "POST",
                                url : "/g/user/rds/failover/",
                                data : {
                                    username:userid,
                                    instance_id:instance_id,
                                    force_host:$("#failover_force_host").val()
                                },
                                success : function(data) {
                                  var d1 = JSON.parse(data);
                                  //alert(d1.result);
                                  if(d1.result != "success"){
                                      alert(d1.result);
                                      return;
                                  }
                                  alert("操作成功!");
                                  window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                                },
                                error : function(XMLHttpRequest,textStatus,errorThrown) {
                                    alert("失败!");
                                    alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                                    alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                                    alert("textStatus:"+textStatus);
                                }
                            });
                    },
                    "取消": function() {
                        $("#failover_dialog").dialog( "close" );
                    }
                }
            });
            $( "#failover_dialog" ).dialog("open");
            }
            ///////////   failover 结束  ///////////


            ///////////   removeInstance 开始  ///////////
            function removeInstance(userid,instance_id){
                    var c= confirm("确定要删除"+instance_id+"吗?");
                    if(c== false){
                        return false;
                    }
                    $.ajax({
                        type: "POST",
                        url : "/g/user/rds/removeInstance/",
                        data : {
                            username:userid,
                            instance_id:instance_id
                        },
                        success : function(data) {
                            var d1 = JSON.parse(data);
                            //alert(d1.result);
                            if(d1.result != "success"){
                                alert(d1.result);
                                return;
                            }
                            alert("操作成功!");
                            window.location.href="/g/user/rdslist/?user_id=<%=userId %>&instance_id=<%=instance_id %>";
                        },
                        error : function(XMLHttpRequest,textStatus,errorThrown) {
                            alert("失败!");
                            alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
                            alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
                            alert("textStatus:"+textStatus);
                        }
                    });
            }
            ///////////   removeInstance 结束  ///////////
</script>
</body>
</html>