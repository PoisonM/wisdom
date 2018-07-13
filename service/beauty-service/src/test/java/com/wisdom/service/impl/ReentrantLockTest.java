package com.wisdom.service.impl;
/**
 * FileName: ReentrantLockTest
 *
 * @author: 赵得良
 * Date:     2018/7/13 0013 10:44
 * Description:
 */
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            System.out.println("加锁");
            lock.lock();  // 看这里就可以
            try {
                i++;
            } finally {
//                System.out.println("释放");
//                lock.unlock(); // 看这里就可以
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest test = new ReentrantLockTest();
        Thread t1 = new Thread(test);
        Thread t2 = new Thread(test);
        t1.start();t2.start();
        t1.join(); t2.join(); // main线程会等待t1和t2都运行完再执行以后的流程
        System.err.println(i);
    }
}