package org.example.demo.manager.api;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Joword
 * @date: 2023/8/4 10:21
 * @version: 0.1.0
 * @description: interface to article
 */
public interface PublicationApi {
    JSONObject getPmidData();

    JSONObject getPmcIdData();
}
