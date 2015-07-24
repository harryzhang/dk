package com.sp2p.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sun.org.apache.xerces.internal.dom.DOMImplementationImpl;

/**
 * 
 */
public class PDFUtil {

	/**
	 * 增加水印
	 */
	protected static void addWater(String urlSource, String urlFor, String path) throws Exception {
		// 待加水印的文件
		PdfReader reader = new PdfReader(urlSource);
		// 加完水印的文件
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(urlFor));
		try {

			int total = reader.getNumberOfPages() + 1;

			PdfContentByte content = null;

			// int high = 50;// 高度
			// 循环对每页插入水印
			for (int i = 1; i < total; i++) {
				// 水印的起始
				// high = 500;
				// content = stamper.getUnderContent(i);//底部添加
				content = stamper.getOverContent(i);// 顶部添加
				// 开始
				content.beginText();
				// 设置颜色
				content.setColorFill(Color.RED);
				// 设置起始位置
				content.setTextMatrix(200, 480);

				// 水印图片
				com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(path + "images/testR.png");
				image.setAbsolutePosition(200, 100);
				// 添加图片
				content.addImage(image);

				content.endText();

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		} finally {
			stamper.close();
		}

	}

	/**
	 * 创建pdf
	 */
	private static String create(String url, String pdfUrl, String pdfName, String rootpath) throws IOException {
		OutputStream os = null;
		try {
			ITextRenderer renderer = new ITextRenderer();
			 renderer.setDocumentFromString(url);
//			URL ur = new URL(url);
//			renderer.setDocument(url);
//			Document doc =null;
//			renderer.setDocument(doc, url);

			// 解决中文支持问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String paths = ServletActionContext.getServletContext().getRealPath("WEB-INF/simsun.ttc");
			fontResolver.addFont(paths, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			renderer.layout();
			os = new FileOutputStream(pdfUrl);
			renderer.createPDF(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				os.close();
		}
		// 添加水印
		// addWater(pdfUrl2, pdfUrl, path);
		return "success";
	}

	/**
	 * 搜索pdf存在,存在返回,不存在则创建
	 */
	public static String pdf(String url, String borrowId) {
		// 用url截取文件名
		String pdfName = "No." + borrowId + "_pact.pdf";
		String rootpath = ServletActionContext.getServletContext().getRealPath("/");
		String pdfUrl = "upload/pdf/" + pdfName;

//		File file = new File(rootpath + pdfUrl);
//		if (file.exists()) { // 存在则返回pdfUrl
//			return pdfUrl;
//		}

		// 不存在则创建
		String ret = null;
		try {
			ret = create(url, rootpath + pdfUrl, pdfName, rootpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("success".equals(ret)) {
			return pdfUrl;
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
		// String url = "http://127.0.0.1/agreementDetail.do?borrowId=2";
		// pdf(url);
		// String url = new File(inputFile).toURI().toURL().toString();
		// String outputFile = "firstdoc.pdf";
		// OutputStream os = new FileOutputStream(outputFile);
		// ITextRenderer renderer = new ITextRenderer();
		// renderer.setDocument(url);
		// // 解决中文支持问题
		// ITextFontResolver fontResolver = renderer.getFontResolver();
		// fontResolver.addFont("C:/Windows/Fonts/SIMSUN.TTC",
		// BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//
		// // 解决图片的相对路径问题
		// renderer.getSharedContext().setBaseURL(
		// "file:/D:/web/Workspaces/MyEclipse 8.5/WebToPDF/WebRoot/");
		//
		// renderer.layout();
		// renderer.createPDF(os);
		//
		// os.close();
		// addWater("file:/D:/web/Workspaces/MyEclipse 8.5/WebToPDF/firstdoc.pdf",
		// "d:/uploadtmp/demo-URL-123.pdf");
	}

}
