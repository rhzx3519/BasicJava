package com.example.maven.third;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author ZhengHao Lou
 * @date 2020/11/30
 */
public class FooBeanUtils {

    public static void copyPropertiesIgnoreNull(Object dest, Object origin) {
        getProperties(origin).stream()
                //.filter(p -> getProperty(origin, p) != null)
                .forEach(p -> setProperty(dest, p, getProperty(origin, p)));
    }

    private static void setProperty(Object dest, String name, Object v) {
        try {
            BeanUtils.setProperty(dest, name, v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getProperty(Object obj, String name) {
        try {
            return BeanUtils.getProperty(obj, name);
        } catch (NestedNullException nn) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<String> getProperties(Object dest) {
        if (dest == null) {
            return Collections.emptySet();
        } else if (isJavaType(dest)) {
            return Collections.emptySet();
        }
        return getProperties(dest.getClass(), dest);
    }

    private static Set<String> getProperties(Class<?> clz, Object obj) {
        if (isJavaType(clz)) {
            return Collections.emptySet();
        }
        if (Map.class.isAssignableFrom(clz)) {
            return getPropertiesFromMap((Map<?, ?>) obj);
        }
        Set<String> properties = new TreeSet<>();
        Arrays.stream(PropertyUtils.getPropertyDescriptors(clz))
                .forEach(pd -> {
                    String propertyName = pd.getName();
                    if (propertyName.equals("class") || propertyName.equals("baseStage")) {
                        return;
                    }
                    try {
                        Object value = null;
                        if (obj != null) {
                            value = pd.getReadMethod().invoke(obj);
                        }
                        Set<String> nestProperties = getProperties(pd.getPropertyType(), value);
                        if (nestProperties.isEmpty()) {
                            properties.add(propertyName);
                        } else {
                            nestProperties.forEach(np -> properties.add(String.format("%s.%s", propertyName, np)));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return properties;
    }

    private static Set<String> getPropertiesFromMap(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> properties = new TreeSet<>();
        map.forEach((k, v) -> {
            Set<String> nestProperties = getProperties(v);
            if (nestProperties.isEmpty()) {
                properties.add((String) k);
            } else {
                nestProperties.forEach(np -> properties.add(String.format("%s.%s", k, np)));
            }
        });
        return properties;
    }

    private static boolean isJavaType(Object dest) {
        return isJavaType(dest.getClass());
    }

    private static boolean isJavaType(Class<?> dest) {
        return Number.class.isAssignableFrom(dest)
                || String.class.isAssignableFrom(dest)
                || Boolean.class.isAssignableFrom(dest)
                || Collection.class.isAssignableFrom(dest)
                || dest.isPrimitive();
    }
}
