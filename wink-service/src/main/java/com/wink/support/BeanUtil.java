package com.wink.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

    public static Map<String, Object> obj2Map(Object obj) {
        return obj2Map(obj, false);
    }

    public static Map<String, Object> obj2Map(Object obj, boolean includeNullValue) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (includeNullValue) {
                    map.put(field.getName(), field.get(obj));
                } else {
                    Object value = field.get(obj);
                    if (value != null) {
                        map.put(field.getName(), value);
                    }
                }
            } catch (Exception e) {
                LOG.error("obj2Map error", e);
            }
        }
        return map;
    }

    /**
     * copy properties from source to target
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, T target) {
        if (source == null || target == null) {
            return null;
        }
        try {
            BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            copier.copy(source, target, null);
        } catch (Exception e) {
            LOG.error("copyProperties error", e);
        }
        return target;
    }

    public static <T> List<T> copyListBean(List<?> source, List<T> target, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source) || target == null) {
            return target;
        }
        try {
            BeanCopier copier = BeanCopier.create(source.get(0).getClass(), clazz, false);
            for (Object obj : source) {
                T t = createInstance(clazz);
                copier.copy(obj, t, null);
                target.add(t);
            }
        } catch (Exception e) {
            LOG.error("copyListBean error", e);
        }
        return target;
    }

    public static <T> List<T> copyList(List<?> source, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source) || clazz == null) {
            return new ArrayList<>();
        }
        try {
            BeanCopier copier = BeanCopier.create(source.get(0).getClass(), clazz, false);
            List<T> target = new ArrayList<>(source.size());
            for (Object obj : source) {
                T t = createInstance(clazz);
                copier.copy(obj, t, null);
                target.add(t);
            }
            return target;
        } catch (Exception e) {
            LOG.error("copyList error", e);
        }
        return new ArrayList<>();
    }


    public static <T> T createInstance(Class<T> cls) {
        T obj = null;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            obj = null;
            e.printStackTrace();
        }
        return obj;
    }
}
