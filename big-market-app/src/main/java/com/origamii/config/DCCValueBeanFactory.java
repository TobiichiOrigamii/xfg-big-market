package com.origamii.config;

import com.origamii.types.annotiations.DCCValue;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Origami
 * @description
 * @create 2024-11-03 19:54
 **/
@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "big-market-dcc";

    private static final String BASE_CONFIG_PATH_CONFIG = BASE_CONFIG_PATH + "/config";

    @Autowired
    private CuratorFramework client;

    @Autowired
    private Map<String, Object> dccObjMap = new HashMap<>();

    public DCCValueBeanFactory() throws Exception {
        if (null == client.checkExists().forPath(BASE_CONFIG_PATH_CONFIG)) {
            client.create().creatingParentsIfNeeded().forPath(BASE_CONFIG_PATH_CONFIG);
        }
        // 开启缓存
        CuratorCache curatorCache = CuratorCache.build(client, BASE_CONFIG_PATH_CONFIG);
        curatorCache.start();
        // 监听数据变化
        curatorCache.listenable().addListener((type, oldData, data) -> {
            switch (type) {
                case NODE_CHANGED:
                    String dccValuePath = data.getPath();
                    Object objBean = dccObjMap.get(dccValuePath);
                    if (null == objBean) return;
                    try {
                        // 1. getDeclaredField 方法用于获取指定类中声明的所有字段 包括public/protected/private字段
                        // 2. getField 方法用于获取指定类中的公共字段 只能获取到public字段
                        Field field = objBean.getClass().getDeclaredField(dccValuePath.substring((dccValuePath.lastIndexOf("/") + 1)));
                        field.setAccessible(true);
                        field.set(objBean, dccObjMap.get(dccValuePath));
                        field.setAccessible(false);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    break;
            }
        });

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DCCValue.class)) {
                DCCValue dccValue = field.getAnnotation(DCCValue.class);

                String value = dccValue.value();
                if (StringUtils.isBlank(value)) {
                    throw new RuntimeException("DCCValue is empty");
                }

                String[] split = value.split(Constants.COLON);
                String key = split[0];
                String defaultValue = split.length == 2 ? split[1] : null;

                String keyPath = BASE_CONFIG_PATH.concat("/").concat(key);
                try {
                    if (null == client.checkExists().forPath(keyPath)) {
                        client.create().creatingParentsIfNeeded().forPath(keyPath);
                        if (StringUtils.isNotBlank(defaultValue)) {
                            field.setAccessible(true);
                            field.set(bean, defaultValue);
                            field.setAccessible(false);
                        }
                    } else {
                        String configValue = new String(client.getData().forPath(keyPath));
                        if (StringUtils.isNotBlank(configValue)) {
                            field.setAccessible(true);
                            field.set(bean, configValue);
                            field.setAccessible(false);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                dccObjMap.put(keyPath, bean);
            }
        }
        return bean;
    }


}
