package com.kiwoong.cmssign;

import org.json.JSONObject;

public interface OnConnectHttp {
	void	onConnectOk(byte[] object);
	void	onConnectFail(byte[] object);
	void    onConnectFinesh();	
}
