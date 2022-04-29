package com.floriceps.rpc_core.balancer;

import com.floriceps.rpc_core.discovery.InstanceDetails;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

/**
 * 负载均衡策略：轮询
 */
public class RoundRobinBalance implements LoadBalance {

    private int index = 0;

    @Override
    public String getBalance(
            Collection<ServiceInstance<InstanceDetails>> instances,
            String requestURL) {
        ServiceInstance<InstanceDetails>[] arr = (ServiceInstance<InstanceDetails>[]) instances.toArray();
        ServiceInstance<InstanceDetails> instance = arr[index++ % arr.length];
        return instance.getAddress() + ":" + instance.getPort();
    }
}
