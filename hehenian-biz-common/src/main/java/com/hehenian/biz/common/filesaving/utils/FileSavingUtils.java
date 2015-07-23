/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.filesaving.utils
 * @Title: FileSavingUtils.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月20日 下午3:57:59
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.filesaving.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hehenian.biz.common.base.constant.Constants;
import com.hehenian.biz.common.util.LogicUtils;
import com.hehenian.biz.common.util.Md5Utils;

/**
 * 用于文件存储工具
 * 
 * @author: liuzgmf
 * @date 2015年1月20日 下午4:02:15
 */
public class FileSavingUtils {

    /**
     * 判断是否为windows OS
     * 
     * @return 是返回true，否返回false
     */
    public static boolean isWindows() {
        if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
            return true;
        }
        return false;
    }

    /**
     * 根据文件名称或路径获取文件的新名称
     * 
     * @param filePath
     *            文件路径或者文件名
     * @return
     */
    public static String getNewFileName(String filePath) {
        // 获取文件的前名称、后缀名
        String preName = Md5Utils.hash(filePath + System.currentTimeMillis() + new Random().nextInt());
        String extName = getExtName(getFileName(filePath));

        // 生成新文件名
        return preName + Constants.POINT + extName;
    }

    /**
     * 根据源图片路径或者名称获取其缩略图的新名称
     * 
     * @param originalImgPath
     *            源图路径或者名称
     * @param width
     *            缩略图的宽度
     * @param height
     *            缩略图的高度
     * @return 缩略图的新名称
     */
    public static String getThumbImgName(String originalImgPath, int width, int height) {
        String fileName = getFileName(originalImgPath);
        if (LogicUtils.isNullOrEmpty(fileName)) {
            return null;
        }

        String extName = getExtName(fileName);
        if (LogicUtils.isNullOrEmpty(extName)) {
            return null;
        }

        return getPreName(fileName) + Constants.UNDERLINE + width + Constants.X + height + Constants.POINT + extName;
    }

    /**
     * 递归获取某个文件下的所有文件或目录，且包含fileDir本身
     * 
     * @param fileDir
     *            目录或文件
     * @param files
     *            保存文件的list容器
     */
    public static void recursiveFiles(File fileDir, List<File> files) {
        if (fileDir != null) {
            files.add(fileDir);
            if (fileDir.isDirectory()) {
                File[] fs = fileDir.listFiles();
                for (int i = 0; null != fs && i < fs.length; i++) {
                    recursiveFiles(fs[i], files);
                }
            }
        }
    }

    /**
     * 格式化文件目录，把window的路径分割付替换成“/”，并且在目录后加上“/”
     * 
     * @param dirPath
     *            文件目录路径
     * @return 格式化后的文件目录
     */
    public static String formatDirPath(String dirPath) {
        if (null != dirPath) {
            return dirPath.trim().replace(Constants.BACKSLASH, Constants.SLASH).replaceAll("/$", Constants.EMPTY_STR)
                    + Constants.SLASH;
        }
        return null;
    }

    /**
     * 格式化文件路径，把window的路径分割付替换成“/”
     * 
     * @param filePath
     *            文件路径
     * @return 格式化后的文件路径
     */
    public static String formatFilePath(String filePath) {
        if (null != filePath) {
            return filePath.trim().replace(Constants.BACKSLASH, Constants.SLASH);
        }
        return null;
    }

    /**
     * 从文件路径中获取该文件所在的目录
     * 
     * @param filePath
     *            合法的文件路径
     * @return 文件目录
     */
    public static String getDirPath(String filePath) {
        if (null != filePath) {
            filePath = formatFilePath(filePath);
            return filePath.lastIndexOf(Constants.SLASH) > -1 ? filePath.substring(0,
                    filePath.lastIndexOf(Constants.SLASH) + 1) : "";
        }
        return null;
    }

    /**
     * 从文件路径中获取该文件的名称
     * 
     * @param filePath
     *            合法的文件路径
     * @return 文件名称
     */
    public static String getFileName(String filePath) {
        if (null != filePath) {
            filePath = formatFilePath(filePath);
            return filePath.lastIndexOf(Constants.SLASH) > -1 ? filePath.substring(filePath
                    .lastIndexOf(Constants.SLASH) + 1) : filePath;
        }
        return null;
    }

    /**
     * 根据文件名获取文件的前缀名
     * 
     * @param fileName
     *            文件名
     * @return 文件的前缀名
     */
    public static String getPreName(String fileName) {
        if (null != fileName && fileName.trim().length() > 0) {
            return fileName.lastIndexOf(Constants.POINT) > 0 ? fileName.substring(0,
                    fileName.lastIndexOf(Constants.POINT)) : Constants.EMPTY_STR;
        }
        return Constants.EMPTY_STR;
    }

    /**
     * 根据文件名称获取文件的后缀名
     * 
     * @param fileName
     *            文件名
     * @return 文件的后缀名
     */
    public static String getExtName(String fileName) {
        if (null != fileName && fileName.trim().length() > 0) {
            return fileName.lastIndexOf(Constants.POINT) > 0 ? fileName
                    .substring(fileName.lastIndexOf(Constants.POINT) + 1) : Constants.EMPTY_STR;
        }
        return Constants.EMPTY_STR;
    }

    /**
     * 删除指定文件路径下的文件
     * 
     * @param filePath
     *            文件路径
     * @return 删除成功返回true，失败返回false。
     */
    public boolean deleteFile(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return f.delete();
        }
        return false;
    }

    /**
     * 读取指定文件路径的文件数据
     * 
     * @param filePath
     *            文件路径
     * @return 文件的字节数据
     */
    public byte[] readFileFromDisc(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(filePath));
            byte[] in = new byte[fis.available()];
            fis.read(in);
            return in;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new byte[0];
    }

    /**
     * 把文件数据写入到指定文件目录下和文件名的磁盘上
     * 
     * @param dirPath
     *            文件存放的目录
     * @param fileName
     *            文件的名称
     * @param fileData
     *            源文件的字节数据
     * @return 成功返回true，失败返回false
     */
    public static boolean writeFileToDisc(String dirPath, String fileName, byte[] fileData) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return writeFileToDisc(new File(dirPath, fileName), fileData);
    }

    /**
     * 把文件数据写入到指定文件的磁盘上
     * 
     * @param newFile
     *            新文件对象
     * @param fileData
     *            源文件的字节数据
     * @return 成功返回true，失败返回false
     */
    public static boolean writeFileToDisc(File newFile, byte[] fileData) {
        boolean res = false;

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile);
            outputStream.write(fileData);
            res = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    /**
     * 保存文件
     * 
     * @param newFile
     *            新文件
     * @param is
     *            输入流
     * @return 成功为true，失败为false
     */
    public static boolean writeFileToDisc(File newFile, InputStream is) {
        OutputStream os = null;
        try {
            byte b[] = new byte[256];

            os = new FileOutputStream(newFile);
            while ((is.read(b)) > 0) {
                os.write(b);
            }
            os.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (LogicUtils.isNotNull(os)) {
                    os.close();
                }
                if (LogicUtils.isNotNull(is)) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取指定文件所在文件夹下所有文件（不含子文件夹）
     * 
     * @param filePath
     *            文件路径
     * @return 上传后的文件所在的路径下的所有文件路径
     */
    public static List<String> getFiles(String filePath) {
        List<String> files = new ArrayList<String>();
        try {

            File uploadedFile = new File(filePath);
            if (!uploadedFile.isFile()) {
                return files;
            }

            File[] fs = uploadedFile.getParentFile().listFiles();
            if (LogicUtils.isNullOrEmpty(fs)) {
                return files;
            }

            for (int i = 0; i < fs.length; i++) {
                if (fs[i].isFile()) {
                    filePath = fs[i].getCanonicalPath();
                    if (!files.contains(filePath)) {
                        files.add(filePath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return files;
    }

    /**
     * 获取指定文件所在文件夹下所有文件的相对路径（不含子文件夹）
     * 
     * @param filePath
     *            上传后的图片所在的路径
     * @return 所有文件的相对路径
     */
    public static List<String> getFileRelativePaths(String baseDirPath, String filePath) {
        List<String> files = new ArrayList<String>();
        try {
            File fileDir = new File(baseDirPath);
            if (fileDir.isFile()) {
                return files;
            }

            List<String> fs = getFiles(filePath);
            if (LogicUtils.isNullOrEmpty(fs)) {
                return files;
            }

            String fileName = getFileName(filePath);
            if (LogicUtils.isNullOrEmpty(fileName)) {
                return files;
            }

            for (String path : fs) {
                baseDirPath = formatDirPath(fileDir.getCanonicalPath());
                path = formatFilePath(path).replace(baseDirPath, Constants.EMPTY_STR);// 获取相对路径
                if (path.contains(fileName)) {
                    files.add(path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return files;
    }

    public static void main(String[] args) {
        // List<String> relativPaths = getOriginalAndThumbImgs("d:\\",
        // "D:\\test1\\2.jpeg");
        // for (String string : relativPaths) {
        // System.out.println(string);
        // }

        // System.out.println(getThumbImgName("D:\\test1\\1.jpeg", "100x200"));
        List<File> list = new ArrayList<File>();

        recursiveFiles(new File("d:/test1"), list);

        for (File file : list) {
            System.out.println(file.getPath());
        }
    }

}
