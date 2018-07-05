package com.wktx.www.subjects.apiresult;

/**
 * Created by yyj on 2018/6/6.
 * 图片上传（个性简历、作品、头像、认证）
 */

public class ImgUploadData {
    private int code;
    private String msg;
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ImgUploadData{" +
                "msg='" + msg + '\'' +
                ", code=" + code + '\'' +
                ", info=" + info +
                '}';
    }
}
