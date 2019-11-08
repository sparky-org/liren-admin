package com.sparky.lirenadmin.invoker;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

/**
 * 根据指定接口获取指定接口的实现类，并调用指定方法
 *
 * @ClassName NotifyInvoker
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class NotifyInvoker {

    private ApplicationContext applicationContext;

    public void invoke(Class componentInterface, String methodName, Object ... args){
        Map<String, Class> beanMap = applicationContext.getBeansOfType(componentInterface);
        if (beanMap == null){
            return;
        }
        for(Class bean : beanMap.values()){
            Method[] methodArray = bean.getMethods();
            for(Method method : methodArray){
                if (methodName.equals(method.getName())){
                    try {
                        method.invoke(bean, args);
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
