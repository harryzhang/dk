package test;
import java.util.HashMap;
import java.util.Map;

import com.hehenian.biz.common.util.HttpClientUtils;

public class agreementTest {

	public static void main(String[] args) {
		String url = "http://localhost/hehenian-agreement/agreementCmd?req_save_path=update";
		Map map = new HashMap();
		// &fileName=3concat.pdf&reqTmplate=Concat
		map.put("fileName", "13.pdf");
		map.put("reqTmplate", "Concat");
		map.put("mobile", "15814477583");
		map.put("cmd", "save");
		map.put("userId", "111122");

		map.put("req_save_path", "upload");

		map.put("lendUserName", "张三");
		map.put("lendIdNo", "41255595");
		map.put("borrowerName", "王五");
		map.put("borrowerIdNo", "2255454545");
		map.put("loanAmount", "50000");
		map.put("repayType", "等本等息");
		map.put("yearRate", "18%");
		map.put("loanPeriod", "12");
		map.put("loanDay", "10");
		map.put("repayDay", "12");
		map.put("limitTime", "2015-12-12");
		map.put("account", "62124556132121545");
		map.put("loanUsage", "买房");
		map.put("orderCode", "D0615010100001");
		try {
			String str = HttpClientUtils.get(url, map);
			System.out.println("result:" + str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
