// js中没有class的概念，所有的数据都可以看成是对象，所谓继承关系也只不过是让A对象的原型(__proto__)指向另一个对象而已

// 所有的{...}定义的对象，其__proto__都指向一个Object对象(Object.prototype)
var student = {
    name: 'Robot',
    height: 1.2,
    run: function () {
        console.log(this.name + ' is running...');
    }
};                                 // student-->Object.prototype-->null

// array的原型链
var arr = [1, 2, 3];              // arr-->Array.prototype-->Object.prototype-->null

// function的原型链
function test(){
    1==1;
};                                // test-->Function.prototype-->Object.prototype-->null


// 除了这些系统默认的原型链外，有以下方法可以让我们调整对象A的原型链：
// 1.(不推荐)直接重定向__proto__属性(在编写JS代码时，不要直接用obj.__proto__去改变一个对象的原型，并且，低版本的IE也无法使用__proto__)
var xiaoming = {
    name: '小明'
};
xiaoming.__proto__ = student;     // xiaoming-->student-->Object.prototype-->null

// 2. 编写一个工厂方法，利用Object.create()方法创建一个基于该原型的新对象
function createStudent(name) {
    // 基于Student原型创建一个新对象:
    var s = Object.create(Student);
    // 初始化新对象:
    s.name = name;
    return s;
}
var xiaohong = createStudent('小红');
xiaohong.__proto__ === Student;            // true

// 3. (推荐)通过构造函数来创建对象，这也是系统的默认做法。构造函数通常以大写字母开头，并且没有return语句，在 new Foo() 时会默认返回所创建的对象
function Student(name) {
    this.name = name;
    this.hello = function () {
        alert('Hello, ' + this.name + '!');
    }
    // 所有的函数都有一个prototype属性，指向一个特殊的对象;
    // foo.prototype = {
    //                   constructor :   指向Function Student
    //                   __proto__   :   指向Object.prototype
    //                  }
}

// 通过new 操作符，我们可以创建一个基于foo.prototype为原型的对象
var stu1 = new Student("stu1");         // stu1--> Student.prototype --> Object.prototype --> null
var stu2 = new Student("stu2");         // stu2--> Student.prototype --> Object.prototype --> null
var stu3 = new Student("stu3");         // stu3--> Student.prototype --> Object.prototype --> null



// 第3点的做法中还有一个问题:方法hello是定义在构造函数中，这种方法是对象级别的，每个对象都会有，造成资源浪费。
stu1.name;                     // "stu1"
stu2.name;                     // "stu2"
stu1.hello === stu2.hello;     // false
// 解决方案有几种
// 方法一：混合的构造函数/原型方法，方法定义在prototype上(推荐)
function Car(sColor, iDoors, iMpg){
    this.color = sColor;
    this.doors = iDoors;
    this.mpg = iMpg;
}

Car.prototype.showColor = function(){
    //...;
}

// 方法二：动态原型方法
function Car2(sColor, iDoors, iMpg){
    this.color = sColor;
    this.doors = iDoors;
    this.mpg = iMpg;

    if(typeof Car2._initialized == "undefined"){
        Car2.prototype.showColor = function(){};
        Car2._initialized = true;
    }
}