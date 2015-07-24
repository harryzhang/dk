/**
\ * Program Name: Shove.DAL.40 for MySQL
 * Program Version: 4.0
 * @author: 3km.shovesoft.shove (zhou changjun)
 * Release Time: 2012.12.11
 *
 * System Request: com.shovesoft.jar, mysql-connector-x.xx.jar
 * All Rights saved.
 */

package com.sp2p.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Field;
import com.shove.data.dao.MySQL;
import com.shove.data.dao.Parameter;
import com.shove.data.dao.ParameterDirection;
import com.shove.data.dao.Table;
import com.shove.data.dao.View;

public class Dao {

	public class Tables {

		public class bt_config extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field var = new Field(this, "`var`", Types.VARCHAR, false);

			public bt_config() {
				name = "`bt_config`";
			}
		}

		public class bt_rights extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field action = new Field(this, "`action`", Types.VARCHAR, false);
			public Field description = new Field(this, "`description`", Types.VARCHAR, false);
			public Field isLog = new Field(this, "`isLog`", Types.INTEGER, false);
			public Field isIntercept = new Field(this, "`isIntercept`", Types.INTEGER, false);
			public Field summary = new Field(this, "`summary`", Types.VARCHAR, false);
			public Field parentID = new Field(this, "`parentID`", Types.BIGINT, false);
			public Field isQuery = new Field(this, "`isQuery`", Types.INTEGER, false);
			public Field indexs = new Field(this, "`indexs`", Types.INTEGER, false);
			public Field menuId = new Field(this, "`menuId`", Types.BIGINT, false);

			public bt_rights() {
				name = "`bt_rights`";
			}
		}

		public class test_c3p0 extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field testid = new Field(this, "`testid`", Types.BIGINT, false);

			public test_c3p0() {
				name = "`test_c3p0`";
			}
		}

		public class t_question extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field question = new Field(this, "`question`", Types.VARCHAR, false);
			public Field puttime = new Field(this, "`puttime`", Types.TIMESTAMP, false);
			public Field username = new Field(this, "`username`", Types.VARCHAR, false);
			public Field putip = new Field(this, "`putip`", Types.VARCHAR, false);

			public t_question() {
				name = "`t_question`";
			}
		}

		public class t_merCash extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field sum = new Field(this, "`sum`", Types.DECIMAL, false);
			public Field poundage = new Field(this, "`poundage`", Types.DECIMAL, false);
			public Field applyTime = new Field(this, "`applyTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field cardNo = new Field(this, "`cardNo`", Types.VARCHAR, false);

			public t_merCash() {
				name = "`t_mercash`";
			}
		}

		public class t_merRecharge extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, false);
			public Field money = new Field(this, "`money`", Types.DECIMAL, false);
			public Field fee = new Field(this, "`fee`", Types.DECIMAL, false);
			public Field usableSum = new Field(this, "`usableSum`", Types.DECIMAL, false);
			public Field rechargeTime = new Field(this, "`rechargeTime`", Types.TIMESTAMP, false);
			public Field subAcct = new Field(this, "`subAcct`", Types.VARCHAR, false);
			public Field result = new Field(this, "`result`", Types.INTEGER, false);
			public Field trxId = new Field(this, "`trxId`", Types.VARCHAR, false);

			public t_merRecharge() {
				name = "`t_merrecharge`";
			}
		}

		public class t_account extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field balance_cash = new Field(this, "`balance_cash`", Types.DECIMAL, false);
			public Field balance_frost = new Field(this, "`balance_frost`", Types.DECIMAL, false);
			public Field frost = new Field(this, "`frost`", Types.DECIMAL, false);
			public Field frost_cash = new Field(this, "`frost_cash`", Types.DECIMAL, false);

			public t_account() {
				name = "`t_account`";
			}
		}

		public class t_account_payment extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.BIGINT, false);
			public Field litpic = new Field(this, "`litpic`", Types.VARCHAR, false);
			public Field style = new Field(this, "`style`", Types.INTEGER, false);
			public Field config = new Field(this, "`config`", Types.LONGVARCHAR, false);
			public Field description = new Field(this, "`description`", Types.LONGVARCHAR, false);
			public Field orders = new Field(this, "`orders`", Types.INTEGER, false);

			public t_account_payment() {
				name = "`t_account_payment`";
			}
		}

		public class t_score extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field experience = new Field(this, "`experience`", Types.INTEGER, false);
			public Field income = new Field(this, "`income`", Types.BIGINT, false);
			public Field investment = new Field(this, "`investment`", Types.INTEGER, false);
			public Field total = new Field(this, "`total`", Types.INTEGER, false);
			public Field internet = new Field(this, "`internet`", Types.INTEGER, false);
			public Field degree = new Field(this, "`degree`", Types.INTEGER, false);
			public Field predilection = new Field(this, "`predilection`", Types.INTEGER, false);
			public Field aim = new Field(this, "`aim`", Types.VARCHAR, false);
			public Field attitude = new Field(this, "`attitude`", Types.INTEGER, false);
			public Field levels = new Field(this, "`levels`", Types.INTEGER, false);
			public Field questionScore = new Field(this, "`questionScore`", Types.INTEGER, false);
			public Field intnetScore = new Field(this, "`intnetScore`", Types.INTEGER, false);
			public Field degreeScore = new Field(this, "`degreeScore`", Types.INTEGER, false);
			public Field prediScore = new Field(this, "`prediScore`", Types.INTEGER, false);

			public t_score() {
				name = "`t_score`";
			}
		}

		public class t_account_users extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field money = new Field(this, "`money`", Types.DECIMAL, false);
			public Field total = new Field(this, "`total`", Types.DECIMAL, false);
			public Field balance = new Field(this, "`balance`", Types.DECIMAL, false);
			public Field income = new Field(this, "`income`", Types.DECIMAL, false);
			public Field expend = new Field(this, "`expend`", Types.DECIMAL, false);
			public Field remark = new Field(this, "`remark`", Types.LONGVARCHAR, false);
			public Field add_time = new Field(this, "`add_time`", Types.TIMESTAMP, false);
			public Field add_ip = new Field(this, "`add_ip`", Types.VARCHAR, false);
			public Field trader = new Field(this, "`trader`", Types.BIGINT, false);

			public t_account_users() {
				name = "`t_account_users`";
			}
		}

		public class t_actionlog extends Table {
			public Field id = new Field(this, "`id`", Types.INTEGER, true);
			public Field logTime = new Field(this, "`logTime`", Types.BIGINT, false);
			public Field userid = new Field(this, "`userid`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field type_action = new Field(this, "`type_action`", Types.INTEGER, false);
			public Field type_location = new Field(this, "`type_location`", Types.INTEGER, false);

			public t_actionlog() {
				name = "`t_actionlog`";
			}
		}

		public class t_admin extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userName = new Field(this, "`userName`", Types.VARCHAR, false);
			public Field password = new Field(this, "`password`", Types.VARCHAR, false);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);
			public Field lastTime = new Field(this, "`lastTime`", Types.TIMESTAMP, false);
			public Field lastIP = new Field(this, "`lastIP`", Types.VARCHAR, false);
			public Field roleId = new Field(this, "`roleId`", Types.BIGINT, false);
			public Field realName = new Field(this, "`realName`", Types.VARCHAR, false);
			public Field telphone = new Field(this, "`telphone`", Types.VARCHAR, false);
			public Field qq = new Field(this, "`qq`", Types.VARCHAR, false);
			public Field email = new Field(this, "`email`", Types.VARCHAR, false);
			public Field img = new Field(this, "`img`", Types.VARCHAR, false);
			public Field isLeader = new Field(this, "`isLeader`", Types.VARCHAR, false);
			public Field sex = new Field(this, "`sex`", Types.INTEGER, false);
			public Field card = new Field(this, "`card`", Types.VARCHAR, false);
			public Field summary = new Field(this, "`summary`", Types.VARCHAR, false);
			public Field nativePlacePro = new Field(this, "`nativePlacePro`", Types.INTEGER, false);
			public Field nativePlaceCity = new Field(this, "`nativePlaceCity`", Types.INTEGER, false);
			public Field address = new Field(this, "`address`", Types.VARCHAR, false);
			public Field addDate = new Field(this, "`addDate`", Types.TIMESTAMP, false);
			public Field moneys = new Field(this, "`moneys`", Types.DECIMAL, false);
			public Field usrCustId = new Field(this, "`usrCustId`", Types.BIGINT, false);
			public Field subAcct = new Field(this, "`subAcct`", Types.VARCHAR, false);
			public Field subAcctMoney = new Field(this, "`subAcctMoney`", Types.DECIMAL, false);

			public t_admin() {
				name = "`t_admin`";
			}
		}

		public class t_admin_record extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field operateTime = new Field(this, "`operateTime`", Types.TIMESTAMP, false);
			public Field operatemodle = new Field(this, "`operatemodle`", Types.VARCHAR, false);
			public Field oparatecontent = new Field(this, "`oparatecontent`", Types.VARCHAR, false);

			public t_admin_record() {
				name = "`t_admin_record`";
			}
		}

		public class t_agreement extends Table {
			public Field id = new Field(this, "`id`", Types.INTEGER, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field agreementType = new Field(this, "`agreementType`", Types.INTEGER, false);
			public Field provisionType = new Field(this, "`provisionType`", Types.INTEGER, false);
			public Field agreementDesc = new Field(this, "`agreementDesc`", Types.VARCHAR, false);

			public t_agreement() {
				name = "`t_agreement`";
			}
		}

		public class t_approve_notice_style extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field notice_style = new Field(this, "`notice_style`", Types.BIGINT, false);
			public Field notice_type = new Field(this, "`notice_type`", Types.BIGINT, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);

			public t_approve_notice_style() {
				name = "`t_approve_notice_style`";
			}
		}

		public class t_approve_notice_template extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field notice_id = new Field(this, "`notice_id`", Types.BIGINT, false);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field template = new Field(this, "`template`", Types.LONGVARCHAR, false);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);

			public t_approve_notice_template() {
				name = "`t_approve_notice_template`";
			}
		}

		public class t_article_manage extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.INTEGER, false);
			public Field ltpic = new Field(this, "`ltpic`", Types.VARCHAR, false);
			public Field itemize = new Field(this, "`itemize`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field parentID = new Field(this, "`parentID`", Types.INTEGER, false);
			public Field sorts = new Field(this, "`sorts`", Types.INTEGER, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field visits = new Field(this, "`visits`", Types.INTEGER, false);
			public Field issection = new Field(this, "`issection`", Types.INTEGER, false);

			public t_article_manage() {
				name = "`t_article_manage`";
			}
		}

		public class t_assignment_debt extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field debtSum = new Field(this, "`debtSum`", Types.DECIMAL, false);
			public Field auctionBasePrice = new Field(this, "`auctionBasePrice`", Types.DECIMAL, false);
			public Field debtLimit = new Field(this, "`debtLimit`", Types.INTEGER, false);
			public Field auctionMode = new Field(this, "`auctionMode`", Types.INTEGER, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field viewCount = new Field(this, "`viewCount`", Types.INTEGER, false);
			public Field details = new Field(this, "`details`", Types.VARCHAR, false);
			public Field auctionDays = new Field(this, "`auctionDays`", Types.INTEGER, false);
			public Field debtStatus = new Field(this, "`debtStatus`", Types.INTEGER, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field alienatorId = new Field(this, "`alienatorId`", Types.BIGINT, false);
			public Field auctionerId = new Field(this, "`auctionerId`", Types.BIGINT, false);
			public Field auctionHighPrice = new Field(this, "`auctionHighPrice`", Types.DECIMAL, false);
			public Field auctionEndTime = new Field(this, "`auctionEndTime`", Types.TIMESTAMP, false);
			public Field applyTime = new Field(this, "`applyTime`", Types.TIMESTAMP, false);
			public Field manageFee = new Field(this, "`manageFee`", Types.DECIMAL, false);
			public Field investId = new Field(this, "`investId`", Types.BIGINT, false);

			public t_assignment_debt() {
				name = "`t_assignment_debt`";
			}
		}

		public class t_auction_debt extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field debtId = new Field(this, "`debtId`", Types.BIGINT, false);
			public Field auctionTime = new Field(this, "`auctionTime`", Types.TIMESTAMP, false);
			public Field auctionPrice = new Field(this, "`auctionPrice`", Types.DECIMAL, false);
			public Field autiontimes = new Field(this, "`autiontimes`", Types.INTEGER, false);

			public t_auction_debt() {
				name = "`t_auction_debt`";
			}
		}

		public class t_automaticbid extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field bidAmount = new Field(this, "`bidAmount`", Types.DECIMAL, false);
			public Field rateStart = new Field(this, "`rateStart`", Types.DECIMAL, false);
			public Field rateEnd = new Field(this, "`rateEnd`", Types.DECIMAL, false);
			public Field deadlineStart = new Field(this, "`deadlineStart`", Types.INTEGER, false);
			public Field deadlineEnd = new Field(this, "`deadlineEnd`", Types.INTEGER, false);
			public Field creditStart = new Field(this, "`creditStart`", Types.BIGINT, false);
			public Field creditEnd = new Field(this, "`creditEnd`", Types.BIGINT, false);
			public Field remandAmount = new Field(this, "`remandAmount`", Types.DECIMAL, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field bidStatus = new Field(this, "`bidStatus`", Types.INTEGER, false);
			public Field bidSetTime = new Field(this, "`bidSetTime`", Types.TIMESTAMP, false);
			public Field borrowWay = new Field(this, "`borrowWay`", Types.VARCHAR, false);

			public t_automaticbid() {
				name = "`t_automaticbid`";
			}
		}

		public class t_automaticbid_user extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_automaticbid_user() {
				name = "`t_automaticbid_user`";
			}
		}

		public class t_award extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field level2userId = new Field(this, "`level2userId`", Types.BIGINT, false);
			public Field level2money = new Field(this, "`level2money`", Types.DECIMAL, false);
			public Field level1userId = new Field(this, "`level1userId`", Types.BIGINT, false);
			public Field level1money = new Field(this, "`level1money`", Types.DECIMAL, false);
			public Field iorId = new Field(this, "`iorId`", Types.BIGINT, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field addDate = new Field(this, "`addDate`", Types.TIMESTAMP, false);
			public Field mx = new Field(this, "`mx`", Types.DECIMAL, false);
			public Field mxType = new Field(this, "`mxType`", Types.INTEGER, false);
			public Field month = new Field(this, "`month`", Types.INTEGER, false);
			public Field level = new Field(this, "`level`", Types.INTEGER, false);
			public Field iorType = new Field(this, "`iorType`", Types.INTEGER, false);

			public t_award() {
				name = "`t_award`";
			}
		}

		public class t_award_detail extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field handleSum = new Field(this, "`handleSum`", Types.DECIMAL, false);
			public Field checkId = new Field(this, "`checkId`", Types.BIGINT, false);
			public Field checkTime = new Field(this, "`checkTime`", Types.DATE, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);

			public t_award_detail() {
				name = "`t_award_detail`";
			}
		}

		public class t_award_level4 extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field level3userId = new Field(this, "`level3userId`", Types.BIGINT, false);
			public Field money = new Field(this, "`money`", Types.DECIMAL, false);
			public Field addDate = new Field(this, "`addDate`", Types.TIMESTAMP, false);

			public t_award_level4() {
				name = "`t_award_level4`";
			}
		}

		public class t_award_moth extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field pepoleid = new Field(this, "`pepoleid`", Types.BIGINT, false);
			public Field moneys = new Field(this, "`moneys`", Types.DECIMAL, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field applystatus = new Field(this, "`applystatus`", Types.INTEGER, false);
			public Field addtime = new Field(this, "`addtime`", Types.DATE, false);
			public Field moth = new Field(this, "`moth`", Types.DATE, false);

			public t_award_moth() {
				name = "`t_award_moth`";
			}
		}

		public class t_bankcard extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field cardUserName = new Field(this, "`cardUserName`", Types.VARCHAR, false);
			public Field bankName = new Field(this, "`bankName`", Types.VARCHAR, false);
			public Field branchBankName = new Field(this, "`branchBankName`", Types.VARCHAR, false);
			public Field cardNo = new Field(this, "`cardNo`", Types.VARCHAR, false);
			public Field cardMode = new Field(this, "`cardMode`", Types.INTEGER, false);
			public Field modifiedCardNo = new Field(this, "`modifiedCardNo`", Types.VARCHAR, false);
			public Field modifiedBankName = new Field(this, "`modifiedBankName`", Types.VARCHAR, false);
			public Field modifiedBranchBankName = new Field(this, "`modifiedBranchBankName`", Types.VARCHAR, false);
			public Field commitTime = new Field(this, "`commitTime`", Types.TIMESTAMP, false);
			public Field modifiedTime = new Field(this, "`modifiedTime`", Types.TIMESTAMP, false);
			public Field modifiedCardStatus = new Field(this, "`modifiedCardStatus`", Types.INTEGER, false);
			public Field cardStatus = new Field(this, "`cardStatus`", Types.INTEGER, false);
			public Field checkTime = new Field(this, "`checkTime`", Types.TIMESTAMP, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field checkUser = new Field(this, "`checkUser`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field province = new Field(this, "`province`", Types.VARCHAR, false);
			public Field city = new Field(this, "`city`", Types.VARCHAR, false);
			public Field openBankId = new Field(this, "`openBankId`", Types.VARCHAR, false);
			public Field modifiedOpenBankId = new Field(this, "`modifiedOpenBankId`", Types.VARCHAR, false);
			public Field provinceId = new Field(this, "`provinceId`", Types.VARCHAR, false);
			public Field cityId = new Field(this, "`cityId`", Types.VARCHAR, false);

			public t_bankcard() {
				name = "`t_bankcard`";
			}
		}

		public class t_bidrecord extends Table {
			public Field bider = new Field(this, "`bider`", Types.BIGINT, false);
			public Field bidSum = new Field(this, "`bidSum`", Types.DECIMAL, false);
			public Field bidTime = new Field(this, "`bidTime`", Types.TIMESTAMP, false);
			public Field creditId = new Field(this, "`creditId`", Types.BIGINT, false);
			public Field bidStatus = new Field(this, "`bidStatus`", Types.INTEGER, false);
			public Field id = new Field(this, "`id`", Types.BIGINT, true);

			public t_bidrecord() {
				name = "`t_bidrecord`";
			}
		}

		public class t_borrow extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field borrowTitle = new Field(this, "`borrowTitle`", Types.VARCHAR, false);
			public Field imgPath = new Field(this, "`imgPath`", Types.VARCHAR, false);
			public Field borrowWay = new Field(this, "`borrowWay`", Types.INTEGER, false);
			public Field borrowInfo = new Field(this, "`borrowInfo`", Types.VARCHAR, false);
			public Field deadline = new Field(this, "`deadline`", Types.INTEGER, false);
			public Field paymentMode = new Field(this, "`paymentMode`", Types.INTEGER, false);
			public Field borrowAmount = new Field(this, "`borrowAmount`", Types.DECIMAL, false);
			public Field annualRate = new Field(this, "`annualRate`", Types.DECIMAL, false);
			public Field minTenderedSum = new Field(this, "`minTenderedSum`", Types.DECIMAL, false);
			public Field maxTenderedSum = new Field(this, "`maxTenderedSum`", Types.DECIMAL, false);
			public Field raiseTerm = new Field(this, "`raiseTerm`", Types.INTEGER, false);
			public Field detail = new Field(this, "`detail`", Types.VARCHAR, false);
			public Field visitors = new Field(this, "`visitors`", Types.INTEGER, false);
			public Field remainTimeStart = new Field(this, "`remainTimeStart`", Types.TIMESTAMP, false);
			public Field tradeType = new Field(this, "`tradeType`", Types.INTEGER, false);
			public Field auditOpinion = new Field(this, "`auditOpinion`", Types.VARCHAR, false);
			public Field borrowStatus = new Field(this, "`borrowStatus`", Types.INTEGER, false);
			public Field publisher = new Field(this, "`publisher`", Types.BIGINT, false);
			public Field excitationType = new Field(this, "`excitationType`", Types.INTEGER, false);
			public Field excitationSum = new Field(this, "`excitationSum`", Types.DECIMAL, false);
			public Field excitationMode = new Field(this, "`excitationMode`", Types.INTEGER, false);
			public Field hasInvestAmount = new Field(this, "`hasInvestAmount`", Types.DECIMAL, false);
			public Field investNum = new Field(this, "`investNum`", Types.INTEGER, false);
			public Field purpose = new Field(this, "`purpose`", Types.INTEGER, false);
			public Field hasPWD = new Field(this, "`hasPWD`", Types.INTEGER, false);
			public Field investPWD = new Field(this, "`investPWD`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field publishIP = new Field(this, "`publishIP`", Types.VARCHAR, false);
			public Field remainTimeEnd = new Field(this, "`remainTimeEnd`", Types.TIMESTAMP, false);
			public Field auditTime = new Field(this, "`auditTime`", Types.DATE, false);
			public Field hasDeadline = new Field(this, "`hasDeadline`", Types.INTEGER, false);
			public Field isAutoBid = new Field(this, "`isAutoBid`", Types.INTEGER, false);
			public Field manageFee = new Field(this, "`manageFee`", Types.DECIMAL, false);
			public Field isDayThe = new Field(this, "`isDayThe`", Types.INTEGER, false);
			public Field autoBidEnableTime = new Field(this, "`autoBidEnableTime`", Types.TIMESTAMP, false);
			public Field version = new Field(this, "`version`", Types.INTEGER, false);
			public Field frozen_margin = new Field(this, "`frozen_margin`", Types.DECIMAL, false);
			public Field smallestFlowUnit = new Field(this, "`smallestFlowUnit`", Types.DECIMAL, false);
			public Field circulationNumber = new Field(this, "`circulationNumber`", Types.INTEGER, false);
			public Field hasCirculationNumber = new Field(this, "`hasCirculationNumber`", Types.INTEGER, false);
			public Field nid_log = new Field(this, "`nid_log`", Types.VARCHAR, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field feestate = new Field(this, "`feestate`", Types.VARCHAR, false);
			public Field feelog = new Field(this, "`feelog`", Types.VARCHAR, false);
			public Field businessDetail = new Field(this, "`businessDetail`", Types.VARCHAR, false);
			public Field assets = new Field(this, "`assets`", Types.VARCHAR, false);
			public Field moneyPurposes = new Field(this, "`moneyPurposes`", Types.VARCHAR, false);
			public Field circulationMode = new Field(this, "`circulationMode`", Types.INTEGER, false);
			public Field circulationStatus = new Field(this, "`circulationStatus`", Types.INTEGER, false);
			public Field undertaker = new Field(this, "`undertaker`", Types.BIGINT, false);
			public Field borrowShow = new Field(this, "`borrowShow`", Types.INTEGER, false);
			public Field hasRepoNumber = new Field(this, "`hasRepoNumber`", Types.INTEGER, false);
			public Field agent = new Field(this, "`agent`", Types.VARCHAR, false);
			public Field counterAgent = new Field(this, "`counterAgent`", Types.VARCHAR, false);
			public Field amount_scale = new Field(this, "`amount_scale`", Types.DECIMAL, false);
			public Field windControl = new Field(this, "`windControl`", Types.VARCHAR, false);
			public Field firstTrial = new Field(this, "`firstTrial`", Types.VARCHAR, false);
			public Field recheck = new Field(this, "`recheck`", Types.VARCHAR, false);
			public Field isTimes = new Field(this, "`isTimes`", Types.INTEGER, false);
			public Field number = new Field(this, "`number`", Types.VARCHAR, false);
			public Field loansOk = new Field(this, "`loansOk`", Types.INTEGER, false);
			public Field unfreeOk = new Field(this, "`unfreeOk`", Types.INTEGER, false);

			public t_borrow() {
				name = "`t_borrow`";
			}
		}

		public class t_borrow_amount extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field credit = new Field(this, "`credit`", Types.DECIMAL, false);
			public Field credit_use = new Field(this, "`credit_use`", Types.DECIMAL, false);
			public Field credit_frost = new Field(this, "`credit_frost`", Types.DECIMAL, false);
			public Field credit_forever = new Field(this, "`credit_forever`", Types.DECIMAL, false);
			public Field credit_once = new Field(this, "`credit_once`", Types.DECIMAL, false);
			public Field update_time = new Field(this, "`update_time`", Types.TIMESTAMP, false);
			public Field update_ip = new Field(this, "`update_ip`", Types.VARCHAR, false);

			public t_borrow_amount() {
				name = "`t_borrow_amount`";
			}
		}

		public class t_borrow_amount_apply extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field amount_type = new Field(this, "`amount_type`", Types.INTEGER, false);
			public Field amount_style = new Field(this, "`amount_style`", Types.INTEGER, false);
			public Field oprate = new Field(this, "`oprate`", Types.INTEGER, false);
			public Field amount_account = new Field(this, "`amount_account`", Types.DECIMAL, false);
			public Field account = new Field(this, "`account`", Types.DECIMAL, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field verify_remark = new Field(this, "`verify_remark`", Types.VARCHAR, false);
			public Field verify_contents = new Field(this, "`verify_contents`", Types.VARCHAR, false);
			public Field verify_time = new Field(this, "`verify_time`", Types.TIMESTAMP, false);
			public Field verify_userid = new Field(this, "`verify_userid`", Types.BIGINT, false);
			public Field add_time = new Field(this, "`add_time`", Types.DATE, false);
			public Field add_ip = new Field(this, "`add_ip`", Types.VARCHAR, false);

			public t_borrow_amount_apply() {
				name = "`t_borrow_amount_apply`";
			}
		}

		public class t_borrow_amount_log extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field amount_type = new Field(this, "`amount_type`", Types.INTEGER, false);
			public Field amount_style = new Field(this, "`amount_style`", Types.INTEGER, false);
			public Field oprate = new Field(this, "`oprate`", Types.INTEGER, false);
			public Field type = new Field(this, "`type`", Types.VARCHAR, false);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field account = new Field(this, "`account`", Types.DECIMAL, false);
			public Field account_once = new Field(this, "`account_once`", Types.DECIMAL, false);
			public Field account_forever = new Field(this, "`account_forever`", Types.DECIMAL, false);
			public Field account_use = new Field(this, "`account_use`", Types.DECIMAL, false);
			public Field account_frost = new Field(this, "`account_frost`", Types.DECIMAL, false);
			public Field account_all = new Field(this, "`account_all`", Types.DECIMAL, false);
			public Field account_return = new Field(this, "`account_return`", Types.DECIMAL, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field add_time = new Field(this, "`add_time`", Types.TIMESTAMP, false);
			public Field add_ip = new Field(this, "`add_ip`", Types.VARCHAR, false);

			public t_borrow_amount_log() {
				name = "`t_borrow_amount_log`";
			}
		}

		public class t_borrow_amount_type extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field once_status = new Field(this, "`once_status`", Types.INTEGER, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field description = new Field(this, "`description`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field init_credit = new Field(this, "`init_credit`", Types.DECIMAL, false);

			public t_borrow_amount_type() {
				name = "`t_borrow_amount_type`";
			}
		}

		public class t_borrow_fee_type extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);

			public t_borrow_fee_type() {
				name = "`t_borrow_fee_type`";
			}
		}

		public class t_borrow_style extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field contents = new Field(this, "`contents`", Types.LONGVARCHAR, false);
			public Field remark = new Field(this, "`remark`", Types.LONGVARCHAR, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);

			public t_borrow_style() {
				name = "`t_borrow_style`";
			}
		}

		public class t_borrow_type extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field description = new Field(this, "`description`", Types.VARCHAR, false);
			public Field account_multiple = new Field(this, "`account_multiple`", Types.DECIMAL, false);
			public Field password_status = new Field(this, "`password_status`", Types.INTEGER, false);
			public Field amount_type = new Field(this, "`amount_type`", Types.INTEGER, false);
			public Field amount_first = new Field(this, "`amount_first`", Types.DECIMAL, false);
			public Field amount_end = new Field(this, "`amount_end`", Types.DECIMAL, false);
			public Field frost_scale_vip = new Field(this, "`frost_scale_vip`", Types.DECIMAL, false);
			public Field apr_first = new Field(this, "`apr_first`", Types.DECIMAL, false);
			public Field apr_end = new Field(this, "`apr_end`", Types.DECIMAL, false);
			public Field check_first = new Field(this, "`check_first`", Types.INTEGER, false);
			public Field check_end = new Field(this, "`check_end`", Types.INTEGER, false);
			public Field tender_account_min = new Field(this, "`tender_account_min`", Types.VARCHAR, false);
			public Field tender_account_max = new Field(this, "`tender_account_max`", Types.VARCHAR, false);
			public Field period_month = new Field(this, "`period_month`", Types.VARCHAR, false);
			public Field period_day = new Field(this, "`period_day`", Types.VARCHAR, false);
			public Field validate = new Field(this, "`validate`", Types.VARCHAR, false);
			public Field award_status = new Field(this, "`award_status`", Types.INTEGER, false);
			public Field award_scale_first = new Field(this, "`award_scale_first`", Types.DECIMAL, false);
			public Field award_scale_end = new Field(this, "`award_scale_end`", Types.DECIMAL, false);
			public Field award_account_first = new Field(this, "`award_account_first`", Types.DECIMAL, false);
			public Field award_account_end = new Field(this, "`award_account_end`", Types.DECIMAL, false);
			public Field subscribe_status = new Field(this, "`subscribe_status`", Types.INTEGER, false);
			public Field verify_auto_status = new Field(this, "`verify_auto_status`", Types.INTEGER, false);
			public Field verify_auto_remark = new Field(this, "`verify_auto_remark`", Types.VARCHAR, false);
			public Field styles = new Field(this, "`styles`", Types.VARCHAR, false);
			public Field vip_frost_scale = new Field(this, "`vip_frost_scale`", Types.DECIMAL, false);
			public Field late_days_month = new Field(this, "`late_days_month`", Types.INTEGER, false);
			public Field late_days_day = new Field(this, "`late_days_day`", Types.INTEGER, false);
			public Field vip_late_scale = new Field(this, "`vip_late_scale`", Types.DECIMAL, false);
			public Field all_late_scale = new Field(this, "`all_late_scale`", Types.DECIMAL, false);
			public Field all_frost_scale = new Field(this, "`all_frost_scale`", Types.DECIMAL, false);
			public Field version = new Field(this, "`version`", Types.INTEGER, false);
			public Field identifier = new Field(this, "`identifier`", Types.VARCHAR, false);
			public Field counter_guarantee = new Field(this, "`counter_guarantee`", Types.VARCHAR, false);
			public Field institution = new Field(this, "`institution`", Types.VARCHAR, false);
			public Field locan_fee = new Field(this, "`locan_fee`", Types.DECIMAL, false);
			public Field locan_month = new Field(this, "`locan_month`", Types.INTEGER, false);
			public Field locan_fee_month = new Field(this, "`locan_fee_month`", Types.DECIMAL, false);
			public Field day_fee = new Field(this, "`day_fee`", Types.DECIMAL, false);
			public Field show_type = new Field(this, "`show_type`", Types.INTEGER, false);

			public t_borrow_type() {
				name = "`t_borrow_type`";
			}
		}

		public class t_borrow_type_log extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field nid = new Field(this, "`nid`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field description = new Field(this, "`description`", Types.VARCHAR, false);
			public Field account_multiple = new Field(this, "`account_multiple`", Types.DECIMAL, false);
			public Field password_status = new Field(this, "`password_status`", Types.INTEGER, false);
			public Field amount_type = new Field(this, "`amount_type`", Types.INTEGER, false);
			public Field amount_first = new Field(this, "`amount_first`", Types.DECIMAL, false);
			public Field amount_end = new Field(this, "`amount_end`", Types.DECIMAL, false);
			public Field frost_scale_vip = new Field(this, "`frost_scale_vip`", Types.DECIMAL, false);
			public Field apr_first = new Field(this, "`apr_first`", Types.DECIMAL, false);
			public Field apr_end = new Field(this, "`apr_end`", Types.DECIMAL, false);
			public Field check_first = new Field(this, "`check_first`", Types.INTEGER, false);
			public Field check_end = new Field(this, "`check_end`", Types.INTEGER, false);
			public Field tender_account_min = new Field(this, "`tender_account_min`", Types.VARCHAR, false);
			public Field tender_account_max = new Field(this, "`tender_account_max`", Types.VARCHAR, false);
			public Field period_month = new Field(this, "`period_month`", Types.VARCHAR, false);
			public Field period_day = new Field(this, "`period_day`", Types.VARCHAR, false);
			public Field validate = new Field(this, "`validate`", Types.VARCHAR, false);
			public Field award_status = new Field(this, "`award_status`", Types.INTEGER, false);
			public Field award_scale_first = new Field(this, "`award_scale_first`", Types.DECIMAL, false);
			public Field award_scale_end = new Field(this, "`award_scale_end`", Types.DECIMAL, false);
			public Field award_account_first = new Field(this, "`award_account_first`", Types.DECIMAL, false);
			public Field award_account_end = new Field(this, "`award_account_end`", Types.DECIMAL, false);
			public Field subscribe_status = new Field(this, "`subscribe_status`", Types.INTEGER, false);
			public Field verify_auto_status = new Field(this, "`verify_auto_status`", Types.INTEGER, false);
			public Field verify_auto_remark = new Field(this, "`verify_auto_remark`", Types.VARCHAR, false);
			public Field styles = new Field(this, "`styles`", Types.VARCHAR, false);
			public Field vip_frost_scale = new Field(this, "`vip_frost_scale`", Types.DECIMAL, false);
			public Field late_days_month = new Field(this, "`late_days_month`", Types.INTEGER, false);
			public Field late_days_day = new Field(this, "`late_days_day`", Types.INTEGER, false);
			public Field vip_late_scale = new Field(this, "`vip_late_scale`", Types.DECIMAL, false);
			public Field all_late_scale = new Field(this, "`all_late_scale`", Types.DECIMAL, false);
			public Field all_frost_scale = new Field(this, "`all_frost_scale`", Types.DECIMAL, false);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field update_time = new Field(this, "`update_time`", Types.BIGINT, false);
			public Field update_ip = new Field(this, "`update_ip`", Types.VARCHAR, false);
			public Field identifier = new Field(this, "`identifier`", Types.VARCHAR, false);
			public Field counter_guarantee = new Field(this, "`counter_guarantee`", Types.VARCHAR, false);
			public Field institution = new Field(this, "`institution`", Types.VARCHAR, false);
			public Field locan_fee = new Field(this, "`locan_fee`", Types.DECIMAL, false);
			public Field locan_month = new Field(this, "`locan_month`", Types.INTEGER, false);
			public Field locan_fee_month = new Field(this, "`locan_fee_month`", Types.DECIMAL, false);
			public Field day_fee = new Field(this, "`day_fee`", Types.DECIMAL, false);

			public t_borrow_type_log() {
				name = "`t_borrow_type_log`";
			}
		}

		public class t_closenetwork extends Table {
			public Field id = new Field(this, "`id`", Types.INTEGER, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);

			public t_closenetwork() {
				name = "`t_closenetwork`";
			}
		}

		public class t_collection extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field collectionDate = new Field(this, "`collectionDate`", Types.TIMESTAMP, false);
			public Field colResult = new Field(this, "`colResult`", Types.VARCHAR, false);
			public Field repayId = new Field(this, "`repayId`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);

			public t_collection() {
				name = "`t_collection`";
			}
		}

		public class t_concern extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field moduleType = new Field(this, "`moduleType`", Types.INTEGER, false);
			public Field moduleId = new Field(this, "`moduleId`", Types.BIGINT, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_concern() {
				name = "`t_concern`";
			}
		}

		public class t_cost_manager extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field descreption = new Field(this, "`descreption`", Types.VARCHAR, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field number = new Field(this, "`number`", Types.DECIMAL, false);

			public t_cost_manager() {
				name = "`t_cost_manager`";
			}
		}

		public class t_creditinfo extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field creditAmount = new Field(this, "`creditAmount`", Types.DECIMAL, false);
			public Field minBidPrice = new Field(this, "`minBidPrice`", Types.DECIMAL, false);
			public Field maxBidPrice = new Field(this, "`maxBidPrice`", Types.DECIMAL, false);
			public Field creditPeriod = new Field(this, "`creditPeriod`", Types.INTEGER, false);
			public Field bidType = new Field(this, "`bidType`", Types.INTEGER, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field bidCount = new Field(this, "`bidCount`", Types.INTEGER, false);
			public Field visiters = new Field(this, "`visiters`", Types.INTEGER, false);
			public Field timeRemain = new Field(this, "`timeRemain`", Types.TIMESTAMP, false);
			public Field transferDesc = new Field(this, "`transferDesc`", Types.VARCHAR, false);
			public Field creditStatus = new Field(this, "`creditStatus`", Types.INTEGER, false);
			public Field creditFTSatus = new Field(this, "`creditFTSatus`", Types.INTEGER, false);
			public Field creditor = new Field(this, "`creditor`", Types.BIGINT, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field bider = new Field(this, "`bider`", Types.BIGINT, false);
			public Field lateStatus = new Field(this, "`lateStatus`", Types.INTEGER, false);
			public Field creditImg = new Field(this, "`creditImg`", Types.VARCHAR, false);

			public t_creditinfo() {
				name = "`t_creditinfo`";
			}
		}

		public class t_crediting extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field creditingName = new Field(this, "`creditingName`", Types.VARCHAR, false);
			public Field applyAmount = new Field(this, "`applyAmount`", Types.DECIMAL, false);
			public Field applyDetail = new Field(this, "`applyDetail`", Types.VARCHAR, false);
			public Field applyer = new Field(this, "`applyer`", Types.BIGINT, false);
			public Field applyTime = new Field(this, "`applyTime`", Types.TIMESTAMP, false);
			public Field reviewer = new Field(this, "`reviewer`", Types.BIGINT, false);
			public Field reviewTime = new Field(this, "`reviewTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field checkMsg = new Field(this, "`checkMsg`", Types.VARCHAR, false);
			public Field agreeAmount = new Field(this, "`agreeAmount`", Types.DECIMAL, false);

			public t_crediting() {
				name = "`t_crediting`";
			}
		}

		public class t_download extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.INTEGER, false);
			public Field visits = new Field(this, "`visits`", Types.INTEGER, false);
			public Field state = new Field(this, "`state`", Types.INTEGER, false);
			public Field attachment = new Field(this, "`attachment`", Types.VARCHAR, false);
			public Field seqNum = new Field(this, "`seqNum`", Types.INTEGER, false);

			public t_download() {
				name = "`t_download`";
			}
		}

		public class t_education_cost extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field freeEducation = new Field(this, "`freeEducation`", Types.DECIMAL, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);

			public t_education_cost() {
				name = "`t_education_cost`";
			}
		}

		public class t_excitation extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field excitationSum = new Field(this, "`excitationSum`", Types.DECIMAL, false);
			public Field excitationMode = new Field(this, "`excitationMode`", Types.INTEGER, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);

			public t_excitation() {
				name = "`t_excitation`";
			}
		}

		public class t_feedback extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field publish_time = new Field(this, "`publish_time`", Types.TIMESTAMP, false);

			public t_feedback() {
				name = "`t_feedback`";
			}
		}

		public class t_flow_repayment extends Table {
			public Field invest_id = new Field(this, "`invest_id`", Types.BIGINT, false);

			public t_flow_repayment() {
				name = "`t_flow_repayment`";
			}
		}

		public class t_fundrecord extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field fundMode = new Field(this, "`fundMode`", Types.VARCHAR, false);
			public Field handleSum = new Field(this, "`handleSum`", Types.DECIMAL, false);
			public Field usableSum = new Field(this, "`usableSum`", Types.DECIMAL, false);
			public Field freezeSum = new Field(this, "`freezeSum`", Types.DECIMAL, false);
			public Field dueinSum = new Field(this, "`dueinSum`", Types.DECIMAL, false);
			public Field trader = new Field(this, "`trader`", Types.BIGINT, false);
			public Field recordTime = new Field(this, "`recordTime`", Types.TIMESTAMP, false);
			public Field dueoutSum = new Field(this, "`dueoutSum`", Types.DECIMAL, false);
			public Field remarks = new Field(this, "`remarks`", Types.VARCHAR, false);
			public Field operateType = new Field(this, "`operateType`", Types.INTEGER, false);
			public Field oninvest = new Field(this, "`oninvest`", Types.DECIMAL, false);
			public Field paynumber = new Field(this, "`paynumber`", Types.VARCHAR, false);
			public Field paybank = new Field(this, "`paybank`", Types.VARCHAR, false);
			public Field cost = new Field(this, "`cost`", Types.DECIMAL, false);
			public Field income = new Field(this, "`income`", Types.DECIMAL, false);
			public Field spending = new Field(this, "`spending`", Types.DECIMAL, false);
			public Field borrow_id = new Field(this, "`borrow_id`", Types.BIGINT, false);
			public Field repayment_id = new Field(this, "`repayment_id`", Types.BIGINT, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);

			public t_fundrecord() {
				name = "`t_fundrecord`";
			}
		}

		public class t_funds extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field fundsLimit = new Field(this, "`fundsLimit`", Types.INTEGER, false);
			public Field totalAmount = new Field(this, "`totalAmount`", Types.DECIMAL, false);
			public Field annualRate = new Field(this, "`annualRate`", Types.DECIMAL, false);
			public Field minBidMonney = new Field(this, "`minBidMonney`", Types.DECIMAL, false);
			public Field maxBidMoney = new Field(this, "`maxBidMoney`", Types.DECIMAL, false);
			public Field bidLimit = new Field(this, "`bidLimit`", Types.INTEGER, false);
			public Field details = new Field(this, "`details`", Types.VARCHAR, false);
			public Field viewCount = new Field(this, "`viewCount`", Types.INTEGER, false);
			public Field wcdIdea = new Field(this, "`wcdIdea`", Types.VARCHAR, false);
			public Field assureOrganization = new Field(this, "`assureOrganization`", Types.VARCHAR, false);
			public Field issuer = new Field(this, "`issuer`", Types.VARCHAR, false);
			public Field unassureWayId = new Field(this, "`unassureWayId`", Types.BIGINT, false);
			public Field createTime = new Field(this, "`createTime`", Types.DATE, false);
			public Field bidTime = new Field(this, "`bidTime`", Types.DATE, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field incomeDestrId = new Field(this, "`incomeDestrId`", Types.BIGINT, false);
			public Field fundAmount = new Field(this, "`fundAmount`", Types.INTEGER, false);
			public Field fundCreateTime = new Field(this, "`fundCreateTime`", Types.TIMESTAMP, false);
			public Field imgPath = new Field(this, "`imgPath`", Types.VARCHAR, false);
			public Field allBidTime = new Field(this, "`allBidTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field repayWayId = new Field(this, "`repayWayId`", Types.BIGINT, false);

			public t_funds() {
				name = "`t_funds`";
			}
		}

		public class t_fund_messageboard extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field createTime = new Field(this, "`createTime`", Types.TIMESTAMP, false);
			public Field replyContent = new Field(this, "`replyContent`", Types.VARCHAR, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field replyTime = new Field(this, "`replyTime`", Types.TIMESTAMP, false);
			public Field fundId = new Field(this, "`fundId`", Types.BIGINT, false);

			public t_fund_messageboard() {
				name = "`t_fund_messageboard`";
			}
		}

		public class t_group extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field groupName = new Field(this, "`groupName`", Types.VARCHAR, false);
			public Field groupRemark = new Field(this, "`groupRemark`", Types.VARCHAR, false);
			public Field cashStatus = new Field(this, "`cashStatus`", Types.INTEGER, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);

			public t_group() {
				name = "`t_group`";
			}
		}

		public class t_group_user extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field groupId = new Field(this, "`groupId`", Types.BIGINT, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_group_user() {
				name = "`t_group_user`";
			}
		}

		public class t_help_answer extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field helpQuestionId = new Field(this, "`helpQuestionId`", Types.BIGINT, false);
			public Field helpAnswer = new Field(this, "`helpAnswer`", Types.VARCHAR, false);

			public t_help_answer() {
				name = "`t_help_answer`";
			}
		}

		public class t_help_question extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field questionDescribe = new Field(this, "`questionDescribe`", Types.VARCHAR, false);
			public Field helpTypeId = new Field(this, "`helpTypeId`", Types.BIGINT, false);
			public Field publisher = new Field(this, "`publisher`", Types.BIGINT, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field browseCount = new Field(this, "`browseCount`", Types.BIGINT, false);
			public Field sortid = new Field(this, "`sortid`", Types.BIGINT, false);

			public t_help_question() {
				name = "`t_help_question`";
			}
		}

		public class t_help_type extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field helpDescribe = new Field(this, "`helpDescribe`", Types.VARCHAR, false);
			public Field sortId = new Field(this, "`sortId`", Types.INTEGER, false);

			public t_help_type() {
				name = "`t_help_type`";
			}
		}

		public class t_integral_detail extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userid = new Field(this, "`userid`", Types.BIGINT, false);
			public Field intergraltype = new Field(this, "`intergraltype`", Types.VARCHAR, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field changetype = new Field(this, "`changetype`", Types.VARCHAR, false);
			public Field changerecore = new Field(this, "`changerecore`", Types.VARCHAR, false);
			public Field time = new Field(this, "`time`", Types.BIGINT, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);

			public t_integral_detail() {
				name = "`t_integral_detail`";
			}
		}

		public class t_intention_fund extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field fundId = new Field(this, "`fundId`", Types.BIGINT, false);
			public Field intentionMoney = new Field(this, "`intentionMoney`", Types.DECIMAL, false);
			public Field createTime = new Field(this, "`createTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field dealTime = new Field(this, "`dealTime`", Types.TIMESTAMP, false);

			public t_intention_fund() {
				name = "`t_intention_fund`";
			}
		}

		public class t_invest extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field investAmount = new Field(this, "`investAmount`", Types.DECIMAL, false);
			public Field monthRate = new Field(this, "`monthRate`", Types.DECIMAL, false);
			public Field investor = new Field(this, "`investor`", Types.BIGINT, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field investTime = new Field(this, "`investTime`", Types.TIMESTAMP, false);
			public Field oriInvestor = new Field(this, "`oriInvestor`", Types.BIGINT, false);
			public Field realAmount = new Field(this, "`realAmount`", Types.DECIMAL, false);
			public Field hasPI = new Field(this, "`hasPI`", Types.DECIMAL, false);
			public Field deadline = new Field(this, "`deadline`", Types.INTEGER, false);
			public Field hasDeadline = new Field(this, "`hasDeadline`", Types.INTEGER, false);
			public Field recivedPrincipal = new Field(this, "`recivedPrincipal`", Types.DECIMAL, false);
			public Field recievedInterest = new Field(this, "`recievedInterest`", Types.DECIMAL, false);
			public Field hasPrincipal = new Field(this, "`hasPrincipal`", Types.DECIMAL, false);
			public Field hasInterest = new Field(this, "`hasInterest`", Types.DECIMAL, false);
			public Field recivedFI = new Field(this, "`recivedFI`", Types.DECIMAL, false);
			public Field hasFI = new Field(this, "`hasFI`", Types.DECIMAL, false);
			public Field manageFee = new Field(this, "`manageFee`", Types.DECIMAL, false);
			public Field reward = new Field(this, "`reward`", Types.DECIMAL, false);
			public Field repayStatus = new Field(this, "`repayStatus`", Types.INTEGER, false);
			public Field flag = new Field(this, "`flag`", Types.VARCHAR, false);
			public Field isAutoBid = new Field(this, "`isAutoBid`", Types.INTEGER, false);
			public Field isDebt = new Field(this, "`isDebt`", Types.INTEGER, false);
			public Field circulationInterest = new Field(this, "`circulationInterest`", Types.DOUBLE, false);
			public Field circulationForpayStatus = new Field(this, "`circulationForpayStatus`", Types.INTEGER, false);
			public Field reason = new Field(this, "`reason`", Types.VARCHAR, false);
			public Field repayDate = new Field(this, "`repayDate`", Types.DATE, false);
			public Field check_principal = new Field(this, "`check_principal`", Types.DECIMAL, false);
			public Field check_interest = new Field(this, "`check_interest`", Types.DECIMAL, false);
			public Field min_invest_id = new Field(this, "`min_invest_id`", Types.BIGINT, false);
			public Field max_invest_id = new Field(this, "`max_invest_id`", Types.BIGINT, false);
			public Field adjust_principal = new Field(this, "`adjust_principal`", Types.DECIMAL, false);
			public Field isCancel = new Field(this, "`isCancel`", Types.INTEGER, false);
			public Field cancelDate = new Field(this, "`cancelDate`", Types.TIMESTAMP, false);
			public Field distinguish_debt = new Field(this, "`distinguish_debt`", Types.INTEGER, false);
			public Field invest_number = new Field(this, "`invest_number`", Types.VARCHAR, false);
			public Field trxId = new Field(this, "`trxId`", Types.BIGINT, false);

			public t_invest() {
				name = "`t_invest`";
			}
		}

		public class t_invest_history extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, false);
			public Field investAmount = new Field(this, "`investAmount`", Types.DECIMAL, false);
			public Field monthRate = new Field(this, "`monthRate`", Types.DECIMAL, false);
			public Field investor = new Field(this, "`investor`", Types.BIGINT, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field investTime = new Field(this, "`investTime`", Types.TIMESTAMP, false);
			public Field oriInvestor = new Field(this, "`oriInvestor`", Types.BIGINT, false);
			public Field realAmount = new Field(this, "`realAmount`", Types.DECIMAL, false);
			public Field hasPI = new Field(this, "`hasPI`", Types.DECIMAL, false);
			public Field deadline = new Field(this, "`deadline`", Types.INTEGER, false);
			public Field hasDeadline = new Field(this, "`hasDeadline`", Types.INTEGER, false);
			public Field recivedPrincipal = new Field(this, "`recivedPrincipal`", Types.DECIMAL, false);
			public Field recievedInterest = new Field(this, "`recievedInterest`", Types.DECIMAL, false);
			public Field hasPrincipal = new Field(this, "`hasPrincipal`", Types.DECIMAL, false);
			public Field hasInterest = new Field(this, "`hasInterest`", Types.DECIMAL, false);
			public Field recivedFI = new Field(this, "`recivedFI`", Types.DECIMAL, false);
			public Field hasFI = new Field(this, "`hasFI`", Types.DECIMAL, false);
			public Field manageFee = new Field(this, "`manageFee`", Types.DECIMAL, false);
			public Field reward = new Field(this, "`reward`", Types.DECIMAL, false);
			public Field repayStatus = new Field(this, "`repayStatus`", Types.INTEGER, false);
			public Field flag = new Field(this, "`flag`", Types.VARCHAR, false);
			public Field isAutoBid = new Field(this, "`isAutoBid`", Types.INTEGER, false);
			public Field isDebt = new Field(this, "`isDebt`", Types.INTEGER, false);
			public Field check_principal = new Field(this, "`check_principal`", Types.DECIMAL, false);
			public Field check_interest = new Field(this, "`check_interest`", Types.DECIMAL, false);
			public Field min_invest_id = new Field(this, "`min_invest_id`", Types.BIGINT, false);
			public Field max_invest_id = new Field(this, "`max_invest_id`", Types.BIGINT, false);
			public Field adjust_principal = new Field(this, "`adjust_principal`", Types.DECIMAL, false);

			public t_invest_history() {
				name = "`t_invest_history`";
			}
		}

		public class t_invest_repayment extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field repayId = new Field(this, "`repayId`", Types.BIGINT, false);
			public Field repayPeriod = new Field(this, "`repayPeriod`", Types.VARCHAR, false);
			public Field repayDate = new Field(this, "`repayDate`", Types.DATE, false);
			public Field realRepayDate = new Field(this, "`realRepayDate`", Types.DATE, false);
			public Field recivedPrincipal = new Field(this, "`recivedPrincipal`", Types.DECIMAL, false);
			public Field recivedInterest = new Field(this, "`recivedInterest`", Types.DECIMAL, false);
			public Field hasPrincipal = new Field(this, "`hasPrincipal`", Types.DECIMAL, false);
			public Field hasInterest = new Field(this, "`hasInterest`", Types.DECIMAL, false);
			public Field interestOwner = new Field(this, "`interestOwner`", Types.INTEGER, false);
			public Field recivedFI = new Field(this, "`recivedFI`", Types.DECIMAL, false);
			public Field isLate = new Field(this, "`isLate`", Types.INTEGER, false);
			public Field lateDay = new Field(this, "`lateDay`", Types.INTEGER, false);
			public Field isWebRepay = new Field(this, "`isWebRepay`", Types.INTEGER, false);
			public Field principalBalance = new Field(this, "`principalBalance`", Types.DECIMAL, false);
			public Field interestBalance = new Field(this, "`interestBalance`", Types.DECIMAL, false);
			public Field invest_id = new Field(this, "`invest_id`", Types.BIGINT, false);
			public Field owner = new Field(this, "`owner`", Types.BIGINT, false);
			public Field ownerlist = new Field(this, "`ownerlist`", Types.VARCHAR, false);
			public Field repayStatus = new Field(this, "`repayStatus`", Types.INTEGER, false);
			public Field iManageFee = new Field(this, "`iManageFee`", Types.DECIMAL, false);
			public Field iManageFeeRate = new Field(this, "`iManageFeeRate`", Types.DECIMAL, false);
			public Field isDebt = new Field(this, "`isDebt`", Types.INTEGER, false);
			public Field borrow_id = new Field(this, "`borrow_id`", Types.BIGINT, false);
			public Field circulationForpayStatus = new Field(this, "`circulationForpayStatus`", Types.INTEGER, false);

			public t_invest_repayment() {
				name = "`t_invest_repayment`";
			}
		}

		public class t_links extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field serialCount = new Field(this, "`serialCount`", Types.BIGINT, false);
			public Field companyName = new Field(this, "`companyName`", Types.VARCHAR, false);
			public Field companyImg = new Field(this, "`companyImg`", Types.VARCHAR, false);
			public Field companyURL = new Field(this, "`companyURL`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field type = new Field(this, "`type`", Types.BIGINT, false);
			public Field ordershort = new Field(this, "`ordershort`", Types.BIGINT, false);

			public t_links() {
				name = "`t_links`";
			}
		}

		public class t_mail extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field mailTitle = new Field(this, "`mailTitle`", Types.VARCHAR, false);
			public Field mailContent = new Field(this, "`mailContent`", Types.VARCHAR, false);
			public Field sendTime = new Field(this, "`sendTime`", Types.TIMESTAMP, false);
			public Field sender = new Field(this, "`sender`", Types.BIGINT, false);
			public Field reciver = new Field(this, "`reciver`", Types.BIGINT, false);
			public Field mailType = new Field(this, "`mailType`", Types.INTEGER, false);
			public Field mailStatus = new Field(this, "`mailStatus`", Types.INTEGER, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field mailMode = new Field(this, "`mailMode`", Types.INTEGER, false);
			public Field backgroundStatus = new Field(this, "`backgroundStatus`", Types.INTEGER, false);

			public t_mail() {
				name = "`t_mail`";
			}
		}

		public class t_mailset extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field mailaddress = new Field(this, "`mailaddress`", Types.VARCHAR, false);
			public Field mailpassword = new Field(this, "`mailpassword`", Types.VARCHAR, false);
			public Field sendmail = new Field(this, "`sendmail`", Types.VARCHAR, false);
			public Field sendname = new Field(this, "`sendname`", Types.VARCHAR, false);
			public Field port = new Field(this, "`port`", Types.VARCHAR, false);
			public Field host = new Field(this, "`host`", Types.VARCHAR, false);

			public t_mailset() {
				name = "`t_mailset`";
			}
		}

		public class t_materialimagedetal extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field imagePath = new Field(this, "`imagePath`", Types.VARCHAR, false);
			public Field materialsauthid = new Field(this, "`materialsauthid`", Types.BIGINT, false);
			public Field option = new Field(this, "`option`", Types.VARCHAR, false);
			public Field checktime = new Field(this, "`checktime`", Types.TIMESTAMP, false);
			public Field uploadingTime = new Field(this, "`uploadingTime`", Types.TIMESTAMP, false);
			public Field auditStatus = new Field(this, "`auditStatus`", Types.INTEGER, false);
			public Field visiable = new Field(this, "`visiable`", Types.INTEGER, false);

			public t_materialimagedetal() {
				name = "`t_materialimagedetal`";
			}
		}

		public class t_materialsauth extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field materAuthTypeId = new Field(this, "`materAuthTypeId`", Types.BIGINT, false);
			public Field imgPath = new Field(this, "`imgPath`", Types.VARCHAR, false);
			public Field auditStatus = new Field(this, "`auditStatus`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field authTime = new Field(this, "`authTime`", Types.TIMESTAMP, false);
			public Field passTime = new Field(this, "`passTime`", Types.TIMESTAMP, false);
			public Field option = new Field(this, "`option`", Types.VARCHAR, false);
			public Field pastTime = new Field(this, "`pastTime`", Types.TIMESTAMP, false);
			public Field materaldetalId = new Field(this, "`materaldetalId`", Types.BIGINT, false);
			public Field criditing = new Field(this, "`criditing`", Types.INTEGER, false);

			public t_materialsauth() {
				name = "`t_materialsauth`";
			}
		}

		public class t_materialsauthtype extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field isMust = new Field(this, "`isMust`", Types.INTEGER, false);

			public t_materialsauthtype() {
				name = "`t_materialsauthtype`";
			}
		}

		public class t_mediareport extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field source = new Field(this, "`source`", Types.VARCHAR, false);
			public Field url = new Field(this, "`url`", Types.VARCHAR, false);
			public Field imgPath = new Field(this, "`imgPath`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field state = new Field(this, "`state`", Types.INTEGER, false);
			public Field stick = new Field(this, "`stick`", Types.INTEGER, false);

			public t_mediareport() {
				name = "`t_mediareport`";
			}
		}

		public class t_message extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field columName = new Field(this, "`columName`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field typeId = new Field(this, "`typeId`", Types.INTEGER, false);

			public t_message() {
				name = "`t_message`";
			}
		}

		public class t_messageset extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);
			public Field username = new Field(this, "`username`", Types.VARCHAR, false);
			public Field password = new Field(this, "`password`", Types.VARCHAR, false);
			public Field url = new Field(this, "`url`", Types.VARCHAR, false);

			public t_messageset() {
				name = "`t_messageset`";
			}
		}

		public class t_message_type extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field typeId = new Field(this, "`typeId`", Types.INTEGER, false);
			public Field typeDesc = new Field(this, "`typeDesc`", Types.VARCHAR, false);

			public t_message_type() {
				name = "`t_message_type`";
			}
		}

		public class t_model_referral extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field columName = new Field(this, "`columName`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field typeId = new Field(this, "`typeId`", Types.INTEGER, false);

			public t_model_referral() {
				name = "`t_model_referral`";
			}
		}

		public class t_money extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field money = new Field(this, "`money`", Types.INTEGER, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field addData = new Field(this, "`addData`", Types.TIMESTAMP, false);
			public Field endData = new Field(this, "`endData`", Types.TIMESTAMP, false);

			public t_money() {
				name = "`t_money`";
			}
		}

		public class t_msgboard extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field msgboardType = new Field(this, "`msgboardType`", Types.INTEGER, false);
			public Field modeId = new Field(this, "`modeId`", Types.BIGINT, false);
			public Field msger = new Field(this, "`msger`", Types.BIGINT, false);
			public Field msgContent = new Field(this, "`msgContent`", Types.VARCHAR, false);
			public Field msgTime = new Field(this, "`msgTime`", Types.TIMESTAMP, false);
			public Field replayer = new Field(this, "`replayer`", Types.BIGINT, false);
			public Field replayContent = new Field(this, "`replayContent`", Types.VARCHAR, false);
			public Field replayTime = new Field(this, "`replayTime`", Types.TIMESTAMP, false);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);
			public Field boardUserID = new Field(this, "`boardUserID`", Types.BIGINT, false);
			public Field ifVerify = new Field(this, "`ifVerify`", Types.BIGINT, false);
			public Field verifyTime = new Field(this, "`verifyTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field ifdelete = new Field(this, "`ifdelete`", Types.INTEGER, false);
			public Field dsc = new Field(this, "`dsc`", Types.VARCHAR, false);

			public t_msgboard() {
				name = "`t_msgboard`";
			}
		}

		public class t_news extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.INTEGER, false);
			public Field visits = new Field(this, "`visits`", Types.INTEGER, false);
			public Field state = new Field(this, "`state`", Types.INTEGER, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);

			public t_news() {
				name = "`t_news`";
			}
		}

		public class t_news_type extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field typeName = new Field(this, "`name`", Types.VARCHAR, false);

			public t_news_type() {
				name = "`t_news_type`";
			}
		}

		public class t_notice extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field mailNoticeEnable = new Field(this, "`mailNoticeEnable`", Types.INTEGER, false);
			public Field emailNoticeEnable = new Field(this, "`emailNoticeEnable`", Types.INTEGER, false);
			public Field noteNoticeEnable = new Field(this, "`noteNoticeEnable`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_notice() {
				name = "`t_notice`";
			}
		}

		public class t_noticecon extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field noticeMode = new Field(this, "`noticeMode`", Types.INTEGER, false);
			public Field reciveRepayEnable = new Field(this, "`reciveRepayEnable`", Types.INTEGER, false);
			public Field showSucEnable = new Field(this, "`showSucEnable`", Types.INTEGER, false);
			public Field loanSucEnable = new Field(this, "`loanSucEnable`", Types.INTEGER, false);
			public Field rechargeSucEnable = new Field(this, "`rechargeSucEnable`", Types.INTEGER, false);
			public Field capitalChangeEnable = new Field(this, "`capitalChangeEnable`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_noticecon() {
				name = "`t_noticecon`";
			}
		}

		public class t_notice_msg extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field msgTitle = new Field(this, "`msgTitle`", Types.VARCHAR, false);
			public Field msgCotent = new Field(this, "`msgCotent`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field userEmail = new Field(this, "`userEmail`", Types.VARCHAR, false);
			public Field userTelPhone = new Field(this, "`userTelPhone`", Types.VARCHAR, false);
			public Field addTime = new Field(this, "`addTime`", Types.TIMESTAMP, false);

			public t_notice_msg() {
				name = "`t_notice_msg`";
			}
		}

		public class t_notice_task extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field email = new Field(this, "`email`", Types.VARCHAR, false);
			public Field telphone = new Field(this, "`telphone`", Types.VARCHAR, false);
			public Field mail_info = new Field(this, "`mail_info`", Types.VARCHAR, false);
			public Field email_info = new Field(this, "`email_info`", Types.VARCHAR, false);
			public Field sms_info = new Field(this, "`sms_info`", Types.VARCHAR, false);
			public Field operate_id = new Field(this, "`operate_id`", Types.BIGINT, false);
			public Field attachment = new Field(this, "`attachment`", Types.VARCHAR, false);
			public Field add_time = new Field(this, "`add_time`", Types.TIMESTAMP, false);
			public Field operate_identify = new Field(this, "`operate_identify`", Types.VARCHAR, false);
			public Field username = new Field(this, "`username`", Types.VARCHAR, false);
			public Field s_nid = new Field(this, "`s_nid`", Types.VARCHAR, false);
			public Field sendtitle = new Field(this, "`sendtitle`", Types.VARCHAR, false);

			public t_notice_task() {
				name = "`t_notice_task`";
			}
		}

		public class t_notice_task_log extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field user_id = new Field(this, "`user_id`", Types.BIGINT, false);
			public Field sendtitle = new Field(this, "`sendtitle`", Types.VARCHAR, false);
			public Field s_nid = new Field(this, "`s_nid`", Types.VARCHAR, false);
			public Field email = new Field(this, "`email`", Types.VARCHAR, false);
			public Field telphone = new Field(this, "`telphone`", Types.VARCHAR, false);
			public Field mail_info = new Field(this, "`mail_info`", Types.VARCHAR, false);
			public Field email_info = new Field(this, "`email_info`", Types.VARCHAR, false);
			public Field sms_info = new Field(this, "`sms_info`", Types.VARCHAR, false);
			public Field operate_id = new Field(this, "`operate_id`", Types.BIGINT, false);
			public Field attachment = new Field(this, "`attachment`", Types.VARCHAR, false);
			public Field add_time = new Field(this, "`add_time`", Types.TIMESTAMP, false);
			public Field operate_identify = new Field(this, "`operate_identify`", Types.VARCHAR, false);
			public Field username = new Field(this, "`username`", Types.VARCHAR, false);

			public t_notice_task_log() {
				name = "`t_notice_task_log`";
			}
		}

		public class t_operation_log extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field operation_table = new Field(this, "`operation_table`", Types.VARCHAR, false);
			public Field operation_user = new Field(this, "`operation_user`", Types.VARCHAR, false);
			public Field operation_type = new Field(this, "`operation_type`", Types.INTEGER, false);
			public Field operation_ip = new Field(this, "`operation_ip`", Types.VARCHAR, false);
			public Field operation_time = new Field(this, "`operation_time`", Types.TIMESTAMP, false);
			public Field operation_money = new Field(this, "`operation_money`", Types.DOUBLE, false);
			public Field operation_remarks = new Field(this, "`operation_remarks`", Types.VARCHAR, false);
			public Field operation_around = new Field(this, "`operation_around`", Types.INTEGER, false);

			public t_operation_log() {
				name = "`t_operation_log`";
			}
		}

		public class t_para_config extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field code = new Field(this, "`code`", Types.VARCHAR, false);
			public Field value = new Field(this, "`value`", Types.VARCHAR, false);

			public t_para_config() {
				name = "`t_para_config`";
			}
		}

		public class t_option extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field questionId = new Field(this, "`questionId`", Types.BIGINT, false);
			public Field option = new Field(this, "`option`", Types.VARCHAR, false);
			public Field score = new Field(this, "`score`", Types.INTEGER, false);

			public t_option() {
				name = "`t_option`";
			}
		}

		public class t_questionnaire extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field question = new Field(this, "`question`", Types.VARCHAR, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field maxScore = new Field(this, "`maxScore`", Types.INTEGER, false);
			public Field questionType = new Field(this, "`questionType`", Types.INTEGER, false);

			public t_questionnaire() {
				name = "`t_questionnaire`";
			}
		}

		public class t_person extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field realName = new Field(this, "`realName`", Types.VARCHAR, false);
			public Field cellPhone = new Field(this, "`cellPhone`", Types.VARCHAR, false);
			public Field sex = new Field(this, "`sex`", Types.VARCHAR, false);
			public Field birthday = new Field(this, "`birthday`", Types.DATE, false);
			public Field age = new Field(this, "`age`", Types.INTEGER, false);
			public Field highestEdu = new Field(this, "`highestEdu`", Types.VARCHAR, false);
			public Field eduStartDay = new Field(this, "`eduStartDay`", Types.DATE, false);
			public Field school = new Field(this, "`school`", Types.VARCHAR, false);
			public Field maritalStatus = new Field(this, "`maritalStatus`", Types.VARCHAR, false);
			public Field hasChild = new Field(this, "`hasChild`", Types.VARCHAR, false);
			public Field hasHourse = new Field(this, "`hasHourse`", Types.VARCHAR, false);
			public Field hasHousrseLoan = new Field(this, "`hasHousrseLoan`", Types.VARCHAR, false);
			public Field hasCar = new Field(this, "`hasCar`", Types.VARCHAR, false);
			public Field hasCarLoan = new Field(this, "`hasCarLoan`", Types.VARCHAR, false);
			public Field nativePlacePro = new Field(this, "`nativePlacePro`", Types.INTEGER, false);
			public Field nativePlaceCity = new Field(this, "`nativePlaceCity`", Types.INTEGER, false);
			public Field registedPlacePro = new Field(this, "`registedPlacePro`", Types.INTEGER, false);
			public Field registedPlaceCity = new Field(this, "`registedPlaceCity`", Types.INTEGER, false);
			public Field address = new Field(this, "`address`", Types.VARCHAR, false);
			public Field telephone = new Field(this, "`telephone`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field personalHead = new Field(this, "`personalHead`", Types.VARCHAR, false);
			public Field bankCar = new Field(this, "`bankCar`", Types.VARCHAR, false);
			public Field bankCarStatus = new Field(this, "`bankCarStatus`", Types.INTEGER, false);
			public Field idNo = new Field(this, "`idNo`", Types.VARCHAR, false);
			public Field idNoStatus = new Field(this, "`idNoStatus`", Types.INTEGER, false);
			public Field auditStatus = new Field(this, "`auditStatus`", Types.INTEGER, false);
			public Field audExplain = new Field(this, "`audExplain`", Types.VARCHAR, false);
			public Field flag = new Field(this, "`flag`", Types.INTEGER, false);
			public Field qq = new Field(this, "`qq`", Types.VARCHAR, false);
			public Field usrCustId = new Field(this, "`usrCustId`", Types.VARCHAR, false);
			public Field creditNum = new Field(this, "`creditNum`", Types.INTEGER, false);
			public Field creditSum = new Field(this, "`creditNum`", Types.DECIMAL, false);
			public Field email = new Field(this, "`email`", Types.VARCHAR, false);

			public t_person() {
				name = "`t_person`";
			}
		}

		public class t_colorlife extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field realName = new Field(this, "`realName`", Types.VARCHAR, false);
			public Field age = new Field(this, "`age`", Types.INTEGER, false);
			public Field sex = new Field(this, "`sex`", Types.VARCHAR, false);
			public Field maritalStatus = new Field(this, "`maritalStatus`", Types.VARCHAR, false);
			public Field houseStatus = new Field(this, "`houseStatus`", Types.VARCHAR, false);
			public Field highestEdu = new Field(this, "`highestEdu`", Types.VARCHAR, false);
			public Field address = new Field(this, "`address`", Types.VARCHAR, false);
			public Field houseWorth = new Field(this, "`houseWorth`", Types.VARCHAR, false);
			public Field surplusMortgageYear = new Field(this, "`surplusMortgageYear`", Types.VARCHAR, false);
			public Field hasCar = new Field(this, "`hasCar`", Types.VARCHAR, false);
			public Field orgName = new Field(this, "`orgName`", Types.VARCHAR, false);
			public Field companyType = new Field(this, "`companyType`", Types.VARCHAR, false);
			public Field companyScale = new Field(this, "`companyScale`", Types.VARCHAR, false);
			public Field job = new Field(this, "`job`", Types.VARCHAR, false);
			public Field workYear = new Field(this, "`workYear`", Types.VARCHAR, false);
			public Field companyPhone = new Field(this, "`companyPhone`", Types.VARCHAR, false);
			public Field companyEmail = new Field(this, "`companyEmail`", Types.VARCHAR, false);
			public Field companyAddress = new Field(this, "`companyAddress`", Types.VARCHAR, false);
			public Field idNo = new Field(this, "`idNo`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field activatedState = new Field(this, "`activatedState`", Types.BIGINT, false);
			public Field annualIncome = new Field(this, "`annualIncome`", Types.VARCHAR, false);

			public t_colorlife() {
				name = "`t_colorlife`";
			}
		}
		
		public class tColorlifeBuyInfo extends Table {
			public Field orderId = new Field(this, "`order_id`", Types.BIGINT, true);
			public Field buyProduct = new Field(this, "`product_id`", Types.VARCHAR, false);
			public Field realName = new Field(this, "`real_name`", Types.INTEGER, false);
			public Field userId = new Field(this, "`user_id`", Types.VARCHAR, false);
			public Field buyMoney = new Field(this, "`buy_money`", Types.VARCHAR, false);
			public Field buyDate = new Field(this, "`buy_time`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.VARCHAR, false);
			public Field verifier = new Field(this, "`verifier`", Types.VARCHAR, false);
			public Field insertTime = new Field(this, "`insert_time`", Types.VARCHAR, false);
			public Field updateTime = new Field(this, "`update_time`", Types.VARCHAR, false);

			public tColorlifeBuyInfo() {
				name = "`t_color_life_buy_info`";
			}
		}
		
		public class td_product_rate extends Table {
			public Field product_rate_id = new Field(this, "`product_rate_id`", Types.BIGINT, true);
			public Field product_name = new Field(this, "`product_name`", Types.VARCHAR, false);

			public td_product_rate() {
				name = "`td_product_rate`";
			}
		}

		public class t_phone_binding_info extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field mobilePhone = new Field(this, "`mobilePhone`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field requsetTime = new Field(this, "`requsetTime`", Types.TIMESTAMP, false);
			public Field reason = new Field(this, "`reason`", Types.VARCHAR, false);
			public Field option = new Field(this, "`option`", Types.VARCHAR, false);
			public Field oldPhone = new Field(this, "`oldPhone`", Types.VARCHAR, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);

			public t_phone_binding_info() {
				name = "`t_phone_binding_info`";
			}
		}

		public class t_platform_cost extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field costName = new Field(this, "`costName`", Types.VARCHAR, false);
			public Field costFee = new Field(this, "`costFee`", Types.DECIMAL, false);
			public Field costMode = new Field(this, "`costMode`", Types.INTEGER, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field createTime = new Field(this, "`createTime`", Types.TIMESTAMP, false);
			public Field alias = new Field(this, "`alias`", Types.VARCHAR, false);
			public Field types = new Field(this, "`types`", Types.INTEGER, false);
			public Field show_view = new Field(this, "`show_view`", Types.INTEGER, false);

			public t_platform_cost() {
				name = "`t_platform_cost`";
			}
		}

		public class t_pre_repayment extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field repayPeriod = new Field(this, "`repayPeriod`", Types.VARCHAR, false);
			public Field repayDate = new Field(this, "`repayDate`", Types.VARCHAR, false);
			public Field stillPrincipal = new Field(this, "`stillPrincipal`", Types.DECIMAL, false);
			public Field stillInterest = new Field(this, "`stillInterest`", Types.DECIMAL, false);
			public Field principalBalance = new Field(this, "`principalBalance`", Types.DECIMAL, false);
			public Field interestBalance = new Field(this, "`interestBalance`", Types.DECIMAL, false);
			public Field consultFee = new Field(this, "`consultFee`", Types.DECIMAL, false);
			public Field mRate = new Field(this, "`mRate`", Types.DECIMAL, false);
			public Field totalSum = new Field(this, "`totalSum`", Types.DECIMAL, false);
			public Field totalAmount = new Field(this, "`totalAmount`", Types.DECIMAL, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field identify = new Field(this, "`identify`", Types.VARCHAR, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);

			public t_pre_repayment() {
				name = "`t_pre_repayment`";
			}
		}

		public class t_privileged_user extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, false);
			public Field privilegeCreateTime = new Field(this, "`privilegeCreateTime`", Types.TIMESTAMP, false);
			public Field privilegedUserName = new Field(this, "`privilegedUserName`", Types.VARCHAR, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field privilegedImgPath = new Field(this, "`privilegedImgPath`", Types.VARCHAR, false);

			public t_privileged_user() {
				name = "`t_privileged_user`";
			}
		}

		public class t_recharge extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field opertorId = new Field(this, "`opertorId`", Types.BIGINT, false);
			public Field rechargeMoney = new Field(this, "`rechargeMoney`", Types.DECIMAL, false);
			public Field cost = new Field(this, "`cost`", Types.DECIMAL, false);
			public Field addTime = new Field(this, "`addTime`", Types.TIMESTAMP, false);
			public Field result = new Field(this, "`result`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_recharge() {
				name = "`t_recharge`";
			}
		}

		public class t_rechargebank extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field bankname = new Field(this, "`bankname`", Types.VARCHAR, false);
			public Field Account = new Field(this, "`Account`", Types.VARCHAR, false);
			public Field accountbank = new Field(this, "`accountbank`", Types.VARCHAR, false);
			public Field bankimage = new Field(this, "`bankimage`", Types.VARCHAR, false);
			public Field accountname = new Field(this, "`accountname`", Types.VARCHAR, false);

			public t_rechargebank() {
				name = "`t_rechargebank`";
			}
		}

		public class t_rechargenumberlist extends Table {
			public Field PayNumber = new Field(this, "`PayNumber`", Types.BIGINT, false);
			public Field Money = new Field(this, "`Money`", Types.DECIMAL, false);

			public t_rechargenumberlist() {
				name = "`t_rechargenumberlist`";
			}
		}

		public class t_recharge_detail extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field rechargeTime = new Field(this, "`rechargeTime`", Types.TIMESTAMP, false);
			public Field rechargeType = new Field(this, "`rechargeType`", Types.INTEGER, false);
			public Field bankName = new Field(this, "`bankName`", Types.VARCHAR, false);
			public Field rechargeMoney = new Field(this, "`rechargeMoney`", Types.DECIMAL, false);
			public Field cost = new Field(this, "`cost`", Types.DECIMAL, false);
			public Field result = new Field(this, "`result`", Types.INTEGER, false);
			public Field paynumber = new Field(this, "`paynumber`", Types.VARCHAR, false);
			public Field rechargeNumber = new Field(this, "`rechargeNumber`", Types.VARCHAR, false);
			public Field buyerEmail = new Field(this, "`buyerEmail`", Types.VARCHAR, false);
			public Field rechargeId = new Field(this, "`rechargeId`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field ipAddress = new Field(this, "`ipAddress`", Types.VARCHAR, false);
			public Field award = new Field(this, "`award`", Types.DECIMAL, false);
			public Field operatorId = new Field(this, "`operatorId`", Types.BIGINT, false);

			public t_recharge_detail() {
				name = "`t_recharge_detail`";
			}
		}

		public class t_recharge_info extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field rechargeType = new Field(this, "`rechargeType`", Types.TINYINT, false);
			public Field bankName = new Field(this, "`bankName`", Types.VARCHAR, false);
			public Field rechargeMoney = new Field(this, "`rechargeMoney`", Types.DECIMAL, false);
			public Field account = new Field(this, "`account`", Types.DECIMAL, false);
			public Field rechargeTime = new Field(this, "`rechargeTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.TINYINT, false);
			public Field poundage = new Field(this, "`poundage`", Types.FLOAT, false);

			public t_recharge_info() {
				name = "`t_recharge_info`";
			}
		}

		public class t_recharge_withdraw_info extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field type = new Field(this, "`type`", Types.TINYINT, false);
			public Field dealMoney = new Field(this, "`dealMoney`", Types.DECIMAL, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field checkUser = new Field(this, "`checkUser`", Types.BIGINT, false);
			public Field checkTime = new Field(this, "`checkTime`", Types.TIMESTAMP, false);
			public Field comment = new Field(this, "`comment`", Types.VARCHAR, false);

			public t_recharge_withdraw_info() {
				name = "`t_recharge_withdraw_info`";
			}
		}

		public class t_recommend_user extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field recommendUserId = new Field(this, "`recommendUserId`", Types.BIGINT, false);
			public Field moneyTypeId = new Field(this, "`moneyTypeId`", Types.BIGINT, false);

			public t_recommend_user() {
				name = "`t_recommend_user`";
			}
		}

		public class t_referral_bonuses extends Table {
			public Field id = new Field(this, "`id`", Types.INTEGER, true);
			public Field typedesc = new Field(this, "`typedesc`", Types.VARCHAR, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);
			public Field general = new Field(this, "`general`", Types.DECIMAL, false);
			public Field fieldVisit = new Field(this, "`fieldVisit`", Types.DECIMAL, false);
			public Field organization = new Field(this, "`organization`", Types.DECIMAL, false);
			public Field netWorth = new Field(this, "`netWorth`", Types.DECIMAL, false);
			public Field desc = new Field(this, "`desc`", Types.VARCHAR, false);

			public t_referral_bonuses() {
				name = "`t_referral_bonuses`";
			}
		}

		public class t_region extends Table {
			public Field regionId = new Field(this, "`regionId`", Types.BIGINT, true);
			public Field parentId = new Field(this, "`parentId`", Types.BIGINT, false);
			public Field regionName = new Field(this, "`regionName`", Types.VARCHAR, false);
			public Field regionType = new Field(this, "`regionType`", Types.INTEGER, false);
			public Field agencyId = new Field(this, "`agencyId`", Types.INTEGER, false);

			public t_region() {
				name = "`t_region`";
			}
		}

		public class t_region_hhn extends Table {
			public Field regionId = new Field(this, "`regionId`", Types.BIGINT, true);
			public Field parentId = new Field(this, "`parentId`", Types.BIGINT, false);
			public Field regionName = new Field(this, "`regionName`", Types.VARCHAR, false);
			public Field regionType = new Field(this, "`regionType`", Types.INTEGER, false);
			public Field agencyId = new Field(this, "`agencyId`", Types.INTEGER, false);

			public t_region_hhn() {
				name = "`t_region_hhn`";
			}
		}

		public class t_relation extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field peopleId = new Field(this, "`peopleId`", Types.BIGINT, false);
			public Field parentId = new Field(this, "`parentId`", Types.BIGINT, false);
			public Field level = new Field(this, "`level`", Types.INTEGER, false);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);

			public t_relation() {
				name = "`t_relation`";
			}
		}

		public class t_repayment extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field repayDate = new Field(this, "`repayDate`", Types.DATE, false);
			public Field realRepayDate = new Field(this, "`realRepayDate`", Types.DATE, false);
			public Field repayPeriod = new Field(this, "`repayPeriod`", Types.VARCHAR, false);
			public Field hasPI = new Field(this, "`hasPI`", Types.DECIMAL, false);
			public Field stillPrincipal = new Field(this, "`stillPrincipal`", Types.DECIMAL, false);
			public Field stillInterest = new Field(this, "`stillInterest`", Types.DECIMAL, false);
			public Field hasFI = new Field(this, "`hasFI`", Types.DECIMAL, false);
			public Field lateFI = new Field(this, "`lateFI`", Types.DECIMAL, false);
			public Field lateDay = new Field(this, "`lateDay`", Types.INTEGER, false);
			public Field repayStatus = new Field(this, "`repayStatus`", Types.INTEGER, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field isLate = new Field(this, "`isLate`", Types.INTEGER, false);
			public Field isWebRepay = new Field(this, "`isWebRepay`", Types.INTEGER, false);
			public Field investorForpayFI = new Field(this, "`investorForpayFI`", Types.DECIMAL, false);
			public Field investorHaspayFI = new Field(this, "`investorHaspayFI`", Types.DECIMAL, false);
			public Field principalBalance = new Field(this, "`principalBalance`", Types.DECIMAL, false);
			public Field interestBalance = new Field(this, "`interestBalance`", Types.DECIMAL, false);
			public Field version = new Field(this, "`version`", Types.INTEGER, false);
			public Field executeTime = new Field(this, "`executeTime`", Types.DATE, false);
			public Field identify = new Field(this, "`identify`", Types.VARCHAR, false);

			public t_repayment() {
				name = "`t_repayment`";
			}
		}

		public class t_repayment_record extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field repayId = new Field(this, "`repayId`", Types.BIGINT, false);
			public Field repayAmount = new Field(this, "`repayAmount`", Types.DECIMAL, false);
			public Field oporator = new Field(this, "`oporator`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field createTime = new Field(this, "`createTime`", Types.TIMESTAMP, false);
			public Field oporatorIp = new Field(this, "`oporatorIp`", Types.VARCHAR, false);
			public Field repayType = new Field(this, "`repayType`", Types.VARCHAR, false);

			public t_repayment_record() {
				name = "`t_repayment_record`";
			}
		}

		public class t_repayment_service extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field serviceContent = new Field(this, "`serviceContent`", Types.VARCHAR, false);
			public Field serviceTime = new Field(this, "`serviceTime`", Types.TIMESTAMP, false);
			public Field repayId = new Field(this, "`repayId`", Types.BIGINT, false);

			public t_repayment_service() {
				name = "`t_repayment_service`";
			}
		}

		public class t_replament_integral extends Table {
			public Field id = new Field(this, "`id`", Types.INTEGER, true);
			public Field userid = new Field(this, "`userid`", Types.INTEGER, false);
			public Field counto = new Field(this, "`counto`", Types.INTEGER, false);
			public Field beintegrals = new Field(this, "`beintegrals`", Types.INTEGER, false);
			public Field counts = new Field(this, "`counts`", Types.INTEGER, false);
			public Field beintegralt = new Field(this, "`beintegralt`", Types.INTEGER, false);
			public Field countt = new Field(this, "`countt`", Types.INTEGER, false);
			public Field beintegralf = new Field(this, "`beintegralf`", Types.INTEGER, false);
			public Field countf = new Field(this, "`countf`", Types.INTEGER, false);
			public Field beintegralff = new Field(this, "`beintegralff`", Types.INTEGER, false);
			public Field countff = new Field(this, "`countff`", Types.INTEGER, false);
			public Field beintegralx = new Field(this, "`beintegralx`", Types.INTEGER, false);
			public Field countx = new Field(this, "`countx`", Types.INTEGER, false);

			public t_replament_integral() {
				name = "`t_replament_integral`";
			}
		}

		public class t_report extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field reportTitle = new Field(this, "`reportTitle`", Types.VARCHAR, false);
			public Field reportContent = new Field(this, "`reportContent`", Types.VARCHAR, false);
			public Field reportTime = new Field(this, "`reportTime`", Types.TIMESTAMP, false);
			public Field reporter = new Field(this, "`reporter`", Types.BIGINT, false);
			public Field user = new Field(this, "`user`", Types.BIGINT, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field reportType = new Field(this, "`reportType`", Types.INTEGER, false);

			public t_report() {
				name = "`t_report`";
			}
		}

		public class t_report_copy extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field reportTitle = new Field(this, "`reportTitle`", Types.VARCHAR, false);
			public Field reportContent = new Field(this, "`reportContent`", Types.VARCHAR, false);
			public Field reportTime = new Field(this, "`reportTime`", Types.TIMESTAMP, false);
			public Field reporter = new Field(this, "`reporter`", Types.BIGINT, false);
			public Field user = new Field(this, "`user`", Types.BIGINT, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field reportType = new Field(this, "`reportType`", Types.INTEGER, false);

			public t_report_copy() {
				name = "`t_report_copy`";
			}
		}

		public class t_risk_detail extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field riskInCome = new Field(this, "`riskInCome`", Types.DECIMAL, false);
			public Field riskSpending = new Field(this, "`riskSpending`", Types.DECIMAL, false);
			public Field riskDate = new Field(this, "`riskDate`", Types.DATE, false);
			public Field riskBalance = new Field(this, "`riskBalance`", Types.DECIMAL, false);
			public Field riskType = new Field(this, "`riskType`", Types.VARCHAR, false);
			public Field resource = new Field(this, "`resource`", Types.VARCHAR, false);
			public Field trader = new Field(this, "`trader`", Types.BIGINT, false);
			public Field borrowId = new Field(this, "`borrowId`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field operator = new Field(this, "`operator`", Types.BIGINT, false);

			public t_risk_detail() {
				name = "`t_risk_detail`";
			}
		}

		public class t_role extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field description = new Field(this, "`description`", Types.VARCHAR, false);

			public t_role() {
				name = "`t_role`";
			}
		}

		public class t_role_rights extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field roleId = new Field(this, "`roleId`", Types.BIGINT, false);
			public Field rightsId = new Field(this, "`rightsId`", Types.BIGINT, false);

			public t_role_rights() {
				name = "`t_role_rights`";
			}
		}

		public class t_select extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field typeId = new Field(this, "`typeId`", Types.BIGINT, false);
			public Field selectValue = new Field(this, "`selectValue`", Types.INTEGER, false);
			public Field selectName = new Field(this, "`selectName`", Types.VARCHAR, false);
			public Field deleted = new Field(this, "`deleted`", Types.INTEGER, false);

			public t_select() {
				name = "`t_select`";
			}
		}

		public class t_selecttype extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);

			public t_selecttype() {
				name = "`t_selecttype`";
			}
		}

		public class t_sendsms extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field sendTime = new Field(this, "`sendTime`", Types.TIMESTAMP, false);
			public Field splitId = new Field(this, "`splitId`", Types.VARCHAR, false);
			public Field splitPhone = new Field(this, "`splitPhone`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);

			public t_sendsms() {
				name = "`t_sendsms`";
			}
		}

		public class t_seoconfig extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field keywords = new Field(this, "`keywords`", Types.VARCHAR, false);
			public Field description = new Field(this, "`description`", Types.VARCHAR, false);
			public Field otherTags = new Field(this, "`otherTags`", Types.VARCHAR, false);
			public Field siteMap = new Field(this, "`siteMap`", Types.INTEGER, false);

			public t_seoconfig() {
				name = "`t_seoconfig`";
			}
		}

		public class t_serviceman extends Table {
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field serviceManName = new Field(this, "`serviceManName`", Types.VARCHAR, false);

			public t_serviceman() {
				name = "`t_serviceman`";
			}
		}

		public class t_site_information extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field siteName = new Field(this, "`siteName`", Types.VARCHAR, false);
			public Field companyName = new Field(this, "`companyName`", Types.VARCHAR, false);
			public Field postcode = new Field(this, "`postcode`", Types.VARCHAR, false);
			public Field address = new Field(this, "`address`", Types.VARCHAR, false);
			public Field principal = new Field(this, "`principal`", Types.VARCHAR, false);
			public Field contact = new Field(this, "`contact`", Types.VARCHAR, false);
			public Field telephone = new Field(this, "`telephone`", Types.VARCHAR, false);
			public Field cellphone = new Field(this, "`cellphone`", Types.VARCHAR, false);
			public Field fax = new Field(this, "`fax`", Types.VARCHAR, false);
			public Field emial = new Field(this, "`emial`", Types.VARCHAR, false);
			public Field qq = new Field(this, "`qq`", Types.VARCHAR, false);
			public Field servicePhone = new Field(this, "`servicePhone`", Types.VARCHAR, false);
			public Field certificate = new Field(this, "`certificate`", Types.VARCHAR, false);
			public Field regionName = new Field(this, "`regionName`", Types.VARCHAR, false);

			public t_site_information() {
				name = "`t_site_information`";
			}
		}

		public class t_sms extends Table {
			public Field id = new Field(this, "`id`", Types.INTEGER, true);
			public Field UserID = new Field(this, "`UserID`", Types.VARCHAR, false);
			public Field Account = new Field(this, "`Account`", Types.VARCHAR, false);
			public Field Password = new Field(this, "`Password`", Types.VARCHAR, false);
			public Field url = new Field(this, "`url`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);

			public t_sms() {
				name = "`t_sms`";
			}
		}

		public class t_successstory extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field imgPath = new Field(this, "`imgPath`", Types.VARCHAR, false);
			public Field publisher = new Field(this, "`publisher`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field browseNum = new Field(this, "`browseNum`", Types.BIGINT, false);
			public Field sort = new Field(this, "`sort`", Types.BIGINT, false);

			public t_successstory() {
				name = "`t_successstory`";
			}
		}

		public class t_sysimages extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field imagePath = new Field(this, "`imagePath`", Types.VARCHAR, false);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);

			public t_sysimages() {
				name = "`t_sysimages`";
			}
		}

		public class t_team extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field intro = new Field(this, "`intro`", Types.VARCHAR, false);
			public Field sort = new Field(this, "`sort`", Types.INTEGER, false);
			public Field imgPath = new Field(this, "`imgPath`", Types.VARCHAR, false);
			public Field publishTime = new Field(this, "`publishTime`", Types.TIMESTAMP, false);
			public Field userName = new Field(this, "`userName`", Types.VARCHAR, false);
			public Field state = new Field(this, "`state`", Types.INTEGER, false);

			public t_team() {
				name = "`t_team`";
			}
		}

		public class t_user extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field email = new Field(this, "`email`", Types.VARCHAR, false);
			public Field username = new Field(this, "`username`", Types.VARCHAR, false);
			public Field password = new Field(this, "`password`", Types.VARCHAR, false);
			public Field dealpwd = new Field(this, "`dealpwd`", Types.VARCHAR, false);
			public Field mobilePhone = new Field(this, "`mobilePhone`", Types.VARCHAR, false);
			public Field refferee = new Field(this, "`refferee`", Types.VARCHAR, false);
			public Field rating = new Field(this, "`rating`", Types.INTEGER, false);
			public Field creditrating = new Field(this, "`creditrating`", Types.INTEGER, false);
			public Field lastIP = new Field(this, "`lastIP`", Types.VARCHAR, false);
			public Field lastDate = new Field(this, "`lastDate`", Types.TIMESTAMP, false);
			public Field vipStatus = new Field(this, "`vipStatus`", Types.INTEGER, false);
			public Field vipCreateTime = new Field(this, "`vipCreateTime`", Types.TIMESTAMP, false);
			public Field creditLimit = new Field(this, "`creditLimit`", Types.BIGINT, false);
			public Field authStep = new Field(this, "`authStep`", Types.INTEGER, false);
			public Field headImg = new Field(this, "`headImg`", Types.VARCHAR, false);
			public Field enable = new Field(this, "`enable`", Types.INTEGER, false);
			public Field cause = new Field(this, "`cause`", Types.VARCHAR, false);
			public Field createTime = new Field(this, "`createTime`", Types.TIMESTAMP, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field usableSum = new Field(this, "`usableSum`", Types.DECIMAL, false);
			public Field freezeSum = new Field(this, "`freezeSum`", Types.DECIMAL, false);
			public Field dueinSum = new Field(this, "`dueinSum`", Types.DECIMAL, false);
			public Field dueinCapitalSum = new Field(this, "`dueinCapitalSum`", Types.DECIMAL, false);
			public Field dueinAccrualSum = new Field(this, "`dueinAccrualSum`", Types.DECIMAL, false);
			public Field dueoutSum = new Field(this, "`dueoutSum`", Types.DECIMAL, false);
			public Field kefuId = new Field(this, "`kefuId`", Types.BIGINT, false);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field groupId = new Field(this, "`groupId`", Types.BIGINT, false);
			public Field usableCreditLimit = new Field(this, "`usableCreditLimit`", Types.BIGINT, false);
			public Field creditlimtor = new Field(this, "`creditlimtor`", Types.BIGINT, false);
			public Field vipFee = new Field(this, "`vipFee`", Types.DECIMAL, false);
			public Field feeStatus = new Field(this, "`feeStatus`", Types.INTEGER, false);
			public Field loginCount = new Field(this, "`loginCount`", Types.BIGINT, false);
			public Field lockTime = new Field(this, "`lockTime`", Types.TIMESTAMP, false);
			public Field cashStatus = new Field(this, "`cashStatus`", Types.INTEGER, false);
			public Field xmax = new Field(this, "`xmax`", Types.DECIMAL, false);
			public Field x = new Field(this, "`x`", Types.DECIMAL, false);
			public Field xmin = new Field(this, "`xmin`", Types.DECIMAL, false);
			public Field isFirstVip = new Field(this, "`isFirstVip`", Types.INTEGER, false);
			public Field source = new Field(this, "`source`", Types.INTEGER, false);
			public Field registerIp = new Field(this, "`registerIp`", Types.VARCHAR, false);
			public Field usrCustId = new Field(this, "`usrCustId`", Types.BIGINT, false);
			//zhangjhmf add 2015.3.26 设置代理人账户
			public Field accountType = new Field(this, "`accountType`", Types.INTEGER, false);
			
			/*
			 * 20140610 by 刘文韬
			 * 增加群组字段
			 */
			public Field userGroup = new Field(this, "`userGroup`", Types.INTEGER, false);
			public Field colorid = new Field(this, "`userGroup`", Types.INTEGER, false);

			public t_user() {
				name = "`t_user`";
			}
		}

		public class t_userintegraldetail extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userid = new Field(this, "`userid`", Types.BIGINT, false);
			public Field intergraltype = new Field(this, "`intergraltype`", Types.VARCHAR, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field changetype = new Field(this, "`changetype`", Types.VARCHAR, false);
			public Field changerecore = new Field(this, "`changerecore`", Types.VARCHAR, false);
			public Field time = new Field(this, "`time`", Types.TIMESTAMP, false);
			public Field type = new Field(this, "`type`", Types.INTEGER, false);

			public t_userintegraldetail() {
				name = "`t_userintegraldetail`";
			}
		}

		public class t_user_check extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field adminId = new Field(this, "`adminId`", Types.BIGINT, false);
			public Field perrecode = new Field(this, "`perrecode`", Types.VARCHAR, false);
			public Field afterrecode = new Field(this, "`afterrecode`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field checkdate = new Field(this, "`checkdate`", Types.TIMESTAMP, false);
			public Field materialType = new Field(this, "`materialType`", Types.INTEGER, false);

			public t_user_check() {
				name = "`t_user_check`";
			}
		}

		public class t_user_finance extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field realName = new Field(this, "`realName`", Types.VARCHAR, false);
			public Field idNo = new Field(this, "`idNo`", Types.VARCHAR, false);
			public Field cellPhone = new Field(this, "`cellPhone`", Types.VARCHAR, false);
			public Field status = new Field(this, "`status`", Types.TINYINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);

			public t_user_finance() {
				name = "`t_user_finance`";
			}
		}

		public class t_user_kefu extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field kefuImage = new Field(this, "`kefuImage`", Types.VARCHAR, false);
			public Field QQ = new Field(this, "`QQ`", Types.VARCHAR, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);

			public t_user_kefu() {
				name = "`t_user_kefu`";
			}
		}

		public class t_user_recorelist extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field url = new Field(this, "`url`", Types.VARCHAR, false);
			public Field time = new Field(this, "`time`", Types.TIMESTAMP, false);

			public t_user_recorelist() {
				name = "`t_user_recorelist`";
			}
		}

		public class t_vipsum extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field vipFee = new Field(this, "`vipFee`", Types.INTEGER, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_vipsum() {
				name = "`t_vipsum`";
			}
		}

		public class t_safe_question extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field question = new Field(this, "`question`", Types.VARCHAR, false);
			public Field answer = new Field(this, "`answer`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);

			public t_safe_question() {
				name = "`t_safe_question`";
			}
		}

		public class t_short_massege extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field title = new Field(this, "`title`", Types.VARCHAR, false);
			public Field content = new Field(this, "`content`", Types.VARCHAR, false);
			public Field sendTime = new Field(this, "`sendTime`", Types.TIMESTAMP, false);
			public Field style = new Field(this, "`style`", Types.INTEGER, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field receiverType = new Field(this, "`receiverType`", Types.INTEGER, false);
			public Field receiverId = new Field(this, "`receiverId`", Types.VARCHAR, false);

			public t_short_massege() {
				name = "`t_short_massege`";
			}
		}

		public class t_withdraw extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field _name = new Field(this, "`name`", Types.VARCHAR, false);
			public Field cellPhone = new Field(this, "`cellPhone`", Types.VARCHAR, false);
			public Field acount = new Field(this, "`acount`", Types.VARCHAR, false);
			public Field sum = new Field(this, "`sum`", Types.DECIMAL, false);
			public Field poundage = new Field(this, "`poundage`", Types.DECIMAL, false);
			public Field applyTime = new Field(this, "`applyTime`", Types.TIMESTAMP, false);
			public Field status = new Field(this, "`status`", Types.INTEGER, false);
			public Field bankId = new Field(this, "`bankId`", Types.BIGINT, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field remark = new Field(this, "`remark`", Types.VARCHAR, false);
			public Field checkId = new Field(this, "`checkId`", Types.BIGINT, false);
			public Field checkTime = new Field(this, "`checkTime`", Types.TIMESTAMP, false);
			public Field ipAddress = new Field(this, "`ipAddress`", Types.VARCHAR, false);
			public Field versoin = new Field(this, "`versoin`", Types.INTEGER, false);
			public Field trxId = new Field(this, "`trxId`", Types.BIGINT, false);

			public t_withdraw() {
				name = "`t_withdraw`";
			}
		}

		public class t_workauth extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field orgName = new Field(this, "`orgName`", Types.VARCHAR, false);
			public Field occStatus = new Field(this, "`occStatus`", Types.VARCHAR, false);
			public Field workPro = new Field(this, "`workPro`", Types.INTEGER, false);
			public Field workCity = new Field(this, "`workCity`", Types.INTEGER, false);
			public Field companyType = new Field(this, "`companyType`", Types.VARCHAR, false);
			public Field companyLine = new Field(this, "`companyLine`", Types.VARCHAR, false);
			public Field companyScale = new Field(this, "`companyScale`", Types.VARCHAR, false);
			public Field job = new Field(this, "`job`", Types.VARCHAR, false);
			public Field monthlyIncome = new Field(this, "`monthlyIncome`", Types.VARCHAR, false);
			public Field monthlyOutcome = new Field(this, "`monthlyOutcome`", Types.VARCHAR, false);
			public Field workYear = new Field(this, "`workYear`", Types.VARCHAR, false);
			public Field companyTel = new Field(this, "`companyTel`", Types.VARCHAR, false);
			public Field workEmail = new Field(this, "`workEmail`", Types.VARCHAR, false);
			public Field companyAddress = new Field(this, "`companyAddress`", Types.VARCHAR, false);
			public Field directedName = new Field(this, "`directedName`", Types.VARCHAR, false);
			public Field directedRelation = new Field(this, "`directedRelation`", Types.VARCHAR, false);
			public Field directedTel = new Field(this, "`directedTel`", Types.VARCHAR, false);
			public Field directedIdNo = new Field(this, "`directedIdNo`", Types.VARCHAR, false);
			public Field directedAddress = new Field(this, "`directedAddress`", Types.VARCHAR, false);
			public Field otherName = new Field(this, "`otherName`", Types.VARCHAR, false);
			public Field otherRelation = new Field(this, "`otherRelation`", Types.VARCHAR, false);
			public Field otherTel = new Field(this, "`otherTel`", Types.VARCHAR, false);
			public Field otherIdNo = new Field(this, "`otherIdNo`", Types.VARCHAR, false);
			public Field otherAddress = new Field(this, "`otherAddress`", Types.VARCHAR, false);
			public Field moredName = new Field(this, "`moredName`", Types.VARCHAR, false);
			public Field moredRelation = new Field(this, "`moredRelation`", Types.VARCHAR, false);
			public Field moredTel = new Field(this, "`moredTel`", Types.VARCHAR, false);
			public Field userId = new Field(this, "`userId`", Types.BIGINT, false);
			public Field auditStatus = new Field(this, "`auditStatus`", Types.INTEGER, false);
			public Field directedStatus = new Field(this, "`directedStatus`", Types.INTEGER, false);
			public Field otherStatus = new Field(this, "`otherStatus`", Types.INTEGER, false);
			public Field moredStatus = new Field(this, "`moredStatus`", Types.INTEGER, false);

			public t_workauth() {
				name = "`t_workauth`";
			}
		}

		public class user_login_log extends Table {
			public Field id = new Field(this, "`id`", Types.BIGINT, true);
			public Field logindate = new Field(this, "`logindate`", Types.DATE, false);
			public Field userid = new Field(this, "`userid`", Types.BIGINT, false);

			public user_login_log() {
				name = "`user_login_log`";
			}
		}
	}

	public class Views {

		public class intentionfund_user extends View {
			public intentionfund_user() {
				name = "`intentionfund_user`";
			}
		}

		public class v_t_borrow_bring extends View {
			public v_t_borrow_bring() {
				name = "`v_t_borrow_bring`";
			}
		}

		public class v_t_perosn_user extends View {
			public v_t_perosn_user() {
				name = "`v_t_person_user`";
			}
		}

		public class v_t_chianpnr_repay extends View {
			public v_t_chianpnr_repay() {
				name = "`v_t_chianpnr_repay`";
			}
		}

		public class v_t_chianpnr_repayhhn extends View {
			public v_t_chianpnr_repayhhn() {
				name = "`v_t_chianpnr_repayhhn`";
			}
		}

		public class t_bankcard_lists extends View {
			public t_bankcard_lists() {
				name = "`t_bankcard_lists`";
			}
		}

		public class v_t_bankcard_update_list extends View {
			public v_t_bankcard_update_list() {
				name = "`v_t_bankcard_update_list`";
			}
		}

		public class v_t_bankcard_query extends View {
			public v_t_bankcard_query() {
				name = "`v_t_bankcard_query`";
			}
		}

		public class t_borrow_success_list extends View {
			public t_borrow_success_list() {
				name = "`t_borrow_success_list`";
			}
		}

		public class t_fundrecord_list extends View {
			public t_fundrecord_list() {
				name = "`t_fundrecord_list`";
			}
		}

		public class t_success_borrow_details extends View {
			public t_success_borrow_details() {
				name = "`t_success_borrow_details`";
			}
		}

		public class t_success_paying_all_details extends View {
			public t_success_paying_all_details() {
				name = "`t_success_paying_all_details`";
			}
		}

		public class t_success_paying_details extends View {
			public t_success_paying_details() {
				name = "`t_success_paying_details`";
			}
		}

		public class t_usercard_lists extends View {
			public t_usercard_lists() {
				name = "`t_usercard_lists`";
			}
		}

		public class t_user_person_info extends View {
			public t_user_person_info() {
				name = "`t_user_person_info`";
			}
		}

		public class t_user_withdraw_info extends View {
			public t_user_withdraw_info() {
				name = "`t_user_withdraw_info`";
			}
		}

		public class v_admin_assignment_debt_borrow extends View {
			public v_admin_assignment_debt_borrow() {
				name = "`v_admin_assignment_debt_borrow`";
			}
		}

		public class v_blacklist_list extends View {
			public v_blacklist_list() {
				name = "`v_blacklist_list`";
			}
		}

		public class v_debt_aucting_borrow extends View {
			public v_debt_aucting_borrow() {
				name = "`v_debt_aucting_borrow`";
			}
		}

		public class v_debt_invest extends View {
			public v_debt_invest() {
				name = "`v_debt_invest`";
			}
		}

		public class v_fundmessageboard_user extends View {
			public v_fundmessageboard_user() {
				name = "`v_fundmessageboard_user`";
			}
		}

		public class v_funds_admin extends View {
			public v_funds_admin() {
				name = "`v_funds_admin`";
			}
		}

		public class v_invest extends View {
			public v_invest() {
				name = "`v_invest`";
			}
		}

		public class v_pr_user extends View {
			public v_pr_user() {
				name = "`v_pr_user`";
			}
		}

		public class v_t_admin extends View {
			public v_t_admin() {
				name = "`v_t_admin`";
			}
		}

		public class v_t_economy_award extends View {
			public v_t_economy_award() {
				name = "`v_t_economy_award`";
			}
		}

		public class v_t_score_list extends View {
			public v_t_score_list() {
				name = "`v_t_score_list`";
			}
		}

		public class v_t_auto_repay_list extends View {
			public v_t_auto_repay_list() {
				name = "`v_t_auto_repay_list`";
			}
		}

		public class v_hhn_baseinfo extends View {
			public v_hhn_baseinfo() {
				name = "`v_hhn_baseinfo`";
			}
		}

		public class v_t_amount_record extends View {
			public v_t_amount_record() {
				name = "`v_t_amount_record`";
			}
		}

		public class v_t_assignment_debt_audit extends View {
			public v_t_assignment_debt_audit() {
				name = "`v_t_assignment_debt_audit`";
			}
		}

		public class v_t_assignment_debt_success extends View {
			public v_t_assignment_debt_success() {
				name = "`v_t_assignment_debt_success`";
			}
		}

		public class v_t_assignment_debt_username extends View {
			public v_t_assignment_debt_username() {
				name = "`v_t_assignment_debt_username`";
			}
		}

		public class v_t_auction_assignmentdebt extends View {
			public v_t_auction_assignmentdebt() {
				name = "`v_t_auction_assignmentdebt`";
			}
		}

		public class v_t_auction_debt_user extends View {
			public v_t_auction_debt_user() {
				name = "`v_t_auction_debt_user`";
			}
		}

		public class v_t_autobid_borrow extends View {
			public v_t_autobid_borrow() {
				name = "`v_t_autobid_borrow`";
			}
		}

		public class v_t_award_detail extends View {
			public v_t_award_detail() {
				name = "`v_t_award_detail`";
			}
		}

		public class v_t_award_ior_info extends View {
			public v_t_award_ior_info() {
				name = "`v_t_award_ior_info`";
			}
		}

		public class v_t_award_leve1 extends View {
			public v_t_award_leve1() {
				name = "`v_t_award_leve1`";
			}
		}

		public class v_t_award_leve2_sum_money extends View {
			public v_t_award_leve2_sum_money() {
				name = "`v_t_award_leve2_sum_money`";
			}
		}

		public class v_t_award_level2 extends View {
			public v_t_award_level2() {
				name = "`v_t_award_level2`";
			}
		}

		public class v_t_award_month extends View {
			public v_t_award_month() {
				name = "`v_t_award_month`";
			}
		}

		public class v_t_award_mxtype extends View {
			public v_t_award_mxtype() {
				name = "`v_t_award_mxtype`";
			}
		}

		public class v_t_backacmount extends View {
			public v_t_backacmount() {
				name = "`v_t_backacmount`";
			}
		}

		public class v_t_backacount extends View {
			public v_t_backacount() {
				name = "`v_t_backacount`";
			}
		}

		public class v_t_backamount extends View {
			public v_t_backamount() {
				name = "`v_t_backamount`";
			}
		}

		public class v_t_bacount_detail extends View {
			public v_t_bacount_detail() {
				name = "`v_t_bacount_detail`";
			}
		}

		public class v_t_bacount_history_detail extends View {
			public v_t_bacount_history_detail() {
				name = "`v_t_bacount_history_detail`";
			}
		}

		public class v_t_base_check extends View {
			public v_t_base_check() {
				name = "`v_t_base_check`";
			}
		}

		public class v_t_borrow_collection extends View {
			public v_t_borrow_collection() {
				name = "`v_t_borrow_collection`";
			}
		}

		public class v_t_borrow_concern extends View {
			public v_t_borrow_concern() {
				name = "`v_t_borrow_concern`";
			}
		}

		public class v_t_borrow_detail extends View {
			public v_t_borrow_detail() {
				name = "`v_t_borrow_detail`";
			}
		}

		public class v_t_borrow_h extends View {
			public v_t_borrow_h() {
				name = "`v_t_borrow_h`";
			}
		}

		public class v_t_borrow_h_detail extends View {
			public v_t_borrow_h_detail() {
				name = "`v_t_borrow_h_detail`";
			}
		}

		public class v_t_borrow_h_firstaudit extends View {
			public v_t_borrow_h_firstaudit() {
				name = "`v_t_borrow_h_firstaudit`";
			}
		}

		public class v_t_borrow_h_firstaudit_detail extends View {
			public v_t_borrow_h_firstaudit_detail() {
				name = "`v_t_borrow_h_firstaudit_detail`";
			}
		}

		public class v_t_borrow_h_flowmark extends View {
			public v_t_borrow_h_flowmark() {
				name = "`v_t_borrow_h_flowmark`";
			}
		}

		public class v_t_borrow_h_flowmark_detail extends View {
			public v_t_borrow_h_flowmark_detail() {
				name = "`v_t_borrow_h_flowmark_detail`";
			}
		}

		public class v_t_borrow_h_fullscale extends View {
			public v_t_borrow_h_fullscale() {
				name = "`v_t_borrow_h_fullscale`";
			}
		}

		public class v_t_borrow_h_fullscale_detail extends View {
			public v_t_borrow_h_fullscale_detail() {
				name = "`v_t_borrow_h_fullscale_detail`";
			}
		}

		public class v_t_borrow_h_tenderin extends View {
			public v_t_borrow_h_tenderin() {
				name = "`v_t_borrow_h_tenderin`";
			}
		}

		public class v_t_borrow_h_tenderin_detail extends View {
			public v_t_borrow_h_tenderin_detail() {
				name = "`v_t_borrow_h_tenderin_detail`";
			}
		}

		public class v_t_borrow_index extends View {
			public v_t_borrow_index() {
				name = "`v_t_borrow_index`";
			}
		}

		public class v_t_borrow_invest extends View {
			public v_t_borrow_invest() {
				name = "`v_t_borrow_invest`";
			}
		}

		public class v_t_borrow_investrecord extends View {
			public v_t_borrow_investrecord() {
				name = "`v_t_borrow_investrecord`";
			}
		}

		public class v_t_borrow_invest_user extends View {
			public v_t_borrow_invest_user() {
				name = "`v_t_borrow_invest_user`";
			}
		}

		public class v_t_borrow_list extends View {
			public v_t_borrow_list() {
				name = "`v_t_borrow_list`";
			}
		}

		public class v_t_borrow_msgbord extends View {
			public v_t_borrow_msgbord() {
				name = "`v_t_borrow_msgbord`";
			}
		}

		public class v_t_borrow_publish extends View {
			public v_t_borrow_publish() {
				name = "`v_t_borrow_publish`";
			}
		}

		public class v_t_borrow_repayment extends View {
			public v_t_borrow_repayment() {
				name = "`v_t_borrow_repayment`";
			}
		}

		public class v_t_borrow_statis extends View {
			public v_t_borrow_statis() {
				name = "`v_t_borrow_statis`";
			}
		}

		public class v_t_borrow_user_info extends View {
			public v_t_borrow_user_info() {
				name = "`v_t_borrow_user_info`";
			}
		}

		public class v_t_borrow_user_materauth_img extends View {
			public v_t_borrow_user_materauth_img() {
				name = "`v_t_borrow_user_materauth_img`";
			}
		}

		public class v_t_borrow_user_materialsauth extends View {
			public v_t_borrow_user_materialsauth() {
				name = "`v_t_borrow_user_materialsauth`";
			}
		}

		public class v_t_borrow_user_materialsauth_img extends View {
			public v_t_borrow_user_materialsauth_img() {
				name = "`v_t_borrow_user_materialsauth_img`";
			}
		}

		public class v_t_callcenter_help_list extends View {
			public v_t_callcenter_help_list() {
				name = "`v_t_callcenter_help_list`";
			}
		}

		public class v_t_can_assignment_borrow extends View {
			public v_t_can_assignment_borrow() {
				name = "`v_t_can_assignment_borrow`";
			}
		}

		public class v_t_crediting_list extends View {
			public v_t_crediting_list() {
				name = "`v_t_crediting_list`";
			}
		}

		public class v_t_criditpicture extends View {
			public v_t_criditpicture() {
				name = "`v_t_criditpicture`";
			}
		}

		public class v_t_debt_borrow_person extends View {
			public v_t_debt_borrow_person() {
				name = "`v_t_debt_borrow_person`";
			}
		}

		public class v_t_debt_msgbord extends View {
			public v_t_debt_msgbord() {
				name = "`v_t_debt_msgbord`";
			}
		}

		public class v_t_deuin_list extends View {
			public v_t_deuin_list() {
				name = "`v_t_deuin_list`";
			}
		}

		public class v_t_download_detail extends View {
			public v_t_download_detail() {
				name = "`v_t_download_detail`";
			}
		}

		public class v_t_download_list extends View {
			public v_t_download_list() {
				name = "`v_t_download_list`";
			}
		}

		public class v_t_forpayment_h extends View {
			public v_t_forpayment_h() {
				name = "`v_t_forpayment_h`";
			}
		}

		public class v_t_forpayment_h_interest extends View {
			public v_t_forpayment_h_interest() {
				name = "`v_t_forpayment_h_interest`";
			}
		}

		public class v_t_forpayment_h_total extends View {
			public v_t_forpayment_h_total() {
				name = "`v_t_forpayment_h_total`";
			}
		}

		public class v_t_fundrecord_index extends View {
			public v_t_fundrecord_index() {
				name = "`v_t_fundrecord_index`";
			}
		}

		public class v_t_fundrecord_user extends View {
			public v_t_fundrecord_user() {
				name = "`v_t_fundrecord_user`";
			}
		}

		public class v_t_groupuser_user_person extends View {
			public v_t_groupuser_user_person() {
				name = "`v_t_groupuser_user_person`";
			}
		}

		public class v_t_group_admin extends View {
			public v_t_group_admin() {
				name = "`v_t_group_admin`";
			}
		}

		public class v_t_group_for_amount extends View {
			public v_t_group_for_amount() {
				name = "`v_t_group_for_amount`";
			}
		}

		public class v_t_group_managefee extends View {
			public v_t_group_managefee() {
				name = "`v_t_group_managefee`";
			}
		}

		public class v_t_group_user_amount extends View {
			public v_t_group_user_amount() {
				name = "`v_t_group_user_amount`";
			}
		}

		public class v_t_group_vip extends View {
			public v_t_group_vip() {
				name = "`v_t_group_vip`";
			}
		}

		public class v_t_hasrepay_h extends View {
			public v_t_hasrepay_h() {
				name = "`v_t_hasrepay_h`";
			}
		}

		public class v_t_has_amount extends View {
			public v_t_has_amount() {
				name = "`v_t_has_amount`";
			}
		}

		public class v_t_investtotal extends View {
			public v_t_investtotal() {
				name = "`v_t_investtotal`";
			}
		}

		public class v_t_invest_amount extends View {
			public v_t_invest_amount() {
				name = "`v_t_invest_amount`";
			}
		}

		public class v_t_invest_backaccount_list_ extends View {
			public v_t_invest_backaccount_list_() {
				name = "`v_t_invest_backaccount_list_`";
			}
		}

		public class v_t_invest_backacount extends View {
			public v_t_invest_backacount() {
				name = "`v_t_invest_backacount`";
			}
		}

		public class v_t_invest_backacount_sum extends View {
			public v_t_invest_backacount_sum() {
				name = "`v_t_invest_backacount_sum`";
			}
		}

		public class v_t_invest_borrow extends View {
			public v_t_invest_borrow() {
				name = "`v_t_invest_borrow`";
			}
		}

		public class v_t_invest_borrow_list extends View {
			public v_t_invest_borrow_list() {
				name = "`v_t_invest_borrow_list`";
			}
		}

		public class v_t_invest_borrow_sum_trun extends View {
			public v_t_invest_borrow_sum_trun() {
				name = "`v_t_invest_borrow_sum_trun`";
			}
		}

		public class v_t_invest_flow extends View {
			public v_t_invest_flow() {
				name = "`v_t_invest_flow`";
			}
		}

		public class v_t_invest_forpay_detail extends View {
			public v_t_invest_forpay_detail() {
				name = "`v_t_invest_forpay_detail`";
			}
		}

		public class v_t_invest_haspay_detail extends View {
			public v_t_invest_haspay_detail() {
				name = "`v_t_invest_haspay_detail`";
			}
		}

		public class v_t_invest_interest_statis extends View {
			public v_t_invest_interest_statis() {
				name = "`v_t_invest_interest_statis`";
			}
		}

		public class v_t_invest_rank extends View {
			public v_t_invest_rank() {
				name = "`v_t_invest_rank`";
			}
		}

		public class v_t_invest_recycled extends View {
			public v_t_invest_recycled() {
				name = "`v_t_invest_recycled`";
			}
		}

		public class v_t_invest_recycled_ extends View {
			public v_t_invest_recycled_() {
				name = "`v_t_invest_recycled_`";
			}
		}

		public class v_t_invest_recycled_my extends View {
			public v_t_invest_recycled_my() {
				name = "`v_t_invest_recycled_my`";
			}
		}

		public class v_t_invest_recycled_sum extends View {
			public v_t_invest_recycled_sum() {
				name = "`v_t_invest_recycled_sum`";
			}
		}

		public class v_t_invest_recycled_sum_ extends View {
			public v_t_invest_recycled_sum_() {
				name = "`v_t_invest_recycled_sum_`";
			}
		}

		public class v_t_invest_recycling extends View {
			public v_t_invest_recycling() {
				name = "`v_t_invest_recycling`";
			}
		}

		public class v_t_invest_recycling_my extends View {
			public v_t_invest_recycling_my() {
				name = "`v_t_invest_recycling_my`";
			}
		}

		public class v_t_invest_recycling_sum extends View {
			public v_t_invest_recycling_sum() {
				name = "`v_t_invest_recycling_sum`";
			}
		}

		public class v_t_invest_statis extends View {
			public v_t_invest_statis() {
				name = "`v_t_invest_statis`";
			}
		}

		public class v_t_laterepay_h extends View {
			public v_t_laterepay_h() {
				name = "`v_t_laterepay_h`";
			}
		}

		public class v_t_level1_34 extends View {
			public v_t_level1_34() {
				name = "`v_t_level1_34`";
			}
		}

		public class v_t_level2_award extends View {
			public v_t_level2_award() {
				name = "`v_t_level2_award`";
			}
		}

		public class v_t_login_session_verify extends View {
			public v_t_login_session_verify() {
				name = "`v_t_login_session_verify`";
			}
		}

		public class v_t_login_statis extends View {
			public v_t_login_statis() {
				name = "`v_t_login_statis`";
			}
		}

		public class v_t_mail_user extends View {
			public v_t_mail_user() {
				name = "`v_t_mail_user`";
			}
		}

		public class v_t_mail_user_detail extends View {
			public v_t_mail_user_detail() {
				name = "`v_t_mail_user_detail`";
			}
		}

		public class v_t_news_list extends View {
			public v_t_news_list() {
				name = "`v_t_news_list`";
			}
		}

		public class v_t_newusercheck extends View {
			public v_t_newusercheck() {
				name = "`v_t_newusercheck`";
			}
		}

		public class v_t_noshangchuan extends View {
			public v_t_noshangchuan() {
				name = "`v_t_noshangchuan`";
			}
		}

		public class v_t_overduepayment_h extends View {
			public v_t_overduepayment_h() {
				name = "`v_t_overduepayment_h`";
			}
		}

		public class v_t_pasttime extends View {
			public v_t_pasttime() {
				name = "`v_t_pasttime`";
			}
		}

		public class v_t_personcheck extends View {
			public v_t_personcheck() {
				name = "`v_t_personcheck`";
			}
		}

		public class v_t_per_picture extends View {
			public v_t_per_picture() {
				name = "`v_t_per_picture`";
			}
		}

		public class v_t_phone_banding_review extends View {
			public v_t_phone_banding_review() {
				name = "`v_t_phone_banding_review`";
			}
		}

		public class v_t_phone_invsert extends View {
			public v_t_phone_invsert() {
				name = "`v_t_phone_invsert`";
			}
		}

		public class v_t_recharge_detail extends View {
			public v_t_recharge_detail() {
				name = "`v_t_recharge_detail`";
			}
		}

		public class v_t_recommendfriend_list extends View {
			public v_t_recommendfriend_list() {
				name = "`v_t_recommendfriend_list`";
			}
		}

		public class v_t_relation_award_level3 extends View {
			public v_t_relation_award_level3() {
				name = "`v_t_relation_award_level3`";
			}
		}

		public class v_t_relation_award_level4 extends View {
			public v_t_relation_award_level4() {
				name = "`v_t_relation_award_level4`";
			}
		}

		public class v_t_relation_enable extends View {
			public v_t_relation_enable() {
				name = "`v_t_relation_enable`";
			}
		}

		public class v_t_relation_level2 extends View {
			public v_t_relation_level2() {
				name = "`v_t_relation_level2`";
			}
		}

		public class v_t_relation_level3 extends View {
			public v_t_relation_level3() {
				name = "`v_t_relation_level3`";
			}
		}

		public class v_t_relation_level4 extends View {
			public v_t_relation_level4() {
				name = "`v_t_relation_level4`";
			}
		}

		public class v_t_repayment_detail extends View {
			public v_t_repayment_detail() {
				name = "`v_t_repayment_detail`";
			}
		}

		public class v_t_repayment_h extends View {
			public v_t_repayment_h() {
				name = "`v_t_repayment_h`";
			}
		}

		public class v_t_risk_detail_h extends View {
			public v_t_risk_detail_h() {
				name = "`v_t_risk_detail_h`";
			}
		}

		public class v_t_risk_list_h extends View {
			public v_t_risk_list_h() {
				name = "`v_t_risk_list_h`";
			}
		}

		public class v_t_role_rights_menu extends View {
			public v_t_role_rights_menu() {
				name = "`v_t_role_rights_menu`";
			}
		}

		public class v_t_sum_invesamount extends View {
			public v_t_sum_invesamount() {
				name = "`v_t_sum_invesamount`";
			}
		}

		public class v_t_usercard_lists extends View {
			public v_t_usercard_lists() {
				name = "`v_t_usercard_lists`";
			}
		}

		public class v_t_usermanage_baseinfo extends View {
			public v_t_usermanage_baseinfo() {
				name = "`v_t_usermanage_baseinfo`";
			}
		}

		public class v_t_usermanage_baseinfoinner extends View {
			public v_t_usermanage_baseinfoinner() {
				name = "`v_t_usermanage_baseinfoinner`";
			}
		}

		public class v_t_usermanage_info extends View {
			public v_t_usermanage_info() {
				name = "`v_t_usermanage_info`";
			}
		}

		public class v_t_usermanage_integralinfo extends View {
			public v_t_usermanage_integralinfo() {
				name = "`v_t_usermanage_integralinfo`";
			}
		}

		public class v_t_usermanage_integralinner extends View {
			public v_t_usermanage_integralinner() {
				name = "`v_t_usermanage_integralinner`";
			}
		}

		public class v_t_usermanage_invest extends View {
			public v_t_usermanage_invest() {
				name = "`v_t_usermanage_invest`";
			}
		}

		public class v_t_usermanage_viprecordinfo extends View {
			public v_t_usermanage_viprecordinfo() {
				name = "`v_t_usermanage_viprecordinfo`";
			}
		}

		public class v_t_user_address extends View {
			public v_t_user_address() {
				name = "`v_t_user_address`";
			}
		}

		public class v_t_user_adminchecklist extends View {
			public v_t_user_adminchecklist() {
				name = "`v_t_user_adminchecklist`";
			}
		}

		public class v_t_user_amountofrecords extends View {
			public v_t_user_amountofrecords() {
				name = "`v_t_user_amountofrecords`";
			}
		}

		public class v_t_user_approve_lists extends View {
			public v_t_user_approve_lists() {
				name = "`v_t_user_approve_lists`";
			}
		}

		public class v_t_user_auth extends View {
			public v_t_user_auth() {
				name = "`v_t_user_auth`";
			}
		}

		public class v_t_user_backrw_lists extends View {
			public v_t_user_backrw_lists() {
				name = "`v_t_user_backrw_lists`";
			}
		}

		public class v_t_user_base_approve_lists extends View {
			public v_t_user_base_approve_lists() {
				name = "`v_t_user_base_approve_lists`";
			}
		}

		public class v_t_user_cash_lists extends View {
			public v_t_user_cash_lists() {
				name = "`v_t_user_cash_lists`";
			}
		}

		public class v_t_user_concern_lists extends View {
			public v_t_user_concern_lists() {
				name = "`v_t_user_concern_lists`";
			}
		}

		public class v_t_user_creditlimit_apply extends View {
			public v_t_user_creditlimit_apply() {
				name = "`v_t_user_creditlimit_apply`";
			}
		}

		public class v_t_user_credit_apply_msgas extends View {
			public v_t_user_credit_apply_msgas() {
				name = "`v_t_user_credit_apply_msgas`";
			}
		}

		public class v_t_user_credit_msg extends View {
			public v_t_user_credit_msg() {
				name = "`v_t_user_credit_msg`";
			}
		}

		public class v_t_user_credit_hhn extends View {
			public v_t_user_credit_hhn() {
				name = "`v_t_user_credit_hhn`";
			}
		}

		public class v_t_user_credit_msg_agin extends View {
			public v_t_user_credit_msg_agin() {
				name = "`v_t_user_credit_msg_agin`";
			}
		}

		public class v_t_user_frends extends View {
			public v_t_user_frends() {
				name = "`v_t_user_frends`";
			}
		}

		public class v_t_user_frontmeg extends View {
			public v_t_user_frontmeg() {
				name = "`v_t_user_frontmeg`";
			}
		}

		public class v_t_user_frontpictur extends View {
			public v_t_user_frontpictur() {
				name = "`v_t_user_frontpictur`";
			}
		}

		public class v_t_user_frontpictur_t extends View {
			public v_t_user_frontpictur_t() {
				name = "`v_t_user_frontpictur_t`";
			}
		}

		public class v_t_user_fundrecord_lists extends View {
			public v_t_user_fundrecord_lists() {
				name = "`v_t_user_fundrecord_lists`";
			}
		}

		public class v_t_user_fundwithdraw_lists extends View {
			public v_t_user_fundwithdraw_lists() {
				name = "`v_t_user_fundwithdraw_lists`";
			}
		}

		public class v_t_user_fund_lists extends View {
			public v_t_user_fund_lists() {
				name = "`v_t_user_fund_lists`";
			}
		}

		public class v_t_user_lock extends View {
			public v_t_user_lock() {
				name = "`v_t_user_lock`";
			}
		}

		public class v_t_user_loginsession_user extends View {
			public v_t_user_loginsession_user() {
				name = "`v_t_user_loginsession_user`";
			}
		}

		public class v_t_user_myborrowlist extends View {
			public v_t_user_myborrowlist() {
				name = "`v_t_user_myborrowlist`";
			}
		}

		public class v_t_user_myborrowrecorde extends View {
			public v_t_user_myborrowrecorde() {
				name = "`v_t_user_myborrowrecorde`";
			}
		}

		public class v_t_user_newuser extends View {
			public v_t_user_newuser() {
				name = "`v_t_user_newuser`";
			}
		}

		public class v_t_user_person_work extends View {
			public v_t_user_person_work() {
				name = "`v_t_user_person_work`";
			}
		}

		public class v_t_user_picture extends View {
			public v_t_user_picture() {
				name = "`v_t_user_picture`";
			}
		}

		public class v_t_user_picture_base extends View {
			public v_t_user_picture_base() {
				name = "`v_t_user_picture_base`";
			}
		}

		public class v_t_user_picture_msg extends View {
			public v_t_user_picture_msg() {
				name = "`v_t_user_picture_msg`";
			}
		}

		public class v_t_user_picture_select extends View {
			public v_t_user_picture_select() {
				name = "`v_t_user_picture_select`";
			}
		}

		public class v_t_user_picture_select_2 extends View {
			public v_t_user_picture_select_2() {
				name = "`v_t_user_picture_select_2`";
			}
		}

		public class v_t_user_picture_select_3 extends View {
			public v_t_user_picture_select_3() {
				name = "`v_t_user_picture_select_3`";
			}
		}

		public class v_t_user_rechargeall_lists extends View {
			public v_t_user_rechargeall_lists() {
				name = "`v_t_user_rechargeall_lists`";
			}
		}

		public class v_t_user_rechargedetails_list extends View {
			public v_t_user_rechargedetails_list() {
				name = "`v_t_user_rechargedetails_list`";
			}
		}

		public class v_t_assignment_debt extends View {
			public v_t_assignment_debt() {
				name = "`v_t_assignment_debt`";
			}
		}

		public class v_t_user_rechargefirst_lists extends View {
			public v_t_user_rechargefirst_lists() {
				name = "`v_t_user_rechargefirst_lists`";
			}
		}

		public class v_t_user_rescher extends View {
			public v_t_user_rescher() {
				name = "`v_t_user_rescher`";
			}
		}

		public class v_t_user_selectpicture extends View {
			public v_t_user_selectpicture() {
				name = "`v_t_user_selectpicture`";
			}
		}

		public class v_t_user_select_credit extends View {
			public v_t_user_select_credit() {
				name = "`v_t_user_select_credit`";
			}
		}

		public class v_t_user_select_credit_last extends View {
			public v_t_user_select_credit_last() {
				name = "`v_t_user_select_credit_last`";
			}
		}

		public class v_t_user_showfirstpicture extends View {
			public v_t_user_showfirstpicture() {
				name = "`v_t_user_showfirstpicture`";
			}
		}

		public class v_t_user_successtotalbid_lists extends View {
			public v_t_user_successtotalbid_lists() {
				name = "`v_t_user_successtotalbid_lists`";
			}
		}

		public class v_t_user_totalbiddone_lists extends View {
			public v_t_user_totalbiddone_lists() {
				name = "`v_t_user_totalbiddone_lists`";
			}
		}

		public class v_t_user_un_approve_lists extends View {
			public v_t_user_un_approve_lists() {
				name = "`v_t_user_un_approve_lists`";
			}
		}

		public class v_t_user_usertel extends View {
			public v_t_user_usertel() {
				name = "`v_t_user_usertel`";
			}
		}

		public class v_t_user_verypictur extends View {
			public v_t_user_verypictur() {
				name = "`v_t_user_verypictur`";
			}
		}

		public class v_t_user_withdraw_lists extends View {
			public v_t_user_withdraw_lists() {
				name = "`v_t_user_withdraw_lists`";
			}
		}

		public class v_t_vippicture extends View {
			public v_t_vippicture() {
				name = "`v_t_vippicture`";
			}
		}

		public class v_t_automaticbid extends View {
			public v_t_automaticbid() {
				name = "`v_t_automaticbid`";
			}
		}
	}

	public static class Functions {

		public static String f_borrow_type_decode(Connection conn, int in_borrow_way) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_borrow_type_decode`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.INTEGER,
					ParameterDirection.IN, in_borrow_way));

			return (String) result;
		}

		public static String f_convertAmount(Connection conn, BigDecimal amount) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_convertAmount`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.DECIMAL,
					ParameterDirection.IN, amount));

			return (String) result;
		}

		public static int f_credit_rating(Connection conn, long creditrating) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_credit_rating`", new Parameter(Types.INTEGER, ParameterDirection.RETURN, null), new Parameter(Types.BIGINT,
					ParameterDirection.IN, creditrating));

			return (Integer) result;
		}

		public static String f_formatAmount(Connection conn, BigDecimal amount) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_formatAmount`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.DECIMAL,
					ParameterDirection.IN, amount));

			return (String) result;
		}

		public static String f_formatting_username(Connection conn, String v_str) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_formatting_username`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, v_str));

			return (String) result;
		}

		public static String f_get_notice_template(Connection conn, String in_nid) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_get_notice_template`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_nid));

			return (String) result;
		}

		public static String f_injectPoint(Connection conn, String pointStr) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_injectPoint`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, pointStr));

			return (String) result;
		}

		public static String f_link(Connection conn, String in_basepath, long in_id, String title, String content, String other, String type) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_link`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_basepath), new Parameter(Types.BIGINT, ParameterDirection.IN, in_id), new Parameter(Types.VARCHAR, ParameterDirection.IN, title),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, content), new Parameter(Types.VARCHAR, ParameterDirection.IN, other), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, type));

			return (String) result;
		}

		public static String f_link_debt(Connection conn, String in_basepath, long in_id, String title, String content, String other, String type) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_link_debt`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_basepath), new Parameter(Types.BIGINT, ParameterDirection.IN, in_id), new Parameter(Types.VARCHAR, ParameterDirection.IN, title),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, content), new Parameter(Types.VARCHAR, ParameterDirection.IN, other), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, type));

			return (String) result;
		}

		public static String f_moneyDecode(Connection conn, int moneyStyle) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_moneyDecode`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.INTEGER,
					ParameterDirection.IN, moneyStyle));

			return (String) result;
		}

		public static int f_moneyEncode(Connection conn, String moneyStyle) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_moneyEncode`", new Parameter(Types.INTEGER, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, moneyStyle));

			return (Integer) result;
		}

		public static BigDecimal f_parsing_json(Connection conn, String in_str, String in_begin_str) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_parsing_json`", new Parameter(Types.DECIMAL, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_str), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_begin_str));

			return (BigDecimal) result;
		}

		public static int f_rating(Connection conn, long rating) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_rating`", new Parameter(Types.INTEGER, ParameterDirection.RETURN, null), new Parameter(Types.BIGINT,
					ParameterDirection.IN, rating));

			return (Integer) result;
		}

		public static int f_send_temple(Connection conn, int in_id) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_send_temple`", new Parameter(Types.INTEGER, ParameterDirection.RETURN, null), new Parameter(Types.INTEGER,
					ParameterDirection.IN, in_id));

			return (Integer) result;
		}

		public static String f_split(Connection conn, String f_string, String f_delimiter, int f_order) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_split`", new Parameter(Types.VARCHAR, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, f_string), new Parameter(Types.VARCHAR, ParameterDirection.IN, f_delimiter),
					new Parameter(Types.INTEGER, ParameterDirection.IN, f_order));

			return (String) result;
		}

		public static int f_split_TotalLength(Connection conn, String f_string, String f_delimiter) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_split_TotalLength`", new Parameter(Types.INTEGER, ParameterDirection.RETURN, null), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, f_string), new Parameter(Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return (Integer) result;
		}

		public static int f_vip(Connection conn, long rating) throws SQLException {
			Object result = MySQL.executeFunction(conn, "`f_vip`", new Parameter(Types.INTEGER, ParameterDirection.RETURN, null), new Parameter(Types.BIGINT,
					ParameterDirection.IN, rating));

			return (Integer) result;
		}
	}

	public static class Procedures {

		public static int pr_examine(Connection conn, DataSet ds, List<Object> outParameterValues, long adminId) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_examine`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, adminId));

			return result;
		}

		public static int pr_getBackAcountStatis(Connection conn, DataSet ds, List<Object> outParameterValues, String startTime, String endTime, String title, long in_uid)
				throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getBackAcountStatis`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, startTime),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, endTime), new Parameter(Types.VARCHAR, ParameterDirection.IN, title), new Parameter(Types.BIGINT,
							ParameterDirection.IN, in_uid));

			return result;
		}

		public static int pr_getBorrowRecord(Connection conn, DataSet ds, List<Object> outParameterValues, long borrowId, Date date) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getBorrowRecord`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, borrowId), new Parameter(
					Types.DATE, ParameterDirection.IN, date));

			return result;
		}

		public static int pr_getBorrowRecord_user(Connection conn, DataSet ds, List<Object> outParameterValues, long in_userId, Date date) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getBorrowRecord_user`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_userId),
					new Parameter(Types.DATE, ParameterDirection.IN, date));

			return result;
		}

		public static int pr_getBorrowStatis(Connection conn, DataSet ds, List<Object> outParameterValues, long user, Date date) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getBorrowStatis`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, user), new Parameter(
					Types.DATE, ParameterDirection.IN, date));

			return result;
		}

		public static int pr_getBorrowTypeNet(Connection conn, DataSet ds, List<Object> outParameterValues, long user, BigDecimal borrowSum) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getBorrowTypeNet`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, user), new Parameter(
					Types.DECIMAL, ParameterDirection.IN, borrowSum));

			return result;
		}

		public static int pr_getFinanceEarnStatis(Connection conn, DataSet ds, List<Object> outParameterValues, String timeStart, String timeEnd) throws SQLException,
				DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getFinanceEarnStatis`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, timeStart),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, timeEnd));

			return result;
		}

		public static int pr_getFinanceStatis(Connection conn, DataSet ds, List<Object> outParameterValues, long uId) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getFinanceStatis`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, uId));

			return result;
		}

		public static int pr_getUserAmountSumary(Connection conn, DataSet ds, List<Object> outParameterValues, long userId) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getUserAmountSumary`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, userId));

			return result;
		}

		public static int pr_getUserInfo(Connection conn, DataSet ds, List<Object> outParameterValues, long user, String timeStart, String timeEnd) throws SQLException,
				DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getUserInfo`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, user), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, timeStart), new Parameter(Types.VARCHAR, ParameterDirection.IN, timeEnd));

			return result;
		}

		public static int pr_getWebStatis(Connection conn, DataSet ds, List<Object> outParameterValues, long id) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getWebStatis`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, id));

			return result;
		}

		public static int pr_getWebStatisFunds(Connection conn, DataSet ds, List<Object> outParameterValues, long id) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_getWebStatisFunds`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, id));

			return result;
		}

		public static int pr_investStatistics(Connection conn, DataSet ds, List<Object> outParameterValues, long adminId) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_investStatistics`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, adminId));

			return result;
		}

		public static int pr_inviteFriendsReward(Connection conn, DataSet ds, List<Object> outParameterValues, Date rewardTime) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_inviteFriendsReward`", ds, outParameterValues, new Parameter(Types.TIMESTAMP, ParameterDirection.IN, rewardTime));

			return result;
		}

		public static int pr_modifyUserAmount(Connection conn, DataSet ds, List<Object> outParameterValues, BigDecimal money, int moneyStyle, String remark, long uId, long tId,
				Date addTime, String addIP) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_modifyUserAmount`", ds, outParameterValues, new Parameter(Types.DECIMAL, ParameterDirection.IN, money), new Parameter(
					Types.INTEGER, ParameterDirection.IN, moneyStyle), new Parameter(Types.VARCHAR, ParameterDirection.IN, remark), new Parameter(Types.BIGINT,
					ParameterDirection.IN, uId), new Parameter(Types.BIGINT, ParameterDirection.IN, tId), new Parameter(Types.TIMESTAMP, ParameterDirection.IN, addTime),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, addIP));

			return result;
		}

		public static int pr_page(Connection conn, DataSet ds, List<Object> outParameterValues, String p_table_name, String p_fields, long p_page_size, long p_page_now,
				String P_order_string, String p_where_string, long p_out_rows) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_page`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, p_table_name), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, p_fields), new Parameter(Types.BIGINT, ParameterDirection.IN, p_page_size), new Parameter(Types.BIGINT,
					ParameterDirection.IN, p_page_now), new Parameter(Types.VARCHAR, ParameterDirection.IN, P_order_string), new Parameter(Types.VARCHAR, ParameterDirection.IN,
					p_where_string), new Parameter(Types.BIGINT, ParameterDirection.OUT, p_out_rows));

			return result;
		}

		public static int pr_refreshBorrowTime(Connection conn, DataSet ds, List<Object> outParameterValues, Date currentTime) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`pr_refreshBorrowTime`", ds, outParameterValues, new Parameter(Types.TIMESTAMP, ParameterDirection.IN, currentTime));

			return result;
		}

		public static int p_amount_withdraw(Connection conn, DataSet ds, List<Object> outParameterValues, long in_uid, String in_dealpwd, BigDecimal in_money, long in_bank_id,
				String in_type, String in_ip, String in_date, int out_ret, String out_desc, String out_ordid) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_amount_withdraw`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, in_dealpwd), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_money), new Parameter(Types.BIGINT,
					ParameterDirection.IN, in_bank_id), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_type), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_ip),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_date), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
							ParameterDirection.OUT, out_desc), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_ordid));

			return result;
		}

		public static int p_amount_withdraw_auth(Connection conn, DataSet ds, List<Object> outParameterValues, long in_uid, long in_aid, long in_wid, BigDecimal in_money,
				BigDecimal in_poundage, int in_status, String in_ip, String in_remark, String in_date, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_amount_withdraw_auth`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_aid), new Parameter(Types.BIGINT, ParameterDirection.IN, in_wid), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_money), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_poundage), new Parameter(Types.INTEGER, ParameterDirection.IN,
							in_status), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_ip), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_remark), new Parameter(
							Types.VARCHAR, ParameterDirection.IN, in_date), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
							ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_amount_withdraw_cancel(Connection conn, DataSet ds, List<Object> outParameterValues, long in_uid, long in_wid, String in_ip, String in_date,
				int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_amount_withdraw_cancel`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_wid), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_ip), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_date), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT,
							out_desc));

			return result;
		}

		public static int p_amount_withdraw_transfer(Connection conn, DataSet ds, List<Object> outParameterValues, long in_aid, long in_wid, long in_status, String in_ip,
				String in_date, String realSum, String feeAmt, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_amount_withdraw_transfer`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_aid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_wid), new Parameter(Types.BIGINT, ParameterDirection.IN, in_status), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_ip), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_date), new Parameter(Types.DECIMAL, ParameterDirection.IN,
							realSum), new Parameter(Types.DECIMAL, ParameterDirection.IN, feeAmt), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(
							Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_automatic_payment(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, int out_ret, String out_desc) throws SQLException,
				DataException {
			int result = MySQL.executeProcedure(conn, "`p_automatic_payment`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_auto_firstvip(Connection conn, DataSet ds, List<Object> outParameterValues, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_auto_firstvip`", ds, outParameterValues, new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(
					Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_auto_pastvipfee(Connection conn, DataSet ds, List<Object> outParameterValues, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_auto_pastvipfee`", ds, outParameterValues, new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(
					Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_bevip(Connection conn, DataSet ds, List<Object> outParameterValues, long in_uid, long in_server, BigDecimal in_vip_fee, BigDecimal in_friend_fee,
				int out_ret, String out_desc, String out_vip_desc, String out_friend_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_bevip`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.BIGINT,
					ParameterDirection.IN, in_server), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_vip_fee), new Parameter(Types.DECIMAL, ParameterDirection.IN,
					in_friend_fee), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc), new Parameter(
					Types.VARCHAR, ParameterDirection.OUT, out_vip_desc), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_friend_desc));

			return result;
		}

		public static int p_borrow_audit(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_uid, int in_status, String in_msg,
				String in_audit_opinion, String in_base_path, Date in_deal_time, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_audit`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.INTEGER, ParameterDirection.IN, in_status), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_msg), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_audit_opinion), new Parameter(Types.VARCHAR, ParameterDirection.IN,
					in_base_path), new Parameter(Types.TIMESTAMP, ParameterDirection.IN, in_deal_time), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret),
					new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_auth_fullscale(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_status, int out_ret, String out_desc,
				BigDecimal out_amount, BigDecimal out_annualrate, int out_deadline, int out_isdaythe, int out_payment_mode) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_auth_fullscale`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_status), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
							ParameterDirection.OUT, out_desc), new Parameter(Types.DECIMAL, ParameterDirection.OUT, out_amount), new Parameter(Types.DECIMAL,
							ParameterDirection.OUT, out_annualrate), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_deadline), new Parameter(Types.INTEGER,
							ParameterDirection.OUT, out_isdaythe), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_payment_mode));

			return result;
		}

		public static int p_borrow_autobid(Connection conn, DataSet ds, List<Object> outParameterValues, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_autobid`", ds, outParameterValues, new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(
					Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_cancel(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_aid, int in_status, String in_audit_opinion,
				String in_basepath, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_cancel`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_aid), new Parameter(Types.INTEGER, ParameterDirection.IN, in_status), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_audit_opinion), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_basepath), new Parameter(Types.INTEGER,
					ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_deal_fullscale(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_aid, long in_status, String auditTime,
				String in_audit_opinion, String in_identify, String in_basepath, double investFeeRate, double borrowFeeRateOne, double borrowInmonthFeeRateOne,
				double borrowOutmonthFeeRateOne, double borrowDayFeeRateOne, double borrowInmonthFeeRateTwo, double borrowOutmonthFeeRateTwo, double borrowDayFeeRateTwo,
				double borrowInmonthFeeRateThree, double borrowOutmonthFeeRateThree, double borrowDayFeeRateThree, double borrowInmonthFeeRateFour,
				double borrowOutmonthFeeRateFour, double borrowFeeHHN, int out_ret, String out_desc) throws SQLException, DataException {

			int result = MySQL.executeProcedure(conn, "p_borrow_deal_fullscale", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_aid), new Parameter(Types.BIGINT, ParameterDirection.IN, in_status), new Parameter(Types.DATE, ParameterDirection.IN,
					auditTime), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_audit_opinion), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_identify),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_basepath), new Parameter(Types.DECIMAL, ParameterDirection.IN, investFeeRate), new Parameter(
							Types.DECIMAL, ParameterDirection.IN, borrowFeeRateOne), new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowInmonthFeeRateOne), new Parameter(
							Types.DECIMAL, ParameterDirection.IN, borrowOutmonthFeeRateOne), new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowDayFeeRateOne),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowInmonthFeeRateTwo), new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowOutmonthFeeRateTwo),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowDayFeeRateTwo), new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowInmonthFeeRateThree),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowOutmonthFeeRateThree), new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowDayFeeRateThree),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowInmonthFeeRateFour), new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowOutmonthFeeRateFour),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, borrowFeeHHN), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
							ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_debt_add(Connection conn, DataSet ds, List<Object> outParameterValues, long in_debt_id, long in_uid, BigDecimal in_aucion_price,
				String in_debt_pwd, String in_basePath, String in_investId, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_debt_add`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_debt_id), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_aucion_price), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_debt_pwd), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_basePath), new Parameter(Types.VARCHAR, ParameterDirection.IN,
					in_investId), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_initialization(Connection conn, DataSet ds, List<Object> outParameterValues, long in_uid, String in_borrow_title, String in_img_path,
				int in_borrow_way, String in_borrow_info, int in_deadline, int in_payment_mode, BigDecimal in_borrow_amount, BigDecimal in_annual_rate,
				BigDecimal in_min_tendered_sum, BigDecimal in_max_tendered_sum, BigDecimal in_raise_term, String in_detail, int in_trade_type, long in_publisher,
				int in_excitation_type, BigDecimal in_excitation_sum, int in_excitation_mode, int in_purpose, int in_haspwd, String in_investpwd, Date in_publish_time,
				String in_publiship, int in_isdaythe, BigDecimal in_smallest_flow_unit, int in_circulation_number, String nid_log, BigDecimal in_frozen_margin, String in_url,
				String in_bathpath, BigDecimal in_cost_fee, String in_feelog, String in_feestate, String in_agent, String in_counter_agent, String in_business_detail,
				String in_assets, String in_money_purposes, int in_borrow_show, int in_source, int in_maxInvest, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_initialization`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_borrow_title), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_img_path), new Parameter(
							Types.INTEGER, ParameterDirection.IN, in_borrow_way), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_borrow_info), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_deadline), new Parameter(Types.INTEGER, ParameterDirection.IN, in_payment_mode), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_borrow_amount), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_annual_rate), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_min_tendered_sum), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_max_tendered_sum), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_raise_term), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_detail), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_trade_type), new Parameter(Types.BIGINT, ParameterDirection.IN, in_publisher), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_excitation_type), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_excitation_sum), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_excitation_mode), new Parameter(Types.INTEGER, ParameterDirection.IN, in_purpose), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_haspwd), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_investpwd), new Parameter(Types.TIMESTAMP,
							ParameterDirection.IN, in_publish_time), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_publiship), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_isdaythe), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_smallest_flow_unit), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_circulation_number), new Parameter(Types.VARCHAR, ParameterDirection.IN, nid_log), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_frozen_margin), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_url), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_bathpath), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_cost_fee), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_feelog), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_feestate), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_agent), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_counter_agent), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_business_detail), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_assets), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_money_purposes), new Parameter(Types.INTEGER, ParameterDirection.IN, in_borrow_show), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_source), new Parameter(Types.INTEGER, ParameterDirection.IN, in_maxInvest), new Parameter(Types.INTEGER,
							ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_initialization_hhn(Connection conn, DataSet ds, List<Object> outParameterValues, long in_uid, String in_borrow_title, String in_img_path,
				int in_borrow_way, String in_borrow_info, int in_deadline, int in_payment_mode, BigDecimal in_borrow_amount, BigDecimal in_annual_rate,
				BigDecimal in_min_tendered_sum, BigDecimal in_max_tendered_sum, BigDecimal in_raise_term, String in_detail, int in_trade_type, long in_publisher,
				int in_excitation_type, BigDecimal in_excitation_sum, int in_excitation_mode, int in_purpose, int in_haspwd, String in_investpwd, Date in_publish_time,
				String in_publiship, int in_isdaythe, BigDecimal in_smallest_flow_unit, int in_circulation_number, String nid_log, BigDecimal in_frozen_margin, String in_url,
				String in_bathpath, BigDecimal in_cost_fee, String in_feelog, String in_feestate, String in_agent, String in_counter_agent, String in_business_detail,
				String in_assets, String in_money_purposes, int in_borrow_show, int in_source, int in_maxInvest, String in_zxf, String in_zxfh,int borrowGroup,String businessNo, int out_ret, String out_desc)
				throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_initialization_hhn`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_borrow_title), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_img_path), new Parameter(
							Types.INTEGER, ParameterDirection.IN, in_borrow_way), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_borrow_info), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_deadline), new Parameter(Types.INTEGER, ParameterDirection.IN, in_payment_mode), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_borrow_amount), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_annual_rate), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_min_tendered_sum), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_max_tendered_sum), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_raise_term), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_detail), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_trade_type), new Parameter(Types.BIGINT, ParameterDirection.IN, in_publisher), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_excitation_type), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_excitation_sum), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_excitation_mode), new Parameter(Types.INTEGER, ParameterDirection.IN, in_purpose), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_haspwd), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_investpwd), new Parameter(Types.TIMESTAMP,
							ParameterDirection.IN, in_publish_time), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_publiship), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_isdaythe), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_smallest_flow_unit), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_circulation_number), new Parameter(Types.VARCHAR, ParameterDirection.IN, nid_log), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_frozen_margin), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_url), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_bathpath), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_cost_fee), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_feelog), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_feestate), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_agent), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_counter_agent), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_business_detail), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_assets), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_money_purposes), new Parameter(Types.INTEGER, ParameterDirection.IN, in_borrow_show), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_source), new Parameter(Types.INTEGER, ParameterDirection.IN, in_maxInvest), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_zxf), 
							new Parameter(Types.VARCHAR, ParameterDirection.IN, in_zxfh),
							new Parameter(Types.INTEGER, ParameterDirection.IN,borrowGroup ),
							new Parameter(Types.VARCHAR, ParameterDirection.IN,businessNo ),
							new Parameter(Types.INTEGER, ParameterDirection.OUT,
							out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_join(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_uid, String in_basepath, BigDecimal in_invest_amount,
				Date in_invest_time, int in_status, int in_num, int out_ret, String out_desc, String out_ordid) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_join`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_basepath), new Parameter(Types.DECIMAL,
					ParameterDirection.IN, in_invest_amount), new Parameter(Types.TIMESTAMP, ParameterDirection.IN, in_invest_time), new Parameter(Types.INTEGER,
					ParameterDirection.IN, in_status), new Parameter(Types.INTEGER, ParameterDirection.IN, in_num), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret),
					new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_ordid));

			return result;
		}

		public static int p_borrow_join_call_back(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_ordid, long in_uid, String in_basepath,
				BigDecimal in_invest_amount, Date in_invest_time, int in_status, int in_num, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_join_call_back`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_ordid), new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_basepath), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_invest_amount), new Parameter(Types.TIMESTAMP,
							ParameterDirection.IN, in_invest_time), new Parameter(Types.INTEGER, ParameterDirection.IN, in_status), new Parameter(Types.INTEGER,
							ParameterDirection.IN, in_num), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT,
							out_desc));

			return result;
		}

		public static int p_borrow_repayment(Connection conn, DataSet ds, List<Object> outParameterValues, long in_pid, long in_uid, String in_dealpwd, String in_basepath,
				Date in_pay_time, BigDecimal in_ifeerate, String in_needSum, String in_fee, String in_consultFee, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_repayment`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_pid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_dealpwd), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_basepath), new Parameter(Types.TIMESTAMP, ParameterDirection.IN, in_pay_time), new Parameter(Types.DECIMAL, ParameterDirection.IN,
					in_ifeerate), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_needSum), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_fee), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, in_consultFee), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
					ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_repayment_time(Connection conn, DataSet ds, List<Object> outParameterValues, long in_pid, long in_uid, String in_dealpwd, String in_basepath,
				Date in_pay_time, BigDecimal in_ifeerate, BigDecimal needSum, String in_fee, String in_consultFee, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_repayment_time`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_pid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_uid), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_dealpwd), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_basepath), new Parameter(Types.TIMESTAMP, ParameterDirection.IN, in_pay_time), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_ifeerate), new Parameter(Types.DECIMAL, ParameterDirection.IN, needSum), new Parameter(Types.VARCHAR, ParameterDirection.IN,
							in_fee), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_consultFee), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret),
					new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_borrow_repayment_overdue(Connection conn, DataSet ds, List<Object> outParameterValues, long in_pid, long in_aid, String in_basepath, Date in_pay_time,
				BigDecimal in_ifeerate, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_borrow_repayment_overdue`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_pid),
					new Parameter(Types.BIGINT, ParameterDirection.IN, in_aid), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_basepath), new Parameter(Types.TIMESTAMP,
							ParameterDirection.IN, in_pay_time), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_ifeerate), new Parameter(Types.INTEGER,
							ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_deleteKefu(Connection conn, DataSet ds, List<Object> outParameterValues, String f_string, String f_delimiter) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_deleteKefu`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, f_string), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return result;
		}

		public static int p_deleteMailBox(Connection conn, DataSet ds, List<Object> outParameterValues, String f_string, String f_delimiter) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_deleteMailBox`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, f_string), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return result;
		}

		public static int p_deleteMediaReport(Connection conn, DataSet ds, List<Object> outParameterValues, String f_string, String f_delimiter) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_deleteMediaReport`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, f_string),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return result;
		}

		public static int p_deleteNews(Connection conn, DataSet ds, List<Object> outParameterValues, String f_string, String f_delimiter) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_deleteNews`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, f_string), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return result;
		}

		public static int p_deleteStory(Connection conn, DataSet ds, List<Object> outParameterValues, String f_string, String f_delimiter) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_deleteStory`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, f_string), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return result;
		}

		public static int p_deleteTeam(Connection conn, DataSet ds, List<Object> outParameterValues, String f_string, String f_delimiter) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_deleteTeam`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, f_string), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, f_delimiter));

			return result;
		}

		public static int p_getnewpaynumber(Connection conn, DataSet ds, List<Object> outParameterValues, long in_userid, int in_rechargetype, String in_bankname,
				double in_rechargemoney, double in_cost, long returnNewPayNumber, String returnDescription) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_getnewpaynumber`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_userid), new Parameter(
					Types.INTEGER, ParameterDirection.IN, in_rechargetype), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_bankname), new Parameter(Types.DOUBLE,
					ParameterDirection.IN, in_rechargemoney), new Parameter(Types.DOUBLE, ParameterDirection.IN, in_cost), new Parameter(Types.BIGINT, ParameterDirection.OUT,
					returnNewPayNumber), new Parameter(Types.VARCHAR, ParameterDirection.OUT, returnDescription));

			return result;
		}

		public static int p_gettopinvestment(Connection conn, DataSet ds, List<Object> outParameterValues, int in_type, int in_count, int out_ret, String out_desc)
				throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_gettopinvestment`", ds, outParameterValues, new Parameter(Types.INTEGER, ParameterDirection.IN, in_type), new Parameter(
					Types.INTEGER, ParameterDirection.IN, in_count), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
					ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_get_topinvestment(Connection conn, DataSet ds, List<Object> outParameterValues, int in_type, int in_count) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_get_topinvestment`", ds, outParameterValues, new Parameter(Types.INTEGER, ParameterDirection.IN, in_type), new Parameter(
					Types.INTEGER, ParameterDirection.IN, in_count));

			return result;
		}

		public static int p_invest_action(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_rid, long in_type, int in_web_pay,
				BigDecimal in_late_fee, long in_borrow_way, BigDecimal in_amount, BigDecimal in_ifeerate, String in_repay_period, String in_repay_date,
				BigDecimal in_still_principal, BigDecimal in_still_interest, BigDecimal in_principal_balance, BigDecimal in_interest_balance, BigDecimal in_excitation_sum,
				BigDecimal in_mrate, String in_basepath, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_invest_action`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_rid), new Parameter(Types.BIGINT, ParameterDirection.IN, in_type), new Parameter(Types.INTEGER, ParameterDirection.IN,
					in_web_pay), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_late_fee), new Parameter(Types.BIGINT, ParameterDirection.IN, in_borrow_way),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, in_amount), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_ifeerate), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_repay_period), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_repay_date), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_still_principal), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_still_interest), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_principal_balance), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_interest_balance), new Parameter(Types.DECIMAL,
							ParameterDirection.IN, in_excitation_sum), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_mrate), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_basepath), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
							ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_invest_auto_webpay(Connection conn, DataSet ds, List<Object> outParameterValues, long in_bid, long in_payid, String in_repay_period,
				BigDecimal in_still_principal, BigDecimal in_still_interest, String in_basepath, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_invest_auto_webpay`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_bid), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_payid), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_repay_period), new Parameter(Types.DECIMAL,
					ParameterDirection.IN, in_still_principal), new Parameter(Types.DECIMAL, ParameterDirection.IN, in_still_interest), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_basepath), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT,
					out_desc));

			return result;
		}

		public static int p_useraddmoney(Connection conn, DataSet ds, List<Object> outParameterValues, long in_userid, double in_money, String in_paynumber, String in_remarks,
				int returnValue, String returnDescription) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_useraddmoney`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_userid), new Parameter(
					Types.DOUBLE, ParameterDirection.IN, in_money), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_paynumber), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, in_remarks), new Parameter(Types.INTEGER, ParameterDirection.OUT, returnValue), new Parameter(Types.VARCHAR, ParameterDirection.OUT,
					returnDescription));

			return result;
		}

		public static int p_useraddmoneymanual(Connection conn, DataSet ds, List<Object> outParameterValues, long in_userid, int in_rechargetype, double in_money,
				String in_remarks, long in_operatorid, int returnValue, String returnDescription) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_useraddmoneymanual`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_userid),
					new Parameter(Types.INTEGER, ParameterDirection.IN, in_rechargetype), new Parameter(Types.DOUBLE, ParameterDirection.IN, in_money), new Parameter(
							Types.VARCHAR, ParameterDirection.IN, in_remarks), new Parameter(Types.BIGINT, ParameterDirection.IN, in_operatorid), new Parameter(Types.INTEGER,
							ParameterDirection.OUT, returnValue), new Parameter(Types.VARCHAR, ParameterDirection.OUT, returnDescription));

			return result;
		}

		public static int p_backbuy(Connection conn, DataSet ds, List<Object> outParameterValues, long in_borrowId, long in_adminId, String basepath, int returnValue,
				String returnDescription) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_back_buy`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_borrowId), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_adminId), new Parameter(Types.VARCHAR, ParameterDirection.IN, basepath), new Parameter(Types.INTEGER,
					ParameterDirection.OUT, returnValue), new Parameter(Types.VARCHAR, ParameterDirection.OUT, returnDescription));

			return result;
		}

		public static int p_clear(Connection conn) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_clear`", new DataSet(), new ArrayList<Object>());

			return result;
		}

		public static int p_userInfo_update(Connection conn, DataSet ds, List<Object> outParameterValues, String in_realname, String in_cellPhone, String in_sex,
				String in_birthday, String in_highestEdu, String in_eduStartDay, String in_school, String in_maritalStatus, String in_hasChild, String in_hasHourse,
				String in_hasHousrseLoan, String in_hasCar, String in_hasCarLoan, long in_nativePlacePro, long in_nativePlaceCity, long in_registedPlacePro,
				long in_registedPlaceCity, String in_address, String in_telephone, String in_personalHead, long in_userId, String in_idNo, String in_username, String in_lastIP,
				String in_num, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_userInfo_update`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, in_realname),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_cellPhone), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_sex), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_birthday), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_highestEdu), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_eduStartDay), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_school), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_maritalStatus), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_hasChild), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_hasHourse), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_hasHousrseLoan), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_hasCar), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_hasCarLoan), new Parameter(Types.BIGINT,
							ParameterDirection.IN, in_nativePlacePro), new Parameter(Types.BIGINT, ParameterDirection.IN, in_nativePlaceCity), new Parameter(Types.BIGINT,
							ParameterDirection.IN, in_registedPlacePro), new Parameter(Types.BIGINT, ParameterDirection.IN, in_registedPlaceCity), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_address), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_telephone), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_personalHead), new Parameter(Types.BIGINT, ParameterDirection.IN, in_userId), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_idNo), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_username), new Parameter(Types.VARCHAR, ParameterDirection.IN,
							in_lastIP), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_num), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(
							Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_userInfo_update2(Connection conn, DataSet ds, List<Object> outParameterValues, String realName, String sex, String birthday, String highestEdu,
				String eduStartDay, String school, Long nativePlacePro, Long nativePlaceCity, Long registedPlacePro, Long registedPlaceCity, String address, Long userId,
				String idNo, String userName, String lastip, String familyName, String familyRelation, String familyAdd, String familyTel, String familyIdNo, String otherAdd,
				String otherIdNo, String otherName, String otherRelation, String otherTel, String num, int i, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_userInfo_detail_update`", ds, outParameterValues,

			new Parameter(Types.VARCHAR, ParameterDirection.IN, realName), new Parameter(Types.VARCHAR, ParameterDirection.IN, sex), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, birthday), new Parameter(Types.VARCHAR, ParameterDirection.IN, highestEdu), new Parameter(Types.VARCHAR, ParameterDirection.IN,
					eduStartDay), new Parameter(Types.VARCHAR, ParameterDirection.IN, school), new Parameter(Types.VARCHAR, ParameterDirection.IN, nativePlacePro), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, nativePlaceCity),

			new Parameter(Types.BIGINT, ParameterDirection.IN, registedPlacePro), new Parameter(Types.BIGINT, ParameterDirection.IN, registedPlaceCity), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, address), new Parameter(Types.BIGINT, ParameterDirection.IN, userId), new Parameter(Types.VARCHAR, ParameterDirection.IN,
					idNo), new Parameter(Types.VARCHAR, ParameterDirection.IN, userName), new Parameter(Types.VARCHAR, ParameterDirection.IN, lastip),

			new Parameter(Types.VARCHAR, ParameterDirection.IN, familyName), new Parameter(Types.VARCHAR, ParameterDirection.IN, familyRelation), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, familyAdd), new Parameter(Types.VARCHAR, ParameterDirection.IN, familyTel), new Parameter(Types.VARCHAR, ParameterDirection.IN,
					familyIdNo), new Parameter(Types.VARCHAR, ParameterDirection.IN, otherAdd), new Parameter(Types.VARCHAR, ParameterDirection.IN, otherIdNo), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, otherName), new Parameter(Types.VARCHAR, ParameterDirection.IN, otherRelation), new Parameter(Types.VARCHAR,
					ParameterDirection.IN, otherTel), new Parameter(Types.VARCHAR, ParameterDirection.IN, otherRelation), new Parameter(Types.VARCHAR, ParameterDirection.IN, num),
					new Parameter(Types.INTEGER, ParameterDirection.OUT, i), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_userWorkInfo_update(Connection conn, DataSet ds, List<Object> outParameterValues, String in_orgName, String in_occStatus, long in_workPro,
				long in_workCity, String in_companyType, String in_companyLine, String in_companyScale, String in_job, String in_monthlyIncome, String in_workYear,
				String in_companyTel, String in_workEmail, String in_companyAddress, String in_directedName, String in_directedRelation, String in_directedTel,
				String in_otherName, String in_otherRelation, String in_otherTel, String in_moredName, String in_moredRelation, String in_moredTel, long in_userId,
				int in_vipStatus, int in_newutostept, String in_lastIP, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_userWorkInfo_update`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, in_orgName),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_occStatus), new Parameter(Types.BIGINT, ParameterDirection.IN, in_workPro), new Parameter(Types.BIGINT,
							ParameterDirection.IN, in_workCity), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_companyType), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_companyLine), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_companyScale), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_job), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_monthlyIncome), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_workYear), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_companyTel), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_workEmail), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_companyAddress), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_directedName), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_directedRelation), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_directedTel), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_otherName), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_otherRelation), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_otherTel), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_moredName), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_moredRelation), new Parameter(Types.VARCHAR,
							ParameterDirection.IN, in_moredTel), new Parameter(Types.BIGINT, ParameterDirection.IN, in_userId), new Parameter(Types.INTEGER, ParameterDirection.IN,
							in_vipStatus), new Parameter(Types.INTEGER, ParameterDirection.IN, in_newutostept), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_lastIP),
					new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_user_login(Connection conn, DataSet ds, List<Object> outParameterValues, String in_userName, String in_passWord, String in_lastIP, int out_ret,
				String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_user_login`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, in_userName), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, in_passWord), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_lastIP), new Parameter(Types.INTEGER,
					ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int p_user_register(Connection conn, DataSet ds, List<Object> outParameterValues, String in_telephone, String in_username, String in_password,
				String in_refferee, int in_demo,int registerType, int out_ret, String out_desc) throws SQLException, DataException {
			int result = MySQL.executeProcedure(conn, "`p_user_register`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, in_telephone),
					new Parameter(Types.VARCHAR, ParameterDirection.IN, in_username), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_password), new Parameter(
							Types.VARCHAR, ParameterDirection.IN, in_refferee), new Parameter(Types.INTEGER, ParameterDirection.IN, in_demo),new Parameter(Types.INTEGER, ParameterDirection.IN, registerType), new Parameter(Types.INTEGER,
							ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR, ParameterDirection.OUT, out_desc));

			return result;
		}

		public static int pr_getStatisFundsType(Connection conn, DataSet ds, List<Object> outParameterValues, String in_date) throws Exception {
			int result = MySQL.executeProcedure(conn, "`pr_getFundTypeStatis`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, in_date));
			return result;
		}

		public static int pr_getFundrecordNm(Connection conn, DataSet ds, List<Object> outParameterValues, long in_id, String in_cmd) throws Exception {
			int result = MySQL.executeProcedure(conn, "`pr_getFundrecordNm`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_id), new Parameter(
					Types.VARCHAR, ParameterDirection.IN, in_cmd));
			return result;
		}

		public static int p_borrow_join_call_back(Connection conn, DataSet ds, List<Object> outParameterValues, long in_ordid, BigDecimal in_invest_amount, String in_basePath)
				throws Exception {
			int result = MySQL.executeProcedure(conn, "`p_borrow_join_call_back`", ds, outParameterValues, new Parameter(Types.BIGINT, ParameterDirection.IN, in_ordid),
					new Parameter(Types.DECIMAL, ParameterDirection.IN, in_invest_amount), new Parameter(Types.VARCHAR, ParameterDirection.IN, in_basePath));
			return result;
		}

		public static int p_debt_combine(Connection conn, DataSet ds, List<Object> outParameterValues, String cmd, long in_userId, int out_ret, String out_desc) throws Exception {
			int result = MySQL.executeProcedure(conn, "`p_debt_combine`", ds, outParameterValues, new Parameter(Types.VARCHAR, ParameterDirection.IN, cmd), new Parameter(
					Types.BIGINT, ParameterDirection.IN, in_userId), new Parameter(Types.INTEGER, ParameterDirection.OUT, out_ret), new Parameter(Types.VARCHAR,
					ParameterDirection.OUT, out_desc));
			return result;
		}

	}
}
