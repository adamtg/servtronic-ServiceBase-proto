package com.devitron.servtronic.servicebase.data;

import com.devitron.servtronic.messages.MessageReply;
import com.devitron.servtronic.messages.MessageRequest;

import java.lang.reflect.Method;
import java.util.function.Function;

public class FunctionArguments {

    private Method method;
    private Class<?> requestClass;
    private Class<?> replyClass;

    public FunctionArguments(Method method, Class<?> requestClass, Class<?> replyClass) {
        this.method = method;
        this.requestClass = requestClass;
        this.replyClass = replyClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(Class<?> requestClass) {
        this.requestClass = requestClass;
    }

    public Class<?> getReplyClass() {
        return replyClass;
    }

    public void setReplyClass(Class<?> replyClass) {
        this.replyClass = replyClass;
    }
}
