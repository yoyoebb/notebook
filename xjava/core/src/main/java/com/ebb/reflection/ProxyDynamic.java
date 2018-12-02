package com.ebb.reflection;

import com.ebb.util.LoggerUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 演示JDK的dynamic proxy原理
 * JSE从1.3版本开始提供了dynamic proxy，它不需要程序员手工写proxy类、形成共性及聚合关系，这些都是在运行时自动生成和绑定。
 * 程序员就只需要干两件事情：
 *    1. 将目标对象和代理行为封装为处理逻辑对象(通过实现 java.lang.reflect.InvocationHandle)
 *    2. 提供共性(接口信息)和处理逻辑对象用于生成proxy对象(利用 java.lang.reflect.Proxy 的工厂方法)
 *
 Proxy生成的代理类有以下特性：
 JVM规范规定，所有自动生成的proxy都默认是Proxy的子类，至于class名称并没有要求，Oracle JVM中采用'$Proxy' + 'n'的方式命名
 对于相同的ClassLoader和相同的interfaces，生成的Proxy class也是唯一的，proxy class可以通过 Class proxyClass = Proxy.getProxyClass(classLoader, interfaces) 来获取
 proxy class 被申明为public & final
 可以通过 Proxy.isProxyClass 来判断一个class是否为代理类
 */
public class ProxyDynamic {

    // proxy的interface
    public interface IUserDao{
        void save() ;
    }

    // target class
    public class UserDao implements IUserDao{
        @Override
        public void save(){
            LoggerUtils.LOGGER.debug("{}:----已经保存数据！----", this.getClass().getSimpleName());
        }
    }

    // 针对接口方法指定代理行为，由于要持有target的引用，不能用匿名内部类实现
    public class SaveHandler implements InvocationHandler {
        private Object target ;

        public SaveHandler(Object t){
            target = t ;
        }

        // 接口里的proxy对象从没用上
        @Override
        public Object invoke(Object proxy, Method m, Object[] args) throws InvocationTargetException, IllegalAccessException {
            LoggerUtils.LOGGER.debug("{}:----before----", this.getClass().getSimpleName());
            // do something before invoke target method
            //... ;

            // invoke target method
            Object o = m.invoke(target, args) ;

            LoggerUtils.LOGGER.debug("{}:----after----", this.getClass().getSimpleName());
            // do something after invoke target method
            //... ;

            return o ;
        }
    }

    public void showDynamicProxy(){
        IUserDao dao = new UserDao();
        InvocationHandler handler = new SaveHandler(dao);
        Class[] interfaces = new Class[]{IUserDao.class};

        // classloader要满足 Class.forName(i.getName(), false, cl) == i 的条件
        // proxy默认实现interfaces包含的所有接口
        // 通过proxy调用接口方法时，其实质是调用handler的invoke方法。
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(),interfaces,handler);
        ((IUserDao)proxy).save();

        // proxy 的class名在Oracle JVM中形如：$Proxy{n}
        LoggerUtils.LOGGER.debug("proxy class name : {}", proxy.getClass().getName());
        LoggerUtils.LOGGER.debug("Is it proxy class:{}", Proxy.isProxyClass(proxy.getClass()));
    }
}
