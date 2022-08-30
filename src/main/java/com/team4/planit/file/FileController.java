package com.team4.planit.file;

import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileController {
   private final FileService fileService;

    @PatchMapping("/members/change")
    public ResponseEntity<?> upload(MultipartFile[] image, RequestBody re) throws Exception {
       List<String> imgUrlList=fileService.getImgUrlList(image);
       return new ResponseEntity<>(Message.success(imgUrlList), HttpStatus.OK);
    }

}