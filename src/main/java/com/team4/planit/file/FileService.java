package com.team4.planit.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class FileService {
   @Value("${bucketName}")
   private String S3Bucket; // Bucket 이름

   @Autowired
   AmazonS3Client amazonS3Client;
   public String getImgUrl(MultipartFile[] multipartFileList) throws IOException {
      for(MultipartFile multipartFile: multipartFileList) {
         String originalName = multipartFile.getOriginalFilename(); // 파일 이름
         long size = multipartFile.getSize();  // 파일 크기
         String type = multipartFile.getContentType();
         if(!type.startsWith("image")) throw new CustomException(ErrorCode.FILE_TYPE_INVALID);
         if(size>3500000) throw new CustomException(ErrorCode.FILE_SIZE_INVALID);

         ObjectMetadata objectMetaData = new ObjectMetadata();
         objectMetaData.setContentType(multipartFile.getContentType());
         objectMetaData.setContentLength(size);

         // S3에 업로드
         amazonS3Client.putObject(
                 new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                         .withCannedAcl(CannedAccessControlList.PublicRead)
         );

         return amazonS3Client.getUrl(S3Bucket, originalName).toString();
      }
      return null;
   }
}
