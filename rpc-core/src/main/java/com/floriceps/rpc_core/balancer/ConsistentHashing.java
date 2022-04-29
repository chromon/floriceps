package com.floriceps.rpc_core.balancer;

import com.floriceps.rpc_core.balancer.hash.ConsistentHashMap;
import com.floriceps.rpc_core.config.Global;
import com.floriceps.rpc_core.discovery.InstanceDetails;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

/**
 * 使用一致性 hash 算法实现负载均衡。
 */
public class ConsistentHashing implements LoadBalance {

    /**
     * 虚拟节点的个数。
     */
    private int VIRTUAL_NODES = Global.ZK_CONSISTENT_HASHING_VN;

    /**
     * 从服务实例列表中通过负载均衡策略选取一个服务实例。
     *
     * @param instances 服务实例列表。
     * @param requestURL 客户端地址。
     * @return 服务实例的地址和端口号，示例：地址:端口。
     */
    @Override
    public String getBalance(
            Collection<ServiceInstance<InstanceDetails>> instances,
            String requestURL) {
        ConsistentHashMap map = new ConsistentHashMap();

        for (ServiceInstance<InstanceDetails> instance : instances) {
            String address = instance.getAddress() + ":" +  instance.getPort();
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                // 虚拟节点示例：127.0.0.1:1786&&VN1
                String VNNode = address + "&&VN" + i;
                map.addServerNode(VNNode);
            }
        }
        return map.selectServerNode(requestURL);
    }
}
