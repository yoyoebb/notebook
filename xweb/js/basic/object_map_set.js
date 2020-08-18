// JavaScript的默认对象表示方式{}可以视为其他语言中的Map或Dictionary的数据结构，即一组键值对。
// 但是JavaScript的对象有个小问题，就是键必须是字符串。但实际上Number或者其他数据类型作为键也是非常合理的。
// 为了解决这个问题，最新的ES6规范引入了新的数据类型Map

// ES6 Map，用来存放键值对
// 可以用一个二维数组初始化Map
var m = new Map([['Michael', 95], ['Bob', 75], ['Tracy', 85]]);
m.get('Michael');                  // 95
// 也可以创建空Map，再添加元素(set/get)
m = new Map();                     // 空Map
m.set('Adam', 67);                 // 添加新的key-value
m.set('Bob', 59);
m.has('Adam');                     // 是否存在key 'Adam': true
m.get('Adam');                     // 67
m.delete('Adam');                  // 删除key 'Adam'
m.get('Adam');                     // undefined


// ES6 Set，用来存放不重复的集合
// 可以用一个一维数组初始化
var s = new Set([1, 2, 3, 3]);   // 含1, 2, 3，重复元素在Set中自动被过滤
// 也可以创建空Set，再添加元素
s = new Set();
s.add(1);                         // 1
s.delete(1);                      // 通过delete删除


// ES6 为了统一集合类型，引入了新的iterable类型，Array、Map和Set都属于iterable类型
// ES6 iterable类型和for...of循环
// 遍历Array
var arr = ['A', 'B', 'C'];
for (var x of arr) {
    console.log(x);
}
// 遍历Set
var set = new Set(['A', 'B', 'C']);
for (var x of set) {
    console.log(x);
}
// 遍历Map, x为一个key-value对的数组，x[0]为key，x[1]为value
var map = new Map([[1, 'x'], [2, 'y'], [3, 'z']]);
for (var x of map) {
    console.log(x[0] + '=' + x[1]);
}

// ES6 iterable类型和forEach函数(forEach函数是ES5.1引入的，当时只支持数组)
arr = ['A', 'B', 'C'];
arr.forEach(function (element, index, array) {
    // element: 指向当前元素的值
    // index: 指向当前索引
    // array: 指向Array对象本身
    console.log(element + ', index = ' + index);
});
// Map的回调函数参数依次为value、key和map本身
// Set的回调函数参数依次为element、element和set本身