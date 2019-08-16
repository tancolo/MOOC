# zxing-app-debug
用于调式分析zxing-original-app [ZXing-3.3.3 android](https://github.com/zxing/zxing/releases/tag/zxing-3.3.3)源码

# 目录
## app
zxing-original-app代码更新到该目录
- 添加debug信息用于分析流程
- 添加类似微信扫描框

## colozxing
这是简单版本的QRCode lib， 按照流程从app/ 目录中提取而来。 

包含了Activity入口， 如何打开Camera, 如何解码， 结果如何处理， 以及ViewfinderView的修改

效果类似微信扫描框。

**TODO:**

**1. 最基本的QRCode lib 包含的内容有哪些？** 
- 1D/2D 扫码解析
- QRCode生成 （只提供API）
- 支持生成带logo QRCode
- 提供默认的扫描UI
- 支持自定义扫描UI （是否有必要）
- 添加混淆
- 加入leakcanary 用于检测是否有泄漏
- 性能测试 （待定）
- 单元测试 （待定）
- .. ...

**2. 重构(Java)**
现有的流程复杂以及涉及的类较多， 需要改进
- 设计新的开发架构， 做好顶层设计
- 支持上述的TODOs, 以及支持扩展

**3. 重写(Kotlin)**
- 实现功能如上TODOs
- 引入Android JetPack(是否有必要)
- 引入Android新的Camera API


# zxingtest
测试App， 用于测试colozxing lib 

button click -> start Capture UI -> return result.


# 分析文章
## CSDN专栏
- [Google ZXing系列讲解(一)——导入AS](http://blog.csdn.net/shrimpcolo/article/details/56286094)
- [Google ZXing系列讲解(二)——生成WIFi二维码](http://blog.csdn.net/shrimpcolo/article/details/56494504)
- [Google ZXing系列讲解(三)——ZXing 目录结构与主体流程](http://blog.csdn.net/shrimpcolo/article/details/57402440)
- [Google ZXing系列讲解(四)——ZXing 解决竖屏扫描问题](http://blog.csdn.net/shrimpcolo/article/details/58176308)
- [Google ZXing系列讲解(五)——ZXing 仿微信扫描UI](https://blog.csdn.net/shrimpcolo/article/details/59484615)

## 简书专栏
- [Google ZXing系列讲解(一)——导入AS](https://www.jianshu.com/p/85e0bdb8bd2c)
- [Google ZXing系列讲解(二)——生成WIFi二维码](https://www.jianshu.com/p/656d6f6f862e)
- [Google ZXing系列讲解(三)——ZXing 目录结构与主体流程](https://www.jianshu.com/p/de529919e4e9)
- [Google ZXing系列讲解(四)——ZXing 解决竖屏扫描问题](https://www.jianshu.com/p/b78a967e2ac7)
- [Google ZXing系列讲解(五)——ZXing 仿微信扫描UI](https://www.jianshu.com/p/cbc1239a9f6f)
