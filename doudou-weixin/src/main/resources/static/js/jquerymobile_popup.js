/* 
 * JQuery Moblie Popup扩展支持
 */

var func_openPopup_okcallback = undefined;
/**
 * 弹出锁屏的popup窗口
 */
function openPopup(context, title, showCancel, showOK, okcallback, type, lang) {
	if (lang == undefined || lang == null ) {
		lang = '';
	}
	if (context == undefined || context == null ) {
		context = '';
	}
	if (title == undefined || title == null || title == '') {
		title = '';
	}
	if (showCancel == undefined) {
		showCancel = false;
	}
	if (showOK == undefined) {
		showOK = false;
	}
	func_openPopup_okcallback = okcallback;
	var popupdom = document.getElementById("msgPopup");
	if (popupdom == undefined) {
		// 当前dom节点中没有包含msgPopup,则动态创建之
		
		var popupdom_innerHtml="<div data-role=\"popup\" id=\"msgPopup\" data-dismissible=\"false\" style=\"z-index:999;max-width: 380px; min-width: 200px; background-color: #FFFFFF;\">";
		if(type == undefined || "info" == type ){
			popupdom_innerHtml += "	<div data-role=\"header\" style=\"background-color: #455aa7; color: #FFFFFF;\">";
		}else{
			popupdom_innerHtml += "	<div data-role=\"header\" style=\"background-color: #455aa7; color: #FFFFFF;\">";
		}
		popupdom_innerHtml += "    <span id=\"msgPopup_title\" style=\"height: 30px; line-height: 40px; text-shadow:none; font-weight: bolder; padding-left: 15px;\"></span>";
		popupdom_innerHtml += "    </div>";
		popupdom_innerHtml += "    <div data-role=\"main\" class=\"ui-content\" style=\"min-width: 200px;\">";
		popupdom_innerHtml += "		<p id=\"msgPopup_context\"></p>";
		popupdom_innerHtml += "		";
		popupdom_innerHtml += "		";
		popupdom_innerHtml += "	</div>";
		popupdom_innerHtml += "	<div id=\"msgPopup_footer\" style=\"text-align: center; display: none;\">";
		if(lang == "cn"){
			popupdom_innerHtml += "		<a id=\"msgPopup_cancel\" href=\"#\" data-role=\"button\" data-rel=\"back\" class=\"ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a ui-btn-icon-left ui-icon-delete\">取消</a>";
			popupdom_innerHtml += "		<a id=\"msgPopup_ok\" href=\"#\" onclick=\"openPopup_okcallback()\" data-role=\"button\" data-rel=\"back\" class=\"ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-d ui-btn-icon-left ui-icon-check\">确认</a>";
		} else if(lang == "en") {
			popupdom_innerHtml += "		<a id=\"msgPopup_cancel\" href=\"#\" data-role=\"button\" data-rel=\"back\" class=\"ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a ui-btn-icon-left ui-icon-delete\">Cancel</a>";
			popupdom_innerHtml += "		<a id=\"msgPopup_ok\" href=\"#\" onclick=\"openPopup_okcallback()\" data-role=\"button\" data-rel=\"back\" class=\"ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-d ui-btn-icon-left ui-icon-check\">Confirm</a>";
		}
		popupdom_innerHtml += "	</div>";
		popupdom_innerHtml += "</div>";
		
		$('body').append(popupdom_innerHtml);
	}
	
	
	$("#msgPopup_title").html(title);
	$("#msgPopup_context").html(context);
	if (showCancel || showOK) {
		$("#msgPopup_footer").css("display", "");
	} else {
		$("#msgPopup_footer").css("display", "none");
	}
	if (showCancel) {
		$("#msgPopup_cancel").css("display", "");
	} else {
		$("#msgPopup_cancel").css("display", "none");
	}
	if (showOK) {
		$("#msgPopup_ok").css("display", "");
	} else {
		$("#msgPopup_ok").css("display", "none");
	}
	
	$("#msgPopup").popup();
	$("#msgPopup").popup("open");
}

/**
 * POPUP确定按钮回调
 */
function openPopup_okcallback() {
	try {
		func_openPopup_okcallback();
	} catch(e) {}
}




