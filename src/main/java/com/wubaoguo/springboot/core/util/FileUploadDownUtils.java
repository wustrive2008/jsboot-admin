package com.wubaoguo.springboot.core.util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: 文件上传下载工具
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-13 17:01
 */
public class FileUploadDownUtils {

    /**
     * 文件上传：用户将自己电脑上的的文件 上传到服务器filePath路径下
     * @param file ：文件
     * @param filePath:文件上传地址
     * @param fileName：文件名称
     */
    public static void saveFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 本地单个文件下载
     * @param file
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public static void downloadFile(File file, HttpServletRequest request, HttpServletResponse response) {
        if (file == null || !file.exists() || file.length() <= 0L) {
            throw new RuntimeException("文件为空或不存在！");
        }
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            boolean isPreview = "preview".equalsIgnoreCase(request.getParameter("source"));
            response.setHeader("Content-Disposition", (!isPreview ? "attachment; " : "") + "filename*=utf-8'zh_cn'" + URLEncoder.encode(file.getName(), "UTF-8"));
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[bis.available()];
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            throw new RuntimeException("单个文件下载异常");
        }finally {
            try {
                if (bis!=null){
                    bis.close();
                }
                if (fis!=null){
                    fis.close();
                }
                if (os!=null){
                    os.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("关闭流处理异常");
            }
        }
    }

    /**
     * 本地多个文件下载(打包zip)
     * @param fileList
     * @param response
     */
    public static void downloadFileZip(String zipName,List<File> fileList, HttpServletResponse response) {
        for (File file : fileList) {
            if (file == null || !file.exists() || file.length() <= 0L) {
                throw new RuntimeException("文件为空或不存在！");
            }
        }
        ZipArchiveOutputStream zout = null;
        try {
            //1、设置response参数并且获取ServletOutputStream
            zout = getServletOutputStream(zipName,response);
            for (File file : fileList) {
                InputStream in = new FileInputStream(file);
                //2、设置字节数组输出流，并开始输出
                setByteArrayOutputStream(file.getName(),in,zout);
            }
        } catch (Exception e) {
            throw new RuntimeException("多个文件打包成zip,下载异常");
        }finally {
            try {
                if (zout != null) {
                    zout.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("关闭流处理异常");
            }
        }
    }

    /**
     * 网络单个文件下载
     *
     * @param urlStr
     * @param request
     * @param response
     * @param fileName
     * @return
     */
    public static void downloadHttpFile(String urlStr, HttpServletRequest request, HttpServletResponse response, String fileName) {
        ServletOutputStream out = null;
        InputStream in = null;
        try {
            //1、通过网络地址获取文件InputStream
            in = getInputStreamFromUrl(urlStr);
            //2、FileInputStream 转换为byte数组
            byte[] getData = inputStreamToByte(in);
            out = response.getOutputStream();
            long contentLength = getData.length;
            // 3、设置Response
            setResponse(fileName, contentLength, request, response);
            out.write(getData);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException("下载失败!");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 网络多个文件下载(打包zip)
     *
     * @param pathList
     * @param request
     * @param response
     */
    public static void downloadHttpFileZip(String zipName, List<Map<String, String>> pathList, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1、设置response参数并且获取ServletOutputStream
            ZipArchiveOutputStream zous = getServletOutputStream(zipName,response);
            for (Map<String, String> map : pathList) {
                String fileName = map.get("name");
                //2、通过网络地址获取文件InputStream
                InputStream inputStream = getInputStreamFromUrl(map.get("path"));
                //3、设置字节数组输出流，并开始输出
                setByteArrayOutputStream(fileName, inputStream, zous);
            }
            zous.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置response参数并且获取ServletOutputStream
    private static ZipArchiveOutputStream getServletOutputStream(String zipName,HttpServletResponse response){
        String outputFileName = zipName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
        response.reset();
        response.setHeader("Content-Type", "application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("设置Content-Disposition失败");
        }
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("获取ServletOutputStream失败");
        }
        ZipArchiveOutputStream zout = new ZipArchiveOutputStream(out);
        zout.setUseZip64(Zip64Mode.AsNeeded);
        return zout;
    }

    //通过网络地址获取文件InputStream
    private static InputStream getInputStreamFromUrl(String path) {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("网络地址获取文件失败："+ url);
        }
        return is;
    }

    //FileInputStream 转换为byte数组
    public static byte[] inputStreamToByte(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("文件转换失败!");
        }
    }

    /**
     * @param fileName
     * @param contentLength
     * @param request
     * @param response
     * @return
     */
    public static void setResponse(String fileName, long contentLength, HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean isPreview = "preview".equalsIgnoreCase(request.getParameter("source"));
            response.addHeader("Content-Disposition", (!isPreview ? "attachment; " : "") + "filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Accept-Ranges", "bytes");

            String range = request.getHeader("Range");
            if (range == null) {
                response.setHeader("Content-Length", String.valueOf(contentLength));
            } else {
                response.setStatus(206);
                long requestStart = 0L;
                long requestEnd = 0L;
                String[] ranges = range.split("=");
                if (ranges.length > 1) {
                    String[] rangeDatas = ranges[1].split("-");
                    requestStart = Long.parseLong(rangeDatas[0]);
                    if (rangeDatas.length > 1) {
                        requestEnd = Long.parseLong(rangeDatas[1]);
                    }
                }
                long length = 0L;
                if (requestEnd > 0L) {
                    length = requestEnd - requestStart + 1L;
                    response.setHeader("Content-Length", String.valueOf(length));
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                } else {
                    length = contentLength - requestStart;
                    response.setHeader("Content-Length", String.valueOf(length));
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1L) + "/" + contentLength);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("response响应失败!");
        }
    }

    //设置字节数组输出流，并开始输出
    private static void setByteArrayOutputStream(String fileName, InputStream in, ZipArchiveOutputStream zout){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            byte[] bytes = baos.toByteArray();
            //设置文件名
            ArchiveEntry entry = new ZipArchiveEntry(fileName);
            zout.putArchiveEntry(entry);
            zout.write(bytes);
            zout.closeArchiveEntry();
            baos.close();
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("设置字节数组输出流失败!");
        }
    }
}
