package com.ebb.grammar;

public enum BasicEnum implements ICommonEnum{
    CLASS("1","class"),
    INTERFACE("2","interface"),
    ENUM("3","enum"),
    ANNOTATION("4","annotation")
    ;

    // 枚举类的构造方法都默认为private
    BasicEnum(String value, String text) {
        this.value=value;
        this.text=text;
    }

    private String value;
    private String text;

    @Override
    public String getValue(){return this.value;}

    @Override
    public String getText(){return this.text;}

    @Override
    public String toString(){return this.toEnumString();}

}


/**
 * 针对业务枚举类的公用接口，采用html form元素value/text的形式，方便理解
 */
interface ICommonEnum {
    String getValue();

    String getText();

    default String toEnumString(){
        return "[value="+getValue()+",text="+getText()+"]";
    }
}