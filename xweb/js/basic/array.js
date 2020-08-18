// array可以包含任意数据类型，并通过索引来访问每个元素
var arr = [1, 2, 3.14, 'Hello', null, true];
arr.length;                     // 6
// 修改array的长度会调整集合的元素
arr = [1,2,3];
arr.length = 5;                 // [1,2,3, undefined,undefined,undefined]
arr.length = 1;                 // 1
// 通过索引访问元素，从0开始
arr[0];                         // 1
arr[3] = 3;                     // [1, undefined, 3]


//常用操作
// indexOf 搜索指定元素的位置, 找不到返回-1
arr.indexOf(3);                 // 2

// slice 截取数组元素(从索引a开始，到索引b结束，不包含b)，返回一个新的array，类似于字符串的子串操作
var arr2 = arr.slice(0,2);      // [1, undefined]
arr2 = arr.slice(1);            // [undefined,3]
arr2 = arr.slice();             // [1, undefined, 3]相当于复制数组

// push/unshift : 向 array 末尾/头部 添加元素
// pop/shift    : 把array 最后一个/第一个 元素删除，空数组[]继续pop/shift不会报错
arr.push(4,5);                  // [1, undefined, 3, 4, 5];
arr.pop();                      // [1, undefined, 3, 4]

// sort : 对数组当前元素进行排序，需要注意的是，默认数值型元素会先转换成字符串再按字符顺序排序，解决办法是传入自定义的排序函数
arr = ['B','C','A'];
arr.sort();                     // arr:['A','B','C']
[10, 20, 1, 2].sort();          // [1, 10, 2, 20]

// reverse : 反转数组
arr = ['A','B','C'];
arr.reverse();                  // arr:['C','B','A']

// splice : 删除和添加操作的结合
arr = ['Microsoft', 'Apple', 'Yahoo', 'AOL', 'Excite', 'Oracle'];
// 从索引2开始删除3个元素,然后再添加两个元素:
arr.splice(2, 3, 'Google', 'Facebook'); // 返回数组 ['Yahoo', 'AOL', 'Excite']，arr:['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']
// 只删除,不添加:
arr.splice(2, 2);                       // 返回数组['Google', 'Facebook']，arr:['Microsoft', 'Apple', 'Oracle']
// 只添加,不删除:
arr.splice(2, 0, 'Google', 'Facebook'); // 返回数组[], arr:['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']

// concat : 连接数组并返回一个新的array
arr = ['A', 'B', 'C'];
arr2 = arr.concat([1,2,3]);             // arr2: ['A', 'B', 'C', 1, 2, 3], arr:['A', 'B', 'C']

// join : 将数组元素用指定字符串连接，并返回连接后的字符串;  如果Array的元素不是字符串，将自动转换为字符串后再连接
arr = ['A', 'B', 'C', 1, 2, 3];
var str = arr.join('-');                // 'A-B-C-1-2-3'


// 遍历数组，一般用for循环
arr = ['Apple', 'Google', 'Microsoft'];
var i, x;
for (i=0; i<arr.length; i++) {
    x = arr[i];
    console.log(x);
}

// 由于Array也是对象，每个索引是它的key，用for...in循环可以输出(但length属性却不包括在内)
arr = ['A', 'B', 'C'];
arr.name = 'Hello';
for (var i in arr) {
    console.log(i);                      // '0', '1', '2', 'name';  这里得到的索引key是字符串而不是数字
    console.log(arr[i]);                 // 'A', 'B', 'C', undefined
}