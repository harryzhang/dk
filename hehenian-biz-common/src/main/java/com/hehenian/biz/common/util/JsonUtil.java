package com.hehenian.biz.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.hehenian.biz.common.exception.BusinessException;

public class JsonUtil {
	
	/**
	 * json 对象转字符串
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String toString(Object obj)throws IOException { 
		ObjectMapper mapper = new ObjectMapper(); 
        // mapper.getSerializationConfig().setDateFormat(new
        // SimpleDateFormat("yyyy-MM-dd"));
		StringWriter sw = new StringWriter(); 
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw); 
		mapper.writeValue(gen, obj); 
		gen.close(); 
		return sw.toString(); 
	}
	
	/**
	 * json字符串转josn对象
	 * @param jsonStr
	 * @param objClass
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public  static Object json2Bean(String jsonStr, 
			                         Class objClass)  { 
		ObjectMapper mapper = new ObjectMapper(); 
		Object o;
        try {
            return o = mapper.readValue(jsonStr, objClass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new BusinessException("解析json字符出错"+ e.getMessage());
        } 
	}

	public static void main(String[] args) throws Exception {

        System.out.println(toString(new Date()));

        // Object[][] feeArray = { { new Double(333d), "aaa", "aaaaa" },
        // { new Double(1111d), "ccc", "ccccccc" },
        // { new Double(222d), "bbb", "bbbbbb" } };
        // List<Map<String, Object>> jarray = new ArrayList<Map<String,
        // Object>>();
        // for (Object[] fee : feeArray) {
        // double feeVal = (Double) fee[0];
        // if (feeVal > 0) {
        // Map<String, Object> jconsultFee = new HashMap<String, Object>();
        // jconsultFee.put("DivCustId", fee[1]);
        // jconsultFee.put("DivAcctId", fee[2]);
        // jconsultFee.put("DivAmt", NumberUtil.formatDouble(feeVal));
        // jarray.add(jconsultFee);
        // }
        // }
        // System.out.println(toString(jarray));

	}

}
