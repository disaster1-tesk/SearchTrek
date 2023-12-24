
package com.search.trek.infrastructure.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ArgsUtil {

    public static Object[] append(Object key, Object... items) {
        Assert.notNull(key, "Key must not be null");
        Assert.notEmpty(items, "Items must not be empty");

        Object[] args = new Object[items.length + 1];
        args[0] = key;
        System.arraycopy(items, 0, args, 1, items.length);
        return args;
    }

    public static Object[] append(Object key, Map<?, ?> params) {
        Assert.notNull(key, "Key must not be null");
        Assert.notEmpty(params, "Params must not be empty");

        List<Object> args = new ArrayList<>(params.size() * 2 + 1);
        args.add(key);
        params.forEach((k, v) -> {
            args.add(k);
            args.add(v);
        });
        return args.toArray();
    }
}
