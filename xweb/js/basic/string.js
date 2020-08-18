//ES6 : 多行字符串，反引号括起来，用来替代 \n
var str1 = `这是一个
多行
字符串`;


//ES6 : 模板字符串，同样用反引号，用来替代 +操作
var name = '小明';
var age = 20;
var message = `你好，${name}, 你今年${age}了!`;


// 常见操作
var str = 'Hello world!';
str.length;                    // 13
str[0];                        // 'H'
str[13];                       // undefined

// 字符串是不可修改，对某个索引赋值不会报错，也没有效果
str[0] = 'X';
alert(str);                    // Hello, world!

str.toUpperCase();             // 返回大写字符串
str.toLowerCase();             // 返回小写字符串
str.indexOf('world');          // 搜索指定字符串第一次出现的位置，没有找到返回-1
str.substring(0,5);            // 返回从索引a开始，到索引b的子串(不包含b)，长度为b-a
str.substring(3);              // 返回从3开始到结束的子串