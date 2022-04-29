package com.floriceps.rpc_core.balancer;

import com.floriceps.rpc_core.discovery.InstanceDetails;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;
import java.util.Random;

/**
 * 随机策略。
 */
public class RandomBalance implements LoadBalance {
    @Override
    public String getBalance(
            Collection<ServiceInstance<InstanceDetails>> instances,
            String requestURL) {
        ServiceInstance<InstanceDetails>[] arr = (ServiceInstance<InstanceDetails>[]) instances.toArray();
        ServiceInstance<InstanceDetails> instance = arr[new Random().nextInt(arr.length)];
        return instance.getAddress() + ":" + instance.getPort();
    }
}
