package com.floriceps.service;

import java.lang.reflect.Constructor;

/**
 * 服务端注册的服务。
 */
public class Service {

    /**
     * 服务名（实现类的名字）
     */
    public String serviceName;

    /**
     * 服务对应类对象
     */
    private Class<?> clazz;

    /**
     * 服务实例
     */
    private Object instance;

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 判断服务名是否可用。
     *
     * @return 是否是可用的服务
     */
    public boolean classIsAvailable() {
        boolean available;
        try {
            available = (null != Class.forName(serviceName));
        } catch (Exception e) {
            available = false;
        }
        return available;
    }

    /**
     * 根据服务名构建服务实例。
     */
    public void newInstance() {
        if (instance == null) {
            try {
                clazz = Class.forName(serviceName);
                Constructor<?> constructor = clazz.getConstructor();
                instance = constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用服务方法。
     * @param methodName 方法名。
     * @param parameterTypes 参数类型列表。
     * @param parameterValues 参数值列表。
     * @return 方法调用返回值。
     */
    public Object invokeMethod(String methodName, Class<?>[] parameterTypes,
                                      Object[] parameterValues) {
        Object obj = null;
        try {
            obj = clazz.getMethod(methodName, parameterTypes)
                    .invoke(instance, parameterValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 获取服务名。
     * @return 服务名。
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 获取 service 实例。
     * @return service 实例。
     */
    public Object getInstance() {
        return instance;
    }
}
