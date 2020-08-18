// js中定义函数
function foo(x) {
    if (x >= 0) {
        return x;
    } else {
        return -x;
    }
}
foo(1);

// 函数本质上也是一种对象，所以有第二种定义方式(匿名函数)
var abs = function (x) {
    if (typeof x !== 'number') {
        throw 'Not a number';
    }
    if (x >= 0) {
        return x;
    } else {
        return -x;
    }
};
abs(1);                           // foo(1)和abs(1)是完全等价的

// 如果没有return语句，函数执行完毕后也会返回结果，只是结果为undefined。
// JS不支持方法重载(overloading)，不同参数的方法只会被认为是覆盖
// JS允许方法传入任意参数，比定义的参数 多或少 都不会影响调用 (但少的情况下，对应实参为undefined，可能会导致逻辑报错)
// 小心你的return语句, JavaScript引擎有一个在行末自动添加分号的机制, 如果return和返回值之间隔行，要在return后面带上{}

// arguments关键字: 它只在函数内部起作用，并且永远指向当前函数的调用者传入的所有参数。arguments类似Array但它不是一个Array
function foo2(x) {
    console.log('x = ' + x);                             // 10
    for (var i=0; i<arguments.length; i++) {
        console.log('arg ' + i + ' = ' + arguments[i]);  // 10, 20, 30
    }
}
foo2(10, 20, 30);


// ES6 可变参数rest数组(在之前也可以通过arguments处理，新标准只是简化了循环而已)
// rest参数只能写在最后，前面用...标识，如果没有符合条件的参数，rest会接收空数组[]，而不是undefined
function foo3(a, b, ...rest) {
    console.log('a = ' + a);
    console.log('b = ' + b);
    console.log(rest);
}
foo3(1);                                                 // a=1, b=undefined, rest=[]


// 对象方法中的this指针
// 绑定到对象属性上的函数都称为方法，在一个方法内部，this是一个特殊变量，它始终指向当前对象
var xiaoming = {
    name: '小明',
    birth: 1990,
    age: function () {
        var y = new Date().getFullYear();
        return y - this.birth;
    }
};
xiaoming.age();                                          // this指向xiaoming

// 在方法内部定义的函数，this指向又会被错误的定向，解决方法是在方法中定义本地变量(如that)，然后在子函数中用that引用
// ES6中定义了箭头函数用于修复这类问题
xiaoming = {
    name: '小明',
    birth: 1990,
    age: function () {
        function getAgeFromBirth() {
            var y = new Date().getFullYear();
            return y - this.birth;                      // this指向window or undefined(strict模式)
        }
        return getAgeFromBirth();
    }
};

// 如果是顶层函数，则this指针会指向全局对象window
// 由于这是一个巨大的设计错误，要想纠正可没那么简单。ECMA决定，在strict模式下让函数的this指向undefined
function getAge() {
    var y = new Date().getFullYear();
    return y - this.birth;
}
getAge();                                  // NaN or error(strict模式)
xiaoming.age = getAge;
xiaoming.age();                            // this指向xiaoming


// apply()和call()，都是Function对象自带的方法，可以用于调用时显式指定this指针
// 两者的区别是：apply把函数正常参数封装成数组传入；call则将参数依次单独传入
getAge.apply(xiaoming, []);          // 25, this指向xiaoming, 参数为空
Math.max.apply(null, [3, 5, 4]);    // 5， 对普通函数调用，我们通常把this绑定为null。
Math.max.call(null, 3, 5, 4);       // 5

// apply的装饰器用法
var count = 0;
var oldParseInt = parseInt;                       // 保存原函数
window.parseInt = function () {
    count += 1;
    return oldParseInt.apply(null, arguments);   // 调用原函数，这里用apply的好处是方便传递参数
};


