<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
    <%@include file="../inc/meta.jspf"%>
    <%
		String tenantId = (String)request.getAttribute("tenantid");
		String userId = (String)request.getAttribute("userid");
 	%>
    <title>VM - VM列表</title>
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
			if (vms == 0) {
				return;
			}
			if (checked) {
				for ( var idx = 0; idx < vms.length; idx++) {
					vms[idx].checked = true;
				}
			} else {
				for ( var idx = 0; idx < vms.length; idx++) {
					vms[idx].checked = false;
				}
			}
		}
				///////////   编辑vm开始  ///////////
		function editvm(){
			//判断虚拟机选中情况
			var vms = $("input[name='vm_list']");
			if(vms.length==0){
				alert("目前没有虚拟机可供操作，请先添加虚拟机");
				return;
			}
			var count=0;
			var vmids = "";
			for(var idx=0; idx<vms.length; idx++){
				if(vms[idx].checked == true){
					++count;
					vmids += vms[idx].id+",";
				}
			}
			if(count==0){
				alert("编辑前至少选择一个虚拟机进行操作");
				return;
			}
			vmids = vmids.substring(0, vmids.lastIndexOf(","));
			//编辑窗口
	   		$( "#editvm_form").dialog({
				autoOpen: false,
				postion: "center",
				modal: true,
				height:"250",
				width:"200",
				buttons: {
					"确认": function() {
						var val=$('input:radio[name="edit"]:checked').val();
						var action = "";
						if(val == "restart"){
							action = "restart";
						}else if(val == "stop"){
							action = "stop";
						}else if(val == "start"){
							action = "start";
						}else if(val == "terminal"){
							action = "terminal";
						}
						$.ajax({
							type: "POST",
							url : "/g/user/edit_vm/"+action+"/<%=tenantId %>/<%=userId %>",
							data : {"vmids":vmids},
							success : function(data) {
                                if(data=="true"){
                                    alert(action+"虚拟机成功，因机器情况各异，可能需要稍等片刻!");
                                    window.location.href="/g/user/vmlist/<%=tenantId %>/<%=userId %>";
                                }else{
                                    alert(action+"虚拟机失败!");
                                }
							},
							error : function(XMLHttpRequest,textStatus,errorThrown) {
								alert(action+"虚拟机失败!");
								alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
								alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
								alert("textStatus:"+textStatus);
							}
						});
						$(this).dialog( "close" );
					},
					"取消": function() {
						$(this).dialog( "close" );
					}	
				},
				close : function() {
				}
			});
			$( "#editvm_form" ).dialog("open");
	   	}
	   	///////////   编辑vm结束  ///////////
	   	
	   	///////////   vm详情开始  ///////////
		function detail(id){
	  		$( "#vm_dialog_"+id ).dialog({
				autoOpen: false,
				postion: "center",
				modal: true,
				height:"700",
				width:"900",
				show: {
					effect: "blind",
					duration: 200
					
				},
				hide: {
					effect: "explode",
					duration: 1000
				}
			});
			$( "#vm_dialog_"+id ).dialog("open");
	   	}

        function reset_system(vm_id){
            $.ajax({
                url : "/g/user/image_public_id_list/<%=tenantId %>/<%=userId %>",
                dataType : 'text',
                success : function(data) {
                    var imageJson = eval('('+data+')');
                    for(i=0;i<imageJson.length;i++){
                        $("#reset_image").append("<option value='"+imageJson[i].id+"'>"+imageJson[i].name+"</option>");
                    }
                }
            });
            $( "#reset_system").dialog({
                autoOpen: false,
                postion: "center",
                modal: true,
                height:"300",
                width:"350",
                show: {
                    effect: "blind",
                    duration: 200

                },
                hide: {
                    effect: "explode",
                    duration: 1000
                },
                buttons:{
                    "重装":function (){
                        var pass = $("#reset_password").val();
                        var image_id=$("#reset_image").val();
                        if(pass==''){
                            alert("请输入重装后的登陆密码!");
                            return;
                        }
                        $.ajax({
                           url:'/g/user/vm/reset/<%=tenantId %>/<%=userId %>',
                           data:{
                               vm_id:vm_id,
                               password:pass,
                               image_id:image_id
                           },
                           dataType:'text',
                           success:function(data){
                                alert(data);
                               $( "#reset_system").dialog("close");
                           }
                        });
                    }
                }
            });
            $( "#reset_system").dialog("open");
        }
   		///////////   vm详情结束  ///////////
   		///////////   创建vm开始  ///////////
	   	function addvm(tenantid,userid){
	   		//获得系统镜像列表
	   		$.ajax({
				url : "/g/user/image_public_id_list/"+tenantid+"/"+userid,
				dataType : 'text',
				success : function(data) {
					var imageJson = eval('('+data+')');
					for(i=0;i<imageJson.length;i++){
						$("#create_imageRef").append("<option value='"+imageJson[i].id+"'>"+imageJson[i].name+"</option>");
					}
				}
			});
			//获得zone列表
			$.ajax({
				url : "/g/zonelist/ajax",
				dataType : 'text',
				success : function(data) {
					var zoneJson = eval('('+data+')');
					for(i=0;i<zoneJson.length;i++){
						$("#create_zone").append("<option value='"+zoneJson[i].name+"'>"+zoneJson[i].name+"</option>");
					}
				}
			});
			//获得用户系统盘镜像列表
			$.ajax({
				url : "/g/user/sys_image_id_list/"+tenantid+"/"+userid,
				dataType : 'text',
				success : function(data) {
					var imageJson = eval('('+data+')');
					for(i=0;i<imageJson.length;i++){
						$("#create_imageRef_self").append("<option value='"+imageJson[i].id+"'>"+imageJson[i].name+"</option>");
					}
				}
			});
			
			$.ajax({
				url : "/g/user/security_groups/ajax/"+tenantid+"/"+userid,
				dataType : 'text',
				success : function(data) {
					var sgJson = eval('('+data+')');
					for(i=0;i<sgJson.length;i++){
						$("#create_security_groups").append("<option value='"+sgJson[i].name+"'>"+sgJson[i].name+"</option>");
					}
				}
			});
			
			//创建虚拟机窗口
		   	$( "#addvm_form" ).dialog({
				autoOpen: false,
				height: 550,
				width: 700,
				modal: true,
				buttons: {
					"创建": function() {
						var bValid = true;
						var create_name = $( "#create_name" ),
						create_imageRef = $( "#create_imageRef" ),
						create_imageRef_self = $( "#create_imageRef_self" ),
						create_count = $( "#create_count" ),
						create_security_groups = $( "#create_security_groups" ),
						create_adminPass = $( "#create_adminPass" ),
						create_vcpu = $( "#create_vcpu" ),
						create_zone = $( "#create_zone" ),
						create_network = $( "#create_network" ),
						create_on_ebs=$('input:radio[name="is_ebs"]:checked');
						create_root_disk = $( "#create_root_disk" ),
						create_ram = $( "#create_ram" ),
					//	allFields = $( [] ).add( create_name ).add( create_imageRef ).add( create_count ).add( create_security_groups ).add( create_adminPass ),
						tips = $( ".validateTips" );
						
					//	allFields.removeClass( "ui-state-error" );
						bValid = bValid && checknull( tips, create_name, "name");
						bValid = bValid && checknull( tips, create_count, "count");
						bValid = bValid && checknull( tips, create_security_groups, "security_groups");
						bValid = bValid && checknull( tips, create_adminPass, "adminPass");
						bValid = bValid && checknull( tips, create_on_ebs, "create_on_ebs");
						bValid = bValid && checknull( tips, create_vcpu, "vcpu");
						bValid = bValid && checknull( tips, create_network, "network");
						bValid = bValid && checknull( tips, create_root_disk, "root_disk");
						bValid = bValid && checknull( tips, create_ram, "ram");
						
						var image = null;
						if(create_imageRef.val() == null || create_imageRef.val() == ""){
							image = create_imageRef_self.val();
						}else if(create_imageRef_self.val() == null || create_imageRef_self.val() == ""){
							image = create_imageRef.val();
						}
						if(image == null || image==""){
							alert("系统镜像与自有镜像必需选择其一!");
							return;
						}
						if(bValid){
							$.ajax({
								type: "POST",
								url : "/g/user/createvm/"+tenantid+"/"+userid,
								data : {name:create_name.val(),
										imageRef:image,
										count:create_count.val(),
										security_groups:create_security_groups.val(),
										vcpu:create_vcpu.val(),
										network:create_network.val(),
										create_on_ebs:create_on_ebs.val(),
										create_zone:create_zone.val(),
										root_disk:create_root_disk.val(),
										ram:create_ram.val(),
										adminPass:create_adminPass.val()},
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
	   	
	   	//////////   创建系统快照 ////////
	   	function createsnapshot(tenantid,userid,vmid){
	   		$( "#add_snapshot" ).dialog({
	   			autoOpen: false,
				height: 550,
				width: 700,
				modal: true,
				buttons: {
					"创建": function() {
						$.ajax({
								type: "GET",
								url : "/g/user/createsnapshot/"+tenantid+"/"+userid+"/"+vmid+"/"+$("#snapshot_name").val(),
								success : function(data) {
									if(data == "failed"){
										alert("创建系统快照失败!");
										return;
									}else {
										alert("创建系统快照成功!");
									}
								},
								error : function(XMLHttpRequest,textStatus,errorThrown) {
									alert("创建系统快照失败!");
									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
									alert("textStatus:"+textStatus);
								}
							});
							$("#add_snapshot").dialog( "close" );
					},
					"取消": function() {
						$("#add_snapshot").dialog( "close" );
					}
				},
				close: function() {
				}
	   		});
	   		$( "#add_snapshot" ).dialog("open");
	   	}
	   	
	   	$(function(){
            $("li").hover(function(){
                $(this).addClass("ui-state-hover");
            },function(){
                $(this).removeClass("ui-state-hover");
            });
	   		$("#withSysImage").change(function(){
	   			$("#sysImage").show();
	   			$("#selfImage").hide();
	   		});
	   		$("#withSelfImage").change(function(){
	   			$("#sysImage").hide();
	   			$("#selfImage").show();
	   		});
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
	   	function ebslist(tenantid,vmid){
	   		window.location.href="/g/vm_ebs/"+tenantid+"/${userid}/"+vmid;
	   	}
	   	function setEBS(tenantid,vmid){
	   		$("#setebs_dialog").dialog({
	   			autoOpen: false,
				height: 300,
				width: 400,
				modal: true,
				buttons: {
					"关联": function() {
						if($("#device").val()==""||$("#device").val()==null){
							alert("device can't be null!");
							return false;
						}
						$.ajax({
							url:"/g/user/setebs/${tenantid}/${userid}",
							data:{vmid:vmid,ebsid:$("#setebs").val(),device:$("#device").val()},
							success:function(data){
								alert(data);
								window.location.href="/g/vm_ebs/"+tenantid+"/${userid}/"+vmid;
							}
						});
						$("#setebs_dialog").dialog( "close" );
					},
					"取消": function() {
						$("#setebs_dialog").dialog( "close" );
					}
				}
	   		});
	   		$.ajax({
	   			url:"/g/user/ebslist/${tenantid}/${userid}",
	   			dataType:'json',
	   			success:function(data){
	   				$.each(data,function(index,val){
                        if(val.status!="in-use"){
	   					    $("#setebs").append("<option value='"+val.id+"'>"+val.name+"</option>");
                        }
	   				});
	   			}
	   		});
	   		$("#setebs_dialog").dialog("open");
	   	}
 	</script>
</head>

<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/user/list/1">用户信息</a><span>&gt;</span>vm列表</p></div>
<div class="main-cont">
    <h3 class="title">vm列表</h3>
    <div class="set-area">
        <div>vm列表，vm数量：<c:out value="${fn:length(vmlist)}"></c:out>
        <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
            <li class="ui-state-default ui-corner-all" onclick="window.location.reload();">
                <span class="ui-icon ui-icon-refresh"></span>
            </li>
            <li class="ui-state-default ui-corner-all" onclick="editvm();">
                <span class="ui-icon ui-icon-tag"></span>
            </li>
            <li class="ui-state-default ui-corner-all" onclick="addvm('<%=tenantId %>','<%=userId %>');">
                <span class="ui-icon ui-icon-circle-plus"></span>
            </li>
        </ul>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
            	<th width="7%">
            		<div class="th-gap"><input name="vm_list_all" type="checkbox" onclick="selectAll(this.checked)"/></div>
            	</th>
                <th width="12%">
                    <div class="th-gap">虚拟机id</div>
                </th>
                <th width="8%">
                    <div class="th-gap">虚拟机状态</div>
                </th>
                <th width="12%">
                    <div class="th-gap">name</div>
                </th>
                <th width="12%">
                    <div class="th-gap">所属物理机</div>
                </th>
                 <th width="8%">
                    <div class="th-gap">详情</div>
                </th>
                 <th >
                    <div class="th-gap">创建系统快照</div>
                </th>
                <th width="12%">
                    <div class="th-gap">操作ebs</div>
                </th>
                <th width="5%">
                    <div class="th-gap">迁移</div>
                </th>
                <th width="5%">
                    <div class="th-gap">重装系统</div>
                </th>
                <th width="12%">
                    <div class="th-gap">状态查看</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="vm" items="${vmlist}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="vm_list" id="${vm.id}" value="${vm.id}"/></td>
                        <td>${vm.id} </td>
                        <td>${vm.status} </td>
                        <td>${vm.name} </td>
                        <td>${vm.host} </td>
						<td><button onclick="detail('${vm.id}')">详情</button></td>
						<td><button onclick="createsnapshot('<%=tenantId %>','<%=userId %>','${vm.id}')">创建</button></td>
						<td><button onclick="ebslist('${vm.tenant_id}','${vm.id}')">查看ebs列表</button><button onclick="setEBS('${vm.tenant_id}','${vm.id}')">关联ebs</button></td>
						<td><button onclick="detail('${vm.id}')">迁移</button></td>
                        <td><button onclick="reset_system('${vm.id}')">重装</button></td>
						<td><button onclick="chart_load('${vm.id}')">查看cpu及内存</button>
                            <button onclick="chart_network('${vm.id}')">查看网络</button>
                            <button onclick="chart_status('${vm.id}')">查看状态</button>
                            <button onclick="chart_disk('${vm.id}')">查看硬盘</button>
                            <button onclick="vnc('${vm.id}')">登陆机器</button>
                        </td>
                        <div id="vm_dialog_${vm.id}" title="vm_${vm.id}详情" style="display:none">
                            <p>status: ${vm.status}</p>
                            <p>updated: ${vm.updated}</p>
                            <p>host: ${vm.host}</p>
                            <p>task_state: ${vm.task_state}</p>
                            <p>vm_state: ${vm.vm_status}</p>
                            <p>instance_name: ${vm.instance_name}</p>
                            <p>hypervisor_hostname: ${vm.hypervisor_hostname}</p>
                            <p>id: ${vm.id}</p>
                            <p>image_id:${vm.image_id}</p>
                            <p>user_id: ${vm.user_id}</p>
                            <p>name: ${vm.name}</p>
                            <p>created: ${vm.created}</p>
                            <p>tenant_id: ${vm.tenant_id}</p>
                            <p>public_ip_address: </p>
                            <c:forEach var="public_address" items="${vm.addresses.pubaddress}" varStatus="status">
                                <p>&nbsp;&nbsp;version: ${public_address.version}</p>
                                <p>&nbsp;&nbsp;address: ${public_address.addr}</p>
                            </c:forEach>
                            <p>private_ip_address: </p>
                            <c:forEach var="private_address" items="${vm.addresses.priaddress}" varStatus="status">
                                <p>&nbsp;&nbsp;version: ${private_address.version}</p>
                                <p>&nbsp;&nbsp;address: ${private_address.addr}</p>
                            </c:forEach>
                            <p>progress: ${vm.progress}</p>
                            <p>OS-EXT-STS:power_state: ${vm.power_state}</p>
                            <p>security_groups: </p>
                            <c:forEach var="security_group" items="${vm.security_groups}" varStatus="status">
                                <p>&nbsp;&nbsp;name: ${security_group.name}</p>
                            </c:forEach>
                            <p>&nbsp;&nbsp;instance_type:</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;ram:${vm.instance_type.ram}</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;cpu:${vm.instance_type.cpu}</p>
                            <p>&nbsp;&nbsp;metadata: </p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;storage_location: ${vm.metadata.storage_location}</p>
						</div>
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
<div id="addvm_form" title="创建虚拟机" style="display: none">
	<p class="validateTips"><b style="color:red">下列所有值均必填</b></p>
	<form>
		<fieldset>
			<label for="create_name">name<b>(必填)</b></label>
			<input type="text" name="name" id="create_name" value="None" class="text ui-widget-content ui-corner-all" />
			<label for="withSysImage">请选择创建虚机的镜像类型<b>(必选)</b></label>
			<div>
			<span><input type="radio" name="imageType" value="sys" id="withSysImage" style="float:left;"/><span style="float:left;">系统镜像</span></span>
			<span><input type="radio" name="imageType" value="self" id="withSelfImage" style="float:left;margin-left:20px;"/>自有镜像</span>
			</div>
			<div id="sysImage" style="display: none">
			<label for="create_imageRef">系统镜像<b>(必选)</b></label>
			<select name="imageRef" id="create_imageRef" class="text ui-widget-content ui-corner-all" >
			<option value="">请选择镜像</option>
			</select>
			</div>
			<div id="selfImage" style="display: none">
			<label for="create_imageRef_self">自定义镜像<b>(必选)</b></label>
			<select name="imageRefSelf" id="create_imageRef_self" class="text ui-widget-content ui-corner-all" >
			<option value="">请选择镜像</option>
			</select>
			</div>
			<label for="create_zone">指定创建于该zone<b>(可选)</b></label>
			<select name="zone" id="create_zone" class="text ui-widget-content ui-corner-all" >
			<option value="">请选zone</option>
			</select>
			<label for="create_vcpu">cpu核数<b>(只需填入数字，如1，必填)</b></label>
			<input type="text" name="vcpu" id="create_vcpu" value="1" class="text ui-widget-content ui-corner-all" />
			<label for="create_network">带宽<b>(只需填入数字，单位：MB，如5或20，必填)</b></label>
			<input type="text" name="network" id="create_network" value="10" class="text ui-widget-content ui-corner-all" />
			<label for="create_root_disk">系统盘大小<b>(必填,单位:<input type="text" name="root_disk" id="create_root_disk" style="width:28px;border:0;color: #f6931f; font-weight: bold;display: inline-block;" />GB)</b></label>
            <div id="slider-range-min" style="width:81%;"></div>
			<!--<input type="text" name="root_disk" id="create_root_disk" value="" class="text ui-widget-content ui-corner-all" />-->
			<label for="create_ram">内存大小<b>(只需填入数字，单位：M，如512或者2048，必填)</b></label>
			<input type="text" name="ram" id="create_ram" value="512" class="text ui-widget-content ui-corner-all" />
			<label for="create_ebs">系统盘是否在ebs上创建<b>(必选)</b></label>
			<div>
			<span><input type="radio" name="is_ebs" value="True" id="create_ebs" style="float:left;"/><span style="float:left;">是</span></span>
			<span><input type="radio" name="is_ebs" value="False" id="create_not_ebs" style="float:left;margin-left:20px;"/>否</span>
			</div>
			<label for="create_count">虚拟机数量<b>(必填)</b></label>
			<input type="text" name="count" id="create_count" value="1" class="text ui-widget-content ui-corner-all" />
			<label for="create_adminPass">初始密码<b>(必填)</b></label>
			<input type="text" name="adminPass" id="create_adminPass" value="" class="text ui-widget-content ui-corner-all" />
			<label for="create_security_groups">security_groups<b>(必选)</b></label>
			<select multiple="multiple" name="security_groups" id="create_security_groups" class="text ui-widget-content ui-corner-all" >
			</select>
		</fieldset>
	</form>
</div>
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