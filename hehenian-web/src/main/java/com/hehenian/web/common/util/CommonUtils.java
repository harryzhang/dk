package com.hehenian.web.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.StringUtils;

/**
 * User: liuwtmf
 * Date: 2015-1-28
 * Time: 14:34
 */
public class CommonUtils {
	
	private static Properties prop = null;
	
    /**
     * 将标题打上星号
     * @param title
     * @param hiddenLength
     * @return
     */
    public static String titleHidden(String title ,int hiddenLength){
        if (hiddenLength<=0){
            return title;
        }
        if (StringUtils.isBlank(title)){
            return title;
        }
        if (title.length()<=hiddenLength){
            title = StringUtils.substring(title,0,title.length()-1);
            return StringUtils.rightPad(title,title.length()+hiddenLength,"*");
        }
        if (title.length()/2<hiddenLength){
            title = StringUtils.substring(title, 0, title.length() - hiddenLength);
            return StringUtils.rightPad(title, title.length()+hiddenLength, "*");
        }
        int middle = title.length()/2;
        int half = hiddenLength/2;
        String title1 = StringUtils.substring(title,0,middle-half);
        return StringUtils.rightPad(title1,title1.length()+hiddenLength,"*")+StringUtils.substring(title,middle-half+hiddenLength);
    }
    /**
     * 将标题打上星号 默认打4个星
     * @param title
     * @return
     */
    public static String titleHidden(String title ){
        return titleHidden(title , 4);
    }

    /**
     * 金额格式化
     * @param s 金额
     * @param len 小数位数
     * @return 格式后的金额
     */
	public static String insertComma(String s, int len) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		double num = Double.parseDouble(s);
		if (len == 0) {
			formater = new DecimalFormat("###,###");
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.00");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}
		return formater.format(num);
    }
	
	public static String getImagePath(String imageName) throws ServiceException {
		String tomcatHome = System.getProperty("catalina.home");
		String setupFile = tomcatHome + File.separatorChar + "conf" + File.separatorChar + "hehenian.properties";
		System.out.println(setupFile);
		String imagePath = "";
		if (prop == null) {
			prop = new Properties();
			try {
				InputStream is = new FileInputStream(new File(setupFile));
				prop.load(is);
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("save.image.path" + prop.get("image.save.path"));
		imagePath = (String) prop.get("image.save.path");
		if(StringUtils.isBlank(imagePath)) {
			throw new ServiceException("save image path can not be null");
		}
		System.out.println("colorlife_api_url::::" + imagePath);
		return imagePath + File.separatorChar + imageName + ".png";
	}
	
	public static void main(String[] args) {
		System.out.println(insertComma("4000268.23", 2));
	}
}
