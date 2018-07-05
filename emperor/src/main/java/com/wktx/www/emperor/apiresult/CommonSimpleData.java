package com.wktx.www.emperor.apiresult;

/**
 * Created by yyj on 2018/1/17.
 * 忘记密码 \ 发送验证码 \ 需求发布 \ 账户认证（个人&店铺） \ 取消雇佣订单 \
 * 收藏简历 \ 余额支付 \ 编辑（添加）店铺
 */

public class CommonSimpleData {
    private int code;
    private String msg;

    public int getCode()
    {
        return this.code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CommonSimpleData{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
