package com.west.lake.blog.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * 自定义Redis序列化,对于redisTemplate.opsForValue.set()有效，对注解@Cache无效
 *
 * @author futao
 * Created on 2019-03-22.
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private Class<T> clazz;

//    public FastJsonRedisSerializer(Class<T> clazz) {
//        super();
//        this.clazz = clazz;
//    }

    private static final SerializerFeature[] SERIALIZER_FEATURES = new SerializerFeature[]{
            SerializerFeature.PrettyFormat
            , SerializerFeature.SkipTransientField
            , SerializerFeature.WriteEnumUsingName
            , SerializerFeature.WriteDateUseDateFormat
            , SerializerFeature.WriteNullStringAsEmpty
            , SerializerFeature.WriteNullListAsEmpty
            , SerializerFeature.WriteMapNullValue
            //TODO(因为 new FastJsonRedisSerializer的时候不知道T的类型，所以没有将T的类型传过来。
            // 所以序列化的时候必须需要带上Class类型，否则反序列化的时候无法知道Class类型)
            , SerializerFeature.WriteClassName
    };

    /**
     * 序列化
     *
     * @param t 数据
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        return t == null ? null : JSON.toJSONString(t, SERIALIZER_FEATURES).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz);
    }
}
