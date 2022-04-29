package com.floriceps.rpc_core.discovery;

import com.floriceps.rpc_core.balancer.ConsistentHashing;
import com.floriceps.rpc_core.balancer.LoadBalance;
import com.floriceps.rpc_core.balancer.RandomBalance;
import com.floriceps.rpc_core.config.Global;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import javax.print.attribute.standard.RequestingUserName;
import java.util.Collection;

/**
 * 服务发现的 Zookeeper 实现。
 */
public class ZookeeperDiscoveryService implements DiscoveryService {

    // 服务发现的实例
    private ServiceDiscovery<InstanceDetails> serviceDiscovery;

    public ZookeeperDiscoveryService() throws Exception {
        // 创建连接
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Global.CONNECT_STRING)
                .sessionTimeoutMs(Global.SESSION_TIMEOUT_MS)
                .connectionTimeoutMs(Global.CONNECTION_TIMEOUT_MS)
                .retryPolicy(Global.RETRY_POLICY)
                .build();
        // 开启连接
        client.start();

        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .basePath(Global.ZK_BASE_PATH)
                .serializer(serializer)
                .build();

        // 启动服务注册中心
        serviceDiscovery.start();
    }

    /**
     * 由服务名查找服务。
     *
     * @param serviceName 服务名
     * @return 服务实例地址和端口，示例：127.0.0.1:1786
     * @throws Exception
     */
    @Override
    public String discovery(String serviceName, String requestURL) throws Exception {
        // 获取所有相同服务名称的所有服务实例
        Collection<ServiceInstance<InstanceDetails>> instances = serviceDiscovery.queryForInstances(serviceName);

        // 权限定名访问负载均衡
        Class clazz = Class.forName(Global.ZK_LOAD_BALANCE);
        LoadBalance balance = (LoadBalance) clazz.getDeclaredConstructor().newInstance();

        return balance.getBalance(instances, requestURL);
    }

    /**
     * 获取全部服务实例。
     * @throws Exception
     */
    @Override
    public void getServiceDetails() throws Exception {
        //根据名称获取所有的服务名称
        Collection<String> serviceNames = serviceDiscovery.queryForNames();
        for (String serviceName : serviceNames) {
            // 获取所有相同服务名称的所有服务实例
            Collection<ServiceInstance<InstanceDetails>> instances = serviceDiscovery.queryForInstances(serviceName);
            System.out.println(serviceName);
            for (ServiceInstance<InstanceDetails> instance : instances) {
                System.out.println("\t" + instance);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ZookeeperDiscoveryService service = new ZookeeperDiscoveryService();
        service.discovery(Global.ZK_SERVICE_NAME, "localhost:12344");
    }
}
