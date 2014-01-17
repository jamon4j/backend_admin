package com.ksyun.pcloud.common.account;

/**
 * TCG卡牌游戏 --- 常数定义表
 */
public interface Define {
    int VERSION_MAJOR = 1;
    int VERSION_MINOR = 0;
    /** 前后端通信协议私钥 */
    String PKEY = "TCGnausa*^hdiuoh3998u>:{23d9d@&#*joalknckjbxbZ";

    //------------------------------ Web接口 定义 ------------------------------
    int WEB_ERR_AUTH      = -10; //auth参数验证失败
    int WEB_ERR_SESSIONID = -11; //缺少sessid，或者sessid失效
    int WEB_ERR_TIMEOUT   = -12; //timestamp超时
    int WEB_ERR_UID       = -20; //uid不存在，或已失效，需要重新注册
    int WEB_ERR_REGISTER  = -21; //注册失败
    int WEB_ERR_UDID      = -22; //UDID不存在
    int WEB_ERR_EMAIL     = -23; //email不存在
    int WEB_ERR_SYSTEM_FAULT = -99; //系统维护中,该服务暂停
    int WEB_ERR_KNOWN     = -100;

    //----bind3rd--ERROR----
    int WEB_ERR_EAMILFORMATERROR = -30;//邮件形式错误
    int WEB_ERR_SERVER_EOOR = -31;//服务器错误，网络链接错误
    int WEB_ERR_EMAILNOTAVAILABLE =-32;//邮箱已经被注册
    int WEB_ERR_CACHEINVALID = -33;//缓存失效了
    int WEB_ERR_Exception = -1;
}
