// JS的函数可以接受函数作为入参(高阶函数)，同样也支持函数作为返回值，当满足以下条件时，被返回的函数会形成闭包

// 1. B函数在A函数内定义，并被直接或作为对象方法返回
// 2. B函数内部引用了A函数的局部变量var
// 形成闭包时，局部变量var在A函数执行完成后不会消失，而是作为了B函数对象上下文的一部分被保留下来，并且之后只能被B函数对象访问，达到了私有变量的效果
function lazy_sum(arr) {
    var sum = function () {
        return arr.reduce(function (x, y) {
            return x + y;
        });
    }
    return sum;
}
var f = lazy_sum([1, 2, 3, 4, 5]);              // f为返回的闭包函数对象
f();                                            // 15
// 每次调用A函数后返回的B函数，都是一个新的函数对象
var f1 = lazy_sum([1, 2, 3, 4, 5]);
var f2 = lazy_sum([1, 2, 3, 4, 5]);
f1 === f2;                                      // false

// 获取到闭包函数B时，其执行环境中保存的A函数局部变量的值，是A函数执行完毕时对应状态
function count() {
    var arr = [];
    for (var i=1; i<=3; i++) {
        arr.push(function () {
            return i * i;
        });
    }
    return arr;
}
var results = count();
results[0]();                   // 16   A函数执行完毕后，i的值为4
results[1]();                   // 16   A函数执行完毕后，i的值为4
results[2]();                   // 16   A函数执行完毕后，i的值为4