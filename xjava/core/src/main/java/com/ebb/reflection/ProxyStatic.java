package com.ebb.reflection;

import com.ebb.util.LoggerUtils;

/**
 *  演示静态代理(编译时行为)
 *  1. target类 和 proxy类 都实现相同的接口
 *  2. proxy类的构造方法中传入target类
 *  3. proxy在override接口方法时，对target的方法进行增强
 *
 *  适用于当目标对象和代理的动作都很明确且较少变化的情况下，通过直接编码生成代理对象
 *  缺点在于：要为代理对象和目标对象显式建立关联关系，而当代理行为和目标对象的组合灵活多变时，需要提供很多代理类实现，不利于扩展。
 *  典型例子：java.io下的字符流、字节流设计
 */
public class ProxyStatic {

    // target和proxy共同实现的interface
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

    // proxy class
    public class UserDaoProxy implements IUserDao{
        private IUserDao userDao ;

        // 利用构造方法初始化target object
        public UserDaoProxy(IUserDao userDao){
            this.userDao = userDao ;
        }

        @Override
        public void save(){
            LoggerUtils.LOGGER.debug("{}:----before----", this.getClass().getSimpleName());
            // do something before invoke target method
            // ...

            // invoke target method
            userDao.save() ;

            LoggerUtils.LOGGER.debug("{}:----after----", this.getClass().getSimpleName());
            // do something after invoke target method
            // ... ;
        }
    }

    public void showStaticProxy(){
        LoggerUtils.LOGGER.debug("show normal class");
        // normal class
        IUserDao dao = new UserDao();
        dao.save();

        LoggerUtils.LOGGER.debug("show static proxy class");
        // static proxy class
        dao = new UserDaoProxy(dao);
        dao.save();
    }
}
