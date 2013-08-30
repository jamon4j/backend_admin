
//鑾峰彇 id 涓� eId 鐨凥TML 鍏冪礌
function $id(eId) {
	return document.getElementById(eId);
}

//鑾峰彇 name 涓� eName鐨凥TML鍏冪礌
function $name(eName) {
	return document.getElementsByName(eName);
}

 /*
  * 瀵瑰閫夋鍋� 鍏ㄩ€夋垨鍙栨秷鍏ㄩ€� 鐨勬搷浣�,渚濊禆jquery
  * 
  * @param eName checkbox鐨勫厓绱犲悕绉�
  * @param isCheck true:閫夋嫨 false:鍙栨秷閫夋嫨 
  * 
  * @example:
  *   <input name="albumID[]" type="checkbox" value="1" />
  * 
  *   <input name="albumIDCheckBtn" onclick="checkAll('albumID[]', this.checked)" type="checkbox"  /> 
  */
function checkAll(eName, isCheck) {
	var chk = typeof(isCheck) == 'boolean' ? isCheck : isCheck.checked;
	jQuery($name(eName)).attr('checked', chk);
}

//妫€鏌MAIL
function isEmail(str) {
  var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
  var flag = reg.test(str);
  return flag;
}

//妫€鏌ユ槸鍚﹀瓧姣�
function isAlpha(str) {
  var reg = /^[A-z]+$/;
  var flag = reg.test(str);
  return flag;
}

//妫€鏌ユ槸鍚﹀瓧姣嶆垨鏁板瓧
function isAlphaNumeric(str) {
  var reg = /^[0123456789A-z]+$/;
  var flag = reg.test(str);
  return flag;
}

//妫€鏌ユ槸鍚﹀瓧姣嶆垨鏁板瓧鎴栦腑鍒掔嚎涓嬪垝绾�
function isAlphaDash(str) {
  var reg = /^[-_0123456789A-z]+$/;
  var flag = reg.test(str);
  return flag;
}

//妫€鏌ユ槸鍚︽暟鍊�
function isNumeric(str) {
  var reg = /^-?[0-9]+\.?[0-9]*$/;
  var flag = reg.test(str);
  return flag;
}

//妫€鏌ユ槸鍚︽暣鍊�
function isInt(str) {
  var reg = /^-?[0-9]+$/;
  var flag = reg.test(str);
  return flag;
}

//妫€鏌ユ湁娌℃湁閫夋嫨鍗曢€夐」鐨勫叾涓竴涓�
function isRadioChecked(rName) {
	var rdos = $name(rName);
	for (var r = 0; r < rdos.length; r++) {
		if (rdos[r].checked) {
			return true;
		}
	}
	
	return false;
}

//妫€鏌ヤ笅鎷夋鏈夋病鏈夎閫�
function isSelected(sName) {
	var ss = $name(sName);

	if (ss[0] && ss[0].selectedIndex > 0) {

		return true;
	}
	
	return false;
}

/**
 * 妫€鏌ユ槸鍚︿负ID鏍煎紡
 * 1349 鎴� 1,2,3 鎴� 1-10
 */ 
function isID(str) {
	var reg1 = /^[0-9]+$/;
	var reg2 = /^([0-9]+,?)+[0-9]+$/;
	var reg3 = /^[0-9]+-[0-9]+$/;

	return ((isInt(str) && str > 0) || reg1.test(str) || reg2.test(str) || reg3.test(str));
}


/**
 * 妫€鏌ュ厓绱犳槸鍚﹀湪鏁扮粍涓�
 * 杩斿洖true or false
 */
function in_array(ele, arr) {
	for (var arr_i = 0; arr_i < arr.length; arr_i++) {
		if (ele == arr[arr_i]) {
			return true;
		}
	}
	
	return false;
}

//妫€鏌ユ棩鏈熸牸寮�
function isDate(str) {
  var reg = /^([0-9]{4}|[0-9]{2})-[0-9]{1,2}-[0-9]{1,2}$/;
  var reg1 = /^([0-9]{4}|[0-9]{2})(-|\/)[0-9]{1,2}(-|\/)[0-9]{1,2}$/;
  var reg2 = /^[0-9]{1,2}(-|\/)[0-9]{1,2}$/;
  var reg3 = /^[0-9]{4}(-|\/)[0-9]{1,2}$/;
  
  return (reg.test(str) || reg1.test(str) || reg2.test(str) || reg3.test(str));   
}


function isFdate(str, fm) {
	arrformat = fm.split(',');
	var reg;
	var regz;
	for (i = 0; i < arrformat.length; i++) {
		if (arrformat[i] == 'yyyy') {
			reg = /^[0-9]{4}$/;
			regz = /^[0-9]{4}骞�$/;
		} else if (arrformat[i] == 'yyyy-mm') {
			reg = /^[0-9]{4}-([0]?[1-9]{1}|10|11|12)$/;
			regz = /^[0-9]{4}骞�([0]?[1-9]{1}|10|11|12)鏈�$/;
		} else if (arrformat[i] == 'yyyy-mm-dd') {
			reg = /^[0-9]{4}-([0]?[1-9]{1}|10|11|12)-([0]?[1-9]{1}|(1[0-9]{1})|(2[0-9]{1})|30|31)$/;
			regz = /^[0-9]{4}骞�([0]?[1-9]{1}|10|11|12)鏈�([0]?[1-9]{1}|(1[0-9]{1})|(2[0-9]{1})|30|31)鏃�$/;
		} else if (arrformat[i] == 'mm-dd') {
			reg = /^([0]?[1-9]{1}|10|11|12)-([0]?[1-9]{1}|(1[0-9]{1})|(2[0-9]{1})|30|31)$/;
			regz = /^([0]?[1-9]{1}|10|11|12)鏈�([0]?[1-9]{1}|(1[0-9]{1})|(2[0-9]{1})|30|31)鏃�$/;
		}
		
		if (reg.test(str)) {
			return true;
		}
		if (regz.test(str)) {
			return true;
		}
	}
	
	return false;
}
/**
 * 娴嬭瘯骞翠唤蹇呴』澶т簬1970骞�
 */
function chkYear(str) {
	var reg = /^\d{4}/;
	if ((result = str.match(reg)) && result[1] < 1970) {
		return false;
	}
	return true;
}

/**
 * 鏄惁灏戜簬褰撳墠鏃ユ湡
 */
function isLessNow(str) {
	var reg = /^(\d{2,4})([\-\/]([01]?\d))?([\-\/]([0-3]?\d))?$/;
	if (!(result = str.match(reg))) {
		return false;
	}
	d = new Date();
	d = new Date(d.getFullYear(), d.getMonth(), d.getDate());
	if (result[1].length < 4) {
		if (new Number(result[1]) > (d.getFullYear() % 100) && new Number(result[1]) >= 70) {
			result[1] = "19" + result[1];
		} else {
			year = new String(d.getFullYear ());
			result[1] = year.substr(0,2) + new String(result[1]);
		}
	}
	if (result[1] && result[3] && result[5]) {
		date = new Date(result[1] , result[3] - 1 , result[5]);
		if (date > d) {
			return false;
		}
	} else if (result[1] && result[3]) {
		if (result[1] > d.getFullYear()) {
			return false;
		} else if (result[1] == d.getFullYear() && result[3] > d.getMonth() + 1) {
			return false;
		}
	} else if (result[1] > d.getFullYear()) {
		return false;
	}
	return true;
}

/**
 * 妫€鏌ユ槸鍚﹀ぇ浜庡綋鍓嶆棩鏈�
 */
function isMoreThanNow(str) {
	var reg = /^(\d{2,4})([\-\/]([01]?\d))?([\-\/]([0-3]?\d))?$/;
	if (!(result = str.match(reg))) {
		return false;
	}
	d = new Date();
	d = new Date(d.getFullYear(), d.getMonth(), d.getDate());
	// 琛ュ叏骞翠唤
	if (result[1].length < 4) {
		if (new Number(result[1]) > (d.getFullYear() % 100) && new Number(result[1]) >= 70) {
			result[1] = "19" + result[1];
		} else {
			year = new String(d.getFullYear ());
			result[1] = year.substr(0,2) + new String(result[1]);
		}
	}
	if (result[1] && result[3] && result[5]) {
		date = new Date(result[1] , result[3] - 1 , result[5]);
		if (date < d) {
			return false;
		}
	} else if (result[1] && result[3]) {
		if (result[1] < d.getFullYear()) {
			return false;
		} else if (result[1] == d.getFullYear() && result[3] > d.getMonth() + 1) {
			return false;
		}
	} else if (result[1] < d.getFullYear()) {
		return false;
	}
	return true;
}

/**
 * 鍔熻兘: 楠岃瘉琛ㄥ崟涓�,鐢ㄦ埛杈撳叆鍐呭鏄惁閫傚悎
 * form 琛ㄥ崟瀵硅薄
 * showmode 鏄剧ず鏂瑰紡锛�'alert'銆�'div'(榛樿)
 * 		alert: 浣跨敤alert鍑芥暟鎻愮ず
 * 		div:   鏄剧ず鍦�"鍏冪礌鍚峗msg"鐨凞IV鎴朤D
 */
function checkForm(form, showmode, act) {
	//绗竴涓湁鎻愮ず淇℃伅鐨勫厓绱�
	this.firstMsgElement = null;
	
	//鎻愮ず淇℃伅鐨勫乏杈硅窛
	this.msgBoxLeft = null;
	
	if (!showmode) {
		showmode = 'div';
	}
	
	if (act) {
		form.action = act;
	}
	if (form.tagName.toLowerCase() == 'form') {
		var eles = form.elements;
	} else {
		var eles = [form];
	}
	//frm鐨勬鏌ユ爣璁�,榛樿true
	var succeed = true;
	
	var alert_msg = '';
	
	this.showError = function (ele, msg) {
		if (!msg || msg == '') {
			return ;
		}
		//璁剧疆绗竴涓湁鎻愮ず淇℃伅鐨勫厓绱�
		if (this.firstMsgElement == null && ele.type != 'hidden') {
			this.firstMsgElement = ele;
		}
		
		var idDiv = ele.name + '_msg';
		var msgp = $id(idDiv);
		
		// 灏濊瘯浣跨敤鎺т欢ID鏋勫缓淇℃伅鏄剧ず妗咺D
		if (!msgp) {
			msgp = $id(ele.id + '_msg');
			}
		
		if (!msgp) {
			msgp = document.createElement('div');
			msgp.className = 'reg_info';
			msgp.id = idDiv;
			msgp.style.zIndex = 9999;
			
			var posele = $name(ele.name);
			posele = posele[posele.length-1];
			
			document.body.appendChild(msgp);
			
			if (posele.type == 'hidden') {
				posele = $(posele).parent()[0];
			}
			
						
			var offset = getScreenOffset(posele);

			if (this.msgBoxLeft == null) {
				this.msgBoxLeft = offset.x + posele.clientWidth + 5;
			}
			msgp.style.position = 'absolute';
			msgp.style.left = this.msgBoxLeft + 'px';
			msgp.style.top = offset.y + 'px';
			
		}
		
		msgp.className = 'reg_info1';
		
		msgp.innerHTML = msg;
		msgp.style.display = '';
		
		if (msgp.style.position == 'absolute') {
			msgp.style.width = (msg.length + 2) + 'em';
		}
		
	}
	
	//闅愯棌淇℃伅
	this.hideError = function(ele) {
		var msgp = $id(ele.name + '_msg');
		if (!msgp) {
			msgp = $id(ele.id + '_msg');
			}
		
		if (msgp) {
			if (msgp.className == 'reg_info1') {
				msgp.className = '';
				msgp.innerHTML = '';
			} else if (msgp.className == 'reg_info'){
				msgp.style.display = 'none';
			}
		}
	}
try{
	CHECKELE:
	for (var i = 0; i < eles.length; i++) {
		var unitresult = true;
		var ele = eles[i];
		
		var break_for = false;
		
		//閿欒鎻愮ず
		var errInfo = '';
		  
		//鑾峰彇妫€鏌ヤ覆
		if ($.browser.msie) {
			var valid_str = ele.valid;
		}else{
			var valid_str = ele.getAttribute('valid');
		}
		

		//娌℃湁璁剧疆鍒欒繑鍥�
		if(!valid_str) {
			continue;
		}
		
		var eleValue = ele.value;
		
		//鍒嗚В灞炴€�
		var valids = valid_str.split('|');
		
		//澶勭悊鍗曢€夊拰澶氶€�
		if ((ele.type == 'radio' || ele.type == 'checkbox') && in_array('required', valids)) {
			var result = isRadioChecked(ele.name);
			if (!result) {
				errInfo += '蹇呴』閫夋嫨鍏朵腑涓€涓€夐」锛�';
				unitresult = false;
			}
		}
		
		//涓婁紶妗�
		if (ele.type == 'file') {
			for (vs = 0; vs < valids.length; vs++) {
				if (valids[vs].indexOf(':') > 0) {
					param = valids[vs].split(':');
					
					if (param[0] == 'type' && ele.value != ''){
						types = param[1].split(',');

						ftype = ele.value.split('.');
						result = in_array(ftype[ftype.length-1].toLowerCase(), types);
						if (!result) {
							errInfo += '鍙兘涓婁紶鎸囧畾鐨勬枃浠剁被鍨嬶細' + types.join(',');
							unitresult = false;
						}
					}
					
				} else {
					if ('required' == valids[vs]) {
						var result = ele.value != '';
						if (!result) {
							errInfo += '璇烽€夋嫨瑕佷笂浼犵殑鏂囦欢锛�';
							unitresult = false;
						}
					}
				}
			}
		}
		
		//涓嬫媺
		if (ele.type == 'select-one' || ele.type == 'select') {
			var result = isSelected(ele.name);
			if (!result) {
				errInfo += '蹇呴』閫夋嫨鍏朵腑涓€涓€夐」锛�';
				unitresult = false;
			}
		}

		//妫€鏌ext绫诲瀷杈撳叆妗�
		if (ele.type == 'text' || ele.type == 'textarea' || ele.type == 'password' || ele.type == 'hidden') {
			for (var j = 0; j < valids.length; j++) {
				//鍏冪礌鍊兼祴璇�
				var result = true;
				switch (valids[j]) {
					case 'trim':
						ele.value = eleValue = jQuery.trim(eleValue);
						break;
					
					case 'required':
						result = (eleValue != '');
						if (!result) {
							errInfo += '璇疯緭鍏ユ椤逛俊鎭�';
							unitresult = false;
						}
						break;
					
					case 'norequired':
						if (eleValue == '') {
							//result = true;
							//unitresult = true;
							break_for = true;
						}
						
						break;
						
					case 'alpha':
						var result = isAlpha(eleValue);
						
						if (!result) {
							errInfo += '鍙兘杈撳叆鑻辨枃锛�';
							unitresult = false;
						}
						
						break;
						
					case 'alpha_numeric':
						var result = isAlphaNumeric(eleValue);
						if (!result) {
							errInfo += '鍙兘杈撳叆鑻辨枃鍜屾暟瀛楋紒';
							unitresult = false;
						}
						break;
					
					case 'alpha_dash':
						var result = isAlphaDash(eleValue);
						if (!result) {
							errInfo += '鍙湁杈撳叆鑻辨枃銆佹暟瀛椼€乢銆�- 锛�';
							unitresult = false;

						}
						break;
						
					case 'numeric':
						var result = isNumeric(eleValue);
						if (!result) {
							errInfo += '鍙兘杈撳叆鏈夋晥鐨勬暟鍊硷紒';
							unitresult = false;
						}
						break;
						
					case 'int':
						var result = isInt(eleValue);
						if (!result) {
							errInfo += '鍙厑璁歌緭鍏ユ暣鏁帮紒';
							unitresult = false;
						}
						break;
						
					case 'email':
						var result = isEmail(eleValue);
						if (!result){
							errInfo += 'email鏍煎紡涓嶆纭紒';
							unitresult = false;
						}
						break;
						
					case 'date':
						var result = isDate(eleValue);
						if (!result) {
							errInfo += '鏃ユ湡鏍煎紡涓嶆纭�;';
							unitresult = false;
						}
						if (!chkYear(eleValue)) {
							errInfo += "骞翠唤涓嶈兘灏戜簬1970";
							unitresult = false;
							}
						
						// 鍙湁瀹屾暣鐨勬棩鏈熸牸寮�(骞淬€佹湀銆佹棩鍐欏叏)鎵嶆墽琛屾祴璇�
						if (in_array("less_now", valids) && eleValue.match(/\d{2,4}[\/\-]\d{1,2}[\/\-]\d{1,2}/) && !isLessNow(eleValue)) {
							errInfo += "涓嶈兘澶т簬褰撳墠鏃ユ湡";
							unitresult = false;
						}
						// 鍙湁瀹屾暣鐨勬棩鏈熸牸寮�(骞淬€佹湀銆佹棩鍐欏叏)鎵嶆墽琛屾祴璇�
						if (in_array("more_than_now", valids) && eleValue.match(/\d{2,4}[\/\-]\d{1,2}[\/\-]\d{1,2}/) && !isMoreThanNow(eleValue)) {
							errInfo += "涓嶈兘灏戜簬褰撳墠鏃ユ湡";
							unitresult = false;
						}
						break;
					case 'time':
						if (eleValue != '') {
							if (!(rs = eleValue.match(/^(\d{2}):(\d{2})(:(\d{2}))?$/))) {
								errInfo += "鏃堕棿鏍煎紡涓嶆纭�"
								unitresult = false;
							} else {
								if (parseInt(rs[1]) > 23) {
									errInfo += "鏃堕棿鏍煎紡涓嶆纭�,锛傚皬鏃讹紓涓嶈兘瓒�23";
									unitresult = false;
								}
								if (parseInt(rs[2]) > 59) {
									errInfo += "鏃堕棿鏍煎紡涓嶆纭�,锛傚垎閽燂紓涓嶈兘瓒�59";
									unitresult = false;
								}
								if (rs[4] && parseInt(rs[4]) > 59) {
									errInfo += "鏃堕棿鏍煎紡涓嶆纭�,锛傜锛備笉鑳借秴59";
									unitresult = false;
								}
							}
						}
						break;
					case 'ID':
						if (eleValue) {
							result = isID(eleValue);
							if (!result) {
								errInfo += '涓嶇鍚圛D鏍煎紡锛�';
								unitresult = false;
							}
						}
						break;
						
					case 'nospace':
						result = eleValue.indexOf(' ') == -1;
						if (!result) {
							errInfo += '涓嶈兘鍚湁绌烘牸锛�';
							unitresult = false;
						}
						break;
					
					default:
						if (valids[j].indexOf(':') > 0) {
							var part = valids[j].split(':'); 
							switch (part[0]) {
								case 'min_length':
									var result = (eleValue.length >= part[1]);
									if (!result) { 
										errInfo += ('鏈€灏戣杈撳叆' + part[1] + '涓瓧绗︼紒');
										unitresult = false;
									}
									break;
									
								case 'max_length':
									var result = (eleValue.length <= part[1]);
									if (!result) {
										errInfo += ('鏈€澶氬彧鑳借緭鍏�' + part[1] + '涓瓧绗︼紒');
										unitresult = false;
									}
									break;
									
								case 'length':
									var result = (eleValue.length == part[1]);
									if (!result) {
										errInfo += ('蹇呴』杈撳叆' + part[1] + '涓瓧绗︼紒');
										unitresult = false;
									}
									break;
									
								case 'fdate':
									if (eleValue == '') {
										break_for = true;
									} else {
										var result = isFdate(eleValue, part[1]);
										if (!result) {
											errInfo += '鏃ユ湡鏍煎紡涓嶆纭€�';
											unitresult = false;
										}
										if (!chkYear(eleValue)) {
											errInfo += "骞翠唤涓嶈兘灏戜簬1970";
											unitresult = false;
										}
										// 鍙湁瀹屾暣鐨勬棩鏈熸牸寮�(骞淬€佹湀銆佹棩鍐欏叏)鎵嶆墽琛屾祴璇�
										if (in_array("less_now", valids) && eleValue.match(/\d{2,4}[\/\-]\d{1,2}[\/\-]\d{1,2}/) && !isLessNow(eleValue)) {
											errInfo += "涓嶈兘澶т簬褰撳墠鏃ユ湡";
											unitresult = false;
										}
										// 鍙湁瀹屾暣鐨勬棩鏈熸牸寮�(骞淬€佹湀銆佹棩鍐欏叏)鎵嶆墽琛屾祴璇�
										if (in_array("more_than_now", valids) && eleValue.match(/\d{2,4}[\/\-]\d{1,2}[\/\-]\d{1,2}/) && !isMoreThanNow(eleValue)) {
											errInfo += "涓嶈兘灏戜簬褰撳墠鏃ユ湡";
											unitresult = false;
										}
									}
									break;
									
								case 'type':
									if (ele.value != ''){
										types = part[1].split(',');
				
										ftype = ele.value.split('.');
										result = in_array(ftype[ftype.length-1].toLowerCase(), types);
										if (!result) {
											errInfo += '鍙兘閫夊畾鏂囦欢鐨勭被鍨嬶細' + types.join(',');
											unitresult = false;
										}
									}
									
									break;
							}
						}
						
				}
						//alert(unitresult);
				//unitresult = unitresult && result;
				
				if (break_for) {
					break;
				}
			}

			//succeed = succeed && unitresult;

			if (showmode == 'alert' && !unitresult) {
				if ($.browser.msie) {
					var msg_title = ele.msg_title;
				}else{
					var msg_title = ele.getAttribute('msg_title');
				}
				if (msg_title) {
					errInfo = msg_title + errInfo;
				}
			}

			
		}
		
		if (unitresult == false){
			succeed = false;
		}
		//鏄剧ず鎻愮ず淇℃伅鍒癉IV 鎴� 鏀惧埌alert_msg
		SHOWMSG:
		if (showmode == 'div') {
			if (!unitresult) {
				this.showError(ele, errInfo);
			} else {
				this.hideError(ele);
			}
		} else if(!unitresult) {
			alert_msg += errInfo + "\n";
		}
	}
	
	
	if (showmode == 'alert' && alert_msg != '') {
		alert(alert_msg);
	}

	//灏嗙劍鐐规斁鍒扮涓€涓湁鎻愮ず淇℃伅鐨勫厓绱�
	if (this.firstMsgElement != null && form.tagName.toLowerCase() == 'form') {
		this.firstMsgElement.focus();
	}
} catch(e) {
	alert(e);
	return false;
}
	return succeed;;
}

/**
 * 杩斿洖鍏冪礌鍦ㄩ〉闈腑鐨刼ffset
 * 杩斿洖: {x,y}
 */
function getScreenOffset(htmlObj){ 
   var  rd  =  {x:0,y:0} 
   do{ 
       rd.x  +=  htmlObj.offsetLeft          
       rd.y  +=  htmlObj.offsetTop 
       htmlObj  =  htmlObj.offsetParent
   }  while(htmlObj) 
   return  rd 
}

/**
 * 杞悜涓€涓垹闄ら摼鎺�
 * url: 閾炬帴鍦板潃
 * msg: 鎻愮ず淇℃伅(鍙€�)
 */
function delConfirm(url, msg) {
	if (!msg) {
		msg = '纭畾瑕佸垹闄よ繖鏉¤褰曞悧锛�';
	}
	Xwb.ui.MsgBox.confirm('鎻愮ず',msg,function(id){
		if(id == 'ok'){
			window.location.href = url;
		}
	})
}





function isIE6() {
	return navigator.appVersion.match(/MSIE [0-6]\./);
}






function tigMsgConfirm(msg)
{
	var width, height, bodywidth, bodyheight, top, left;
	var offset = 0;
	var selects = $('select');
	bodywidth = document.documentElement.clientWidth;
	bodyheight = document.documentElement.clientHeight;
	$("#trans").css('display', 'block');
	$("#trans").css('width', document.body.scrollWidth + "px");
	$("#trans").css('height', document.body.scrollHeight + "px");
	$("#trans").css('backgroundColor', "");
	if (msg != '') {
		msg = msg.replace(/\n/g, '<br/>');
		$('#contentDiv').html(msg);
	}
	$('#tigMsg').css('display', 'block');
	height = $id('tigMsg').clientHeight;
	width = $id('tigMsg').clientWidth;
	top = Math.floor(bodyheight / 2) - Math.floor(height / 2) + document.documentElement.scrollTop;
	if (top < 0)
	{
		top = 60;
	}
	left = Math.floor(bodywidth / 2) - Math.floor(width / 2);
	$('#tigMsg').css('left', left+'px');
	$('#tigMsg').css('top', top+'px');
	if (isIE6()) {
		if (selects.length > 0) {
			for (var n = 0; n < selects.length; n++) {
				selects[n].disabled= 'disabled';
			}
		}
		var layer = document.createElement('IFRAME');
		if ($.browser.msie) {
			layer.id = 'ifdiv';
			layer.frameborder = 0;
		}else{
			layer.setAttribute('id', 'ifdiv');
			layer.setAttribute('frameborder', 0);
		}
		layer.style.zIndex = 100001;
		layer.style.border = 0;
		layer.style.position = 'absolute';
		layer.style.display = 'block';
		layer.style.width = width + 10 + 'px';
		layer.style.height = height + 'px';
		layer.style.visibility = '';
		layer.style.left =  left + 'px';
		layer.style.top = top + 'px';
		layer.style.backgroundColor = '#000';
		document.body.appendChild(layer);
	}
}

function closeTigMsg()
{
	$("#trans").css('display', 'none');
	$("#trans").css('width', 0);
	$("#trans").css('height', 0);
	$('#tigMsg').css('display', 'none');
	if (isIE6()) {
		$('#ifdiv').remove();
		var selects = $('select');
		if (selects.length > 0) {
			for (var n = 0; n < selects.length; n++) {
				selects[n].disabled= '';
			}
		}
	}
}

function defaultChecked(check_name)
{
	var url = window.location.href;
	var para_str = url.substring(url.indexOf("?")+1);
	var para_arr = para_str.split("&");
	var para_name;
	var para_value = "";
	var i,j;
	for(i=0;i<para_arr.length;i++){
		var para_name = para_arr[i].substring(0,para_arr[i].indexOf("="));
		if (para_name == "selected"){
			para_value = para_arr[i].substring(para_arr[i].indexOf("=")+1);
			break;
		}
	}

	if (para_value != ""){
		var id_arr = para_value.split(",");
		var name = $name(check_name);
		for(j=0;j<name.length;j++){
			if (in_array(name[j].value, id_arr)){
				name[j].checked = true;
			}
		}
	}
}

function toggleDiv(source,id)
{
	if (source.checked){
		$(id).hide();
	}else{
		$(id).show();
	}
}
function buildModulePath(module, action, querystring) {
		var base = typeof BASE_PATH == 'undefined' ? '': BASE_PATH;
		var file = typeof W_BASE_FILENAME == 'undefined' ? '': W_BASE_FILENAME;
		var get_var_mame = typeof R_VAR_NAME == 'undefined' ? 'm': R_VAR_NAME;
		var mode = ROUTE_MODE || 0;
		
		if (mode == 2 || mode == 3) {
			var uri = base + module;
			
			if (action) {
				if (action.indexOf('.') > -1) {
					uri += '/';
				}
				else {
					uri += '.';
				}
				uri += action;
			}
			
			if (querystring) {
				if (uri.indexOf('?') == -1) {
					uri += '?';
				}
				uri += querystring;
			}
			
			return uri;
		} else {
			var params = '?' + get_var_mame + '=' + module;
			if (action) {
				params += '.' + action;
			}
			
			if (querystring) {
				params += '&' + querystring;
			}
			
			return base + file + params; 
		}
	}

function isElement(o) {
	return !!(o && o.nodeType == 1);
}
function isNumber(o) {
	return Object.prototype.toString.call(o) == '[object Number]';
}
function isString(o) {
	return Object.prototype.toString.call(o) == '[object String]';
}

var mIndex = 0, sIndex = 0;

var admin = {
	index: {
		$selectedSubMenu : null,
		$selectedMainMenu : null,
		$selectedSubMenuContainer: null,
		$mainmenu_container: null,
		$submenu_container: null,
		$mainmenu: null,
		$submenu: null,
		
		/**
		 * select main menu
		 * @param n HTMLElement|int|'default'
		 *
		 */
		selectMainMenu: function(n, m) {
			with (admin.index) {
				if (!$mainmenu) {
					return;
				}
				if ($selectedMainMenu) {
					$selectedMainMenu.removeClass('current');
				}

				n = isNumber(n) || isElement(n) ? n : 'default';
				if (n == 'default') {
					$selectedMainMenu = $mainmenu.filter('.default');
					mIndex = $mainmenu.index($selectedMainMenu);
				} else {
					if (isElement(n))
					{
						$selectedMainMenu = $(n);
						mIndex = $mainmenu.index($selectedMainMenu);
					} else {
						$selectedMainMenu = $mainmenu.eq(n);
						mIndex = n;
					}
				}

				if (mIndex == -1)
				{
					mIndex = 0;
				}

				if (!$selectedMainMenu.length && !($selectedMainMenu = $mainmenu.eq(0)).length) {
					$selectedMainMenu  = null;
					return;
				}

				$selectedMainMenu.addClass('current').trigger('blur');
				// show selected sub-menu container
				$selectedSubMenuContainer = $submenu_container.eq($mainmenu.index($selectedMainMenu));
				selectSubMenu(m);
				$submenu_container.hide();
				$selectedSubMenuContainer.show();
			}
		},

		/**
		 * select sub menu
		 * @param n HTMLElement|int|'.default'
		 *
		 */
		selectSubMenu: function(n, jump) {
			jump = jump == undefined? true : false;
			with (admin.index) {
				if (!$submenu_container) {
					return;
				}
				if ($selectedSubMenu && $selectedSubMenu.length) {
					$selectedSubMenu.parent('li').removeClass('current');
				}
				if (!$selectedSubMenuContainer.length && !($selectedSubMenuContainer = $submenu_container.eq(0)).length) {
					$selectedSubMenuContainer = null;
					return;
				}

				$submenu = $selectedSubMenuContainer.find('a');
				n = isNumber(n) || isElement(n) || isString(n) ? n : '.default';
				if (n == '.default') {
					$selectedSubMenu = $submenu.filter('.default');
				}else if(isString(n) && n.indexOf('#') === 0) {
					$selectedSubMenu = $(n);
				} else {
					$selectedSubMenu = isElement(n) ? $(n) : $submenu.eq(n);
				}

				sIndex = $submenu.index($selectedSubMenu);
				(sIndex == -1) && (sIndex = 0);

				if (!$selectedSubMenu.length && !($selectedSubMenu = $submenu.eq(0)).length) {
					$selectedSubMenu = null;
					return false;
				}
				$selectedSubMenu.parent('li').addClass('current').trigger('blur');
				if (jump) {
					var router = isElement(n) ? '&router='+$(n).attr('router') : '';
					$('#mainframe').attr('src', $selectedSubMenu.attr('href') + router );
				}

				var hash = [mIndex,sIndex].join(',');
				window.location.hash = hash
			}
		},

		init: function() {
			with (admin.index) {
				// init main menu
				$mainmenu_container = $('#header ul li');
				$submenu_container = $('#side-menu > .menu-group ');
				$mainmenu = $mainmenu_container;

				// attach event to container
				$mainmenu_container.click(function(e) {
						selectMainMenu(this);
						return false;
				});
				$submenu_container.click(function(e) {
					if (e.target.tagName.toLowerCase() == 'a') {
						console.log(e.target);
						selectSubMenu(e.target);
						return false;
					}
				})

				var n = [], hash = window.location.hash;
				if (hash.length>1)
				{
					n = hash.substr(1).split(',', 2);
					selectMainMenu(parseInt(n[0]), parseInt(n[1]));
				} else {
					selectMainMenu();
				}
			}
		}
	}
}

$.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};


//濡傦細1.1.0
function compareVersion(ver1, ver2) {
	var arr1 = ver1.split('.');
	var arr2 = ver2.split('.');
	
	var maxLen = arr1.length == arr2.length ? arr1.length: Math.max(arr1.length, arr2.length);

	for (var i = 0; i < maxLen ; i++ )
	{
		var part1 = arr1[i],part2 = arr2[i];

		if (!part1 && part2)
		{
			return 1;
		} else if (!part1 && !part2)
		{
			continue;
		} else if (part1 && !part2)
		{
			return -1;
		}

		part1 = parseInt(part1);
		part2 = parseInt(part2);

		if (part1 === part2)
		{
			continue;
		}

		return part1 - part2;
	}

	return 0;
}

//妫€娴嬫柊鐗堟湰
// 涓嬫鍐嶈锛氬綋鏃ヤ笉鍐嶅嚭鐜�
// 鍙夊弶锛� 鐧诲綍浼氬啀鍑虹幇
// 鐐瑰嚮鍗囩骇閾炬帴锛氬綋鏃ヤ笉鍐嶅嚭鐜�

var update_cookie = 'noUpdate';

function checkNewVer(url, currVer) {
	if ($.cookie(update_cookie))
	{
		return;
	}

	$.getJSON(url + '&jsonp=?',
	function (r) {

		if (!(r && r.downurl && r.ver))
		{
			return;
		}

		if (compareVersion(r.ver, currVer) > 0)
		{
			var html = [
				'<div class="update-tips fixed-update" id="xwb_update_tips">',
				'<h4><a class="clos" id="ud_close" href="javascript:;"></a>鎻愮ず</h4>',
				'<div class="add-float-content">',
				r.text,
				'<a id="up_yes" class="update-link" href="'+r.downurl+'" target="_blank">绔嬪嵆鍗囩骇</a><a href="javascript:;" id="up_no">浠ュ悗鍐嶈</a>',
				'</div></div>'
			].join('');

			var $tips = $(html);

			$tips.find('#ud_close').click(function(e) { //鍏抽棴
				$.cookie(update_cookie, 1);
				$tips.hide();
			});
			
			$tips.find('#up_no').click(function(e) { //鍙栨秷
				$.cookie(update_cookie, 1, {expires:1});
				$tips.hide();
			});

			$tips.find('#up_yes').click(function(e) {
				$.cookie(update_cookie, 1, {expires:1});
				$tips.hide();
			});

			$tips.appendTo('body');
		}

	});
}

	
