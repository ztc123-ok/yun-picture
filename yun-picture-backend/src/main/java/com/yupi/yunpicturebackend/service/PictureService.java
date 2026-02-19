package com.yupi.yunpicturebackend.service;

import com.yupi.yunpicturebackend.model.dto.picture.PictureUploadRequest;
import com.yupi.yunpicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 28446
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2026-02-19 12:50:45
*/
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

}
