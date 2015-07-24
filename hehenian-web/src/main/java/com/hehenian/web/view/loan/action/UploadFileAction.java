/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.loan.action
 * @Title: UploadFileAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月21日 下午3:51:42
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.loan.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.filesaving.IFileServerService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 文件上传控制器
 * 
 * @author: liuzgmf
 * @date 2015年1月21日 下午3:51:42
 */
@Scope("prototype")
@Component("uploadFileAction")
public class UploadFileAction extends ActionSupport {
    private static final long  serialVersionUID = 1L;
    @Autowired
    private IFileServerService fileServerService;
    private File[]             files;
    private String[]           filesContentType;
    private String[]           filesFileName;
    
    public String uploadFile() throws Exception {
        if (files == null || files.length == 0) {
            return ERROR;
        }
        for (int i = 0; i < files.length; i++) {
            fileServerService.saveFile(files[i], filesFileName[i], new int[][] { { 400, 400 } });
        }
        return SUCCESS;
    }

    /**
     * @return files
     */
    public File[] getFiles() {
        return files;
    }

    /**
     * @param files
     *            the files to set
     */
    public void setFiles(File[] files) {
        this.files = files;
    }

    /**
     * @return filesContentType
     */
    public String[] getFilesContentType() {
        return filesContentType;
    }

    /**
     * @param filesContentType
     *            the filesContentType to set
     */
    public void setFilesContentType(String[] filesContentType) {
        this.filesContentType = filesContentType;
    }

    /**
     * @return filesFileName
     */
    public String[] getFilesFileName() {
        return filesFileName;
    }

    /**
     * @param filesFileName
     *            the filesFileName to set
     */
    public void setFilesFileName(String[] filesFileName) {
        this.filesFileName = filesFileName;
    }

}
