package com.devitron.servtronic.servicebase.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FunctionToMethodMap {

    private Map<String, FunctionArguments> map = null;
    private static FunctionToMethodMap functionToMethodMap = null;

    public static FunctionToMethodMap FunctionToMethodFactory() {
        if (functionToMethodMap == null) {
            functionToMethodMap = new FunctionToMethodMap();
        }
        return functionToMethodMap;
    }

    private FunctionToMethodMap() {
        map = new HashMap<>();
    }

    // Probably should throw an exception if function already exists
    // in the map, which means there is a duplicate
    public void add(String function, FunctionArguments functionArguments) {
        map.put(function, functionArguments);
    }

    // Probably should throw an exception if function does not
    // exist in the map
    public FunctionArguments get(String function) {
        return map.get(function);
    }
}
