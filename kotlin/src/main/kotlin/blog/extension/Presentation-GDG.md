# 用心写Kotlin代码---链式调用

参考 SFP (Functional Programming With Stream in Kotlin)


大纲草稿

1. 兔子和我
图片，兔子， 我的图片引入
兔子问题： 图片，一对兔子， 一年生多少兔子, ==》 形式： 图片，效果： duangduang

2. 看视频
Kotlin跟Java开发比较
google I/O 大会 Kotlin的视频截取， 就是比较java & Kotlin 编写java bean的.. 非常深刻印象
https://www.youtube.com/watch?v=X1RVYt2QKQE ==> 

# 为什么要选这个主题
上手Kotlin不难，有编程语言基础，特别是Java基础。 基本语法对照官方参考文档过一遍就基本了解了。
但是，在我们实际开发中，还是出现了一个有趣的现象（包括我在内）， 用java的思路来写Kotlin.
举个例子：
判断语句， 分支语句，传统的是if .. else if, else if ... else ...
在我前期开发Kotlin中就是这么用的，但是Kotlin中提供了其他的具有Kotlin风格的方式,
```
when (xxx) {
    condition1 -> {xxx}
    condition2 -> {xxx}
    ...
    else -> {xxxx}
}
`
或者 
```
when {
    condition1 -> {xxx}
    condition2 -> {xxx}
    ...
}
```

特别是Kotlin提供的链式调用特别的好，但是我们一开始都不会去关注这些。
我会举一些有趣的例子，用java & Kotlin同样实现，给听众一个直观的感受， Kotlin确实比Java简洁，让听众愿意去尝试Kotlin.

# 大纲
## 1. Kotlin的简介 ==》 是否有必要

## 2. 从某个问题引出今天的主题

## 3. 针对某个问题的实现 对比 Java & Kotlin代码， 突出我们在工作中要尽可能的用Kotlin的风格来写代码，而不是用之前的思路来用Kotlin.

## 4. 考虑是否要说明 Kotlin链式调用的实现， 要讲清楚，估计要涉及高阶函数，lambda表达式，所以是否有必要？

## 5. 结语，把时间浪费在美好的事情上,Kotlin就是这样的事情。
```





















分享主题：用心写Kotlin代码---Kotlin链式调用
通过一些例子来分享如何更优雅的书写 Kotlin，让更多的开发者关注Kotlin、实践Kotlin、喜欢上Kotlin

檀海勤，AXEL Android 工程师，Kotlin 技术爱好者，多年 Android 开发经验，现在从事Kotlin Android开发。



====end===