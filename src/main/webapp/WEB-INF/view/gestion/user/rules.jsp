<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:80%; padding: .4em; }
		select {margin-bottom:12px; width:80%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
        ul#icons {margin: 0; padding: 0;}
        ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
        ul#icons span.ui-icon {float: left; margin: 0 4px;}
	</style>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>安全组规则列表</title>
   	<script language="javascript">
   	var j = jQuery.noConflict(true);
    $(function(){
        $("li").hover(function(){
            $(this).addClass("ui-state-hover");
        },function(){
            $(this).removeClass("ui-state-hover");
        });
    });
   	//全选操作
	function selectAll(checked) {
		var sgs = $("input[name='rule_list']");
		if (sgs == 0) {
			return;
		}
		if (checked) {
			for ( var idx = 0; idx < sgs.length; idx++) {
				sgs[idx].checked = true;
			}
		} else {
			for ( var idx = 0; idx < sgs.length; idx++) {
				sgs[idx].checked = false;
			}
		}
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
		
   	function checkcontent( tips, o, n) {
		if ( o.val() != "tcp" && o.val() != "udp" && o.val() != "icmp") {
			alert( n + " 只能在 tcp,udp,icmp中选其一!!!" );
			o.addClass( "ui-state-error" );
			updateTips( tips, n + " 只能在 tcp,udp,icmp中选其一!!!" );
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
  	function addrule(sgId,tenantId, userId){
  		//创建虚拟机窗口
	   	$( "#addrule_form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 400,
			modal: true,
			buttons: {
				"创建": function() {
					var bValid = true;
					var create_protocal = $( "#create_protocal" );
					var create_from_port = $( "#create_from_port" );
					var create_to_port = $( "#create_to_port" );
					var create_cidr = $( "#create_cidr" );
					allFields = $( [] ).add( create_protocal ).add( create_from_port ).add( create_to_port );
					tips = $( ".validateTips" );
					
					allFields.removeClass( "ui-state-error" );
					bValid = bValid && checknull( tips, create_protocal, "protocal") && checkcontent(tips, create_protocal, "protocal");
					bValid = bValid && checknull( tips, create_from_port, "from_port");
					bValid = bValid && checknull( tips, create_to_port, "to_port");
					if(bValid){
						$.ajax({
							type: "POST",
							url : "/g/user/createrule/"+sgId+"/"+tenantId+"/"+userId,
							data : {protocal:create_protocal.val(),from_port:create_from_port.val(),to_port:create_to_port.val(),cidr:create_cidr.val()},
							success : function(data) {
								if(data == "failed"){
									alert("创建安全组规则失败");
									return;
								}else{
									alert("创建安全组规则成功");
								}
								window.location.href="/g/user/security_groups/rules/"+sgId+"/"+tenantId+"/"+userId;
							},
							error : function(XMLHttpRequest,textStatus,errorThrown) {
								alert("创建安全组规则失败!");
								alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
								alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
								alert("textStatus:"+textStatus);
							}
						});
					
					$(this).dialog( "close" );
					}
				},
				"取消": function() {
					$(this).dialog( "close" );
				}
			},
			close: function() {
			}
		});
   		$( "#addrule_form" ).dialog("open");
   	}
   	function delrule(ruleId,tenantId, userId){
  		$.ajax({
			type: "POST",
			url : "/g/user/deleterule/"+ruleId+"/"+tenantId+"/"+userId,
			success : function(data) {
				if(data == "failed"){
					alert("删除安全组规则失败");
					return;
				}else{
					alert("删除安全组规则成功");
				}
                window.location.reload();
			},
			error : function(XMLHttpRequest,textStatus,errorThrown) {
				alert("删除安全组规则失败!");
				alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
				alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
				alert("textStatus:"+textStatus);
			}
		});
   	}
 	</script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/user/list/1">用户信息</a><span>&gt;</span>rule列表</div>
<div class="main-cont">
    <h3 class="title">安全组规则列表
    </h3>

    <div class="set-area">
        <div>安全组规则列表<ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
            <li class="ui-state-default ui-corner-all" onclick="window.location.reload();">
                <span class="ui-icon ui-icon-refresh"></span>
            </li>
            <li class="ui-state-default ui-corner-all" onclick="addrule('${sgid}','${tenantid}','${userid}');">
                <span class="ui-icon ui-icon-circle-plus"></span>
            </li>
        </ul></div>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
            	<!-- <th width="10%">
            		<div class="th-gap"><input name="vm_list_all" type="checkbox" onclick="selectAll(this.checked)"/></div>
            	</th>
 -->                <th width="10%">
                    <div class="th-gap">id</div>
                </th>
                <th width="10%">
                    <div class="th-gap">fromPort</div>
                </th>	
                <th width="10%">
                    <div class="th-gap">ipProtocol</div>
                </th>
                <th width="10%">
                    <div class="th-gap">toPort</div>
                </th>
                <th width="10%">
                    <div class="th-gap">parentGroupId</div>
                </th>
                 <th width="20%">
                    <div class="th-gap">ipRange</div>
                </th>
                 <th width="10%">
                    <div class="th-gap">删除</div>
                </th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="rule" items="${dto.rules}" varStatus="status">
                <tr>
                    <%-- <td><input type="checkbox" name="rule_list" id="${rule.id}" value="${rule.id}"/></td> --%>
                    <td>${rule.id}</td>
                    <td>${rule.from_port}</td>
                    <td>${rule.ip_protocol}</td>
                    <td>${rule.to_port}</td>
                    <td>${rule.parent_group_id}</td>
                    <td>${rule.ip_range.cidr}</td>
                    <td><button onclick="delrule(${rule.id},'${tenantid}','${userid}')">删除</button></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="addrule_form" title="新建规则" style="display: none">
	<p>以下均必填</p>
	<form>
		<fieldset>
			<label for="create_protocal">protocal</label>
			<input type="text" name="protocal" id="create_protocal" placeholder="tcp/udp/icmp" class="text ui-widget-content ui-corner-all" />
			<label for="create_from_port">from_port</label>
			<input type="text" name="from_port" id="create_from_port" placeholder="10" class="text ui-widget-content ui-corner-all" />
			<label for="create_to_port">to_port</label>
			<input type="text" name="to_port" id="create_to_port" placeholder="10" class="text ui-widget-content ui-corner-all" />
			<label for="create_cidr">cidr</label>
			<input type="text" name="cidr" id="create_cidr" placeholder="0.0.0.0/0" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
</div>
</body>
</html>