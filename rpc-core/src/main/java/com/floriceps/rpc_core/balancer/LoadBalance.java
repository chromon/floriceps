package com.floriceps.rpc_core.balancer;

import com.floriceps.rpc_core.discovery.InstanceDetails;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

/**
 * 负载均衡策略接口。
 */
public interface LoadBalance {
     /**
      * 从服务实例列表中通过负载均衡策略选取一个服务实例。
      *
      * @param instances 服务实例列表。
      * @param requestURL 客户端地址。
      * @return 服务实例的地址和端口号，示例：地址:端口。
      */
      String getBalance(Collection<ServiceInstance<InstanceDetails>> instances,
                        String requestURL);
}
