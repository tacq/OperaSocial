package com.ramytech.android.util.client;

public enum ReturnResult {
	OK, // 成功
	ERR_INVAILD_PARAM, // 不合法的参数
	ERR_SERVER_INTERNAL_ERROR, // 服务器端错误
	ERR_ACTION_NOT_SUPPORT, // 不支持的Action
	ERR_USER_NOT_EXISTS, // 用户不存在
	ERR_USERNAME_ALREADY_EXISTS, // 用户名已经存在
	ERR_EMAIL_ALREADY_EXISTS, ERR_USER_PW_NOT_MATCH, ERR_SEED_TIMEOUT, ERR_SEED_HASH_NOT_MATCH, ERR_NO_CONERENCE_OR_ROLE_SELECTED, ERR_CONFERENCE_NOT_EXIST, ERR_NOT_PERMITTED, ERR_ROLE_CONFERENCE_NOT_MATCH, NO_DATA, // 暂无数据
	ERR_PUSH_MESSAGE, // 消息推送出错
	ERR_SEND_VERIFICATIONCODE, // 发送验证码出错
	ERR_TEL_ALREADY_EXISTS, // 手机号已经存在
	ERR_AUTH_CODE, // 验证码不正确
}
