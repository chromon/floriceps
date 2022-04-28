package com.floriceps.rpc_core.config;

import com.floriceps.rpc_core.protocol.SerializerCollection;
import com.floriceps.rpc_core.protocol.serializer.Serializer;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 全局变量。
 */
public class Global {

    /**
     * 服务器主机地址。
     */
    public static final String SERVER_HOST = Config.getServerHost();

    /**
     * 服务器端口号。
     */
    public static final int SERVER_PORT = Config.getServerPort();

    /**
     * 消息序列化算法。
     */
    public static final Serializer SERIALIZER = SerializerCollection.getSerializer();

    /**
     * 消息序列化算法类型。
     */
    public static final int SERIALIZER_TYPE = SerializerCollection.getSerializerType();

    /**
     * 连接超时时间。
     */
    public static final int CONNECT_TIMEOUT_MILLIS = Config.getConnectTimeout();

    /**
     * 数据传输的最大帧数。
     */
    public static final int MAX_FRAME_LENGTH = Config.getMaxFrameLength();

    /**
     * zookeeper 连接地址
     */
    public static final String CONNECT_STRING = Config.getZkConnectString();

    /**
     * 会话超时时间（ms）
     */
    public static final int SESSION_TIMEOUT_MS = Config.getZkSessionTimeoutMs();

    /**
     * 连接超时时间（ms）
     */
    public static final int CONNECTION_TIMEOUT_MS = Config.getZkConnectionTimeoutMs();

    /**
     * 服务注册节点根目录
     */
    public static final String ZK_BASE_PATH = Config.getZkBasePath();

    /**
     * 每隔 3 秒重试一次，共重试 10 次
     */
    public static final RetryPolicy RETRY_POLICY = new ExponentialBackoffRetry(3000, 10);

    /**
     * 负载均衡策略
     */
    public static final String ZK_LOAD_BALANCE = Config.getZkLoadBalance();

    /**
     * 服务注册名称
     */
    public static final String ZK_SERVICE_NAME = Config.getZkServiceName();

    /**
     * 一致性 hash 算法虚拟节点个数。
     */
    public static final int ZK_CONSISTENT_HASHING_VN = Config.getZkConsistentHashingVN();
}
