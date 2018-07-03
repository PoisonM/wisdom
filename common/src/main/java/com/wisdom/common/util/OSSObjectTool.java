package com.wisdom.common.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.wisdom.common.config.Global;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * oss 工具类
 * @author ft
 *
 */
public class OSSObjectTool {

    private static Logger logger = Logger.getLogger(OSSObjectTool.class);
    public static String BUCKET_MX_PIC =  Global.getConfig("oss.bucket.mx.pic");

    /**
     * 上传文件到oss
     * @param key
     * @param length
     * @param in
     * @param bucketName
     * @return key
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public static String uploadFileInputStream(String key, Long length ,InputStream in, String bucketName) {
        //创建上传Object的Metadata
        ObjectMetadata objectMeta = new ObjectMetadata();
        //设置上传文件长度
        objectMeta.setContentLength(length);
        new HashMap<String,String>();
        //OSSClient.putObject流试上传
        OSSClientUtil.OSSClient().putObject(bucketName, key , in, objectMeta);
        String url = getUrl(key,bucketName);
        return url;
    }


    /**
     * 将文件上传至
     * @param file
     * @param key 上传至 OSS 后的文件名
     */
    public static void upLoadFileToOSS(File file,String key,String... bucketName){

        try {
            InputStream  inputStream = new FileInputStream(file);
            if(bucketName.length<1){
                OSSClientUtil.OSSClient().putObject("yhllaoyou", key, inputStream);
            }else{
                OSSClientUtil.OSSClient().putObject(bucketName[0], key, inputStream);
            }
            // 关闭client
            OSSClientUtil.OSSClient().shutdown();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  static String createBucketName(String bucketName){

        //存储空间
        final String bucketNames=bucketName;
        if(! OSSClientUtil.OSSClient().doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket= OSSClientUtil.OSSClient().createBucket(bucketName);
            logger.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }
    /**
     * 图片上传
     * @param file
     * @param bucketName
     * @param folder
     * @param fileName
     */
    public static String imageUploadToOSS(MultipartFile file , String bucketName, String folder , String fileName){

        String resultStr= null;
        try {

            ObjectMetadata objectMeta = new ObjectMetadata();
            //上传的文件的长度
            objectMeta.setContentLength(file.getSize());
            //指定Object被下载时的网页的缓存行为
            objectMeta.setCacheControl("no-cache");
            //指定该Object下设置Header
            objectMeta.setHeader("Pragma","no-cache");
            //指定该Object被下载时的内容编码格式
            objectMeta.setContentEncoding("utf-8");

            PutObjectResult putResult= OSSClientUtil.OSSClient().putObject(bucketName,folder+fileName,file.getInputStream(),objectMeta);
            resultStr =putResult.getETag();
            String url = getUrl(folder+fileName,bucketName);
            return url;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public static String getUrl(String key,String bucketName) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24
                * 365 * 10);
        // 生成URL
        // 初始化OSSClient
        URL url =  OSSClientUtil.OSSClient().generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            String urlStr = url.toString();
            if(urlStr.contains("?")||urlStr.contains("?")){
                String[] split = urlStr.split("\\?");
                urlStr = split[0];
            }
            return urlStr;
        }
        return null;
    }

    /**
     * 上传视频
     * @param file
     * @param key
     * @param bucketName
     */
    public static String bigUploadToOSS(MultipartFile file, String key , String bucketName){
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result =  OSSClientUtil.OSSClient().initiateMultipartUpload(request);
        String uploadId = result.getUploadId();

        List<PartETag> partETags= new ArrayList<>();
        try {
            InputStream inputStream= file.getInputStream();
            UploadPartRequest uploadPartRequest=new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(inputStream);
            //设置分片大小,除最后一个分片外,其他分片要大于100kb
            uploadPartRequest.setPartSize(100*1024);
            //设置分片号,范围是1-10000;
            uploadPartRequest.setPartNumber(1);

            UploadPartResult uploadPartResult=  OSSClientUtil.OSSClient().uploadPart(uploadPartRequest);
            //每次上传part之后，OSS的返回结果会包含一个 PartETag 对象，他是上传块的ETag与块编号（PartNumber）的组合，
            //在后续完成分片上传的步骤中会用到它，因此我们需要将其保存起来。一般来讲我们将这些 PartETag 对象保存到List中
            partETags.add(uploadPartResult.getPartETag());
            //快速排序
            Collections.sort(partETags, new Comparator<PartETag>() {
                @Override
                public int compare(PartETag p1, PartETag p2) {
                    return p1.getPartNumber() - p2.getPartNumber();
                }
            });

            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
            OSSClientUtil.OSSClient().completeMultipartUpload(completeMultipartUploadRequest);
            String url = getUrl(key,bucketName);
            return url;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
