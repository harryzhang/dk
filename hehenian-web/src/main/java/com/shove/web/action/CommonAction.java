package com.shove.web.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;

import com.shove.web.util.JSONUtils;


/**
 * @version Sep 15, 2011 4:30:49 PM
 * @declaration 公用Action
 */
public class CommonAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String imageCode() throws IOException {
		// 在内存中创建图象
		int width = 65, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(230, 255));
		g.fillRect(0, 0, 100, 25);
		// 设定字体
		g.setFont(new Font("Arial", Font.CENTER_BASELINE | Font.ITALIC, 18));
		// 产生0条干扰线，
		g.drawLine(0, 0, 0, 0);
		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(getRandColor(100, 150));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 15 * i + 6, 16);
		}
		  for(int i=0;i<(random.nextInt(5)+5);i++){  
		        g.setColor(new Color(random.nextInt(255)+1,random.nextInt(255)+1,random.nextInt(255)+1));  
		        g.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));  
	    }   
		String pageId = request().getParameter("pageId");
		String key = pageId + "_checkCode";
		// 将验证码存入页面KEY值的SESSION里面
		session().setAttribute(key, sRand);
		session().setAttribute("keys", sRand);
		// 图象生效
		g.dispose();
		ServletOutputStream responseOutputStream = response().getOutputStream();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", responseOutputStream);
		// 以下关闭输入流！
		responseOutputStream.flush();
		responseOutputStream.close();
		// 获得页面key值
		return null;
	}
	
	/**
	 * 检测验证码是否过期
	 * 
	 * @return
	 * @throws IOException
	 */
	public String codeIsExpired() throws IOException {
		String pageId = request().getParameter("pageId");
		String codes = (String) request().getSession().getAttribute(
				pageId + "_checkCode");
		if (org.apache.commons.lang.StringUtils.isBlank(codes)) {
			JSONUtils.printStr("1");
		}
		return null;
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	public String message(){
		request().setAttribute("title", request("title"));
        String platform=(String) session().getAttribute("platform");
		if(StringUtils.isNotBlank(platform)){
            if("colorlifeapp".equals(platform)||"appcomm".equals(platform)){
                return "colorlifeapp";
            }else{
                return platform;
            }
		}
		return SUCCESS;
	}
	
	public String webapp(){
		
		return SUCCESS;
	}
}