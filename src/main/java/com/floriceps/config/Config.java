package com.floriceps.config;

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
}