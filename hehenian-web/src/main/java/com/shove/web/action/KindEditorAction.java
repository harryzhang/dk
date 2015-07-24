  package com.shove.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.shove.web.util.FileUtils;



/**
 * kindeditor编辑器上传与文件管理
 *
 */
public class KindEditorAction extends BaseAction{
	public static Log log = LogFactory.getLog(KindEditorAction.class);
	private static final long serialVersionUID = 1L;
	private File imgFile;
    
	/**
     * 文件名称
     */
    private String imgFileFileName;

    /**
     * 图片宽度
     */
    private String imgWidth;

    /**
     * 图片高度
     */
    private String imgHeight;

    /**
     * 图片对齐方式
     */
    private String align;

    /**
     * 图片标题
     */
    private String imgTitle;
    
    /**
     * 上传文件到指定的文件夹下会根据当前日期生成新想文件夹进行区分 
     * 编辑器会传递相应的参数 其中dir为上传的类型
     * 类型分为image，flash，media，file
     * @return
     */
    public String fileUpload() {
    	
    	//定义允许上传的文件扩展名
    	HashMap<String, String> extMap = new HashMap<String, String>();
    	extMap.put("image", "gif,jpg,jpeg,png,bmp");
    	extMap.put("flash", "swf,flv");
    	extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
    	extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
        response().setContentType("text/html; charset=UTF-8");
        // 文件保存目录路径
        String savePath = ServletActionContext.getServletContext().getRealPath("/") + "attached/";
        // 文件保存目录URL
        String saveUrl = request().getContextPath() + "/attached/";
        // 最大文件大小
        HttpServletResponse response = ServletActionContext.getResponse();
    	response.setContentType("text/html;charset=utf-8");
    	
        long maxSize = 111111111;
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            log.error(e1);
        }
        
//        Admin admin = (Admin) session().getAttribute(IConstants.ADMIN_SESSION);
//    	AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.USER_SESSION);
//    	if(admin == null && user == null){
//    		out.println(getError("权限不够！"));
//            return null;
//    	}
        if (imgFile == null) {
            out.println(getError("请选择文件。"));
            return null;
        }

        // 检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
        	uploadDir.mkdirs();
        }
        // 检查目录写权限
        if (!uploadDir.canWrite()) {
            out.println(getError("上传目录没有写权限。"));
            return null;
        }
        
        String dirName = request().getParameter("dir");
        if (dirName == null) {
        	dirName = "image";
        }
        if(!extMap.containsKey(dirName)){
        	out.println(getError("目录名不正确。"));
        	return null;
        }
      //创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
        	saveDirFile.mkdirs();
        }
        // 创建文件夹
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1).toLowerCase();
        if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return null;
		}
        if (imgFile.length() > maxSize) {
            out.println(getError("[ " + imgFileFileName + " ]超过单个文件大小限制，文件大小[ " + imgFile.length() + " ]，限制为[ " + maxSize + " ] "));
            return null;
        }
        String newFileName = FileUtils.getFileName()+ "." + fileExt;
        File uploadedFile = new File(savePath, newFileName);
        try {
            FileUtil.copyFile(imgFile, uploadedFile);
            JSONObject obj = new JSONObject();
            obj.put("error", 0);
            obj.put("url", saveUrl + newFileName);
            log.debug(obj);
            out.println(obj.toString());
            log.debug("上传图片:[" + uploadedFile.getName() + "]" + ">>>[" + newFileName + "]成功");
        } catch (IOException e) {
            log.error("图片上传失败:" + e);
        }
        return null;
    }
    
    /**
     * 上传文件管理
     * 根据指定的目录查询出相应的文件中的文件
     * 根据类型进行文件排序
     * 因为编辑器通过ajax方式请求获取数据
     * js中为了防止缓存在请求连接中加入了当前时间戳
     * 会导致struts2转型拦截器报错
     * 可以在 filemanager/filemanager.js文件中找到相应调用方法对请求参数进行一个修改即可 
     * @return   
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public String fileManager() throws IOException{
    	HttpServletResponse response = ServletActionContext.getResponse();
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	//根目录路径，可以指定绝对路径，比如 /var/www/attached/
    	String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "attached/";
    	//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
    	String rootUrl  = request().getContextPath() + "/attached/";
    	//图片扩展名
    	String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
    	
    	String dirName = request().getParameter("dir");
    	
//    	Admin admin = (Admin) session().getAttribute(IConstants.ADMIN_SESSION);
//     	AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.USER_SESSION);
//     	if(admin == null && user == null){
//     		out.println(getError("权限不够！"));
//             return null;
//     	}
    	
    	if (dirName != null) {
    		if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
    			out.println("Invalid Directory name.");
    			return null;
    		}
    		rootPath += dirName + "/";
    		rootUrl += dirName + "/";
    		File saveDirFile = new File(rootPath);
    		if (!saveDirFile.exists()) {
    			saveDirFile.mkdirs();
    		}
    	}
    	//根据path参数，设置各路径和URL
    	String path = request().getParameter("path") != null ? request().getParameter("path") : "";
    	String currentPath = rootPath + path;
    	String currentUrl = rootUrl + path;
    	String currentDirPath = path;
    	String moveupDirPath = "";
    	if (!"".equals(path)) {
    		String str = currentDirPath.substring(0, currentDirPath.length() - 1);
    		moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
    	}

    	//排序形式，name or size or type
    	String order = request().getParameter("order") != null ? request().getParameter("order").toLowerCase() : "name";

    	//不允许使用..移动到上一级目录
    	if (path.indexOf("..") >= 0) {
    		out.println("Access is not allowed.");
    		return null;
    	}
    	//最后一个字符不是/
    	if (!"".equals(path) && !path.endsWith("/")) {
    		out.println("Parameter is not valid.");
    		return null;
    	}
    	//目录不存在或不是目录
    	File currentPathFile = new File(currentPath);
    	if(!currentPathFile.isDirectory()){
    		out.println("Directory does not exist.");
    		return null;
    	}

    	//遍历目录取的文件信息
    	List<Hashtable> fileList = new ArrayList<Hashtable>();
    	if(currentPathFile.listFiles() != null) {
    		for (File file : currentPathFile.listFiles()) {
    			Hashtable<String, Object> hash = new Hashtable<String, Object>();
    			String fileName = file.getName();
    			if(file.isDirectory()) {
    				hash.put("is_dir", true);
    				hash.put("has_file", (file.listFiles() != null));
    				hash.put("filesize", 0L);
    				hash.put("is_photo", false);
    				hash.put("filetype", "");
    			} else if(file.isFile()){
    				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    				hash.put("is_dir", false);
    				hash.put("has_file", false);
    				hash.put("filesize", file.length());
    				hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
    				hash.put("filetype", fileExt);
    			}
    			hash.put("filename", fileName);
    			hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
    			fileList.add(hash);
    		}
    	}

    	if ("size".equals(order)) {
    		Collections.sort(fileList, new SizeComparator());
    	} else if ("type".equals(order)) {
    		Collections.sort(fileList, new TypeComparator());
    	} else {
    		Collections.sort(fileList, new NameComparator());
    	}
    	JSONObject result = new JSONObject();
    	result.put("moveup_dir_path", moveupDirPath);
    	result.put("current_dir_path", currentDirPath);
    	result.put("current_url", currentUrl);
    	result.put("total_count", fileList.size());
    	result.put("file_list", fileList);

    	response().setContentType("application/json; charset=UTF-8");
    	out.println(result.toString());
    	return null;
    }
    
	/**
	 * 根据文件名称排序
	 *
	 */
	@SuppressWarnings("unchecked")
	public class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	
	/**
	 * 根据文件大小排序
	 *
	 */
	@SuppressWarnings("unchecked")
	public class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
	/**
	 * 根据文件类型排序
	 *
	 */
	@SuppressWarnings("unchecked")
	public class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}
    
    /**
     * 返回JSON格式的错误信息
     * @param message
     * @return
     */
    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        log.debug(obj);
        return obj.toString();
    }
	
	public File getImgFile() {
		return imgFile;
	}
	
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	
	public String getImgWidth() {
		return imgWidth;
	}
	
	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}
	
	public String getImgHeight() {
		return imgHeight;
	}
	
	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}
	
	public String getAlign() {
		return align;
	}
	
	public void setAlign(String align) {
		this.align = align;
	}
	
	public String getImgTitle() {
		return imgTitle;
	}
	
	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
    
}
