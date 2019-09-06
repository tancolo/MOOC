# Kotlin 常用点之-Extension

## 前言
要想快速熟悉Kotlin， 看文档或者把文档中的示例代码敲一遍， 有可能效果不是很好。 
这里推荐官方的[Koans](https://play.kotlinlang.org/koans/overview) 
和 [Example](https://play.kotlinlang.org/byExample/overview)
- Koans 侧重实战， 题目给的是Java实现，如何用Kotlin的知识点完成
- Example 这个学习模块 偏向讲解，内容更多些，也提供在线的Kotlin环境来练习

本篇主题需要参考: 
- [Extension 英文](https://kotlinlang.org/docs/reference/extensions.html)
- [Extension 中文](https://www.kotlincn.net/docs/reference/extensions.html)


## 扩展方式和目的
> Kotlin 能够扩展一个类的新功能而无需继承该类或者使用像装饰者这样的设计模式。 这通过叫做 扩展 的特殊声明完成。 
>For example, you can write new functions for a class from a third-party library that you can't modify. 
>Such functions are available for calling in the usual way as if they were methods of the original class. 
>This mechanism is called extension functions. There are also extension properties that let you define new properties 
>for existing classes.

官方文档的说法 清晰明白。 不仅仅有扩展方法，而且有扩展属性!

## 扩展的组织形式 
现看个栗子, [扩展函数](https://play.kotlinlang.org/koans/Introduction/Extension%20functions/Task.kt) 
> Implement extension functions Int.r() and Pair.r() and make them convert Int and Pair to RationalNumber.

```kotlin
data class RationalNumber(val numerator: Int, val denominator: Int)

fun Int.r(): RationalNumber = TODO()
fun Pair<Int, Int>.r(): RationalNumber = TODO()
```
答案自行写出。

扩展函数形式: 
> fun 扩展的类.函数名(参数列表...) {
}

> fun 扩展的类.函数名(参数列表...) = ......

## 实际项目中的使用
先来看看扩展函数在我们项目中的使用情况吧。
```kotlin
├── extension
│   ├── ActivityExt.kt
│   ├── CommonExt.kt
│   ├── ContextExt.kt
│   ├── DateExt.kt
│   ├── LiveDataExt.kt
│   ├── MapExt.kt
│   ├── StringExt.kt
│   ├── SystemServicesExt.kt
│   ├── ToastExt.kt
│   └── ViewExt.kt
```
实际项目中会有各种的需求， 针对**Date, String, View, Fragment, Activity...** 集中在一个包中，方便管理。

## 其他
- 扩展是静态解析的 (扩展的局限性) 
- 可空接收者
- 扩展属性
- 扩展的作用域
... ...

想了解，花10分钟去看看文档吧!!!

别走开，下篇接着来~


