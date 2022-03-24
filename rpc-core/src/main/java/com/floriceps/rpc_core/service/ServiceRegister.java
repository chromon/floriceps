package com.floriceps.rpc_core.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册类
 */
public class ServiceRegister {

    /**
     * 服务器中注册的服务 Map。
     */
    private static final Map<String, Service> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * 将服务注册到服务器。
     *
     * @param service 待注册的服务。
     * @param interfaceName 父接口名。
     */
    public static void registerService(String interfaceName, Service service) {
        SERVICE_MAP.putIfAbsent(interfaceName, service);
    }

    /**
     * 根据服务名查找服务。
     *
     * @param interfaceName 服务名
     * @return 服务，如果服务不存在则返回 null。
     */
    public static Service findService(String interfaceName) {
        return SERVICE_MAP.get(interfaceName);
    }

    /**
     * 根据服务名删除已注册的服务。
     * @param interfaceName 待删除的服务名。
     */
    public static void removeService(String interfaceName) {
        SERVICE_MAP.remove(interfaceName);
    }
}
