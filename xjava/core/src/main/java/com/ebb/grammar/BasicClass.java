package com.ebb.grammar;

import static java.lang.System.out;

public class BasicClass {
    // private member field
    private String name;    //default null
    private int age;        //default 0

    // public setter&getter method
    public void setAge(int age) { this.age = age; }

    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    // public static void main method
    // 命令行输入java ClassName，调用的就是其main函数
    public static void main(String[] args){
        // 创建一个BasicClass对象实例
        BasicClass foo = new BasicClass();
        // 调用其member method
        foo.setAge(5);
        foo.setName("drake");

        // std out 标准输出(console)
        out.println("name="+foo.getName()+",age="+foo.getAge());

    }
}
