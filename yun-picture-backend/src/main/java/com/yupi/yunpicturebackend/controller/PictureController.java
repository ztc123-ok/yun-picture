package com.yupi.yunpicturebackend.controller;

import com.yupi.yunpicturebackend.annotation.AuthCheck;
import com.yupi.yunpicturebackend.common.BaseResponse;
import com.yupi.yunpicturebackend.common.ResultUtils;
import com.yupi.yunpicturebackend.constant.UserConstant;
import com.yupi.yunpicturebackend.model.dto.picture.PictureUploadRequest;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.PictureVO;
import com.yupi.yunpicturebackend.service.PictureService;
import com.yupi.yunpicturebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
@Slf4j
public class PictureController {

    @Autowired
    private UserService userService;
    @Autowired
    private PictureService pictureService;

    /**
     * 上传图片（可重新上传）
     */
    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(
            @RequestPart("file") MultipartFile multipartFile,
            PictureUploadRequest pictureUploadRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(multipartFile, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }
}
