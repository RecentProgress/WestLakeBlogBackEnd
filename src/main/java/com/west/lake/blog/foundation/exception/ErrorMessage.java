package com.west.lake.blog.foundation.exception;

/**
 * 非国际化异常信息
 *
 * @author futao
 * Created on 2019-03-25.
 */
public class ErrorMessage {

    public static class ApplicationErrorMessage {

    }

    public static class LogicErrorMessage {
        public static final String USER_NOT_EXIST = "01020_用户不存在，请先注册";
        public static final String ACCOUNT_DISABLE = "01021_您的账号被禁用，请联系系统管理员";
        public static final String PASSWORD_WRONG = "01022_密码错误，请重新输入或找回密码";
        public static final String MUILTY_REGISTER_SUCCESS = "01023_您已注册成功，请直接登录";
        public static final String NOT_LOGIN = "01024_您还未登录或者登录已超时，请重新登录";
        public static final String USER_STATUS_ILLEGAL = "01025_用户状态不合法";
        public static final String USER_SEX_ILLEGAL = "01025_用户性别不合法";
        public static final String GEN_URI_FAIL = "00026_构造请求地址时发生异常";
        public static final String REQUEST_FAIL = "00027_请求时发生异常%s";
        public static final String GET_RESPONSE_FAIL = "00028_获取响应失败%s";
        public static final String CLOSE_RESPONSE_FAIL = "00029_关闭response失败%s";
        public static final String ADD_AUTH_HEAD_FAIL = "00030_request authentication fail %s";
        public static final String REQUEST_METHOD_NOT_EXISTS = "00031_请求方式不存在";


        public static final String REVIEW_CONTENT_MIN_MAX = "00032_评论内容长度限制{min}-{max}";

        public static final String SEND_MESSAGE_FAIL = "00033_短信发送失败，请稍后重试";

        public static final String FILE_DOWN_LOAD_FAIL = "00034_文件下载失败";

    }

}
