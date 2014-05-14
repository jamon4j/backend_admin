<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
    <%@include file="../inc/meta.jspf"%>
    <%
		String userId = (String)request.getAttribute("userid");
 	%>
    <title>RDS - RDS列表</title>
	<style>
			label, input { display:block; }
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
        function chart_load(id){
            window.location.href="/g/chart/load/"+id;
        }
        function chart_disk(id){
            window.location.href="/g/chart/disk/"+id;
        }
        function chart_network(id){
            window.location.href="/g/chart/network/"+id;
        }
        function chart_status(id){
            window.location.href="/g/chart/status/"+id;
        }
        function vnc(instanceId){
            $("#vncDialog").dialog({
                autoOpen: false,
                postion: "center",
                height:"450",
                width:"550",
                modal: true
            });
            $.ajax({
                url:"/g/vnc/"+instanceId,
                dataType:"json",
                success:function(data){
                    window.open(data.url,instanceId,'height=600,width=800');
                }
            })
        }
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
		//全选操作
		function selectAll(checked) {
			var vms = $("input[name='vm_list']");
			if (rdss == 0) {
				return;
			}
			if (checked) {
				for ( var idx = 0; idx < rdss.length; idx++) {
					vms[idx].checked = true;
				}
			} else {
				for ( var idx = 0; idx < vms.length; idx++) {
					vms[idx].checked = false;
				}
			}
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
            function security(id){
                $( "#security_"+id ).dialog({
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
                $( "#security_"+id ).dialog("open");
            }
    ///////////   security结束  ///////////

    ///////////   backup开始  ///////////
            function backup(id){
                $( "#backup_"+id ).dialog({
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
                $( "#backup_"+id ).dialog("open");
            }
    ///////////   backup结束  ///////////

    ///////////   backup_config开始  ///////////
            function backup_config(id){
                $( "#backup_config_"+id ).dialog({
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
                $( "#backup_config_"+id ).dialog("open");
            }
    ///////////   backup_config结束  ///////////

 	</script>
</head>

<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/user/list/1">用户信息</a><span>&gt;</span>rds列表</p></div>
<div class="main-cont">
    <h3 class="title">rds列表</h3>
    <div class="set-area">
        <div>rds列表，rds数量：<c:out value="${fn:length(rdslist.rdsInstances)}"></c:out>
        <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
            <li class="ui-state-default ui-corner-all" onclick="window.location.reload();">
                <span class="ui-icon ui-icon-refresh"></span>
            </li>
            <li class="ui-state-default ui-corner-all" onclick="editrds();">
                <span class="ui-icon ui-icon-tag"></span>
            </li>
            <li class="ui-state-default ui-corner-all" onclick="addrds('<%=userId %>');">
                <span class="ui-icon ui-icon-circle-plus"></span>
            </li>
        </ul>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
            	<th width="3%">
            		<div class="th-gap"><input name="vm_list_all" type="checkbox" onclick="selectAll(this.checked)"/></div>
            	</th>
                <th width="5%">
                    <div class="th-gap">实例名称</div>
                </th>
                <th width="12%">
                    <div class="th-gap">实例ID</div>
                </th>
                <th width="12%">
                    <div class="th-gap">租户ID</div>
                </th>
                <th width="5%">
                    <div class="th-gap">实例类型</div>
                </th>
                 <th width="5%">
                    <div class="th-gap">实例状态</div>
                </th>
                 <th width="12%">
                    <div class="th-gap">复制组ID</div>
                </th>
                <th width="12%">
                    <div class="th-gap">虚拟机ID</div>
                </th>
                <th width="5%">
                    <div class="th-gap">虚拟机IP</div>
                </th>
                <th width="5%">
                    <div class="th-gap">虚拟机状态</div>
                </th>
                <th width="5%">
                    <div class="th-gap">VIP</div>
                </th>
                <th width="8%">
                    <div class="th-gap">安全组信息</div>
                </th>
                <th width="8%">
                    <div class="th-gap">安全组规则信息</div>
                </th>
                <th width="8%">
                    <div class="th-gap">备份列表</div>
                </th>
                 <th width="8%">
                     <div class="th-gap">备份配置</div>
                 </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="rds" items="${rdslist.rdsInstances}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="rds_list" id="${rds.id}" value="${rds.id}"/></td>
                        <td>${rds.name} </td>
                        <td>${rds.id} </td>
                        <td>${rdslist.vmValidationPo.userName}</td>
                        <td>${rds.type} </td>
                        <td>${rds.status} </td>
                        <td>${rds.group} </td>
                        <td>虚拟机ID</td>
                        <td><c:forEach var="i" items="${rds.ip}" varStatus="status">
                            <p>ip: ${i}</p>
                        </c:forEach></td>
                        <td>虚拟机状态</td>
                        <td>${rds.vip} </td>
						<td><button class="ui-state-default ui-corner-all" onclick="security('${rds.rDSInstanceSecGroup.id}')">安全组信息</button>
						    <div id="security_${rds.rDSInstanceSecGroup.id}" title="security_${rds.rDSInstanceSecGroup.id}详情" style="display:none">
                                <p>id:${rds.rDSInstanceSecGroup.id}</p>
                                <p>名称:${rds.rDSInstanceSecGroup.name}</p>
                                <c:forEach var="link" items="${rds.rDSInstanceSecGroup.links}" varStatus="status">
                                    <p>&nbsp;&nbsp;href: ${link.href}</p>
                                    <p>&nbsp;&nbsp;rel: ${link.rel}</p>
                                </c:forEach>
                                <p>描述:${rds.rDSInstanceSecGroup.description}</p>
                                <p>创建时间:${rds.rDSInstanceSecGroup.created}</p>
                                <p>更新时间:${rds.rDSInstanceSecGroup.updated}</p>
                            </div>
						</td>
						<td><button class="ui-state-default ui-corner-all" onclick="securityDetail('${rds.rDSInstanceSecGroup.id}')">安全组信息详情</button>
                            <div id="securityDetail_${rds.rDSInstanceSecGroup.id}" title="securityDetail_${rds.rDSInstanceSecGroup.id}详情" style="display:none">
                                <c:forEach var="rule" items="${rds.rDSInstanceSecGroup.rules}" varStatus="status">
                                <p>id: ${rule.id}</p>
                                <p>to_port: ${rule.to_port}</p>
                                <p>cidr: ${rule.cidr}</p>
                                <p>from_port: ${rule.from_port}</p>
                                <p>protocol: ${rule.protocol}</p>
                                </c:forEach>
                            </div>
						</td>
						<td><button class="ui-state-default ui-corner-all" onclick="backup('${rds.id}')">备份列表</button>
                            <div id="backup_${rds.id}" title="backup_${rds.id}详情" style="display:none">
                                <c:forEach var="backup" items="${rds.backups}" varStatus="status">
                                <p>id: ${backup.id}</p>
                                <p>name: ${backup.name}</p>
                                <p>status: ${backup.status}</p>
                                <p>locationRef: ${backup.locationRef}</p>
                                <p>size: ${backup.size}</p>
                                <p>type: ${backup.type}</p>
                                <p>instance_id: ${backup.instance_id}</p>
                                <p>实例名称: 实例名称</p>
                                <p>group_id: ${backup.group_id}</p>
                                </c:forEach>
                            </div>
                        </td>
                        <td><button class="ui-state-default ui-corner-all" onclick="backup_config('${rds.id}')">备份配置</button>
                            <div id="backup_config_${rds.id}" title="backup_config_${rds.id}详情" style="display:none">
                                <p>duration: ${rds.backup_config.duration}</p>
                                <p>autobackup_at: ${rds.backup_config.autobackup_at}</p>
                                <p>expire_after: ${rds.backup_config.expire_after}</p>
                            </div>
                        </td>
					</tr>
				</c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="editvm_form" title="请选择下列操作" style="display: none">
<form>
		<div><input type="radio" name="edit" value="terminal" id="edit_terminal" style="float:left;"/>&nbsp;&nbsp;删除虚拟机</div>
		<div><input type="radio" name="edit" value="restart" id="edit_restart" style="float:left;"/>&nbsp;&nbsp;重启虚拟机</div>
		<!--<div><input type="radio" name="edit" value="pause" id="edit_pause" style="float:left;"/>&nbsp;&nbsp;暂停虚拟机</div>
		<div><input type="radio" name="edit" value="suspend" id="edit_suspend" style="float:left;"/>&nbsp;&nbsp;挂起虚拟机</div>
		<div><input type="radio" name="edit" value="resume" id="edit_resume" style="float:left;"/>&nbsp;&nbsp;恢复虚拟机</div>-->
		<div><input type="radio" name="edit" value="stop" id="edit_stop" style="float:left;"/>&nbsp;&nbsp;停止虚拟机</div>
		<div><input type="radio" name="edit" value="start" id="edit_start" style="float:left;"/>&nbsp;&nbsp;启动虚拟机</div>
</form>
</div>
<div id="add_snapshot" title="请输入快照名称" style="display: none">
	<p class="validateTips">所有字段均必填</p>
	<form>
		<fieldset>
			<label for="snapshot_name">快照名称(必填)</label>
			<input type="text" name="name" id="snapshot_name" value="" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
</div>
<div id="update_brand" title="更新带宽" style="display: none">
	<table align="left">
		<tr>
			<td>虚拟机ID:</td>
			<td><span id="brand_vm_id"></span></td>
			<td></td>
		</tr>
	</table>
	<table>
		<tr>
			<td>当前带宽:</td>
			<td><span id="now_brand_value"></span></td>
			<td></td>
		</tr>
		<tr>
			<td>新带宽值:</td>
			<td><input id="new_brand" type="text" size="7"/></td>
			<td>M</td>
		</tr>
	</table>
</div>
<div id="reset_system" title="重置系统" style="display: none">
    <form>
        <fieldset>
            <label for="reset_image">镜像(不选择为重装当前系统)</label>
            <select name="imageRef" id="reset_image" class="text ui-widget-content ui-corner-all" >
                <option value="">请选择镜像</option>
            </select>
            <label for="reset_password">密码:</label>
            <input type="text" name="password" id="reset_password" class="text ui-widget-content ui-corner-all" ></input>
        </fieldset>
    </form>
</div>

<div id="addrds_form" title="创建RDS" style="display: none">
    <p class="validateTips"><b style="color:red"></b></p>
	<form>
		<fieldset>
			<p>名称<b>(必填)</b>：</p>
			<input type="text" name="create_name" id="create_name"  value="" class="text ui-widget-content ui-corner-all" />
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
            <input type="text" name="create_admin_user" id="create_admin_user" value="" class="text ui-widget-content ui-corner-all" />
           <p>超管密码<b>(必填)</b>：</p>
            <input type="password" name="create_admin_password" id="create_admin_password" value="" class="text ui-widget-content ui-corner-all" />
           <p>再次输入超管密码<b>(必填)</b>：</p>
           <input type="password" name="create_admin_password2" id="create_admin_password2" value="" class="text ui-widget-content ui-corner-all" />
            <p>数据库端口<b>(必填)</b>：</p>
            <input type="text" name="create_port" id="create_port" value="" class="text ui-widget-content ui-corner-all" />
            <p>cpu核数</p>
            <select name="create_vcpu" id="create_vcpu" class="text ui-widget-content ui-corner-all" >
               <option value="1">1</option>
               <option value="2">2</option>
               <option value="4">4</option>
               <option value="8">8</option>
            </select>
            <label for="create_root_disk">系统盘大小<b>(必填,单位:<input type="text" name="create_root_disk" id="create_root_disk" style="width:28px;border:0;color: #f6931f; font-weight: bold;display: inline-block;" />GB)</b></label>
            <div id="slider-range-min" style="width:81%;"></div>
            <p>内存大小：</p>
            <select name="create_ram" id="create_ram" class="text ui-widget-content ui-corner-all" >
               <option value="512">512</option>
               <option value="2048">2048</option>
            </select>
		</fieldset>
	</form>
</div>

<script>
       		///////////   创建rds开始  ///////////
    	   	function addrds(userid){

    			//创建虚拟机窗口
    		   	$( "#addrds_form" ).dialog({
    				autoOpen: false,
    				height: 550,
    				width: 700,
    				modal: true,
    				buttons: {
    					"创建": function() {
    						var bValid = true;
    						var create_name = $( "#create_name" ),
    						create_type = $( "#create_type" ),
    						create_service_type = $( "#create_service_type" ),
    						create_admin_user = $( "#create_admin_user" ),
    						create_admin_password = $( "#create_admin_password" ),
    						create_admin_password2 = $( "#create_admin_password2" ),
    						create_port = $( "#create_port" ),
    						create_vcpu = $( "#create_vcpu" ),
    						create_root_disk = $( "#create_root_disk" ),
    						create_ram=$('create_ram');
    					//	allFields = $( [] ).add( create_name ).add( create_imageRef ).add( create_count ).add( create_security_groups ).add( create_adminPass ),
    						tips = $( ".validateTips" );

    					//	allFields.removeClass( "ui-state-error" );
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
                            if(! (create_admin_password==create_admin_password2)){
                                bValid = false;
                            }
    						if(bValid){
    							$.ajax({
    								type: "POST",
    								url : "/g/user/createrds/"+userid,
    								data : {create_name:create_name.val(),
    										create_type:image,
    										create_service_type:create_count.val(),
    										create_admin_user:create_security_groups.val(),
    										create_admin_password:create_vcpu.val(),
    										create_admin_password2:create_network.val(),
    										create_port:create_on_ebs.val(),
    										create_vcpu:create_zone.val(),
    										create_root_disk:create_root_disk.val(),
    										create_ram:create_ram.val()},
    								success : function(data) {
    									if(data == "failed"){
    										alert("创建虚拟机失败!");
    										window.location.href="/g/user/vmlist/"+tenantid+"/"+userid;
    										return;
    									}
    									var jsonobj=eval('('+data+')');
    									window.location.href="/g/user/vmlist/"+tenantid+"/"+userid;
    								},
    								error : function(XMLHttpRequest,textStatus,errorThrown) {
    									alert("创建虚拟机失败!");
    									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
    									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
    									alert("textStatus:"+textStatus);
    								}
    							});

    						$("#addvm_form").dialog( "close" );
    						}
    					},
    					"取消": function() {
    						$("#addvm_form").dialog( "close" );
    					}
    				},
    				close: function() {
    					$("#create_imageRef").empty();
    					$("#create_imageRef").append("<option value=''>请选择镜像</option>");
    					$("#create_zone").empty();
    					$("#create_zone").append("<option value=''>请选择zone</option>");
    					$("#create_security_groups").empty();
    				}
    			});
    	   		$( "#addvm_form" ).dialog("open");
    	   	}
    	   	///////////   创建vm结束  ///////////
</script>

<div id="setebs_dialog" title="关联EBS" style="display:none;">
	<fieldset>
		<legend>setebs</legend>
		<select id="setebs" name="setebs">
		</select>
		<p>device:<input id="device" name="device"/></p>
	</fieldset>
</div>
</body>
</html>