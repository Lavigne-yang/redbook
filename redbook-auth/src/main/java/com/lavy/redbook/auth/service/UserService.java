package com.lavy.redbook.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lavy.redbook.auth.domain.dataobject.UserDO;
import com.lavy.redbook.auth.model.vo.user.UserLoginReqVO;
import com.lavy.redbook.framework.common.response.Response;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/12
 * @version: v1.0.0
 * @description: 用户服务
 */
public interface UserService extends IService<UserDO> {

    /**
     * 登录
     *
     * @param reqVO 请求参数
     * @return 响应结果
     */
    Response<?> doLogin(UserLoginReqVO reqVO);
}
