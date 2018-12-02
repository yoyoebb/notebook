package com.ebb.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Show Content:
 *  1. how to get Class object
 *  2. the usage of common Class/interface
 *  3. get member(Constructor、Field、Method、Inner Class)
 *  4. special usage for Array、Enum、Inner Class、Annotation
 *  5. resource loader
 */
@SuppressWarnings("aaa")
public class Reflection implements Cloneable, Comparable<Reflection>{
    private static final Logger LOGGER = LoggerFactory.getLogger(Reflection.class);
    private String privateString;
    public String publicString;
    public class MemberInner{}
    public enum MemberEnum{RED,BLUE,YELLOW;}

    public Reflection(){}
    private Reflection(String field){}
    @Override public int compareTo(Reflection o) { return 0;}
    private int privateMethod(String field){return 1;}

    /*
     * 1. 有好几种获取Class Object的方式
     */

    // 1-1 类名.class 返回对应的Class
    public Class showGetClass1(){
        return Reflection.class;
    }

    // 1-2 Object.getClass() 返回
    public Class showGetClass2(){
        Reflection a = new Reflection();
        return a.getClass();
    }

    // 1-3 Class.forName() 静态方法
    public Class showGetClass3() throws Exception{
        return Class.forName("Reflect_01_ShowCLass");
    }

    /*
     * 2. common usage
     */
    public void showCommonUsage(){
        Class cl = Reflection.class;

        // 2-1 通过Class对象获取对应类型的实例
        LOGGER.debug("2-1 通过Class对象获取对应类型的实例：");
        try {
            // 要求有默认的无参构造方法，否则抛异常
            Object o = cl.newInstance();
            LOGGER.debug(o.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 2-2 获取对应类型的信息
        LOGGER.debug("2-2 获取Class对象的信息");
        //虚拟机里的带包类名
        LOGGER.debug("fullName     :{}",cl.getName());
        //大多数类和上面没区别，针对数组等特殊类型名称更容易理解
        LOGGER.debug("canonicalName:{}",cl.getCanonicalName());
        //不带包的类名
        LOGGER.debug("shortName    :{}",cl.getSimpleName());

        // 2-3 get super class
        LOGGER.debug("2-3 获取Super Class对象的信息");
        //没有范型信息
        LOGGER.debug("superClass         :{}", cl.getSuperclass().getName());
        //with Generic parameter
        LOGGER.debug("superClass<T>      :{}", cl.getGenericSuperclass().getTypeName());
        // for Object
        LOGGER.debug("Object's super     :{}",Object.class.getSuperclass());
        // for array
        LOGGER.debug("array's super      :{}",int[].class.getSuperclass());
        // for primitive
        LOGGER.debug("primitive's super  :{}",int.class.getSuperclass());

        // 2-4 get implements interfaces
        LOGGER.debug("2-4 获取实现的interface信息");
        //没有范型信息
        for(Class i: cl.getInterfaces())
            LOGGER.debug("interfaces   :{}",i.getName());
        //with Generic parameter
        for(Type t: cl.getGenericInterfaces())
            LOGGER.debug("interfaces<T>:{}",t.getTypeName());

        // 2-5 get annotations
        //返回指定类型的annotation(只能返回运行时生效的注解)
        LOGGER.debug("annotation         :{}",cl.getAnnotation(SuppressWarnings.class));
        //返回class上所有的annotation(只能返回运行时生效的注解)
        for(Annotation i:cl.getAnnotations())
            LOGGER.debug("annotations         :{}",i.annotationType().getName());
        //jse1.8支持重复注解后，返回多个同类型注解(只能返回运行时生效的注解)
        Annotation[] typeAnnotations = cl.getAnnotationsByType(Override.class);
        for(Annotation i: cl.getAnnotationsByType(SuppressWarnings.class))
            LOGGER.debug("annotations by type :{}",i.annotationType().getName());
    }


    /*
     *  3 access member
     */
    public void showAccessMember(){
        Class cl = Reflection.class;

        // 3.1 member: constructor
        LOGGER.debug("3.1 get member Constructor");
        // iterate all constructor
        for(Constructor<Reflection> cons: cl.getDeclaredConstructors())
            LOGGER.debug("member Constructor : {}", cons);
        // iterate all public constructor
        for(Constructor<Reflection> cons: cl.getConstructors())
            LOGGER.debug("public member Constructor : {}", cons);
        // get the constructor with given params
        try {
            LOGGER.debug("member Constructor with special parms: {}", cl.getDeclaredConstructor(String.class));
            LOGGER.debug("public member COnstructor with special parms: {}",cl.getConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 3.2 member: method
        LOGGER.debug("3.2 get member Method");
        // iterate all method
        for(Method method : cl.getDeclaredMethods())
            LOGGER.debug("member Method : {}", method);
        // iterate all public method（包括所有继承的）
        for(Method method : cl.getMethods())
            LOGGER.debug("public member Method : {}", method);
        // get the method with given name & params
        try {
            LOGGER.debug("member method with special parms: {}", cl.getDeclaredMethod("privateMethod", String.class));
            LOGGER.debug("public member method with special parms: {}",cl.getMethod("compareTo", Reflection.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 3.3 member: field
        LOGGER.debug("3.3 get member field");
        // iterate all fields
        for(Field field : cl.getDeclaredFields())
            LOGGER.debug("member field : {}", field.getName());
        // iterate all public fields（包括所有继承的）
        for(Field field : cl.getDeclaredFields())
            LOGGER.debug("public member field : {}", field);
        try {
            LOGGER.debug("membe field with given name : {}",cl.getDeclaredField("publicString"));
            LOGGER.debug("public membe field with given name : {}",cl.getField("privateString"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        // 3.4 member type: inner class, interface, enum, annotation
        LOGGER.debug("3.4 get member type");
        // iterate all types
        for(Class clazz : cl.getDeclaredClasses())
            LOGGER.debug("member type : {}", clazz.getName());
        // iterate all public types
        for(Class clazz : cl.getClasses())
            LOGGER.debug("public member type : {}", clazz.getName());

    }


    /*
     * 4 special type only: enum/annotation/interface
     */
    public void showSpecialType(){
        class LocalInner{};

        // 4.1 如果Class是枚举，获取其常量数组
        LOGGER.debug("4.1 if class is Enum type:");
        if(MemberEnum.class.isEnum()){
            // 获取enum常量数组
            for(MemberEnum me: MemberEnum.class.getEnumConstants())
                LOGGER.debug("enum constant : {}", me.toString());
        }

        // 4.2 inner member 获取外部类
        LOGGER.debug("4.2 How to get Outer Class for inner member");
        // 获取member：内部类、接口、枚举、注释所在的外部类
        // 对local inner class不起作用
        LOGGER.debug("normal inner class's outer class : {}",MemberInner.class.getDeclaringClass());
        LOGGER.debug("normal inner enum's outer class  : {}",MemberEnum.class.getDeclaringClass());
        LOGGER.debug("local inner class's outer class  : {}",LocalInner.class.getDeclaringClass());

        // 获取member：内部类、接口、枚举、注释所在的外部类
        // 对local inner class有效
        LOGGER.debug("normal inner class's outer class : {}",MemberInner.class.getEnclosingClass());
        LOGGER.debug("normal inner enum's outer class  : {}",MemberEnum.class.getEnclosingClass());
        LOGGER.debug("local inner class's outer class  : {}",LocalInner.class.getEnclosingClass());

        // 如果是local or anonymous inner class
        if(LocalInner.class.isAnonymousClass() || LocalInner.class.isLocalClass()){
            //返回定义local inner class的构造方法
            LOGGER.debug("constractor for local inner   : {}", LocalInner.class.getEnclosingConstructor());
            //返回定义local inner class的方法
            LOGGER.debug("normal method for local inner : {}", LocalInner.class.getEnclosingMethod());
        }
    }
}