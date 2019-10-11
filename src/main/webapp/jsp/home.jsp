<%@page import="com.eg.egsc.scp.simulator.common.DeviceType"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模拟设备生成器</title>
<link rel="stylesheet" type="text/css" href="bootstrap-4.1.1/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="jsoneditor/jsoneditor.css" />
<link rel="stylesheet" type="text/css" href="css/simulator.css" />
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="bootstrap-4.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ace.js"></script>
<script type="text/javascript" src="jsoneditor/jsoneditor.min.js"></script>
<script type="text/javascript" src="js/simulator.js"></script>



  <style type="text/css">
    #jsoneditor {
      width: 500px;
      height: 500px;
    }
    #jsoneditor2 {
      width: 500px;
      height: 500px;
    }

  </style>
</head>
<body>


	<div class="card" style="background-color: #182e67; padding:10px;">
		<div class="row">
			<div class="col-sm-8">
				<span class="sys-title-icon"><span class="badge badge-secondary" style="background-color: #f20000;">New</span></span>
				<span class="sys-title">设备模拟器</span> 
			</div>
		</div>
	</div>
	
	<div class="alert alert-primary" role="alert">
		<button id="chargeDevice" type="button" class="btn btn-primary">创建充电设备</button>
		<button id="urgencyStop" type="button" class="btn btn-primary">急停</button>
		<button id="requestQrCodeBtn" type="button" class="btn btn-primary">二次请求序列号</button>
	</div>
	<div style="margin: 0 auto; width:1200px;height:auto;">
	

	<form class="form" style="margin-top:15px;">
	<div class="form-inline">
		<div class="form-group row">
			<div class="form-group">
		    	<div class="form-inline">
			    	<label for="selectGatewayIp" class="col-sm-5 col-form-label">选择网关IP</label>
			    	<div class="col-sm-7">
				        <select class="form-control" id="selectGatewayIp" name="selectGatewayIp">
					        <option value="10.101.70.155">10.101.70.155</option>
					        <option value="10.101.70.156">10.101.70.156</option>
					        <option value="10.101.70.52">10.101.70.52</option>
					        <option value="10.101.70.94">10.101.70.94</option>
					        <option value="10.101.72.29">10.101.72.29</option>
				        </select>
			        </div>
		    	</div>
		    </div>
		</div>
	</div>
	<div class="form-inline">
	<div class="form-group row">
	    <div class="form-group col-md-4">
	    	<div class="form-inline">
		    	<label for="gatewayIp" class="col-sm-4 col-form-label">网关IP</label>
		    	<div class="col-sm-8">
			        <input type="text" class="form-control" name="gatewayIp" id="gatewayIp" value="" placeholder="输入或选择">
		        </div>
	    	</div>
	    </div>
	    <div class="form-group col-md-4">
	      <div class="form-inline">
		    	<label for="gatewayPort" class="col-sm-4 col-form-label">网关端口</label>
		    	<div class="col-sm-8">
		        	<input type="text" class="form-control" name="gatewayPort" id="gatewayPort" value="">
		        </div>
	    	</div>
	    </div>
	    <div class="form-group col-md-4">
	      <div class="form-inline">
		    	<label for="deviceIdEx" class="col-sm-4 col-form-label">设备编码</label>
		    	<div class="col-sm-8">
		        	<input type="text" class="form-control" name="deviceId" id="deviceIdEx" value="">
		        </div>
	    	</div>
	    </div>
	</div>
	<div class="col-auto my-1">
		<button type="button" class="btn btn-primary" id="deviceRegisterBtn" >设备注册</button>
	</div>
	</div>

	
	<div class="container-fluid form-inline">
	  <div id="jsoneditor"></div>
	  <div style="width:150px; height:auto;">
	  	<div style="margin:55px;">
		  	<button id="setRightBtn" type="button" class="btn btn-primary"> &gt; </button><br/><br/>
			<button id="setLeftBtn" type="button" class="btn btn-primary"> &lt; </button>
		</div>
	  </div>
	  <div id="jsoneditor2"></div>
	</div>
	
	<div class="form-inline">
		<div class="form-group row">
			<div class="form-group">
		    	<div class="form-inline">
			    	<label for="replyFlag" class="col-sm-6 col-form-label">请求/应答</label>
			    	<div class="col-sm-7">
				        <select class="form-control" id="replyFlag" name="replyFlag">
				        	<option value="0">请求</option>
					        <option value="1">应答</option>
				        </select>
			        </div>
		    	</div>
		    	<button id="sendMessage" type="button" class="btn btn-primary">发消息</button>
		    </div>
		</div>
	</div>
	</form>
	
	</div>
	
</body>
</html>
