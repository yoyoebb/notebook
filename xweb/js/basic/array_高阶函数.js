//高阶函数(Higher-order function)：接收其他函数作为参数的函数，常用的有Arrya的各种方法

var arr;
// 1. Array的map: 将数组中每个元素应用传入的函数，并返回结果集对应的新array
function pow(x) {
    return x * x;
}
arr = [1, 2, 3, 4, 5, 6, 7, 8, 9];
var results = arr.map(pow);              // [1, 4, 9, 16, 25, 36, 49, 64, 81]

// map和parseInt
arr = ['1', '2', '3'];
arr.map(parseInt);                       // 1, NaN, NaN
// 原因：map内部会传递3个参数(element,index,array)给传入的foo函数，而parseInt实际上支持两个参数:parseInt(string, radix)，第二个参数指定进制


// 2. Array的reduce: 把一个函数作用在这个Array的[x1, x2, x3...]上，这个函数必须接收两个参数，reduce()把结果继续和序列的下一个元素做累积计算
//    效果等同于[x1, x2, x3, x4].reduce(f) = f(f(f(x1, x2), x3), x4)
arr = [1, 2, 3, 4, 5, 6, 7, 8, 9];
arr.reduce(function (x, y) {
    return x + y;                       // 返回1-9的和
});


// 3. Array的sort: 对数组元素按指定方式进行排序
arr = [10, 20, 1, 2];
arr.sort(function (x, y) {
    if (x < y) {
        return -1;
    }
    if (x > y) {
        return 1;
    }
    return 0;
});
console.log(arr);                       // [1, 2, 10, 20]


// 4. Array的filter: 对数组元素进行过滤，返回剩下的元素集合
//    filter()把传入的函数依次作用于每个元素，然后根据返回值是true还是false决定保留还是丢弃该元素。
arr = [1, 2, 4, 5, 6, 9, 10, 15];
var r = arr.filter(function (x) {
    return x % 2 !== 0;
});
r;                                      // [1, 5, 9, 15]


// 除此之外，Array还有很多高阶函数：every()、find()、findIndex()、forEach()