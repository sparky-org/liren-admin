package com.sparky.lirenadmin.invoker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 根据指定接口获取指定接口的实现类，并调用指定方法
 *
 * @ClassName NotifyInvoker
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
@Component
public class NotifyInvoker {

    public static NotifyInvoker notifyInvoker;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        notifyInvoker = this;
    }

    public static void invoke(Class componentInterface, String methodName, Object ... args){
        Map<String, Class> beanMap = notifyInvoker.applicationContext.getBeansOfType(componentInterface);
        if (beanMap == null){
            return;
        }
        for(Object bean : beanMap.values()){
            Method[] methodArray = bean.getClass().getMethods();
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
}
