package com.hehenian.biz.common.util.huifu;

import java.math.BigDecimal;

/**
 * @author 杨程
 * @version Oct 19, 2011 1:55:18 PM
 * @declaration
 */
public class IConstants {
	
	/**
	 * 排序方式：倒序
	 */
	public static final String SORT_TYPE_DESC = " DESC ";
	
	/**
	 * 排序方式：正序
	 */
	public static final String SORT_TYPE_ASC = " ASC ";

	/**
	 * session内容：admin管理员
	 */
	public static final String SESSION_ADMIN="admin";
	
	/**
	 * session内容：user用户
	 */
	public static final String SESSION_USER="user";
	
	/**
	 * session内容：网站关闭状态
	 */
	public static final String Session_CLOSENETWORK="network";
	
	/**
	 * 是否开启用户请求过滤，true：开启false：关闭
	 */
	public static final boolean USER_SESSION_SWITCH = true;
	
	/**
	 * 管理员登录验证开关
	 */
	public static final boolean ADMIN_SESSION_SWITCH = true;
	
	public static final String USER_VIRTUAL = "virtual";
	
	/**
	 * 软件注册码
	 */
	public static final String REGISCODE = "regiscode";
	
	/**
	 * 管理员登录返回
	 */
	public static final String ADMIN_AJAX_LOGIN="noLogin";
	
	
	//--------------------------注册常量--------------------------//
	/**
	 * 注册成功
	 */
	public static final String USER_REGISTER_OK = "1";
	
	/**
	 * 注册失败
	 */
	public static final String USER_REGISTER_ERROR = "2";
	
	/**
	 * 注册用户名重复
	 */
	public static final String USER_REGISTER_REPEAT_NAME = "3";
	
	/**
	 * 注册邮箱重复
	 */
	public static final String USER_REGISTER_REPEAT_EMAIL = "4";
	
	/**
	 * 注册验证错误
	 */
	public static final String USER_REGISTER_CODE_ERROR = "5";
	
	/**
	 * 邮箱验证成功
	 */
	public static final int USER_EMAIL_VERIFICATION_SUCCESS = 1;
	
	public static String NICK = "桂林市合和年在线";//邮件发送人昵称
	public static  String MAIL_HOST="";
	public static  String MAIL_USERNAME="";
	public static  String MAIL_PASSWORD="";
	public static  String MAIL_FROM="";
	public static String ISDEMO="";
	
	/**
	 * 新浪: app key
	 */
	public static String SINA_APP_KEY = "";
	
	/**
	 * 新浪: secret key
	 */
	public static String SINA_SECRET_KEY = "";
	
	/**
	 * 新浪: secret key
	 */
	public static String SINA_REDIRECT_URL = "";
	
	/**
	 * 新浪: sina_oauth_url
	 */
	public static String SINA_OAUTH_URL = "";
	
	/**
	 * qq: appid
	 */
	public static String QQ_APP_KEY = "";
	
	/**
	 * qq: secret key
	 */
	public static String QQ_SECRET_KEY_SECRET = "";
	
	/**
	 * qq: appid
	 */
	public static String QQ_REDIRECT_URL = "";
	
	/**
	 * qq: secret key
	 */
	public static String QQ_OAUTH_URL = "";
	
	/**
	 * taobao: appid
	 */
	public static String TAOBAO_APP_KEY = "";
	
	/**
	 * taobao: secret key
	 */
	public static String TAOBAO_SECRET_KEY_SECRET = "";
	
	/**
	 * taobao: appid
	 */
	public static String TAOBAO_REDIRECT_URL = "";
	
	/**
	 * taobao: secret key
	 */
	public static String TAOBAO_OAUTH_URL = "";
	
	/**
	 * SP2P网贷： api key
	 */
	public static String RENREN_API_KEY = "";
	
	/**
	 * SP2P网贷：api secret
	 */
	public static String RENREN_API_SECRET = "";
	
	/**
	 * SP2P网贷： api key
	 */
	public static String RENREN_REDIRECT_URL = "";
	
	/**
	 * SP2P网贷：api secret
	 */
	public static String RENREN_OAUTH_URL = "";
	
	public static String  ZCM="";

	/**
	 * BBS url
	 */
	
	public static String BBS_URL = "";
	
	public static String PWD_SES_KEY = "";
	
	public static String BBS_SES_KEY = "";
	public static String BBS_KEY = "";
	public static String WEB_URL = "";
	/**
	 * 是否启用加密
	 */
	public static String ENABLED_PASS = "";
	/**
	 * 加密字符串
	 */
	public static String PASS_KEY ="";
	public static String PRO_GLOBLE_NAME = "";
	static{
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		/*MAIL_HOST =  pf.read("mail_host");
		MAIL_USERNAME =  pf.read("mail_username");
		MAIL_PASSWORD =  pf.read("mail_password");
		MAIL_FROM =  pf.read("mail_from");*/
		ISDEMO = pf.read("isDemo");
		BBS_URL = pf.read("bbs_url");
		PWD_SES_KEY = pf.read("pwd_ses_key");
		BBS_SES_KEY = pf.read("bbs_ses_key");
		BBS_KEY = pf.read("bbs_key");
		WEB_URL = pf.read("web_url");
		ZCM = pf.read("com.shove.security.License.SerialNumber");
		
		ENABLED_PASS =pf.read("enabled_pass");
		PASS_KEY = pf.read("pass_key");
		PRO_GLOBLE_NAME = pf.read("pro_globle_name");
		//新浪常量初始化
//		SINA_APP_KEY = pf.read("sina_app_key");
//		SINA_SECRET_KEY = pf.read("sina_secret_key");
//		SINA_REDIRECT_URL = pf.read("sina_redirect_url");
//		SINA_OAUTH_URL = pf.read("sina_oauth_url");
//		
//		QQ_APP_KEY = pf.read("qq_app_key");
//		QQ_SECRET_KEY_SECRET = pf.read("qq_secret_key_secret");
//		QQ_REDIRECT_URL = pf.read("qq_redirect_url");
//		QQ_OAUTH_URL = pf.read("qq_oauth_url");
//		
//		TAOBAO_APP_KEY = pf.read("taobao_app_key");
//		TAOBAO_SECRET_KEY_SECRET = pf.read("taobao_secret_key_secret");
//		TAOBAO_REDIRECT_URL = pf.read("taobao_redirect_url");
//		TAOBAO_OAUTH_URL = pf.read("taobao_oauth_url");
//		
//		RENREN_API_KEY = pf.read("renren_api_key");
//		RENREN_API_SECRET = pf.read("renren_api_secret");
//		RENREN_REDIRECT_URL = pf.read("renren_redirect_url");
//		RENREN_OAUTH_URL = pf.read("renren_oauth_url");
	}
	
	/**
	 * 当填写基本资料时候 手机号码绑定审核
	 */
	public static final int INSERT_BASE_TYPE = 1;
	/**
	 * 当填写手机号码变更时候 手机号码绑定审核
	 */
	public static final int INSERT_CHANGE_TYPE = 2;
	
	
	/**
	 * 用户积分类型 信用积分类型
	 */
	public static final int USER_INTERGRALTYPECREDIT = 1;
	/**
	 * 用户积分类型  vip积分类型
	 */
	public static final int USER_INTERGRALTYPEVIP = 2;
	
	
	/**
	 * 第三方oauth认证：新浪
	 */
	public final static int OAUTH_TYPE_SINA = 1;
	
	/**
	 * 第三方oauth认证：qq
	 */
	public final static int OAUTH_TYPE_QQ = 2;
	
	/**
	 * 第三方oauth认证：淘宝
	 */
	public final static int OAUTH_TYPE_TAOBAO = 3;
	
	/**
	 * 第三方oauth认证：SP2P网贷
	 */
	public final static int OAUTH_TYPE_RENREN = 4;
	
	/**
	 * 支付状态:启用
	 */
	public final static int PAYMENT_ENABLE_ON = 1;
	
	/**
	 * 支付状态：禁用
	 */
	public final static int PAYMENT_ENABLE_OFF = 2;
	
	/**
	 * 送货状态:启用
	 */
	public final static int DELIVERY_ENABLE_ON = 1;
	
	/**
	 * 送货状态：禁用
	 */
	public final static int DELIVERY_ENABLE_OFF = 2;
	
	/**
	 * 用户明细金钱类型：RMB
	 */
	public final static int USERDETAILS_MONEYTYPE_RMB=1;
	
	/**
	 * 用户明细金钱类型：EB
	 */
	public final static int USERDETAILS_MONEYTYPE_EB=2;
	
	/**
	 * 金钱类型：RMB
	 */
	public final static int MONEY_TYPE_RMB=1;
	
	/**
	 * 金钱类型:EB
	 */
	public final static int MONEY_TYPE_EB=2;
	
	/**
	 * 用户明细类型：管理员手工充值
	 */
	public final static int USERDETAILS_TYPE_USERADD=1;
	
	/**
	 * 用户明细类型：第三方充值
	 */
	public final static int USERDETAILS_TYPE_THREE=2;
	
	/**
	 * 用户明细类型：礼劵充值
	 */
	public final static int USERDETAILS_TYPE_VOUCHERS=3;
	
	/**
	 * 用户明细类型：礼劵已使用
	 */
	public final static int VOUCHERS_HAS_USED = 2;
	
	/**
	 * 用户明细类型：礼劵已过期
	 */
	public final static int VOUCHERS_HAS_EXPIRED = 3;
	
	/**
	 * 用户明细类型：消费
	 */
	public final static int USERDETAILS_TYPE_CONSUME=101;
	
	/**
	 * 订单类型：实体物品
	 */
	public final static int ORDER_TYPE_MALL=1;
	
	/**
	 * 订单类型：虚拟物品
	 */
	public final static int ORDER_TYPE_VIRTUAL=2;
	
	/**
	 * 订单类型：充值
	 */
	public final static int ORDER_TYPE_RECHARGE=3;
	
	/**
	 * 页面跳转拦截
	 */
	public static final String FRONTPAGEJUMP = "frontpagejump";
	
	
	public static final String BORROW_OTHER = "0";
	
	/**
	 * 借款类型：1 薪金贷
	 *  
	 */
    public static final String BORROW_TYPE_NET_VALUE = "1"; 
	
    /**
	 * 借款类型：2 生意贷
	 */
    public static final String BORROW_TYPE_SECONDS = "2";
    
    /**
	 * 借款类型： 3 业主贷 
	 */
    public static final String BORROW_TYPE_GENERAL = "3";
    
    /**
	 * 借款类型： 4 实地考察借款 
	 */
    public static final String BORROW_TYPE_FIELD_VISIT = "4";
    
    
    /**
	 * 借款类型：5 机构担保借款
	 */
	public static final String BORROW_TYPE_INSTITUTION_GUARANTEE = "5";
	
	/**
	 * 借款类型 6 流转标借款
	 */
	public static final String BORROW_TYPE_INSTITUTION_FLOW = "6";
	
    public static final String BORROW_TYPE_NET_VALUE_STR = "薪金贷";
    
    public static final String BORROW_TYPE_SECONDS_STR = "生意贷";
    
    public static final String BORROW_TYPE_GENERAL_STR = "业主贷";
    
    public static final String BORROW_TYPE_FIELD_VISIT_STR = "实地考察借款";
    
    public static final String BORROW_TYPE_INSTITUTION_GUARANTEE_STR = "机构担保借款";
    
    public static final String BORROW_TYPE_INSTITUTION_FLOW_STR = "流转标借款";
   
    public static final String BORROW_TYPE_FLOW ="flow";
	
	/**   
	 *每页显示列表数量6   
	 *   
	 * @since 1.0.0   
	 */   
	public static final int PAGE_SIZE_6 = 6;
	
	/**   
	 *每页显示列表数量10   
	 *   
	 * @since 1.0.0   
	 */   
	public static final int PAGE_SIZE_10 = 10;
    
	/**   
	 *每页显示列表数量15   
	 *   
	 * @since 1.0.0   
	 */
	public static final int PAGE_SIZE_15 = 15;
	
	/**   
	 *每页显示列表数量20   
	 *   
	 * @since 1.0.0   
	 */
	public static final int PAGE_SIZE_20 = 20;
	
	
	/**   
	 *费用利率0.7   
	 * @since 1.0.0   
	 */   
	public static final double FEE_RATE_1 = 0.7;
	
	
	/**   
	 *费用利率0.005   
	 * @since 1.0.0   
	 */   
	public static final double FEE_RATE_2 = 0.005;
	
	/**
	 * 债权转让服务费
	 */
	public static final double FEE_DEBT_RATE = 0.02;
	
	
	
	/**   
	 * 默认数字  
	 *   
	 * @since 1.0.0   
	 */   
	public static final int DEFAULT_NUMERIC = -1;
	
	
	/**   
	 * 站内信标题,1 借款初审报告  
	 *   
	 * @since 1.0.0   
	 */   
	public static final String MAIL_TITLE_1 = "借款初审报告";
	
	/**   
	 * 站内信标题,2 借款撤消报告  
	 *   
	 * @since 1.0.0   
	 */   
	public static final String MAIL_TITLE_2 = "借款撤消报告";
	
	
	/**   
	 *一般站内信类型   
	 *   
	 * @since 1.0.0   
	 */   
	public static final int MALL_TYPE_COMMON = 1;
	
	
	/**   
	 * 系统站内信类型   
	 *   
	 * @since 1.0.0   
	 */   
	public static final int MALL_TYPE_SYS = 2;
	
	/**   
	 * 借款状态,1 等待资料审核   
	 *   
	 * @since 1.0.0   
	 */
	public static final int BORROW_STATUS_1 = 1;
	
	/**   
	 * 借款状态,2 正在招标中   
	 *   
	 * @since 1.0.0   
	 */
	public static final int BORROW_STATUS_2 = 2;
	
	/**   
	 * 借款状态,3 满标   
	 *   
	 * @since 1.0.0   
	 */
	public static final int BORROW_STATUS_3 = 3;
	
	/**   
	 * 借款状态,4 还款中   
	 *   
	 * @since 1.0.0   
	 */
	public static final int BORROW_STATUS_4 = 4;
	
	public static final String BORROW_STATUS_4_STR = "还款中";
	
	/**   
	 * 借款状态,5 已还完   
	 *   
	 * @since 1.0.0   
	 */
	public static final int BORROW_STATUS_5 = 5;
	
	public static final String BORROW_STATUS_5_STR = "已还完";
	
	/**   
	 * 借款状态,6 流标   
	 *   
	 * @since 1.0.0   
	 */
	public static final int BORROW_STATUS_6 = 6;
	
	/**   
	 * 操作返回值,操作成功   
	 *   
	 * @since 1.0.0   
	 */   
	public static final String ACTION_SUCCESS = "操作成功";
	
	
	/**   
	 * 操作返回值,操作失败   
	 *   
	 * @since 1.0.0   
	 */   
	public static final String ACTION_FAILURE = "操作失败";
	
	/**   
	 * 操作返回值,非法操作  
	 *   
	 * @since 1.0.0   
	 */
	public static final String ACTOIN_ILLEGAL = "非法操作";
	 
	/**   
	 * 关注的模块,2 借款  
	 *   
	 * @since 1.0.0   
	 */
	public static final int FOCUSON_BORROW = 2;
	
	/**   
	 * 关注的模块, 1 用户
	 *   
	 * @since 1.0.0   
	 */
	public static final int FOCUSON_USER = 1;
	
	/**   
	 * 验证码错误
	 *   
	 * @since 1.0.0   
	 */
	public static final String CODE_FAULS = "验证码错误";
	
	/**
	 * 站内信状态(1 默认未读 2 删除 3 已读)
	 */
	public static final int MAIL_UN_READ = 1;
	
	//public static final int MAIL_DELETE = 2;
	
	public static final int MAIL_READED = 3;
	
	/**
	 * //[通知方式(1 邮件 2 站内信 3 短信]
	 */
	public static final int EMAIL_NOTICE = 1;
	
	public static final int MAIL_NOTICE = 2;
	
	public static final int NOTE_NOTICE = 3;
	
	/**
	 * [通知状态(1 关闭 2 开启 ]
	 */
	public static final int NOTICE_OFF = 1;
	
	public static final int NOTICE_ON = 2;
	
	/**
	 * 系统管理员标识
	 */
	public static final String MAIL_SYS = "admin";
	
	public static final int MAIL_MODE = 1;//站内信模式(1 前台 默认2 后台)
	
	public static final int MAIL_SYS_ = 2;//站内信模式(1 前台 2 后台默认)
	
	/**
	 * 手机绑定状态 状态(1已绑定  2 正在审核 3 取消)
	 */
	public static final int PHONE_BINDING_ON = 1;
	
	public static final int PHONE_BINDING_CHECK = 2;
	
	public static final int PHONE_BINDING_CANCEL = 3;
	
	public static final int PHONE_BINDING_UNPASS = 4;
	
	/**
	 * 提现状态(1 默认审核中  2 已提现 3 取消 4转账中 5 失败)
	 */
	/**
	 * 默认审核中
	 */
	public static final int WITHDRAW_CHECK = 1;
	
	/**
	 * 已提现
	 */
	public static final int WITHDRAW_SUCCESS = 2;
	
	/**
	 * 取消
	 */
	public static final int WITHDRAW_CANCEL = 3;
	
	/**
	 * 转账中
	 */
	public static final int WITHDRAW_TRANS = 4;
	/**
	 *  提现失败
	 */
	public static final int WITHDRAW_FAIL = 5;
	
	public static final String WITHDRAW_CHECK_STR = "审核中";
	
	public static final String WITHDRAW_SUCCESS_STR = "成功";
	
	public static final String WITHDRAW_CANCEL_STR = "取消";
	
	public static final String WITHDRAW_TRANS_STR = "转账中";
	
	public static final String WITHDRAW_FAIL_STR = "失败";
	
	public static final float WITHDRAW_POUNDAGE = 0.005f;
	
	/**
	 * 银行卡绑定信息
	 */
	public static final int BANK_SUCCESS = 1;
	
	public static final int BANK_CHECK = 2;
	
	public static final int BANK_FAIL= 3;
	
//	public static final int BANK_CHANGE = 4;
	
	/**
	 * 充值类型 1网上充值  2线下充值
	 */
	public static final int RECHARGE_ONLINE = 1;
	
	public static final int RECHARGE_OUTLINE = 2;
	
	public static final String RECHARGE_ONLINE_STR = "网上充值";
	
	public static final String RECHARGE_OUTLINE_STR = "线下充值";
	
	public static final String OPERATOR_ONLINE = "桂林市合和年信贷";
	
    /**
     * 
     */
    public static final int RECHARGE_FAIL = 0;
	
	public static final int RECHARGE_SUCCESS = 1;
	
	public static final String RECHARGE_CHECKING_STR = "失败";
	
	public static final String RECHARGE_SUCCESS_STR = "成功";
	
	
	/**
	 * 状态(1 非理财人  2 理财人)
	 */
	public static final int FINANCE_NON = 1;
	
	public static final int FINANCE_OK = 2;
	
	/**
	 * 还款方式(1 按月等额本息还款 2 按先息后本还款 3 秒还)
	 */
	public static final int PAY_WAY_MONTH = 1;
	
	public static final String PAY_WAY_MONTH_STR = "按月等额本息还款";
	
	public static final int PAY_WAY_RATE = 2;
	
	public static final String PAY_WAY_RATE_STR = "按先息后本还款";
	
	public static final int PAY_WAY_SECONDS = 3;
	
	public static final String PAY_WAY_SECONDS_STR = "秒还";
	
	public static final int PAY_WAY_ONE = 4;
	
	public static final String PAY_WAY_ONE_STR = "一次性";
	
	/**
	 * 还款状态（1 默认未偿还 2 已偿还 3 偿还中）
	 */
	public static final int PAYING_STATUS_NON = 1;
	
	public static final String PAYING_STATUS_NON_STR = "未偿还";
	
	public static final int PAYING_STATUS_SUCCESS = 2;
	
	public static final String PAYING_STATUS_SUCCESS_STR = "已偿还";
	
	public static final int PAYING_STATUS_PAYING = 3;
	
	public static final String PAYING_STATUS_PAYING_STR = "偿还中";
	
	public static final int ADMIN_ENABLE = 1;//t_admin用户状态
	
	public static final int WITHDRAW_MAX = 50000;//设置最大提现金额，超过之后要收取手续费
	
	/**
	 * 15天的时间限定。15天未投标的金额进行提现要收取0.5%的手续费
	 */
	public static final int WITHDRAW_TIME = 15;
	
	/**
	 * 充值管理  1 充值  2 扣费
	 */
	public static final int RECHARAGE = 1;
	public static final int WITHDRAW = 2;
	
	public static final float NOTE_CHARGE = 0.1f;
	
	
	//角色等级
	/**
	 * 团队长
	 */
	public static final int RELATION_LEVEL1 =1;
	/**
	 * 经纪人
	 */
	public static final int RELATION_LEVEL2 =2;
	/**
	 * 投资人
	 */
	public static final int RELATION_LEVEL3 =3;
	/**
	 * 理财人
	 */
	public static final int RELATION_LEVEL4 =4;
	
	//角色编号
	/**
	 * 团队长
	 */
	public static final long ADMIN_ROLE_GROUP = 1;
	
	/**
	 * 经纪人
	 */
	public static final long ADMIN_ROLE_ECONOMY = 2;
	
	//角色关系是否解除
	/**
	 * 未解除
	 */
	public static final int RELATION_ENABLE_TRUE = 1;
	
	/**
	 * 已解除
	 */
	public static final int RELATION_ENABLE_FALSE = 2;
	
	/**
	 * 理财人第一次交易奖励金额数
	 */
	public static final BigDecimal AWARD_LEVEL4= new BigDecimal(10);
	
	/**
	 * 债权转让问题帮助ID
	 */
	public static final int CREDITOR_TRANS = 5;
	
	/**
	 * 标的类型，薪金贷
	 */
	public static final int BORROWWAY_TYPE_1 = 1; 
	/**
	 * 标的类型，生意贷
	 */
	public static final int BORROWWAY_TYPE_2 = 2; 
	/**
	 * 标的类型，业主贷
	 */
	public static final int BORROWWAY_TYPE_3 = 3; 
	/**
	 * 标的类型，实地认证标的
	 */
	public static final int BORROWWAY_TYPE_4 = 4; 
	/**
	 * 标的类型，机构担保标的
	 */
	public static final int BORROWWAY_TYPE_5 = 5; 
	
	/**
	 * 操作类型，1投资
	 */
	public static final int MONEY_TYPE_1 =1 ;
	/**
	 * 操作类型，2还款
	 */
	public static final int MONEY_TYPE_2 =2 ;
	
	/**
	 * 变量类型：最大值
	 */
	public static final int MX_TYPE_MAX = 1;
	
	/**
	 * 变量类型：最小值
	 */
	public static final int MX_TYPE_MIN = 2;
	
	/**
	 * 未申请VIP
	 */
	public static final int UNVIP_STATUS = 1;
	
	/**   
	 * VIP_STATUS:正常的VIP状态   
	 *   
	 * @since 1.0.0   
	 */   
	public static final int VIP_STATUS = 2;
	
	/**
	 * 过期的vip
	 */
	public static final int VIP_FAIL= 3;
	
	/**   
	 * NOTICE_MODE_1:收到还款通知
	 *   
	 * @since 1.0.0   
	 */   
	public static final String NOTICE_MODE_1 = "reciveRepayEnable";
	
	/**   
	 * NOTICE_MODE_2: 提现成功通知
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_MODE_2 = "showSucEnable";
	
	/**   
	 * NOTICE_MODE_3: 借款成功通知
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_MODE_3 = "loanSucEnable";
	
	/**   
	 * NOTICE_MODE_4: 充值成功通知
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_MODE_4 = "rechargeSucEnable";
	
	/**   
	 * NOTICE_MODE_5: 资金变化通知
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_MODE_5 = "capitalChangeEnable";
	
	/**   
	 * NOTICE_TYPE_1: 站内信
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_TYPE_1 = "邮件";
	
	/**   
	 * NOTICE_TYPE_2: 邮件
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_TYPE_2 = "站内信";
	
	/**   
	 * NOTICE_TYPE_3: 短信
	 *   
	 * @since 1.0.0   
	 */ 
	public static final String NOTICE_TYPE_3 = "短信";
	
	
	/**   
	 * NOTICE_STATUS_1:通知状态关闭
	 *   
	 * @since 1.0.0   
	 */   
	public static final int NOTICE_STATUS_1 = 1;
	
	
	/**   
	 * NOTICE_STATUS_2:通知状态开启   
	 *   
	 * @since 1.0.0   
	 */   
	public static final int NOTICE_STATUS_2 = 2;
	
	public static final int PHONE_IMP = 1;
	
	/**
	 * 类型名称
	 */
	public static final String FUNDMODE_RECHARGE = "充值";
	
	public static final String FUNDMODE_WITHDRAW = "提现";
	
	public static final String FUNDMODE_WITHDRAW_FAIL = "提现失败";
	
	public static final String FUNDMODE_RECHARGE_HANDLE = "手动充值";
	
	public static final String FUNDMODE_WITHDRAW_HANDLE = "手动扣费";
	
	
	/**   
	 * DAY_THE_1:一般借款  
	 *   
	 * @since 1.0.0   
	 */   
	public static final int DAY_THE_1 = 1;
	
	
	/**   
	 * DAY_THE_2: 天标借款
	 *   
	 * @since 1.0.0   
	 */   
	public static final int DAY_THE_2 = 2;
	
	/**
	 * 机构担保ID
	 */
	public static final long GUARANTEE = 15;
	
	/**
	 * 实地考察ID
	 */
	public static final long FIELD_VISIT = 13;
	
	/**
	 * 手机认证 ID
	 */
	public static final long FLOW_PHONE = 11;
	
	public static final int APPROVE_PASS = 3;
	
	/**
	 * 用户组提现状态  提现状态(默认1 启动 2 禁止)
	 */
	public static final int CASH_STATUS_ON = 1;
	
	public static final int CASH_STATUS_OFF = 2;
	
	
	/**
	 * 缓存字段	
	 * CACHE_WZGG_index
	 * CACHE_WZGG_page
	 * CACHE_WZGG_info
	 */
	
	/*
	 * 首页
	 */
	public static final String CACHE_TZB_INDEX = "cache_tzb_index";//投资榜，投资排名前20条记录，首页
	public static final String CACHE_CGGS_INDEX = "cache_cggs_index";//成功故事，首页
	public static final String CACHE_CGGS_PAGE_ = "cache_cggs_page_";//成功故事
	public static final String CACHE_CGGS_INFO_ = "cache_cggs_info_";//成功故事
	
	/*
	 * 网站信息，公司简介、平台原理.....等
	 */
	public static final String CACHE_WZXX_INFO_TYPEID_ = "cache_wzxx_info_typeId_";//网站信息明细，后面拼接信息编号
	
	/*
	 * 团队介绍
	 */
	public static final String CACHE_TDJS_ALL = "cache_tdjs_all";//所有团队信息
	
	/*
	 * 首页_关于我们_媒体报道_
	 */
	public static final String CACHE_MTBD_PAGE_= "cache_mtbd_page_";//媒体报道
	public static final String CACHE_MTBD_INFO_= "cache_mtbd_info_";//媒体报道
	
	/*
	 * 首页_下载专区
	 */
	public static final String CACHE_XZZQ_PAGE_="cache_xzzq_page_";//下载专区
	public static final String CACHE_XZZQ_INFO_="cache_xzzq_info_";//下载明细
	
	/*
	 * 网站公告
	 */
	public static final String CACHE_WZGG_INDEX = "cache_wzgg_index";//网站公告，首页
	public static final String CACHE_WZGG_WZDT = "cache_wzgg_wzdt";//网站公告其他（网站动态）
	public static final String CACHE_WZGG_PAGE_ = "cache_wzgg_page_";//网站公告分页
	public static final String CACHE_WZGG_INFO_ = "cache_wzgg_info_";//网站公告明细
	
	/*
	 * 客服中心_帮助中心
	 */
	public static final String CACHE_BZZX_PAGE_ = "cache_bzzx_page_";//帮助中心分页
	public static final String CACHE_BZZX_INFO_ = "cache_bzzx_info_";//帮助中心明细
	
	/*
	 * 客服中心_客服列表
	 */
	public static final String CACHE_LXKF_PAGE4 = "cache_lxkf_page4";//客服中心_联系客服
	public static final String CACHE_LXKF_ALL = "cache_lxkf_all";//联系客服_全部
	
	
	public static final Integer EXCEL_MAX = 50000;  //导出记录_最大值
	
		
	/**
	 * 测试系统参数配置
	 * 当为真值时，用户注册等环节不需要激活、个人信息验证、VIP 申请的步骤
	 *//*
	public static final boolean ISDEMO = true;*/
	
	//管理员没有权限跳转页面
	public static final String ADMIN_NO_PERMISSION = "noPermission";
	
	/**
	 * SEO标准配置
	 */
	public static  String SEO_TITLE="";
	public static  String SEO_KEYWORDS="";
	public static  String SEO_DESCRIPTION="";
	public static  int SEO_SITEMAP=0;
	public static  String SEO_OTHERTAGS="";
	
	/**
	 * The key used to register and retrieve the cron4j scheduler within the
	 * current servlet context.
	 */
	public static String SCHEDULER = "cron4j.scheduler";
	
	public static String  IPS = "ips";
	public static String  GOPAY = "gopay";
	 /**
	 * 提问状态  0开启 1关闭
	 */
	public static int ASK_STATUS =0;
	 /**
	 * 提问转发管理员    0开启 1关闭
	 */
	public static int FORWARD_STATUS =0;
	
	/**   
	 *   流转标发布模式 1 前台用户
	 * @since 1.0.0   
	 */   
	public static final int CIRCULATIONMODE_1 = 1;
	
	/**   
	 *   流转标发布模式 2 后台用户
	 * @since 1.0.0   
	 */   
	public static final int CIRCULATIONMODE_2 = 1;
	/**   
	 *  排序序号  1
	 * @since 1.0.0   
	 */
	public static final int SORT_1 = 1;
	
	/**   
	 *  排序序号  2
	 * @since 1.0.0   
	 */
	public static final int SORT_2 = 2;
	
	/**   
	 *  排序序号  3
	 * @since 1.0.0   
	 */
	public static final int SORT_3 = 3;
	
	/**   
	 *  排序序号  4
	 * @since 1.0.0   
	 */
	public static final int SORT_4 = 4;
	
	/**   
	 *  排序序号  5
	 * @since 1.0.0   
	 */
	public static final int SORT_5 = 5;
	
	/**   
	 *  排序序号  10
	 * @since 1.0.0   
	 */
	public static final int SORT_10 = 10;
	
	/**
	 * 用于系统日志
	 */
	public static final  int  INSERT= 1;
	
	public static final  int  UPDATE= 2;
	
	public static final  int  DELETE= 3;
	
	public static final  int  DOWNLOAD= 4;
	
	public static final  int  EXCEL= 5;
	
}

