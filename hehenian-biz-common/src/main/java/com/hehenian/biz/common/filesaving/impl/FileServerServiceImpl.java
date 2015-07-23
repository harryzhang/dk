/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.filesaving.impl
 * @Title: FileServerServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月22日 上午10:11:18
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.filesaving.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.hehenian.biz.common.filesaving.IFileServerService;
import com.hehenian.biz.common.filesaving.utils.FileSavingUtils;
import com.hehenian.biz.common.filesaving.utils.ImageThumbUtils;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月22日 上午10:11:18
 */
public class FileServerServiceImpl implements IFileServerService {
    private final Logger logger = Logger.getLogger(this.getClass());
    private String       fileServerDir;
    private String       fileAccessUrl;

    @Override
    public String saveFile(File srcFile, String fileName, int[][] thumbSizes) {
        try {
            return saveFile(new FileInputStream(srcFile), fileName, thumbSizes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String saveFile(InputStream source, String fileName, int[][] thumbSizes) {
        String subDir = File.separator + DateFormatUtils.format(new Date(), "yyyyMM") + File.separator
                + DateFormatUtils.format(new Date(), "dd");
        File dir = new File(fileServerDir + subDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newFileName = FileSavingUtils.getNewFileName(fileName);
        try {
            File destFile = new File(dir + File.separator + newFileName);
            FileUtils.copyInputStreamToFile(source, destFile);
            thumbSizes = ((thumbSizes == null || thumbSizes.length == 0) ? new int[][] { { 400, 400 } } : thumbSizes);
            for (int i = 0; i < thumbSizes.length; i++) {
                int[] wh = thumbSizes[i];
                String thumbImgName = FileSavingUtils.getThumbImgName(destFile.getCanonicalPath(), wh[0], wh[1]);
                ImageThumbUtils.thumbImage(destFile.getCanonicalPath(), wh[0], wh[1], (dir.getCanonicalPath()
                        + File.separator + thumbImgName));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return (subDir + File.separator + newFileName);
    }

    
	@Override
	public List<String> saveAppFile(InputStream source, String fileName,
			int[][] thumbSizes) {
		List<String> list = new ArrayList<String>();
		String subDir = File.separator
				+ DateFormatUtils.format(new Date(), "yyyyMM") + File.separator
				+ DateFormatUtils.format(new Date(), "dd");
		File dir = new File(fileServerDir + subDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String newFileName = FileSavingUtils.getNewFileName(fileName);
		try {
			File destFile = new File(dir + File.separator + newFileName);
			FileUtils.copyInputStreamToFile(source, destFile);
			list.add(subDir + File.separator + newFileName);
			thumbSizes = ((thumbSizes == null || thumbSizes.length == 0) ? new int[][] { {
					400, 400 } }
					: thumbSizes);
			for (int i = 0; i < thumbSizes.length; i++) {
				int[] wh = thumbSizes[i];
				String thumbImgName = FileSavingUtils.getThumbImgName(
						destFile.getCanonicalPath(), wh[0], wh[1]);
				ImageThumbUtils
						.thumbImage(
								destFile.getCanonicalPath(),
								wh[0],
								wh[1],
								(dir.getCanonicalPath() + File.separator + thumbImgName));
				list.add(subDir + File.separator + thumbImgName);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean delFile(String filePath) {
		if(StringUtils.isNotBlank(filePath)){
			File file = new File(fileServerDir+filePath);
			if(file.exists()){
				try {
					boolean rs = file.delete();
					return rs ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
     * @return fileServerDir
     */
    public String getFileServerDir() {
        return fileServerDir;
    }

    /**
     * @param fileServerDir
     *            the fileServerDir to set
     */
    public void setFileServerDir(String fileServerDir) {
        this.fileServerDir = fileServerDir;
    }

    /**
     * @return fileAccessUrl
     */
    public String getFileAccessUrl() {
        return fileAccessUrl;
    }

    /**
     * @param fileAccessUrl
     *            the fileAccessUrl to set
     */
    public void setFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
    }

	@Override
	public String getFilePath() {
		return this.fileServerDir;
	}

}
