/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.filesaving
 * @Title: IFileService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月22日 上午10:03:50
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.filesaving;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月22日 上午10:03:50
 */
public interface IFileServerService {
    /**
     * 保存文件到文件服务器
     * 
     * @param srcFile
     *            源文件
     * @param fileName
     *            文件名称
     * @param thumbSizes
     *            缩放比例（如果为空，则则不生产缩放图片）
     * @return
     * @author: liuzgmf
     * @date: 2015年1月22日上午10:04:34
     */
    String saveFile(File srcFile, String fileName, int[][] thumbSizes);

    /**
     * 保存文件到文件服务器
     * 
     * @param source
     *            文件输入流
     * @param fileName
     *            文件名称
     * @param thumbSizes
     *            缩放比例（如果为空，则则不生产缩放图片）
     * @return
     * @author: liuzgmf
     * @date: 2015年1月22日上午10:05:03
     */
    String saveFile(InputStream source, String fileName, int[][] thumbSizes);

    /**
     * 保存手机上传的文件 
     * @param source
     * @param fileName
     * @param thumbSizes
     * @return 原始图片路径、压缩后的图片路径
     * @author zhengyfmf
     */
    List<String> saveAppFile(InputStream source, String fileName, int[][] thumbSizes);
    
    /**
     * 删除文件
     * @param filePath
     * @return
     * @author zhengyfmf
     */
    boolean delFile(String filePath);
    
    /**
     * 获取资源访问URL
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年1月29日上午9:43:09
     */
    String getFileAccessUrl();
    
    String getFilePath();
}
