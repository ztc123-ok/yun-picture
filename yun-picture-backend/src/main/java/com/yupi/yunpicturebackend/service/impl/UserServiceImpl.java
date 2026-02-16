package com.yupi.yunpicturebackend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.enums.UserRoleEnum;
import com.yupi.yunpicturebackend.service.UserService;
import com.yupi.yunpicturebackend.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author 28446
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2026-02-16 14:22:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.参数校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 2.检查用户账号是否和数据库重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 3.密码一定要加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 4.插入数据到数据库中
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }

    /**
     * 获得加密算法
     * @param userPassword
     * @return
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        final String SALT = "ztc";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
}




