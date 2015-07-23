package com.hehenian.biz.common.util;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {
    private final static Logger logger = Logger.getLogger(XmlUtil.class);

    /**
     * 
     * @param in
     * @param node
     * @return
     * @throws Exception
     */
    public static List<Element> getElementList(InputStream in, String node) throws Exception {
        Document document = getDocument(in);
        @SuppressWarnings("unchecked")
        List<Element> list = document.selectNodes(node);
        document = null;
        return list;
    }

    /**
     * 
     * @param in
     * @param node
     * @return
     * @throws Exception
     */
    public static List<Element> getElementList(String in, String node) throws Exception {
        Document document = getDocument(in);
        @SuppressWarnings("unchecked")
        List<Element> list = document.selectNodes(node);
        document = null;
        return list;
    }

    /**
     * 获取Document
     * 
     * @param path
     * @return
     */
    public static Document getDocument(String in) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(in);
        } catch (Exception e1) {
            logger.error("加载XML文档失败", e1);
        }
        return document;
    }

    public static Document getDocument(InputStream in) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(in);
        } catch (Exception e1) {
            logger.error("加载XML文档失败", e1);
        }
        reader = null;
        return document;
    }
}
