package com.devitron.servtronic.servicebase.data;

import com.devitron.servtronic.messages.MessageReply;
import com.devitron.servtronic.messages.MessageRequest;
import com.devitron.servtronic.servicebase.annotations.ServiceMethod;
import com.devitron.servtronic.servicebase.data.FunctionArguments;
import com.devitron.servtronic.servicebase.data.FunctionToMethodMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class SetupFunctionToMethod {

    public static void pullInAnnotations() throws IOException, NoSuchFieldException {

        FunctionToMethodMap ftmm = FunctionToMethodMap.FunctionToMethodFactory();

        ImmutableSet<ClassPath.ClassInfo> classSet = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses();

        for (ClassPath.ClassInfo classInfo : classSet) {
            if (classInfo.getName().contains("springframework")) {
                continue;
            }
            try {
            Class<?> c = classInfo.load();

                for (Method method : c.getMethods()) {
                    ServiceMethod a = method.getAnnotation(ServiceMethod.class);
                    if (a != null) {
                        String functionName = a.name();
                        if (functionName.isEmpty()) {
                            functionName = method.getName();
                        }

                        Class<?> messageReplyClass = method.getReturnType();
                        Class<?> messageRequestClass = null;
                        if (method.getParameterCount() == 1) {
                            messageRequestClass = method.getParameterTypes()[0];
                        }

                        if ((messageRequestClass == null) || (!MessageRequest.class.isAssignableFrom(messageRequestClass))) {
                            System.out.println("001 messageRequestClass is not a worthy class");
                            System.exit(0);
                        }

                        if ((messageReplyClass != null) && (!MessageReply.class.isAssignableFrom(messageReplyClass))) {
                            System.out.println("messageReplyClass is not a worthy class");
                            System.exit(0);
                            // thrown an exception
                        }

                        System.out.println("==============> " + functionName);
                        System.out.println("----> method: " + method.getName());
                        System.out.println("----> request: " + messageRequestClass.getName());
                        if (messageReplyClass != null) {
                            System.out.println("----> reply: " + messageReplyClass.getName());
                        } else {
                            System.out.println("----> reply: null");
                        }

                        ftmm.add(functionName, new FunctionArguments(method, messageRequestClass, messageReplyClass));
                    }
                }
            } catch (NoClassDefFoundError e) {
            }
        }
    }

}

