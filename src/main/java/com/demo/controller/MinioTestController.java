package com.demo.controller;

import com.google.common.collect.Lists;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @className: MinioTestDemo
 * @description: 测试minio 压缩文件存储与读取
 * @version: 1.0
 * @author: minsky
 * @date: 2022/10/18
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class MinioTestController {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @RequestMapping("/upload")
    public List<String> fileUpload(@RequestParam("file") MultipartFile file){
        log.info("图片上传进入请求...");
        List<String> imageUrls = Lists.newArrayList();
        Long startTime = System.currentTimeMillis();
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)){
            throw new RuntimeException();
        }
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("."));

        try {
            if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // zip文件解压缩后返回每个文件的地址
            String originalFileName = file.getOriginalFilename();
            System.out.println(originalFileName);
            String postfix = originalFileName.substring(originalFileName.lastIndexOf("."));
            if(!".zip".equalsIgnoreCase(postfix)){
                throw new RuntimeException("当前仅支持ZIP格式的压缩文件");
            }
            InputStream inputStream = file.getInputStream();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            for(ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()){
                if (nextEntry.isDirectory()) {
                    continue;
                }else{
                    byte[] aByte = getByte(zipInputStream);
                    InputStream is = new ByteArrayInputStream(aByte);
                    String[] split = nextEntry.getName().split("/");
                    // 若是隐藏文件则跳过
                    if(isHiddenFile(split)) continue;
                    String finalFileName = split[split.length - 1];
                    PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName)
                            .object(finalFileName)
                            .stream(is, aByte.length, -1)
                            .build();
                    minioClient.putObject(args);
                    imageUrls.add("/" + bucketName + "/" + split[split.length-1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        log.info("文件大小:{},上传耗时:{}",file.getSize() , System.currentTimeMillis() - startTime);
        return imageUrls;
    }

    /**
     * 判断文件是否是隐藏文件
     * @param split
     * @return
     */
    private boolean isHiddenFile(String[] split) {
        String fileName = split[split.length - 1];
        if(fileName.startsWith(".")) return true;
        else return false;
    }


    /**
     * 获取条目byte[]字节
     * @param zis
     * @return
     */
    public byte[] getByte(InflaterInputStream zis) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            byte[] buf = null;
            int length = 0;

            while ((length = zis.read(temp, 0, 1024)) != -1) {
                bout.write(temp, 0, length);
            }
            buf = bout.toByteArray();
            bout.close();
            return buf;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @RequestMapping("/downloadImg")
    public void downloadImage(@RequestParam("filePath") String filePath, HttpServletResponse res) throws Exception{
        GetObjectArgs build = GetObjectArgs.builder().bucket(bucketName).object(filePath).build();
        GetObjectResponse object = minioClient.getObject(build);

        String[] split = filePath.split("/");
        String fileName = split[split.length - 1];

        res.reset();
        res.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        res.setContentType("image/jpg;image/png;image/jpeg");

        ServletOutputStream outputStream = res.getOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = object.read(bytes)) > 0){
            outputStream.write(bytes, 0 , len);
        }
        outputStream.close();
    }
}
