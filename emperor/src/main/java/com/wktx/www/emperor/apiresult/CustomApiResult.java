package com.wktx.www.emperor.apiresult;

import com.zhouyou.http.model.ApiResult;

/**
 * Created by yyj on 2018/1/17.
 * 描述：自定义ApiResult
 * 主要思想就是：默认标准的是code、msg、data三个字段，如果当前项目的结构字段和默认的不一样，
 * 只需要覆写不一样的就可以了，一样的字段不用覆写，添加反而会出问题。
 * 返回json：
 * {
 * "ret": 200,
 * "data": {
 * "code": 0,
 * "msg": "发送成功"
 * },
 * "msg": ""
 * }
 */

public class CustomApiResult<T> extends ApiResult<T> {

    private int ret;//对应默认标准ApiResult的code

    @Override
    public int getCode() {
        return ret;
    }

    @Override
    public boolean isOk() {
        return ret==200;//200表示成功
    }
}
