package com.baohuquan.cache;


import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class XMemcached {

    private final static Logger logger = LoggerFactory.getLogger(XMemcached.class);

    private static MemcachedClient clientDelegate = init();



    public static boolean set(String key, int exp, Object value) {
        try {
            return clientDelegate.set(key, exp, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String key) {
        try {
            return clientDelegate.delete(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static <T> T get(String key) {
        try {
            return clientDelegate.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> Map<String, T> get(Collection<String> keyCollections) {
        try {
            return clientDelegate.get(keyCollections);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * +1
     * 不存在主动添加
     * @param key
     * @return
     */
    public static  long incrOne(String key,int expireTime){

        try {
            Object o  = clientDelegate.get(key);
            long l;
            if(o==null){
                clientDelegate.set(key,expireTime,"1");
                l=1L;
            }else{
                l = clientDelegate.incr(key, 1);
            }
            return l;
        } catch (TimeoutException e) {
            logger.error(e.getMessage(), e);
            return 0;
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            return 0;
        } catch (MemcachedException e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }


    /**
     * +1
     * 不存在不添加
     * @param key
     * @return
     */
    public static  long incr(String key,int factor){

        try {
            return clientDelegate.incr(key,factor);
        } catch (TimeoutException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return 0;
        } catch (MemcachedException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return 0;
        }
    }


    /**
     * 刷新 不失效
     * @param key
     * @param exp
     * @return
     */
    public static boolean touch(String key, int exp){

        try {
            clientDelegate.touch(key,exp);
        } catch (TimeoutException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (MemcachedException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private static MemcachedClient init() {

        String host = "c09f679c12de454b.m.cnbjalicm12pub001.ocs.aliyuncs.com";
        int port =11211;
        XMemcachedClientBuilder builder =
                new XMemcachedClientBuilder(AddrUtil.getAddresses(String.format("%s:%s", host, port)));
        builder.setCommandFactory(new TextCommandFactory());
        try {
            return builder.build();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
