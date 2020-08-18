// ES6 新增了一种新的函数：Arrow Function（箭头函数），看起来像是匿名函数的简化，但有个本质区别，就是箭头函数中this是词法作用域，由上下文确定
// 不再需要以前那种 that变量的hack写法

// 格式:  x=>{...}    (x,y)=>{...}

// 函数体只返回一个表达式，可以省略{...}和return
var foo = x => x * x;
// 等价于
foo = function (x) {
    return x * x;
};

// 如果函数体比较复杂，则不能省略{...}和return
foo = x => {
    if (x > 0) {
        return x * x;
    }
    else {
        return - x * x;
    }
};

//多个参数
(x, y) => x * x + y * y;
//无参数
() => 3.14;
//可变参
(x, y, ...rest) => {
    var i, sum = x + y;
    for (i=0; i<rest.length; i++) {
        sum += rest[i];
    }
    return sum;
};

//单表达式返回对象时，要带上括号，防止语法冲突
x => ({ foo: x });

// 内部函数中this的上下文被正确修复
var obj = {
    birth: 1990,
    getAge: function (year) {
        var b = this.birth; // 1990
        var fn = (y) => y - this.birth; // this.birth仍是1990
        return fn.call({birth:2000}, year);
    }
};
obj.getAge(2015); // 25