package com.ruzzyfer.twitterlike.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class S3Service implements FileServiceImpl{

    private final AmazonS3 s3;

    private final String bucketName = "twitterlikebucket";

    public S3Service(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String saveFile(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        try{
            File file1 = convertMultiPartToFile(file);
            PutObjectResult putObjectResult = s3.putObject(bucketName,originalFileName,file1);
            return putObjectResult.getContentMd5();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] downloadFile(String fileName) {

        S3Object object = s3.getObject(bucketName,fileName);
        S3ObjectInputStream objectContent = object.getObjectContent();
        try{
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteFile(String fileName) {

        s3.deleteObject(bucketName,fileName);

        return "file deleted";
    }

    @Override
    public List<String> listALlFiles() {
        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws Exception
    {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }

}
