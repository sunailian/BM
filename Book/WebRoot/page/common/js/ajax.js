var ACT_SELECT = 1;// 下拉框
var ACT_TABLE_SUB = 2;// 表格：子表
var ACT_TABLE_ALLOT = 3;// 表格：调拨
var ACT_TABLE_RETURN = 4;// 表格：退货
var ACT_IMEI_CHECK = 21;// 多选框：IMEI检测
var ACT_IMEI_INPUT = 22;// 多选框：IMEI检测
var ACT_DATA_ALLOTAPPLY = 51;// 字段：调拨
var ACT_DATA_RETURNAPPLY = 52;// 字段：退货
var ACT_CHECK_PRODUCT = 101;// 检测：机型
var ACT_SPAN = 9;// span或DIV或文本框

var act = ACT_SELECT;// 操作类型
var sourceTag;// 源控件对象
var targetTag;// 目标控件对象
var myValue;// 远程验证后的值
var rowid;

// 表格
var tableType = TABLE_FILLPROV2PROV;
var TABLE_FILLPROV = 1;// 省仓补货
var TABLE_FILLPROV2PROV = 2;// 省仓横向补货
var TABLE_FILLHALL = 3;// 服务厅补货

function setSourceTag(sourceID) {
	sourceTag = document.getElementById(sourceID);
}

function clearSourceTag() {
	sourceTag = null;
}

/**
 * 状态信息显示
 * @author dyh 2011-02-05
 */
var Status = new function() {
	this.statusDiv = null;
	this.x = 3;
	this.y = 3;

	/**
	 * 初始化状态显示层
	 */
	this.init = function() {
		this.initXY();
		if (this.statusDiv != null) {
			this.statusDiv.style.left = this.x;
			this.statusDiv.style.top = this.y;
			return;
		}
		var body = document.getElementsByTagName("body")[0];
		var div = document.createElement("div");
		div.style.position = "absolute";
		div.style.left = this.x;
		div.style.top = this.y;
		div.style.width = "120px";
		div.style.height = "15px";
		div.style.padding = "0px";
		div.style.backgroundColor = "#D3F764";
		div.style.border = "1px solid #FFFFFF";
		div.style.color = "black";
		div.style.fontSize = "12px";
		div.style.zIndex = 1000;
		div.style.textAlign = "center";
		div.id = "ajaxstatus";
		body.appendChild(div);
		div.style.display = "none";
		this.statusDiv = div;
	}

	this.initXY = function() {
		if (sourceTag) {
			this.x = sourceTag.offsetLeft;
			this.y = sourceTag.offsetTop;
		} else {
			var e = window.event;
			if (e) {
				this.x = e.clientX;
				this.y = e.clientY;
			}
		}
	}

	/**
	 * 设置状态信息
	 * @param _message:要显示的信息
	 */
	this.loading = function() {
		this.init();
		this.statusDiv.innerHTML = "<img src='/img/loading.gif' width='15' height='15'> 数据加载中...";
		if (this.statusDiv.style.backgroundColor == "#6699cc") {
			this.statusDiv.style.backgroundColor = "#D3F764";
			this.statusDiv.style.width = "120px";
			this.statusDiv.style.height = "15px";
		}
		this.setStatusShow(true);
	}

	this.showInfo = function(_message) {
		this.init();
		this.statusDiv.style.width = "180px";
		this.statusDiv.style.backgroundColor = "#6699cc";
		this.setStatusShow(true);
		this.statusDiv.innerHTML = _message;
	}

	/**
	 * 设置状态层是否显示
	 * @param _show:true为显示，false为不显示
	 */
	this.setStatusShow = function(_show) {
		this.init();
		if (_show) {
			this.statusDiv.style.display = "";
		} else {
			this.statusDiv.innerHTML = "";
			this.statusDiv.style.display = "none";
		}
	}
}

/**
 * 存放通道名称及通信对象的类
 * @author dyh 2011-02-05
 */
function HttpRequestObject(obj) {
	if (obj) {
		this.channel = obj.channel;
		this.instance = obj.instance;
	} else {
		this.channel = null;
		this.instance = null;
	}
}

/**
 * 存放缓存数据的类
 * @author dyh 2011-02-05
 */
function DataCacheObject(key, value) {
	if (key) {
		this.key = key;
		this.value = value;
	} else {
		this.key = null;
		this.value = null;
	}
}

/**
 * 通信处理类
 * @author dyh 2011-02-05
 */
var Request = new function() {
	this.showStatus = true;

	// 通信类的缓存
	this.httpRequestCache = new Array();

	// 数据缓存
	this.dataCache = new Array();

	/**
	 * 创建新的通信对象
	 * @return 一个新的通信对象
	 */
	this.createInstance = function() {
		var instance = null;
		if (window.XMLHttpRequest) {
			// mozilla
			instance = new XMLHttpRequest();
			// 有些版本的Mozilla浏览器处理服务器返回的未包含XML
			// mime-type头部信息的内容时会出错。因此，要确保返回的内容包含text/xml信息
			if (instance.overrideMimeType) {
				instance.overrideMimeType = "text/xml";
			}
		} else if (window.ActiveXObject) {
			// IE
			var MSXML = [ 'MSXML2.XMLHTTP.5.0', 'Microsoft.XMLHTTP', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP' ];
			for ( var i = 0; i < MSXML.length; i++) {
				try {
					instance = new ActiveXObject(MSXML[i]);
					break;
				} catch (e) {
				}
			}
		}
		return instance;
	}

	/**
	 * 获取一个通信对象 若没指定通道名称，则默认通道名为"default" 若缓存中不存在需要的通信类，则创建一个，同时放入通信类缓存中
	 * @param _channel：通道名称，若不存在此参数，则默认为"default"
	 * @return 一个通信对象，其存放于通信类缓存中
	 */
	this.getInstance = function(_channel) {
		var instance = null;
		var object = null;
		if (_channel == undefined)// 没指定通道名称
		{
			_channel = "default";
		}
		var getOne = false;
		for ( var i = 0; i < this.httpRequestCache.length; i++) {
			object = this.httpRequestCache[i];
			if (object.channel == _channel) {
				if (object.instance.readyState == 0 || object.instance.readyState == 4) {
					instance = object.instance;
				}
				getOne = true;
				break;
			}
		}
		if (!getOne) // 对象不在缓存中，则创建
		{
			object = new HttpRequestObject();
			object.channel = _channel;
			object.instance = this.createInstance();
			this.httpRequestCache.push(object);
			instance = object.instance;
		}
		return instance;
	}

	/**
	 * 根据_key获取缓存数据
	 */
	this.getDataCache = function(_key) {
		var instance = null;
		var object = null;
		if (_key) {
			for ( var i = 0; i < this.dataCache.length; i++) {
				object = this.dataCache[i];
				if (object.key == _key) {
					instance = object.value;
					break;
				}
			}
		}
		return instance;
	}
	/**
	 * 设置缓存数据
	 */
	this.setDataCache = function(_key, _value) {
		var _object = new DataCacheObject(_key, _value);
		this.dataCache.push(_object);
		return true;
	}

	/**
	 * 客户端向服务端发送请求
	 * @param _url:请求目的
	 * @param _data:要发送的数据(多个值使用`间隔)
	 * @param _processRequest:用于处理返回结果的函数，其定义可以在别的地方，需要有一个参数，即要处理的通信对象
	 * @param _dataCacheKey:如果_dataCacheKey为null，则不缓存数据;如果有值，则用_dataCacheKey获取缓存数据
	 * @param _channel:通道名称，可不填，缺省值为"default"
	 * @param _asynchronous:是否异步处理，，可不填，缺省值为true,即异步处理
	 */
	this.send = function(_url, _data, _processRequest, _dataCacheKey, _channel, _asynchronous) {
		if (typeof (_processRequest) != "function") {
			return;
		}
		if (this.showStatus) {
			Status.loading();
		}
		var _obj = null;
		var _cacheFlag = (_dataCacheKey != null && _dataCacheKey.length > 0);// 是否使用缓存
		if (_cacheFlag) {
			_obj = this.getDataCache(_dataCacheKey);
			if (_obj) {
				Status.setStatusShow(false);
				Request.showStatus = true;
				_processRequest(_obj);
				return;
			}
		}
		if (_url.length == 0 || _url.indexOf("?") == 0) {
			Status.showInfo("由于url为空，请求失败。<br>请与管理员联系！");
			window.setTimeout("Status.setStatusShow(false)", 5000);
			return;
		}

		if (_channel == undefined || _channel == "") {
			_channel = "default";
		}
		if (_asynchronous == undefined) {
			_asynchronous = true;
		}
		try {
			var instance = this.getInstance(_channel);
		} catch (e) {
			alert(e.message);
		}
		if (instance == null) {
			Status.showInfo("浏览器不支持ajax。<br>请与管理员联系！")
			window.setTimeout("Status.setStatusShow(false)", 5000);
			return;
		}
		// _url加一个时刻改变的参数，防止由于被浏览器缓存后同样的请求不向服务器发送请求
		if (_url.indexOf("?") != -1) {
			_url += "&requestTime=" + (new Date()).getTime();
		} else {
			_url += "?requestTime=" + (new Date()).getTime();
		}
		if (_data.length == 0) {
			instance.open("GET", _url, _asynchronous);
			instance.send(null);
		} else {
			instance.open("POST", _url, _asynchronous);
			instance.setRequestHeader("Content-Length", _data.length);
			instance.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			instance.send(_data);
		}
		instance.onreadystatechange = function() {
			if (instance.readyState == 4) // 判断对象状态
			{
				if (instance.status == 200) // 信息已经成功返回，开始处理信息
				{
					Status.setStatusShow(false);
					Request.showStatus = true;
					if (_cacheFlag) {
						Request.setDataCache(_dataCacheKey, processXML(instance));
					}
					_processRequest(instance);
				} else {
					Status.showInfo("您所请求的页面有异常。<br>请与管理员联系！");
					window.setTimeout("Status.setStatusShow(false)", 5000);
				}
			}
		}
	}
}
/**
 * 执行ajax(有缓存)
 * @param myact:动作
 * @param sql_key:使用key获取SQL
 * @param sql_para:SQL参数(多个使用`间隔)
 * @return
 */
function myAjax(myact, sql_key, sql_para) {
	act = myact;
	Request.send("/Ajax", "key=" + sql_key + "&para=" + sql_para, processData, sql_key + sql_para);
}

/**
 * 执行ajax(无缓存)
 * @param myact:动作
 * @param sql_key:使用key获取SQL
 * @param sql_para:SQL参数(多个使用`间隔)
 * @return
 */
function myAjaxNoCache(myact, sql_key, sql_para) {
	act = myact;
	Request.send("/Ajax", "key=" + sql_key + "&para=" + sql_para, processData, null);
}

/**
 * ajax动态更新下拉框
 * @param sql_key:使用key获取SQL
 * @param sql_para:SQL参数(多个使用`间隔)
 * @param sourceSelectID:源下拉框ID
 * @param targetSelectID:目标下拉框ID
 * @return
 */
function changeSelect(sql_key, sql_para, sourceID, targetID) {
	setSourceTag(sourceID);
	targetTag = document.getElementById(targetID);
	if (targetTag && sourceTag) {
		clearSelect(targetID);
		if (sourceTag.value != SELECT_FIRST_VALUE && sourceTag.value != "") {
			myAjax(ACT_SELECT, sql_key, sql_para);
		}
	}
}

/**
 * 处理ajax的XML数据
 * @param obj
 * @return arrValues
 */
function processXML(obj) {
	var arrValues = null;
	if (obj) {
		var _data = obj.responseXML;
		if (_data) {
			var _root = _data.getElementsByTagName("root");
			if (_root) {
				var _root0 = _root[0];
				if (_root0) {
					var _colcount = parseInt(_root0.getAttribute("colcount"), 10);
					if (_colcount > 0) {
						var _children = _root0.childNodes;
						var _child, _value;
						arrValues = new Array(_children.length);// 存放ajax值的数组
						for ( var i = 0; i < _children.length; i++) {
							_child = _children[i];
							arrValues[i] = new Array(_colcount);
							for ( var j = 0; j < _colcount; j++) {
								arrValues[i][j] = _child.getAttribute("col" + j);
							}
						}
					}
				}
			}
		}
	}
	return arrValues;
}

/**
 * 处理ajax数据
 * @param obj
 * @return
 */
function processData(obj) {
	var arrValues = null;
	if (isArray(obj)) {
		arrValues = obj;
	} else {
		arrValues = processXML(obj);
	}
	if (arrValues) {
		switch (act) {
		case ACT_SELECT:
			buildSelect(arrValues, targetTag);
			break;
		case ACT_TABLE_SUB:
			buildSubTable(arrValues);
			break;
		case ACT_IMEI_INPUT:
			buildIMEIInput(arrValues);
			break;
		case ACT_IMEI_CHECK:
			buildIMEICheck(arrValues);
			break;
		case ACT_TABLE_ALLOT:
			addAllotRow(arrValues);
			break;
		case ACT_DATA_ALLOTAPPLY:
			allotApplySubmit(arrValues);
			break;
		case ACT_TABLE_RETURN:
			addReturnRow(arrValues);
			break;
		case ACT_DATA_RETURNAPPLY:
			returnApplySubmit(arrValues);
			break;
		case ACT_CHECK_PRODUCT:
			getProductResult(arrValues);
			break;
		case ACT_SPAN:
			buildSpan(arrValues);
			break;
		}
	}
}

/**
 * @param id
 * @param str
 * @param sel
 * @return
 */
function buildSelect(arrValues, targetTag) {
	if (arrValues && targetTag) {
		var len = arrValues.length;
		if (len > 0) {
			var firsttext = targetTag.options[0].text;
			if (firsttext == '全部' || firsttext == '请选择')
				targetTag.options.length = 1;
			else
				targetTag.options.length = 0;
			var id, value;
			for ( var i = 0; i < arrValues.length; i++) {
				id = arrValues[i][0];
				value = arrValues[i][1];
				targetTag.options[targetTag.options.length] = new Option(value, id);
			}
		}
	}
}

/**
 * 添加一行
 * @param arrTDHTML
 *            单元格HTML
 * @param rowidTagID
 *            存放行号id的控件ID
 * @param tableID
 *            表ID
 * @param hasMyButton
 *            是否包括自定义按钮。false表示每行自动“删除”按钮;true表示不自动添加，完全根据arrTDHTML
 * @return
 */
function addRow(arrTDHTML, rowidTagID, tableID, headrows, hasMyButton) {
	if (!isArray(arrTDHTML))
		return;
	if (rowidTagID == undefined || rowidTagID == null || rowidTagID == "") {
		rowidTagID = "rowids";
	}
	var rowids = document.getElementById(rowidTagID);
	if (rowids == null)
		return;
	if (tableID == undefined || tableID == null || tableID == "") {
		tableID = "mytable";
	}
	var mytable = document.getElementById(tableID);
	if (mytable == null)
		return;
	if (hasMyButton == undefined || hasMyButton == null) {
		hasMyButton = false;
	}
	if (headrows == undefined || headrows == null) {
		headrows = 1;
	}
	var row = mytable.insertRow(mytable.rows.length);
	row.align = "center";
	row.id = rowindex;

	var len = arrTDHTML.length;

	var td;
	for (i = 0; i < len; i++) {
		td = row.insertCell();
		td.innerHTML = arrTDHTML[i];
	}
	// 自动添加“删除”按钮
	if (!hasMyButton) {

		td = row.insertCell();
		td.innerHTML = '<input type=button value="删除" class="button" onclick="delRow(' + rowindex + ',\'' + rowidTagID + '\',\'' + tableID + '\','
				+ headrows + ')">';
	}
	rowids.value += rowindex + ",";
	rowindex++;
}

/**
 * 删除一行
 * @param id
 *            序号
 * @param rowidTagID
 *            存放id的控件ID
 * @param tableID
 *            表ID
 * @param headrows
 *            表头行数
 * @return
 */
function delRow(id, rowidTagID, tableID, headrows) {
	if (rowidTagID == undefined || rowidTagID == null || rowidTagID == "") {
		rowidTagID = "rowids";
	}
	var rowids = document.getElementById(rowidTagID);
	if (rowids == null)
		return;
	if (tableID == undefined || tableID == null || tableID == "") {
		tableID = "mytable";
	}
	var mytable = document.getElementById(tableID);
	if (mytable == null)
		return;
	if (headrows == undefined || headrows == null) {
		headrows = 1;
	}
	var row = document.getElementById(id);
	if (row != null) {
		mytable.deleteRow(row.rowIndex);
		rowids.value = rowids.value.replaceAll(id + ",", "");
		if (mytable.rows.length == headrows) {// 如果已全部移除，则需要将有些控件清除
			var oImeiprovider = document.getElementById("imeiprovider");
			if (oImeiprovider != null) {
				oImeiprovider.value = "";
			}
		}

	} else {
		alert("对不起，目前无法删除该行!");
	}
}

// headrows:表头行数
function delAllRow(headrows) {
	if (headrows == null || headrows <= 0) {
		startrow = 1
	}
	var mytable = document.getElementById("mytable");
	if (mytable == null)
		return;
	var rowcount = mytable.rows.length;
	for ( var i = 1; i < rowcount - headrows + 1; i++) {
		row = document.getElementById(i);
		if (row != null) {
			mytable.deleteRow(row.rowIndex);
		} else {
			alert("对不起，目前无法删除该行!");
		}
	}
}

/**
 * 清空表格
 * @param ExcelType
 * @return
 */
function clearTable() {
	var mytable = document.getElementById("mytable");
	if (mytable == null)
		return;
	var rowcount = mytable.rows.length;
	if (rowcount > 1) {
		if (confirm("您确定要清空所有数据吗？")) {
			delAllRow(1);
		}
	}
}

// 下拉框：常用业务方法
// 获取区/县移动下拉框
function getCountyFull(areaid, channeltype, DDflag, provider) {
	clearSelect(TAGID_HALL);
	if (provider != '' && provider != '-1') {
		if (provider == 'ZD')
			getCountyByZDdepot(areaid);
		else
			getCountyByProvider(areaid, provider);
	} else {
		if (channeltype != '' && channeltype != '-1') {
			var oChannelType = document.getElementById("channeltype");
			if (oChannelType != null) {
				channeltype = oChannelType.value;
			}
		}
		if (DDflag == '1' || DDflag.toLocaleLowerCase() == 'true') {
			if (channeltype != '' && channeltype != '-1') {
				getCountyByDD_Channeltype(areaid, channeltype);
			} else {
				getCountyByDD(areaid);
			}
		} else if (channeltype != '' && channeltype != '-1') {
			getCountyByChanneltype(areaid, channeltype);
		} else {
			getCounty(areaid);
		}
	}
}
// 获取区/县移动下拉框
function getCounty(areaid) {
	changeSelect('mobile_county', areaid, TAGID_CITY, TAGID_COUNTY);
}

// 获取区/县移动下拉框(分渠道)
function getCountyByChanneltype(areaid, channeltype) {
	changeSelect('mobile_county_channeltype', areaid + '`' + channeltype, TAGID_CITY, TAGID_COUNTY);
}

// channeltype的参与调度服务厅相应的区/县移动下拉框(调度)
function getCountyByDD_Channeltype(areaid, channeltype) {
	changeSelect('mobile_county_DD_channeltype', areaid + '`' + channeltype, TAGID_CITY, TAGID_COUNTY);
}

// 所有参与调度服务厅相应的区/县移动下拉框(调度)
function getCountyByDD(areaid) {
	changeSelect('mobile_county_DD', areaid, TAGID_CITY, TAGID_COUNTY);
}

// 根据省平台获取区/县移动下拉框
function getCountyByProvider(areaid, provider) {
	changeSelect('mobile_county_provider', areaid + '`' + provider, TAGID_CITY, TAGID_COUNTY);
}

// 根据市平台获取区/县移动下拉框
function getCountyByCityProvider(cityprovider) {
	changeSelect('mobile_county_cityprovider', cityprovider, 'cityprovider', TAGID_COUNTY);
}

// 根据终端公司库存获取区/县移动下拉框
function getCountyByZDdepot(areaid) {
	changeSelect('mobile_county_ZD_depot', areaid, TAGID_CITY, TAGID_COUNTY);
}

// 服务厅下拉框
function getHallFull(county, channeltype, DDflag, provider) {
	if (provider != '' && provider != '-1') {
		getHallByProvider(county, provider);
	} else {
		if (channeltype != '' && channeltype != '-1') {
			var oChannelType = document.getElementById("channeltype");
			if (oChannelType != null) {
				channeltype = oChannelType.value;
			}
		}
		if (DDflag == '1' || DDflag.toLowerCase() == 'true') {
			if (channeltype != '' && channeltype != '-1') {
				getHallByDD_Channeltype(county, channeltype);
			} else {
				getHallByDD(county);
			}
		} else if (channeltype != '' && channeltype != '-1') {
			getHallByChanneltype(county, channeltype);
		} else {
			getHall(county);
		}
	}
}

// 根据区/县获取服务厅下拉框
function getHall(county) {
	changeSelect('mobile_hall', county, TAGID_COUNTY, TAGID_HALL);
}

// 根据区/县和渠道类型获取服务厅下拉框
function getHallByChanneltype(county, channeltype) {
	changeSelect('mobile_hall_channeltype', county + '`' + channeltype, TAGID_COUNTY, TAGID_HALL);
}

// channeltype的参与调度服务厅相应的区/县下的服务厅下拉框
function getHallByDD_Channeltype(county, channeltype) {
	changeSelect('mobile_hall_DD_channeltype', county + '`' + channeltype, TAGID_COUNTY, TAGID_HALL);
}

// 所有参与调度服务厅下拉框
function getHallByDD(county) {
	changeSelect('mobile_hall_DD', county, TAGID_COUNTY, TAGID_HALL);
}
// 根据省平台获取服务厅
function getHallByProvider(county, provider) {
	changeSelect('mobile_hall_provider', county + '`' + provider, TAGID_COUNTY, TAGID_HALL);
}
// 根据市平台获取服务厅
function getHallByCityProvider(county, cityprovider) {
	if (cityprovider == null || isNaN(cityprovider)) {
		var cityproviderSelect = document.getElementById("cityprovider");
		if (cityproviderSelect != null) {
			cityprovider = cityproviderSelect.value;
		}
	}
	changeSelect('mobile_hall_cityprovider', county + '`' + cityprovider, TAGID_COUNTY, TAGID_HALL);
}

// 根据终端公司库存获取服务厅
function getHallByZDdepot(county) {
	changeSelect('mobile_hall_ZD_depot', county, TAGID_COUNTY, TAGID_HALL);
}
/**
 * 调度中心的区域:根据zoneid改变SelectMobileTag
 * @author dyh 2011-12-05
 * @param zoneid
 * @return
 */
function changeZone(zoneid) {
	clearSelect("areaid");
	clearSelect("county");
	clearSelect("hall");
	if (zoneid != "-1" && zoneid != "05") {
		changeSelect('mobile_zone', zoneid, TAGID_ZONE, TAGID_CITY);
	}
}
/**
 * 调度中心的区域:根据zoneid改变SelectAreaTag
 * @author dyh 2011-12-05
 * @param zoneid
 * @return
 */
function changeZoneForArea(zoneid) {
	clearSelect("areaid");
	if (zoneid != "-1" && zoneid != "05") {
		changeSelect('mobile_zone', zoneid, TAGID_ZONE, TAGID_CITY);
	}
}
/**
 * 区域中心的区域:根据zoneid改变SelectMobileTag
 * @author dyh 2011-12-05
 * @param zoneid
 * @return
 */
function changeQY(zoneid) {
	clearSelect("areaid");
	clearSelect("county");
	clearSelect("hall");
	if (zoneid != "-1" && zoneid != "05") {
		changeSelect('mobile_QY', zoneid, TAGID_ZONE, TAGID_CITY);
	}
}
/**
 * 区域中心的区域:根据zoneid改变SelectAreaTag
 * @author dyh 2011-12-05
 * @param zoneid
 * @return
 */
function changeQYForArea(zoneid, id) {
	var groupid;
	if (!id)
		groupid = "zoneid";
	else
		groupid = id;
	clearSelect("areaid");
	if (zoneid != "-1" && zoneid != "05") {
		changeSelect('mobile_QY', zoneid, groupid, TAGID_CITY);
	}
}

// 获取服务厅所属平台-当前库存
function getProviderByHallDepot(hall) {
	changeSelect('hall_provider_depot', hall, TAGID_HALL, 'provider');
}

// 获取服务厅所属平台
function getProviderByHall(hall) {
	changeSelect('hall_provider', hall, TAGID_HALL, 'provider');
}

// 根据产品ID获取合同信息
function getConstractsByProductID(productid) {
	changeSelect('contract_productid', productid, 'productid', 'contractid');
}

function getBrandNameByProvider(orgcode, providercode, provid) {
	clearSelect("productid");
	if (providercode != "-1") {
		var oProvider = document.getElementById('__provider');
		oProvider.value = providercode;
		var hasProvid = provid > 0;
		changeSelect('brand_depot_provider' + (hasProvid ? "_provid" : ""), orgcode + "`" + providercode + (hasProvid ? "`" + provid : ""),
				'provider', 'brandcode');
	}
}

function getBrandNameByProvid(orgcode, providercode, provid) {
	clearSelect("productid");
	if (providercode != "-1") {
		var oProvider = document.getElementById('__provider');
		oProvider.value = providercode;
		var hasProvid = provid > 0;
		changeSelect('brand_depot_provider' + (hasProvid ? "_provid" : ""), orgcode + "`" + providercode + (hasProvid ? "`" + provid : ""),
				'toprovid', 'brandcode');
	}
}

function getBrandNameByProviderID(orgcode, providerID, provid) {
	clearSelect("productid");
	if (providerID > 0) {
		var oProvider = document.getElementById('__provider');
		oProvider.value = providerID;
		var hasProvid = provid > 0;
		changeSelect('brand_depot_providerid' + (hasProvid ? "_provid" : ""), orgcode + "`" + providerID + (hasProvid ? "`" + provid : ""),
				'provider', 'brandcode');
	}
}

function getBrandNameByVendorID(orgcode, vendorid, provid) {
	clearSelect("productid");
	if (vendorid != "-1") {
		var hasProvid = provid > 0;
		changeSelect('brand_depot_vendorid' + (hasProvid ? "_provid" : ""), orgcode + "`" + vendorid + (hasProvid ? "`" + provid : ""), 'provider',
				'brandcode');
	}
}

function getProduct(brandname, spec) {
	act = ACT_CHECK_PRODUCT;
	if (brandname != null && spec != null) {
		brandname = brandname.trim();
		spec = spec.trim();
		myValue = "";
		if (brandname.length > 0 && spec.length > 0) {
			myAjax(act, "check_brand_spec", brandname + "`" + spec);
		}
	}
	return myValue;
}
function getProductResult(arrValues) {
	if (arrValues) {
		var len = arrValues.length;
		if (len > 0) {
			myValue = arrValues[0][0];
		}
	}
}

/**
 * 根据渠道类型获取社会渠道
 * @param channeltype
 * @param showcust
 *            是否显示客户
 * @return
 */
function getRDCustByChanneltype(channeltype, showcust) {
	var id = showcust ? "custid" : "orgid"
	var sql = showcust ? "rdcust" : "socialorg";
	clearSelect(id);
	if (channeltype != "-1") {
		changeSelect(sql, channeltype, 'channeltype', id);
	}
}

/**
 * 插入新行
 */
function insertNewRow(i) {
	var newCell = document.getElementById("cell" + i);
	if (newCell == null) {
		var row = document.getElementById("row" + i);
		var newRowIndex = row.rowIndex + 1;
		var objTable = document.getElementById("mytable");
		var newRow = objTable.insertRow(newRowIndex);
		newCell = newRow.insertCell(-1);
		newCell.colSpan = objTable.rows[1].cells.length;
		newCell.id = "cell" + i;
	}
	return newCell;
}
/**
 * 显示产品明细
 */
function showDetailTable(i, bizid, tabletype) {
	act = ACT_TABLE_SUB;
	rowid = i;
	if (tabletype == undefined)
		tableType = TABLE_FILLPROV2PROV;
	else
		tableType = tabletype;
	var objImg = document.getElementById("img" + rowid);
	var detailOpened = (objImg.src.endsWith("/img/jian.gif"));
	var objTable = document.getElementById("mytable");
	if (detailOpened) {
		objImg.src = "/img/jia.gif";
		closeDetail(rowid);
	} else {
		objImg.src = "/img/jian.gif";
		var sql_para = bizid;
		var sql_key = "";
		switch (tableType) {
		case TABLE_FILLPROV:
			sql_key = "filldetail_prov";
			break;
		case TABLE_FILLPROV2PROV:
			sql_key = "filldetail_prov2prov";
			break;
		case TABLE_FILLHALL:
			sql_key = "filldetail_hall";
			break;
		}
		myAjax(act, sql_key, sql_para);
	}
}

/**
 * 生成子表页面
 */
function buildSubTable(arrValues) {
	if (arrValues) {
		var len = arrValues.length;
		if (len > 0) {
			var newCell = insertNewRow(rowid);

			if (newCell != null) {
				var out = "<table id='mydetail'><tr style='background:#B0E0E6' align='center' height='25'>";
				switch (tableType) {
				case TABLE_FILLPROV:
					out += "<td>品牌</td><td>型号</td><td>用户申请量</td><td>已审批量</td><td>已发数量</td><td>到货数量</td><td>机型说明</td>";
					break;
				case TABLE_FILLPROV2PROV:
					out += "<td rowspan='2'>品牌</td><td rowspan='2'>型号</td><td rowspan='2'>用户申请量</td><td rowspan='2'>待审批/分货量</td><td rowspan='2'>已审批量</td><td rowspan='2'>总分货量</td><td rowspan='2'>总出库量</td><td rowspan='2'>总入库量</td><td colspan='3'>广州仓</td><td colspan='3'>深圳仓</td><td colspan='3'>汕头仓</td><td rowspan='2'>机型说明</td></tr><tr style='background:#B0E0E6' align='center' height='25'><td>已分货量</td><td>已出库量</td><td>已入库量</td><td>已分货量</td><td>已出库量</td><td>已入库量</td><td>已分货量</td><td>已出库量</td><td>已入库量</td>";
					break;
				case TABLE_FILLHALL:
					out += "filldetail_hall";
					break;
				}
				out += "</tr>";
				for (i = 0; i < len; i++) {
					out += "<tr  align='center'";
					if (i % 2 != 0)
						out += " style='background:#f5f5f5'";
					out += ">";
					for (j = 0; j < arrValues[i].length; j++) {
						out += "<td>" + arrValues[i][j] + "</td>";
					}
					out += "</tr>";
				}
				out += "</table>";
				newCell.innerHTML = out;
			}
		}
	}
}

/**
 * 显示在span中
 */
function buildSpan(arrValues) {
	if (arrValues && targetTag) {
		var len = arrValues.length;
		if (len > 0) {
			var value = arrValues[0][0];
			value = (value == "null" ? "" : value);

			if (targetTag.tagName == 'DIV' || targetTag.tagName == 'SPAN') {
				targetTag.innerText = value;
			} else {
				targetTag.value = value;
			}
		}
	}
}

/**
 * 显示在span中
 * @param sql_key
 * @param sql_para
 * @param targetID
 * @return
 */
function showSpan(sql_key, sql_para, targetID) {
	if (!targetID) {
		targetID = "myspan";
	}
	targetTag = document.getElementById(targetID);
	if (targetTag) {
		if (sql_para == "-1") {
			if (targetTag.tagName == 'DIV' || targetTag.tagName == 'SPAN') {
				targetTag.innerHTML = "";
			} else {
				targetTag.value = "";
			}
		} else {
			if (targetTag.tagName == 'DIV' || targetTag.tagName == 'SPAN') {
				targetTag.innerHTML = "<font color='green'>正在获取信息...</font>";
			} else {
				targetTag.value = "";
			}
			myAjax(ACT_SPAN, sql_key, sql_para);
		}
	}
}

/**
 * 关闭明细信息
 */
function closeDetail(i) {
	var row = document.getElementById("row" + i);
	var newRowIndex = row.rowIndex + 1;
	var objTable = document.getElementById("mytable");
	objTable.deleteRow(newRowIndex);
}