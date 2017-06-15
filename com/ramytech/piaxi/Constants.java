package com.ramytech.piaxi;

public class Constants {
	public static final int ROLE_STUDENT = 1;
	public static final int ROLE_TEACHER = 0;
	
	public static final int TRACK_STATUS_FINISH = 1;

	public static final String PARTNER = "2088611478800135";
	public static final String SELLER = "zhifubao@wegenart.com";
	//支付宝公钥，此处无用，服务器用于验证异步通知消息
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//pkcs8编码 RSA 私钥
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMtDIG7X4YnzsFYHnNcN0iadd0yQPm3vE55Oo5bACt9d47t0gmw9DKyFU7yJrB10hV/q+ueo10IAuDuOt6O8ZfL73gOwuN7xUZx7YHfakuNDMDToiEFWMdkArC5G9jbjEAoYgUslDe2BTbNUwc63K+Sd5Y174eIA5UAA9hntasr7AgMBAAECgYBb11zUOJ8j4gQPGyl20jveTtAUvCg5LAlFQmsQNn2zJ406qg40MfGEEkofqmUmefu5t0bfXZ7Fp+WWo4m+ZeX58j/996O4kdYLpDW8XhDYSCMTRQyRPpa2GX6vsMVn77mq9kKMj5HtHW+6FQ8Hq9nWq9GsByojRHQBv1okgkkQoQJBAOgLj3GJHscuE9E72lUW1wulrp9FAPOhCWgD57zguFbF7A2r7dK1eJ5Q+0K821kjz9hW2OFYRLxi7EOBcJah2z8CQQDgPuWa6gjOEA1diP9rtihJs5Oq9EjCxOr7e1haY0tkwAON4XpZ3NM6XXHvJwpxNfKgWITgD3Rn0ndChdCMt41FAkAzrf9whd149uYq/22M7d1PFsNhbXjcyczSgawnANWNXd9I3DXHa+tFNzv+jRk9LSCpSg5bgdEtM8ry7e0pXzr5AkEAkt4+7S445BU5W7h9aWNdkZ/OqFnkScRFDr5jSJfd6jekXEmAI9jwczp+g56zRSCLLx+qr0EQAWuj9mrEA1W4bQJAFsBaDVuBh1g3eXmGfPrj5Z2Jy3TogH7GyGUKvp8YKXsO2udF72pUARU0Rap9jBu97TVYDN+tJUz6Yg4DS9/Ayw==";
	
	
	
	public static final String PAYTYPE_ARR[] = {"微信", "支付宝"};
	public static final String GENDER_ARR[] = {"女", "男", "未知"};
	public static final String CITY_ARR[] = {"北京", "上海", "深圳", "广州"};
	public static final String DIST_ARR[] = {"0", "10km", "50km", "100km"};
	public static final String ORDER_ARR[] = {"按等级", "按距离"};
	public static final String PRICE_ARR[] = {"不限制", "100-200元", "200-300元", "300-400元", "400-600元", "600-800元", "800+元"};
	public static final String CLASS_PLACE_ARR[] = {"皆可", "老师家", "学生家"};
	public static final String INST_ARR[] = {"视唱练耳", "音基理论", "声乐", "钢琴", "双排键", "小提琴", "中提琴", "大提琴", "低音提琴", "古典吉他", "长笛", "单簧管", "双簧管", "巴松管", "手风琴", "萨克斯", "小号", "圆号", "长号", "大号", "打击乐", "竖琴"};
	public static final String LEVEL_ARR[] = {"零基础","初级（1 - 4 级）","中级（5 - 7 级）","高级（8 - 10 级）","演奏级（Diploma）","艺考学生"};
	public static final String AGE_ARR[] = {"4 岁以下","4 岁 - 8 岁","4 岁 - 12 岁","12 岁 - 15 岁","15 岁 - 18 岁"};	
	public static final String LANGUAGE_ARR[] = {"中文", "英语（English）", "法语（français）", "西班牙语 （español）",
			"俄语  （русский）",
			"阿拉伯语  （العربية）",
			"德语 （Deutsch）",
			"日语 （日本語）",
			"葡萄牙语  （português）",
			"韩语  （한국어）",
			"意大利语 （lingua italiana）"};
	
	public static final String DEGREE_ARR[] = {"学士", "硕士", "博士", "博士后", "其他"};
}
