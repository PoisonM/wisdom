package com.wisdom.common.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.wisdom.common.config.Global;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * oss 工具类
 * @author ft
 *
 */
public enum OSSClientUtil {

    instance;

    private static String accesskey = Global.getConfig("aliyun.accesskey");

    private static String secret =  Global.getConfig("aliyun.secret");

    private static String host =  Global.getConfig("oss.host");

    public static OSSClient OSSClient()
    {
        return new OSSClient(host,accesskey, secret);
    }
}
