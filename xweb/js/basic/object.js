// js 对象用{}表示，其实质是key-value的无序集合，key为字符串，value为任意类型(包括函数)
var xiaohong = {
    name: '小红',
    'middle-school': 'No.1 Middle School'
};
xiaohong.name;                        // 访问有效变量名可以用obj.key
xiaohong['middle-school'];            // 访问无效变量名可以用obj['key']
xiaohong.age;                         // 访问不存在的key，返回undefined

// 可以任意添加、删除对象的属性
xiaohong.age = 18;
delete xiaohong.age;                 // 删除属性，即使属性不存在也不会报错

// 用 in 检测对象是否拥有某个属性 (包含继承父类的属性)
// 用 hasOwnProperty() 来判断对象是否拥有某个属性 (就是不会往上追溯原型链)
'name' in xiaohong;                   // true
'grade' in xiaohong;                  // false
'toString' in xiaohong;               // true, 继承自object
xiaohong.hasOwnProperty('toString');  // false

// 用 for...in 可以遍历对象的属性
for (var key in xiaohong) {
    if (xiaohong.hasOwnProperty(key)) {
        console.log(key);             // 'name', 'age', 'city'
    }
}