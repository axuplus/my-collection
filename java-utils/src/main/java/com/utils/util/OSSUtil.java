package com.utils.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

public class OSSUtil {

    private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";

    private static final String accessKeyId = "";

    private static final String accessKeySecret = "";

    public static void createBucket(String bucketName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 如果创建存储空间的同时需要指定存储类型以及数据容灾类型, 可以参考以下代码。
        // 此处以设置存储空间的存储类型为标准存储为例。
        // createBucketRequest.setStorageClass(StorageClass.Standard);
        // 默认情况下，数据容灾类型为本地冗余存储，即DataRedundancyType.LRS。如果需要设置数据容灾类型为同城冗余存储，请替换为DataRedundancyType.ZRS。
        // createBucketRequest.setDataRedundancyType(DataRedundancyType.ZRS)
        // 创建存储空间。
        Bucket bucket = ossClient.createBucket(createBucketRequest);
        logger.info("bucket--->>>"+bucket);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static String uploadFile(String bucketName, String fileName, File file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentType("image/jpg");
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        putObjectRequest.setMetadata(metadata);
        // 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        logger.info("putObjectResult--->>>"+putObjectResult.getETag());
        // 关闭OSSClient。
        ossClient.shutdown();
        return "http://"+bucketName+".oss-cn-shenzhen.aliyuncs.com/"+fileName;
    }

    /**
     * 上传除了图片之外的其它文件
     * @param bucketName
     * @param fileName
     * @param file
     * @return
     */
    public static String uploadBaseFile(String bucketName, String fileName, File file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
         ObjectMetadata metadata = new ObjectMetadata();
         // metadata.setContentType("image/jpg");
         metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
         metadata.setObjectAcl(CannedAccessControlList.PublicRead);
         putObjectRequest.setMetadata(metadata);
        // 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        logger.info("putObjectResult--->>>"+putObjectResult.getETag());
        // 关闭OSSClient。
        ossClient.shutdown();
        return "http://"+bucketName+".oss-cn-shenzhen.aliyuncs.com/"+fileName;
    }


    public static String copyFile(String bucketName, String fileName,String newNucketName, String newFileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建新名称
       /* String[] nameArray = fileName.split("\\.");
        String newFileName = StringUtils.getRandomString(25) +"_temp"+ "." +nameArray[nameArray.length-1];*/


        // 拷贝文件。
        CopyObjectResult result = ossClient.copyObject(bucketName,fileName,newNucketName,newFileName);
        System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
        // 关闭OSSClient。
        ossClient.shutdown();
        return "http://"+bucketName+".oss-cn-shenzhen.aliyuncs.com/"+newFileName;
    }

    /**
     * 删除文件
     * @param bucketName
     * @param fileName
     */
    public static void deleteFile(String bucketName, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。
        ossClient.deleteObject(bucketName, fileName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 流式下载文件   返回文本
     * @param bucketName
     * @param fileName
     */
    public static InputStream getFileToString(String bucketName, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //获取文件
        OSSObject ossObject = ossClient.getObject(bucketName, fileName);
        // 关闭OSSClient。
        ossClient.shutdown();

        return ossObject.getObjectContent();
    }


    public static String uploadFileInputStream(String bucketName, String fileName, InputStream inputStream) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        putObjectRequest.setMetadata(metadata);
        // 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        logger.info("putObjectResult--->>>"+putObjectResult.getETag());
        // 关闭OSSClient。
        ossClient.shutdown();
        return "http://"+bucketName+".oss-cn-shenzhen.aliyuncs.com/"+fileName;
    }

    public static void main(String[] args){
//        createBucket("jkdzbucket01");
        String ff = uploadBaseFile("jkdzbucket01",  StringUtils.getRandomString(20)+".png",new File("C:\\Users\\8\\Desktop\\A3.png"));
        /*String ff = copyFile("jkdzbucket01","nrpdjhgivxn997ba1m3c.mtl");*/
        logger.info("ff--->>>"+ff);
    }

}
