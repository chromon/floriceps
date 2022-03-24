package com.floriceps.rpc_core.message;

/**
 * RPC 响应消息封装类。
 */
public class RpcResponseMessage extends Message {

    /**
     * RPC 调用正常时的返回值。
     */
    private Object returnValue;

    /**
     * RPC 调用异常时返回的异常信息。
     */
    private Exception exceptionValue;

    /**
     * 获取当前消息类的类型。
     * @return 消息类的类型。
     */
    @Override
    public int getMessageType() {
        return MessageType.RPC_MESSAGE_TYPE_RESPONSE;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getExceptionValue() {
        return exceptionValue;
    }

    public void setExceptionValue(Exception exceptionValue) {
        this.exceptionValue = exceptionValue;
    }
}
