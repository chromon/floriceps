package com.floriceps.rpc_core.message;

/**
 * RPC 请求消息封装类。
 */
public class RpcRequestMessage extends Message {

    /**
     * 服务名，调用接口全限定名。
     */
    private String serviceName;

    /**
     * 接口中被调用的方法名。
     */
    private String methodName;

    /**
     * 被调用的方法返回类型。
     */
    private Class<?> returnType;

    /**
     * 被调用方法的参数类型列表。
     */
    private Class[] parameterTypes;

    /**
     * 被调用方法的实际参数列表。
     */
    private Object[] parameterValues;

    public RpcRequestMessage(int sequenceId, String serviceName, String methodName, Class<?> returnType,
                             Class[] parameterTypes, Object[] parameterValues) {
        super.setSequenceId(sequenceId);
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    /**
     * 获取当前消息类的类型。
     * @return 消息类的类型。
     */
    @Override
    public int getMessageType() {
        return MessageType.RPC_MESSAGE_TYPE_REQUEST;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }
}
