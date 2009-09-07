package cn.touki.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BeanUtils {
    //Properties

    //Constructor

    //Methods
    /**
     * Set the properties of bean <code>obj</code> with the value contained in map <code>properties</code>.
     */
    public static void populate(Object obj, Map<String, String[]> properties) {

        Method[] methods = obj.getClass().getMethods();
        List<Method> setters = new ArrayList<Method>(methods.length);

        for (Method method : methods) {
            // Only method which has one parameter and whose name starts with 'set' are considered as
            // setter method.
            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                setters.add(method);
            }
        }

        for (Method setter : setters) {
            Class<?> paramType = setter.getParameterTypes()[0];
            String propertyName = setter.getName().substring(3);
            propertyName = propertyName.substring(0, 1).toLowerCase().concat(propertyName.substring(1));
            String[] values = properties.get(propertyName);

            try {
                if (values.length == 0) {
                    continue;
                }
                else if (values.length == 1) {
                    invokeSetter(obj, setter, values[0]);
                }
                else {
                    if (paramType.equals(String[].class)) {
                        setter.invoke(obj, (Object)values);
                    }
                }
            }
            catch (Exception e) {
                // If the invocation failed, go on for the next one.
                continue;
            }
        }
    }

    private static void invokeSetter(Object invoker, Method setter, String param)
            throws IllegalAccessException, InvocationTargetException {

        Class<?> paramType = setter.getParameterTypes()[0];

        if (paramType.equals(String.class)) {
            setter.invoke(invoker, param);
        }
        else if (paramType.equals(int.class)) {
            setter.invoke(invoker, Integer.parseInt(param));
        }
        else if (paramType.equals(Integer.class)) {
            setter.invoke(invoker, Integer.valueOf(param));
        }
        else if (paramType.equals(long.class)) {
            setter.invoke(invoker, Long.parseLong(param));
        }
        else if (paramType.equals(Long.class)) {
            setter.invoke(invoker, Long.valueOf(param));
        }
        else if (paramType.equals(float.class)) {
            setter.invoke(invoker, Float.parseFloat(param));
        }
        else if (paramType.equals(Float.class)) {
            setter.invoke(invoker, Float.valueOf(param));
        }
        else if (paramType.equals(double.class)) {
            setter.invoke(invoker, Double.parseDouble(param));
        }
        else if (paramType.equals(Double.class)) {
            setter.invoke(invoker, Double.valueOf(param));
        }
        else if (paramType.equals(boolean.class)) {
            setter.invoke(invoker, Boolean.parseBoolean(param));
        }
        else if (paramType.equals(Boolean.class)) {
            setter.invoke(invoker, Boolean.valueOf(param));
        }
        else if (paramType.equals(short.class)) {
            setter.invoke(invoker, Short.parseShort(param));
        }
        else if (paramType.equals(Short.class)) {
            setter.invoke(invoker, Short.valueOf(param));
        }
        else if (paramType.equals(byte.class)) {
            setter.invoke(invoker, Byte.parseByte(param));
        }
        else if (paramType.equals(Byte.class)) {
            setter.invoke(invoker, Byte.valueOf(param));
        }
        else if (paramType.equals(char.class)) {
            setter.invoke(invoker, param.charAt(0));
        }
    }

} //end class
