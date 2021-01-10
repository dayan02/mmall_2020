package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author: Guoxy
 * @Email: guoxy@primeton.com
 * @desc 本地缓存
 * @create: 2021-01-09 12:14
 **/
public class TokenCache {

    private static Logger logger  = LoggerFactory.getLogger(TokenCache.class);

    public  static final String TOKEN_PREFIX = "token_";

    //声明一个静态内存块，使用java内部的本地缓存
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12,TimeUnit.HOURS)
           .build(new CacheLoader<String, String>() {
               //默认的数据加载实现，当调用get取值的时候，当key没有对应的值时候，默认使用这个方法进行加载
               //默认返回null，当判断null.equal就会报错，所以就需要返回“null”
               @Override
               public String load(String s) throws Exception {
                   return "null";
               }
           });

    public static void  setKey(String key,String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){

        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return  value;
        } catch (ExecutionException e) {
            e.printStackTrace();
            logger.error("localCache get error",e);
        }
        return null;

    }



}
