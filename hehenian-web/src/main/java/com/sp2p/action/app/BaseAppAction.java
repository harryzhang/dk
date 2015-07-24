package com.sp2p.action.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;

import com.shove.web.action.BasePageAction;
import com.shove.web.util.FileUtils;

public class BaseAppAction extends BasePageAction {

	protected Map<String, String> getAppAuthMap() {
		return getRequestMap("auth");

	}

	protected Map<String, String> getAppInfoMap() {
		return getRequestMap("info");
	}

	private Map<String, String> getRequestMap(String requestAttr) {
		HttpServletRequest request =  request();
		Map<String,Object> paraMap = request.getParameterMap();
		Set<String> keySet = paraMap.keySet();
		for(String key : keySet){
			Object val = paraMap.get(key);
			if(val instanceof String[]){
				System.out.println(key+"==>"+Arrays.toString((String[])val));
			}else{
				System.out.println(key+"==>"+val);
			}
		}
		String json = request(requestAttr);
		Map<String, String> map = (Map<String, String>) JSONObject.toBean(
				JSONObject.fromObject(json), HashMap.class);
		if(map == null){
			map = new HashMap<String, String>();
		}
		return map;
	}
	
	public void  getStream(String source){
		try{
			HttpServletRequest request =  request();
			Map<String,Object> paraMap = request.getParameterMap();
			Set<String> keySet = paraMap.keySet();
			for(String key : keySet){
				Object val = paraMap.get(key);
				String conten = "";
				if(val instanceof String[]){
					System.out.println(key+"==>"+Arrays.toString((String[])val));
				}else{
					System.out.println(key+"==>"+val);
				}
				if(key.equals("str")){
					if(val instanceof String[]){
						conten = ((String[])val)[0];
					}else{
						conten = val+"";
					}
					 byte[] bytes = null;     
				        ByteArrayOutputStream bos = new ByteArrayOutputStream();     
				        try {       
				            ObjectOutputStream oos = new ObjectOutputStream(bos);        
				            oos.writeObject(Base64.encodeBase64((val+"").getBytes()));       
				            oos.flush();      
				            bytes = bos.toByteArray ();     
				            oos.close();        
				            bos.close();       
				        } catch (IOException ex) {       
				            ex.printStackTrace();  
				        }  
				    FileOutputStream out = new FileOutputStream( new File(source + "/" +"2.TXT") ) ;  
				    Writer writer = new OutputStreamWriter(out,"UTF-8") ;
				    
				    writer.write(conten);
				    writer.flush();
				    writer.close();
				    out.close();
				}
			}
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	}
}
