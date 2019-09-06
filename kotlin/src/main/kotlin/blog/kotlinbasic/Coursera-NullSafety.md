# Kotlin 常用点之-Null Safety

## 前言
要想快速熟悉Kotlin， 看文档或者把文档中的示例代码敲一遍， 有可能效果不是很好。 
这里推荐官方的[Koans](https://play.kotlinlang.org/koans/overview) 
和 [Example](https://play.kotlinlang.org/byExample/overview)
- Koans 侧重实战， 题目给的是Java实现，如何用Kotlin的知识点完成
- Example 这个学习模块 偏向讲解，内容更多些，也提供在线的Kotlin环境来练习

本篇主题需要参考: 
- [Null Safety 英文](https://kotlinlang.org/docs/reference/null-safety.html)
- [Null Safety 中文](https://www.kotlincn.net/docs/reference/null-safety.html)


## Null Safety Sample 
从事Java开发, 一个不能忽视的问题就是NPE (NullPointerException), 俗话说 "常在河边走，哪有不湿鞋"。

先来看看Koans上的一个题目： [Nullable types](https://play.kotlinlang.org/koans/Introduction/Nullable%20types/Task.kt)
```
public void sendMessageToClient(
    @Nullable Client client,
    @Nullable String message,
    @NotNull Mailer mailer
) {
    if (client == null || message == null) return;
​
    PersonalInfo personalInfo = client.getPersonalInfo();
    if (personalInfo == null) return;
​
    String email = personalInfo.getEmail();
    if (email == null) return;
​
    mailer.sendMessage(email, message);
}
```

在实际开发中经常会写这样的代码， 老实说 这段代码很漂亮的, 基于几点
1. 参数有Nullable注解， 所以要有非空判断
2. 方法中获取的实例也是Nullable，所以还是要有非空判断
3. 提前return, 没有基于 if .. else ..else if ...

按照这个小练习的目的 
>  ...rewrite the following Java code using only one if expression:

以及给出的 show answer：
```kotlin
val email = client?.personalInfo?.email
    if (email != null && message != null) {
        mailer.sendMessage(email, message)
    }
```

这里，我也给出我当时做练习的答案:
```kotlin
fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    if (message == null) return

    client?.personalInfo?.email?.let { mailer.sendMessage(it, message) }
}
```

## Null Safety(空安全)
并不是说编写Kotlin就不用考虑NPE， 想怎么写，就怎么来了，并不是！
只是说Kotlin较Java针对NPE这个有更多的思考
1. 从语法层面就有 val, var区分， 针对non-null & nullable
如： 
`var nullableVariable: PersionInfo? = null` ==> OK
`val nullableVariable: PersionInfo? = null` ==> IDE就会红色提醒

2. 从编写判断来说，比java方便了
当要使用上述变量 `nullableVariable`, 传统方式肯定是
```
if (nullableVariable != null) {
    println(nullableVariable.toString())
}
```
那Kotlin呢？ 只需要这样:
```kotlin
nullableVariable?.let{
    println(it.toString())
}
```

**这样我们就记住了第一个符号: "?" 以及 "?.", 还有顺带的接触了`let`关键词**
**?** 我觉得是非常的形象， 也直接了当。

安全调用在链式调用中很有用, 也很有意思。
例如，如果一个员工 Bob 可能会（或者不会）分配给一个部门， 并且可能有另外一个员工是该部门的负责人，那么获取 Bob 所在部门负责人（如果有的话）的名字，我们写作：
```kotlin
bob?.department?.head?.name
```

## Elvis 操作符
Kotlin 是没有这样语法的 `? :` （xxx条件 ? xxx : xxx）, 举个栗子:
```kotlin
    val a = 1
    val b = 10
    val c = a > b ? a : b
```
IDE 直接报错!!!

但是 Kotlin 有Elvis操作符 (PS： Elvis 翻译过来是"猫王", 知道为什么不翻译成中文了吧)

Elvis 操作符 如下： **?:**

来看栗子: 当我们有一个可空的引用 r 时，我们可以说“如果 r 非空，我使用它；否则使用某个非空的值 x”
- Java式的写法
```kotlin
val l: Int = if (b != null) b.length else -1
``` 
- More kotlin写法
```kotlin
val l = b?.length ?: -1
```


## 实际项目中的使用
这是执行`onActivityResult`的例子
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
if (requestCode == XXXX_CODE) {
            if (resultCode == RESULT_OK) {
                intent?.data?.host?.takeIf { isValid(it) }?.run {
                    XXXViewModel.XXX.value = this
                }
            }
        }
}
```
有同学肯定会说 "你怎么用if, 而且还2个 if", 确实，代码是可以改进的，Kotlin并没有排斥使用 if.. else 之类的条件语法。
这里的重点是实际项目中使用 **Null Safety**非常的多， 而且可以结合 let, run, apply, also 形成链式调用。 

## 其他
- 安全的类型转换
- !! 操作符

想了解，花一分钟去看看文档吧!!!

别走开，下篇接着来~
 
 

