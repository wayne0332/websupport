function startRequest(keyName, keyValue, errorPage,rootPath) {
	var xmlHttp = new XMLHttpRequest();
	// var url = "timeServlet?keyName=" + keyName + "&keyValue=" + keyValue
	// + "&forbiddenPageUrl=" + forbiddenPageUrl;
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var doc = xmlHttp.responseXML;
				var nameRepeat = doc.getElementsByTagName("isRedirect");
				var isRedirect = nameRepeat.item(0).firstChild.nodeValue;
				if (isRedirect == 'true') {
					self.location = errorPage;
				}
			}
		}
	};
	xmlHttp.open("POST", rootPath + "/devil/action/ConcurrentControlAction", true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	//xmlHttp.setRequestHeader('If-Modified-Since', '0'); //������ie�Ỻ��·�� �������
	// ·��һ���Ļ��Ͳ�������= =!
	xmlHttp.send("keyName=" + keyName + "&keyValue=" + keyValue);
}

function start(keyName, keyValue, errorPage, rootPath) {
	startRequest(keyName, keyValue, errorPage,rootPath);
	setTimeout("start('" + keyName + "','" + keyValue + "','" + errorPage + ',' + rootPath
			+ "')", 120000);
}