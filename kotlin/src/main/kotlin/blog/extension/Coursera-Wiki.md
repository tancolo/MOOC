# Kotlin工程实践专栏

# 作者简介

# 专栏特点
1. 讲解项目中常用的Kotlin知识点
2. 讲解项目中使用过的Jetpack组件，以及遇到的坑
3. 用小项目将知识点以及组件使用起来
4. 探讨如何对App选用成熟的开发框架

所以，本专栏围绕3个部分展开：
1. 将我在项目中使用到Kotlin的知识点梳理一遍， 聪明的你肯定也猜到了会有哪些的内容。
但请注意，不会去介绍val,var是什么， 条件语句怎么写之类的, 那是浪费时间而已。

2. 讲解Jetpack组件内容，确实是给自己挖坑，从我现在实践到的，内容超级多。
给自己一个挑战，看能完成多少。 尽可能的把在项目中使用的Jetpack组件，在网络上找到对应的sample。

3. 探讨如何构建App开发架构，结合我参与的项目情况来聊聊这个话题。


# 专栏大纲

## 发刊词和导论
了解关注Kotlin还要追溯到2016年，当时是经过朋友说, "嘿， 最近有个新的语言出来了，叫Kotlin, 这个有意思"
1. 语法简洁， 支持Lambda
3. 支持函数链式调用
2. 兼容Java， 实现同样的功能代码量少

记得当时我的反应是： "是吗？ Java挺好的啊"。 那时正好是里约奥运会，然后尝试用Kotlin写了个简单的代码，针对奖牌榜进行排序并打印的例子， 
链式调用，一气呵成。 后续考虑在讲解Kotlin的时候加入一些小例子， 读者不妨一起来玩玩，动下小脑筋。

对Kotlin的第一亲密接触就这样over了，后续还是停留在写Demo阶段，并没有在实际项目中实践。 接手的项目都是Java, 能做的也就是用MVP模式改造重写。
在现在的公司，经历了2个项目， 一个是在Play Store有着100M+下载量[AXEL](https://play.google.com/store/apps/details?id=com.stoamigo.storage&hl=en&showAllReviews=true);
另一个是正在开发的Wallet项目，Kotlin为主 Java为辅。 完整的经历了一个Kotlin开发。
我们是个小型的国际化团队，小组成员有国内的，国外的，有在家办公的， code view非常严格， 有时候是几乎重写自己提交的代码。
Why? 按照Leader以及reviewer话说，用更多的Kotlin,而不是Java； 代码风格要一致。

Kotlin只是语言, 实际开发还是要采用分层模式， 采用了很多[JetPack](https://developer.android.com/jetpack)项目中的Components.
https://prnt.sc/ogn08e  红框标记是在项目中使用，还有其他的Components.

在使用过程中确实遇到了一些不适应，举几个简单的例子。
- Q1 用传统的方式去启动一个Activity或者Fragment不是挺好的吗? 干吗要用Navigation哦?
- Q2 用MVP模式就很不错阿，干吗要用MVVM呢？
- Q3 不就是接收个网络数据吗？ 用一个xxxEntity搞定不就行了，何必用三层呢？ （data / domain / ui）    

当我们实践过传统方式以及Google提供给我们新的开发方法，就会有心得了。
简单的讲， 
- A1 Navigation 可以保持所有的Activity / fragment 在一个xml中，而且还有图形化的界面， 方便维护。

- A2 选用MVP / MVVM 本身没什么好讨论的， 都可以。 但是因为我们要基于Jetpack其他的组件，所以就选择了MVVM方式。
这就好比，你进入了五星级酒店，啥都给你准备好了，而且还免费，你还需要自己携带酒店里已有的东西吗？ 

- A3 要是遇到后台经常改动返回的数据，你就知道做到不变应万变的好处了。

千里之行，始于足下。 
> 美好的事情，即将发生


## 大纲
现阶段还是仅限于总结自己实践的内容。 此处应该有 类似得到app那样的课程doc
*备注： 以下内容为不完全目录，部分专题可能会有更新和调整*

### Kotlin重点内容
- Null Safety
- Extension
- Data Class
- Type Aliases
- Function and Lambdas
- Coroutines

### Android App架构
参考图形 [Android App Architecture](../../blogmaterial/android-app-final-architecture.png)
说明如下:
1. Activity / Fragment  就是UI显示层 VIEW
2. Repository "数据驱动层"  Model
3. ViewModel  View-Model层


### Jetpact组件
Jetpact组件太丰富了， 只能对一些主要的组件进行分享， 大概的内容会包含 https://prnt.sc/ogn08e




