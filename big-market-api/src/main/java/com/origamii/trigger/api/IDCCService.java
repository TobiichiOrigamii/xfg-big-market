package com.origamii.trigger.api;

import com.origamii.types.model.Response;

/**
 * @author Origami
 * @description dynamic config center 管理服务接口
 * @create 2024-11-03 20:44
 **/
public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);
}
