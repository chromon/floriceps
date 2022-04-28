package com.floriceps.rpc_core.Register;

import java.io.IOException;

/**
 * 服务注册接口。
 */
public interface RegistryService {

    /**
     * 服务注册。
     * @throws Exception
     */
    void register() throws Exception;

    /**
     * 服务注销。
     * @throws Exception
     */
    void unRegister() throws Exception;

    /**
     * 服务销毁。
     * @throws IOException
     */
    void destroy() throws IOException;
}
