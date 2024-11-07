package com.origamii.config;

import com.origamii.types.annotiations.DCCValue;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
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

    private static final String BASE_CONFIG_PATH = "/big-market-dcc";
    private static final String BASE_CONFIG_PATH_CONFIG = BASE_CONFIG_PATH + "/config";

    private final CuratorFramework client;

    private final Map<String, Object> dccObjMap = new HashMap<>();

    public DCCValueBeanFactory(CuratorFramework client) throws Exception {
        this.client = client;

        // 节点判断
        if (null == client.checkExists().forPath(BASE_CONFIG_PATH_CONFIG)) {
            client.create().creatingParentsIfNeeded().forPath(BASE_CONFIG_PATH_CONFIG);
            log.info("DCC 节点监听 base node {} not absent create new done!", BASE_CONFIG_PATH_CONFIG);
        }

        CuratorCache curatorCache = CuratorCache.build(client, BASE_CONFIG_PATH_CONFIG);
        curatorCache.start();

        curatorCache.listenable().addListener((type, oldData, data) -> {
            switch (type) {
                case NODE_CHANGED:
                    String dccValuePath = data.getPath();
                    Object objBean = dccObjMap.get(dccValuePath);
                    if (null == objBean) return;
                    try {
                        Class<?> objBeanClass = objBean.getClass();
                        // 检查 objBean 是否是代理对象
                        if (AopUtils.isAopProxy(objBean)) {
                            // 获取代理对象的目标对象
                            objBeanClass = AopUtils.getTargetClass(objBean);
//                            objBeanClass = AopProxyUtils.ultimateTargetClass(objBean);
                        }

                        // 1. getDeclaredField 方法用于获取指定类中声明的所有字段，包括私有字段、受保护字段和公共字段。
                        // 2. getField 方法用于获取指定类中的公共字段，即只能获取到公共访问修饰符（public）的字段。
                        Field field = objBeanClass.getDeclaredField(dccValuePath.substring(dccValuePath.lastIndexOf("/") + 1));
                        field.setAccessible(true);
                        field.set(objBean, new String(data.getData()));
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
        Class<?> targetBeanClass = bean.getClass();
        Object targetBeanObject = bean;
        if (AopUtils.isAopProxy(bean)) {
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        Field[] fields = targetBeanClass.getDeclaredFields();
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
                            field.set(targetBeanObject, defaultValue);
                            field.setAccessible(false);
                        }
                    } else {
                        String configValue = new String(client.getData().forPath(keyPath));
                        if (StringUtils.isNotBlank(configValue)) {
                            field.setAccessible(true);
                            field.set(targetBeanObject, configValue);
                            field.setAccessible(false);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                dccObjMap.put(keyPath, targetBeanObject);
            }
        }
        return bean;
    }


}
