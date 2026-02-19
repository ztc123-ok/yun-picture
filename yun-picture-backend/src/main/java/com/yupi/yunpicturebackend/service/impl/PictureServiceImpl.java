package com.yupi.yunpicturebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yunpicturebackend.model.entity.Picture;
import com.yupi.yunpicturebackend.service.PictureService;
import com.yupi.yunpicturebackend.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author 28446
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2026-02-19 12:50:45
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}




