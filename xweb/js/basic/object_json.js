// JSON是JavaScript Object Notation的缩写，它是一种数据交换格式。在很多场合取代了难用的XML

// 在JSON中，一共就这么几种数据类型：
//   number：和JavaScript的number完全一致；
//   boolean：就是JavaScript的true或false；
//   string：就是JavaScript的string；
//   null：就是JavaScript的null；
//   array：就是JavaScript的Array表示方式——[]；
//   object：就是JavaScript的{ ... }表示方式。

// JSON中指定字符集必须是UTF-8，字符串必须用双引号""，Object的键也必须用双引号""
// 有了这些严格的规定，json对象和字符串之间可以非常便利的互相转换(即使在不同的语言环境中)

// 序列化成json字符串
var xiaoming = {
    name: '小明',
    age: 14,
    gender: true,
    height: 1.65,
    grade: null,
    'middle-school': '\"W3C\" Middle School',
    skills: ['JavaScript', 'Java', 'Python', 'Lisp']
};
var s = JSON.stringify(xiaoming);
// {"name":"小明","age":14,"gender":true,"height":1.65,"grade":null,"middle-school":"\"W3C\" Middle School","skills":["JavaScript","Java","Python","Lisp"]}

JSON.stringify(xiaoming, null, '  ');                // 提供更格式化的输出，便于查看

JSON.stringify(xiaoming, ['name', 'skills'], '  ');  // 第二个参数可以传入数组，用于控制输出object的哪些属性

function convert(key, value) {
    if (typeof value === 'string') {
        return value.toUpperCase();
    }
    return value;
}
JSON.stringify(xiaoming, convert, '  ');             // 第二个参数还可以传入函数，对象的每个键值对都会被函数先处理


// 反序列化
JSON.parse('[1,2,3,true]');              // [1, 2, 3, true]
JSON.parse('{"name":"小明","age":14}');  // Object {name: '小明', age: 14}
JSON.parse('true');                      // true
JSON.parse('123.45');                    // 123.45

// 第二个参数可以传入函数，对序列化后对象的键值对进行处理
var obj = JSON.parse('{"name":"小明","age":14}', function (key, value) {
    if (key === 'name') {
        return value + '同学';
    }
    return value;
});
console.log(JSON.stringify(obj)); // {name: '小明同学', age: 14}
