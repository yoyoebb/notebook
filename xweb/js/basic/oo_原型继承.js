// 由于JS没有class的概念，对象继承实现也就比较奇葩

// 假设先定义了构造函数Student
function Student(props) {
    this.name = props.name || 'Unnamed';
}
Student.prototype.hello = function () {
    alert('Hello, ' + this.name + '!');
}   // 原型链:  new Student --> Student.prototype --> Object.prototype --> null

// 现在要基于Student扩展出PrimaryStudent，可以先定义出PrimaryStudent：
function PrimaryStudent(props) {
    // 调用Student构造函数，绑定this变量:
    Student.call(this, props);             // 这句话会让new PrimaryStudent对象拥有name属性，即 hasOwnProperty("name") === true
    this.grade = props.grade || 1;
}   // 原型链:  new PrimaryStudent --> PrimaryStudent.prototype --> Object.prototype --> null

// 要想实现继承，我们要想办法做到  new PrimaryStudent --> PrimaryStudent.prototype --> Student.prototype --> Object.prototype --> null
// 直接 PrimaryStudent.prototype = Student.prototype; 肯定是不行的
// 我们必须借助一个中间对象来实现正确的原型链，这个中间对象的原型要指向Student.prototype。
// 为了实现这一点，参考道爷（就是发明JSON的那个道格拉斯）的代码，中间对象可以用一个空函数F来实现：

// 空函数F:
function F() {
}
// 把F的原型指向Student.prototype:
F.prototype = Student.prototype;
// 把PrimaryStudent的原型指向一个新的F对象，F对象的原型正好指向Student.prototype:
PrimaryStudent.prototype = new F();
// 把PrimaryStudent原型的构造函数修复为PrimaryStudent:
PrimaryStudent.prototype.constructor = PrimaryStudent;

// 创建xiaoming:
var xiaoming = new PrimaryStudent({
    name: '小明',
    grade: 2
});  // 原型链:  xiaoming --> PrimaryStudent.prototype === new F() --> F.prototype === Student.prototype --> Object.prototype --> null


//如果把继承这个动作用一个inherits()函数封装起来，还可以隐藏F的定义，并简化代码：
function inherits(Child, Parent) {
    var F = function () {};
    F.prototype = Parent.prototype;
    Child.prototype = new F();
    Child.prototype.constructor = Child;
}