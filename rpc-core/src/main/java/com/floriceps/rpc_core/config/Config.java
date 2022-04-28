package com.floriceps.rpc_core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件，用于读取 config.properties 文件中的配置。
 */
public class Config {

    static Properties properties;

    static {
        // 读取配置文件
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 读取配置文件中的端口号。
     *
     * @return 端口号，如果配置文件中没有设置端口号，则默认使用 9137。
     */
    public static int getServerPort() {
        String value = properties.getProperty("server.port");

        if (value == null) {
            return 9137;
        }
        return Integer.parseInt(value);
    }

    /**
     * 读取配置文件中的主机地址。
     * @return 主机地址，如果不存在，则使用 localhost
     */
    public static String getServerHost() {
        String value = properties.getProperty("server.host");

        if (value == null) {
            return "localhost";
        }
        return value;
    }

    /**
     * 读取配置文件中的序列化算法。
     *
     * @return 序列化算法名称。
     */
    public static String getSerializer() {
        return properties.getProperty("message.serializer");
    }

    /**
     * 读取配置文件中的连接超时时间，默认为 1000 毫秒。
     * @return 连接超时时间。
     */
    public static int getConnectTimeout() {
        String value = properties.getProperty("connect.timeout.millis");

        if (value == null) {
            return 1000;
        }
        return Integer.parseInt(value);
    }

    /**
     * 从配置文件中读取 Netty 客户端与服务端数据传输的最大帧数，默认为 1024。
     * @return 数据传输的最大帧数。
     */
    public static int getMaxFrameLength() {
        String value = properties.getProperty("max.frame.length");

        if (value == null) {
            return 1024;
        }
        return Integer.parseInt(value);
    }

    /**
     * 读取配置文件中的 zookeeper 连接地址。
     *
     * @return zookeeper 地址。
     */
    public static String getZkConnectString() {
        return properties.getProperty("zk.connect.string");
    }

    /**
     * 从配置文件中读取 zookeeper 会话超时时间（ms）。
     * @return 会话超时时间。
     */
    public static int getZkSessionTimeoutMs() {
        String value = properties.getProperty("zk.session.timeout.ms");

        if (value == null) {
            return 60 * 1000;
        }
        return Integer.parseInt(value);
    }

    /**
     * 从配置文件中读取 zookeeper 连接超时时间（ms）。
     * @return 连接超时时间。
     */
    public static int getZkConnectionTimeoutMs() {
        String value = properties.getProperty("zk.connection.timeout.ms");

        if (value == null) {
            return 15 * 1000;
        }
        return Integer.parseInt(value);
    }

    /**
     * 读取配置文件中的 zookeeper 服务注册节点根目录。
     *
     * @return 服务注册节点根目录地址。
     */
    public static String getZkBasePath() {
        return properties.getProperty("zk.base.path");
    }

    /**
     * 读取配置文件中的 zookeeper 负载均衡策略。
     *
     * @return 负载均衡策略实现全限定名。
     */
    public static String getZkLoadBalance() {
        return properties.getProperty("zk.load.balance");
    }

    /**
     * 从配置文件中读取 zookeeper 一致性 hash 算法虚拟节点个数，默认为 10 个。
     * @return 虚拟节点个数。
     */
    public static int getZkConsistentHashingVN() {
        String value = properties.getProperty("zk.consistent.hashing.vn");

        if (value == null) {
            return 10;
        }
        return Integer.parseInt(value);
    }

    /**
     * 读取配置文件中服务注册名称。
     *
     * @return 服务注册名称
     */
    public static String getZkServiceName() {
        return properties.getProperty("zk.service.name");
    }
}