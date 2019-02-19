/* 1. simple data type (Number, String, Boolean) */
// Number 包含定点数和浮点数
123;      // 整数123
0.456;    // 浮点数0.456
1.2345e3; // 科学计数法表示1.2345x1000，等同于1234.5
-99;      // 负数
NaN;      // NaN表示Not a Number，当无法计算结果时用NaN表示
Infinity; // Infinity表示无限大

// String 以单引号'或双引号"括起来的任意文本，跟java一样是Immutable
'abc';
"hello world";
'\u4e2d\u6587'; // Unicode模式，完全等同于 '中文'
'\x41';         // ASCII码模式，完全等同于 'A'
// ES6语法糖
`这是一个
多行
字符串`;        // ``扩起来的多行字符串，避免手动添加 \n
`你好, ${name}, 你今年${age}岁了!`;  // 模版字符串，避免手动字符串拼接

// 常用字符串操作
var str = "test";
str.length;          // 返回字符串长度
str.toUpperCase();   // 返回大写字符串'TEST', toLowerCase()返回小写字符串
str.indexOf("s");    // 搜索子串首次出现的位置，没有找到则返回-1
str.indexOf("t",1);  // 从指定位置开始搜索
str.substr(position,length);  // 返回从x位开始的长度为y的子串, length可省略
str.split('');       // 根据指定字符,将字符串切分并返回数组
                     // 如果头尾有指定字符，会返回空字符串元素

// Boolean
true;
false;

// 比较时，如果使用'=='，会自动进行类型转换
false == 0;          // true   
undefined == null;   // ture
false == undefined;  // false
// 使用==时 false 会和 0, 空字符串'' 进行自动转换判等，不会等于undefined/null
// 但是if()等条件表达式中，undefined/null会被视为条件为false
if(undefined)
    console.log('aaa');   // 不会被执行

// 比较时，使用'==='，则不会进行类型转换
false === 0;         // false
undefined === null;  // false
NaN === NaN;         // false NaN不能判等，只能用isNaN

// JavaScript设计者希望用null表示空值，而undefined表示值未定义。
// 事实证明，这毫无意义。
// 大多数情况下应该用null。undefined仅仅在判断函数参数是否传递的情况下有用。

/* 2. define a variable */
var name = "ebb";
var a;            //undefined
// 如果一个变量没有通过var申明就被使用，那么该变量就自动被申明为全局变量
// 这种默认做法非常危险(很容易造成变量被错误地公用，还很难调试)
// 为了修复这一缺陷ECMA在后续规范中推出了strict模式
// 在该模式下，未使用var申明变量就使用的，将导致运行错误(Reference Error)。
// 开启方式是在第一行使用
'use strict';

/* 3. Array */
// array
var arr = [1, 2, 3.14, 'Hello', null, true];
// 常用数组操作
arr.length;           // 返回数组长度, 6
arr.length=3;         // 修改数组长度会导致数组发生变化 arr=[1,2,3.14]
arr[0];               // 获取指定索引的元素 1
arr.indexOf('Hello'); // 搜索数组元素并返回第一个匹配的索引 3

// 这些操作不会修改arr本身
arr.slice(position,length); // 获取子数组，类似字符串的substr
arr.slice();          // 返回完整元素的数组，方便的数组复制
arr.concat([1]);      // 数组拼接(返回新数组，不改变arr)
arr.concat(1,2,[1]);
arr.join('-');        // 将数组元素按指定字符拼接成一个字符串

// 这些操作会导致arr被修改
arr.push('a');        // 在末尾添加新元素,支持多个参数
arr.pop();            // 移除末尾元素
arr.unshift('a');     // 在首部添加新元素,支持多个参数
arr.shift();          // 移除首部元素
arr.sort();           // 按默认规则排序 (修改arr)
arr.reverse();        // 数组元素反转 (修改arr)
arr.splice();         // 万能的删除及添加数组元素方法，用法见文档


/* 4. Object */
// object是一组由键-值组成的无序集合，key为字符串类型，值可以为任意类型
// js的对象是基于原型链，而不是java的class模版
var person = {
    name: 'Bob',
    age: 20,
    tags: ['js', 'web', 'mobile'],
    city: 'Beijing',
    hasCar: true,
    zipcode: null,
    'middle-school': 'No.1 Middle School'  //key不是有效变量名时需要包起来
};
person.name;
person['middle-school'];

person.notExists;   // 访问不存在的prop时不会报错，只会返回undefined
person.aaa='aaa';   // 可以自由的添加属性
delete person.aaa;  // 也可以删除属性 (不能删除默认属性)
delete preson.bbb;  // 删除不存在属性也不会报错

'aaa' in person;    // 判断对象中是否有指定prop (父类的也算)
person.hasOwnProperty('aaa');  // 判断是否自己定义的prop


/* 5. loop */
// js支持经典的for，while，do-while循环，以及for...in
var o = {
    name: 'Jack',
    age: 20,
    city: 'Beijing'
};
for (var key in o) {
    console.log(key);    // 'name', 'age', 'city'
    console.log(o[key]); // 'Jack', 20, 'Beijing'

    if (o.hasOwnProperty(key)) {  //过滤继承属性
        console.log(key);         // 'name', 'age', 'city'
    }
};
// for...in遍历数组时，索引是字符串而不是数字('0','1','2'...)

/* 6. 判断类型 */
// 可以用typeof获取类型
typeof 123;       // 'number'
typeof NaN;       // 'number'
typeof 'str';     // 'string'
typeof true;      // 'boolean'
typeof undefined; // 'undefined'
typeof Math.abs;  // 'function'
typeof null;      // 'object'   ...
typeof [];        // 'object'   数组
typeof {};        // 'object'

// js模仿java，提供了number、string、boolean的包装类型Number、String、Boolean
var n = new Number(123);    // 123,生成了新的包装类型
var b = new Boolean(true); // true,生成了新的包装类型
var s = new String('str'); // 'str',生成了新的包装类型
// typeof n/b/s，类型变为了object，用 === 和原始值判等返回false
typeof n;   // object
n === 123;  // false
// 如果没有使用new，则包装类被视为基本类型转换函数
var n = Number('123');   // 123，相当于parseInt()或parseFloat()
var b = Boolean('true'); // true
b = Boolean('false');    // true,因为它是非空字符串！
b = Boolean('');         // false
var s = String(123.45);  // '123.45'
var s = String(false);   // 是'false'，而不是''，惊喜吧
// 总结规律:
// 1. 不要使用new Number、String、Boolean创建包装对象
// 2. 类型转换
//    使用parseInt()或parseFloat()来转换为数字
//    使用String()或toString()方法来转换为字符串
//    不用特意转换为boolean，可以直接if(myvar){...}
// 3. typeof可以区分number, string, boolean, undefined, function
//    判断arrary : Array.isArray(arr)
//    判断null   : myvar === null
// 4. 判断某个全局变量是否存在     typeof window.myVar === 'undefined'
//    函数内部判断某个变量是否存在 typeof myVar === 'undefined'
//    ?是否可以不用typeof
// 5. 
