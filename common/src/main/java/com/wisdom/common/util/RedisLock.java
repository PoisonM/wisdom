/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.wisdom.common.util;

import com.wisdom.common.util.SpringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 活动
 */
public class RedisLock {

	/**jedis客户端**/
	private final Jedis jedis;

	/**锁定资源的key**/
	private final String lockName;

	/**持有锁的最长时间**/
	private final int expireTime = 300;

	/**获取不到锁的休眠时间**/
	private final long sleepTime = 100;

	/**锁中断状态**/
	private boolean interruped = true;

	/**超时时间**/
	private long expireTimeOut = 0;

	private static JedisPool jedisPool = SpringUtil.getBean(JedisPool.class);

	public RedisLock(String lockName){
		this.jedis = jedisPool.getResource();
		this.lockName = lockName;
	}

	public void lock() {
		if (jedis == null)
			throw new NullPointerException("jedis is null");
		if (lockName == null)
			throw new NullPointerException("key is null");
		while (true){
			if (!interruped)
				throw new RuntimeException("获取锁状态被中断");
			long id = jedis.setnx(lockName, lockName);
			if (id == 0L){
				try {
					Thread.currentThread().sleep(this.sleepTime);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}else{
				expireTimeOut = System.currentTimeMillis()/1000 + expireTime;
				jedis.expireAt(this.lockName, expireTimeOut);
				break;
			}
		}
	}

	public void lockInterruptibly() throws InterruptedException {
		this.interruped = false;
	}

	public boolean tryLock() {
		if (jedis == null)
			throw new NullPointerException("jedis is null");
		if (lockName == null)
			throw new NullPointerException("lockName is null");
		if (!interruped)
			throw  new RuntimeException("线程被中断");
		long id = jedis.setnx(lockName, lockName);
		if (id == 0L)
			return false;
		else {
			// 设置锁过期时间
			expireTimeOut = System.currentTimeMillis()/1000 + expireTime;
			jedis.expireAt(this.lockName, expireTimeOut);
			return true;
		}
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		if (jedis == null)
			throw new NullPointerException("jedis is null");
		if (lockName == null)
			throw new NullPointerException("lockName is null");
		if (time == 0)
			return false;
		long now = System.currentTimeMillis();
		long timeOutAt = now + calcSeconds(time, unit);
		while (true){
			if (!interruped)
				throw new InterruptedException("线程被中断");
			long id = jedis.setnx(this.lockName, this.lockName);
			// id = 0 表示加锁失败
			if(id == 0){
				// 获取锁超时
				if(System.currentTimeMillis() > timeOutAt)
					return false;
				try {
					// 休眠一段时间，继续获取锁
					Thread.currentThread().sleep(this.sleepTime);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}else {
				//获取锁成功，设置锁过期时间
				expireTimeOut = System.currentTimeMillis()/1000 + expireTime;
				jedis.expireAt(this.lockName, expireTimeOut);
				return true;
			}
		}
	}

	public void unlock() {
		try {
			//当前时间小于过期时间,则锁未超时,删除锁定
			if (System.currentTimeMillis() / 1000 < expireTimeOut)
				jedis.del(lockName);
		}catch (Exception e){

		}finally {
			jedis.close();
		}
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException("不支持当前的操作");
	}

	/**
	 * 时间转换成毫秒
	 * @param time
	 * @param unit
	 * @return
	 */
	private long calcSeconds (long time, TimeUnit unit){
		if (unit == TimeUnit.DAYS)
			return time * 24 * 60 * 60 * 1000;
		else if (unit == TimeUnit.HOURS)
			return time * 60 * 60 * 1000;
		else  if (unit == TimeUnit.MINUTES)
			return time * 60 * 1000;
		else
			return time * 1000;
	}

}
