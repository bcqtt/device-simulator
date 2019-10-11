/**
 * 创建充电设备
 */

/* 页面初始化代码区 */
$(document).ready(function() {
	// create the editor
	var container = document.getElementById('jsoneditor');
	var options = {
		modes : [ 'text', 'code', 'tree', 'form', 'view' ],
		mode : 'code',
		ace : ace
	};
	var json = {
			  "Command": "COM_DEV_REGISTER",
			  "Data": [
			    {
			      "Type": 1,
			      "deviceID": "1001201600FF81992F49",
			      "manufacturer": "XXX厂商",
			      "macNO": 102,
			      "locationAddr": "南门停车场入口",
			      "name": "停车场设备",
			      "ip": "192.168.10.88",
			      "gateWay": "00000000000000000",
			      "mac": "00:FF:81:99:2F:49",
			      "mask": "255.255.255.0",
			      "version": "V1.0.16_20171225001"
			    }
			  ]
			};
	var editor = new JSONEditor(container, options, json);

	// create the editor
	var container2 = document.getElementById('jsoneditor2');
	var options2 = {};
	var editor2 = new JSONEditor(container2, options2);
	editor2.set(json);
	editor2.expandAll();
	
//=======================================================================
	
	$("#selectGatewayIp").change(function(){
		onSelectGatewayIp();
	});
	
	$("#chargeDevice").click(function() {
		createChargeDevice();
	});

	$("#requestQrCodeBtn").click(function() {
		requestQrCode();
	});

	// jsonEditor部分
	$("#setRightBtn").click(function() {
		setJsonToRight(editor,editor2);
	});
	
	$("#setLeftBtn").click(function() {
		setJsonToLeft(editor,editor2);
	});
	
	$("#deviceRegisterBtn").click(function() {
		deviceRegister();
	});
	
	$("#sendMessage").click(function() {
		sendMessage(editor);
	});

	// ***********************websocket代码区**********************//
	var websocket = null;
	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://127.0.0.1:28080/simulator/websocket");
		// websocket = new WebSocket("ws://localhost:9018/getServiceIpAndPort");
	} else {
		alert('当前浏览器 Not support websocket');
	}

});

/* 方法区 */

function onSelectGatewayIp(){
	$("#gatewayIp").val($("#selectGatewayIp").val());
}

// 直接创建（练习用）
function createChargeDevice() {
	$.ajax({
		url : "./createDevice",
		dataType : "json",
		async : true,
		type : "POST",
		data : {
			localIp : "172.25.84.31",
			localPort : 38089
		},
		// 成功后开启模态框
		success : function(data) {
			alert(data);
		},
		error : function() {
			// alert("请求失败");
		}
	});
}
// 直接创建（练习用）
function requestQrCode() {
	$.ajax({
		url : "./requestQrCode",
		dataType : "json",
		async : true,
		type : "POST",
		data : {
			localIp : "172.25.83.231",
			localPort : 38089
		},
		// 成功后开启模态框
		success : function(data) {
			swal("Good job!", data, "success");
		},
		error : function() {
			// alert("请求失败");
		}
	});
}


//jsoneditor部分
function setJsonToRight(editor,editor2) {
	editor2.set(editor.get());
	editor2.expandAll();
}

function setJsonToLeft(editor,editor2) {
	editor.set(editor2.get());
}

function deviceRegister() {
	$.ajax({
		url : "./deviceRegister",
		dataType : "json",
		async : true,
		type : "POST",
		data : {
			deviceId : $("#deviceIdEx").val(),
			gatewayIp : $("#gatewayIp").val(),
			gatewayPort : $("#gatewayPort").val()
		},
		success : function(data) {
			alert(data);
		},
		error : function() {
			// alert("请求失败");
		}
	});
}

function sendMessage(editor) {
	var jsonData = JSON.stringify(editor.get(), null, 2)
	$.ajax({
		url : "./sendMessage",
		dataType : "json",
		async : true,
		type : "POST",
		data : {
			gatewayIp : $("#gatewayIp").val(),
			gatewayPort : $("#gatewayPort").val(),
			deviceId : $("#deviceIdEx").val(),
			replyFlag : $("#replyFlag").val(),
			jsonData : jsonData
		},
		// 成功后开启模态框
		success : function(data) {
			swal("Good job!", data, "success");
		},
		error : function() {
			// alert("请求失败");
		}
	});
}