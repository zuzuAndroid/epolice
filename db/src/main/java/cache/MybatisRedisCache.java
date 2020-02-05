package cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCache implements Cache{

    private static Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);
    // 读写锁
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    private static final long EXPIRE_TIME_IN_DAY = 1;

    // 这里使用redis缓存，使用springBoot自动注入
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private String id;

    public MybatisRedisCache(final String id){
        if(null == id){
            throw new IllegalStateException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if(redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>) SpringContextHolder.getApplicationContext().getBean("redisTemplate");
        }
        if(value != null){
            redisTemplate.opsForValue().set(key.toString(),value,EXPIRE_TIME_IN_DAY, TimeUnit.DAYS);
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }

    @Override
    public Object getObject(Object key) {
        try{
            if(null != key){
                return redisTemplate.opsForValue().get(key.toString());
            }
        }catch(Exception e){
            log.error("缓存错误");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if(null != key){
            return redisTemplate.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        log.debug("清空缓存");
        if(null == redisTemplate){
            redisTemplate = (RedisTemplate<String, Object>) SpringContextHolder.getApplicationContext().getBean("redisTemplate");
        }
        Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 获取缓存中的缓存数量
     * @return
     */
    @Override
    public int getSize() { // 获取缓存中的缓存数量
        return  redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::dbSize).intValue();
    }

}
