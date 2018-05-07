package com.wisdom.weixin.service.beauty;

import com.wisdom.common.entity.ReceiveXmlEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class ProcessBeautyViewEventService {

    private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

    public void processEvent(ReceiveXmlEntity xmlEntity)
    {
        //开启线程，将用户的openId存入session和cookie中
        Runnable processViewThread = new ProcessViewThread(xmlEntity);
        threadExecutorCached.execute(processViewThread);
    }

    private class ProcessViewThread extends Thread {

        private String token;
        private ReceiveXmlEntity xmlEntity;

        public ProcessViewThread(ReceiveXmlEntity xmlEntity) {
            this.token = token;
            this.xmlEntity = xmlEntity;
        }

        @Override
        public void run() {

        }
    }
}
