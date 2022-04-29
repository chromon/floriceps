package com.floriceps.rpc_core.discovery;

import org.apache.curator.x.discovery.ServiceInstance;

/**
 * 服务发现
 */
public interface DiscoveryService {
    /**
     * 由服务名查找服务。
     *
     * @param serviceName 服务名
     * @param requestURL 客户端地址
     * @return 服务实例地址和端口，示例：127.0.0.1:1786
     * @throws Exception
     */
     String discovery(String serviceName, String requestURL)
            throws Exception;

    /**
     * 获取全部服务实例
     * @throws Exception
     */
    void getServiceDetails() throws Exception;
}
