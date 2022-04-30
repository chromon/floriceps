package com.floriceps.rpc_core.Register;

import com.floriceps.rpc_core.config.Global;
import com.floriceps.rpc_core.discovery.InstanceDetails;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;

/**
 * 服务注册 Zookeeper 实现。
 */
public class ZooKeeperRegistryService implements RegistryService {

    // 服务发现的实例
    private ServiceDiscovery<InstanceDetails> serviceDiscovery;
    // 服务注册信息实例
    private ServiceInstance<InstanceDetails> serviceInstance;
    // Zookeeper 连接
    private CuratorFramework client;

    public ZooKeeperRegistryService(String serviceName, String host, int port, String description) throws Exception {
        // 服务实例
        serviceInstance = ServiceInstance.<InstanceDetails>builder()
                .name(serviceName)
                .address(host)
                .port(port)
                .payload(new InstanceDetails(description))
                .build();

        // 创建连接
        client = CuratorFrameworkFactory.builder()
                .connectString(Global.CONNECT_STRING)
                .sessionTimeoutMs(Global.SESSION_TIMEOUT_MS)
                .connectionTimeoutMs(Global.CONNECTION_TIMEOUT_MS)
                .retryPolicy(Global.RETRY_POLICY)
                .build();

        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
        // 构建服务注册中心
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .basePath(Global.ZK_BASE_PATH)
                .serializer(serializer)
                .build();
    }

    /**
     * 服务注册
     * @throws Exception
     */
    @Override
    public void register() throws Exception {
        // 开启 zookeeper 连接
        client.start();
        // 向注册中心注册服务
        serviceDiscovery.registerService(serviceInstance);
        // 启动服务注册中心
        serviceDiscovery.start();
    }

    /**
     * 服务注销
     * @throws Exception
     */
    @Override
    public void unRegister() throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);
    }

    /**
     * 关闭注册中心
     * @throws IOException
     */
    @Override
    public void destroy() throws IOException {
        serviceDiscovery.close();
    }
}
