package com.ebb.grammar;

import static java.lang.System.out;

public interface BasicInterface {
    // interface中定义的member field，自动为public static final，主要用于声明常量
    int DEFAULT_ROOM_NUM = 304;

    // interface中定义的member method，自动为abstract method
    int yourRoomNum();

    // static method
    // jse8新特性，用于减少接口对应的工具类，通过Interface.staticMethod()调用
    static boolean isDefaultRoommate(int yourRoomNum){
        if (yourRoomNum==DEFAULT_ROOM_NUM)
            return true;
        return false;
    }

    // default method
    // jse8新特性，用于提供默认实现，目的也是减少公共抽象父类
    default int getDefaultRoomNum(){
        return DEFAULT_ROOM_NUM;
    }
}

// simple sub class
class SimpleInterface implements BasicInterface {
    private int myRoomNum = 303;

    // class implements interface时，对于normal method，要么提供实现，要么将class声明为abstract
    @Override
    public int yourRoomNum() {
        return myRoomNum;
    }

    // class implements interface时，对于default method，可以不提供实现，也无需将class声明为abstract
    /*
    @Override
    public int getDefaultRoomNum() {
        return 0;
    }
    */

    public static void main(String[] args){
        BasicInterface inter = new SimpleInterface();
        out.println("Public Default Room:" + inter.getDefaultRoomNum());
        out.println("Your Room:" + inter.yourRoomNum());
        out.println("Is Your Room Public Default Room? " + BasicInterface.isDefaultRoommate(inter.yourRoomNum()));
    }
}