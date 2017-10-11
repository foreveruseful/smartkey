# smartkey
A smarter and more elegant way to manage your keys in android Intent and SharedPreferences and more.

# NOTICE: The document(or tutorial) for this SmartKey hasn't been finished yet, it would come out soon.
# 注意：关于SmartKey的文档（教程）正在编写整理中，请稍候。
# 中文相关博客文章
> 1. [Android下如何优雅流畅地写代码](http://www.jianshu.com/p/55cc632b506f)
>
> 2. [原来Activity跳转还可以这么舒畅](http://www.jianshu.com/p/c359d72b5231)
>
> 3. [如何优雅地管理SharedPreferences](http://www.jianshu.com/p/bd864f5baa4e)

1. Demo on simple and easy databinding.
> The demo app is kind of a very easy and simple databinding demo.

2. Demo on a friendlier toast.
> Check out ToastUtil and try to toast something for several times simultaneously you'll know what I mean.

3. Managing keys when using Intent.
> How to elegantly manage the keys involved? The answer is do not manage it yourself and leave it to the system, and cares only about your business.

4. Managing keys when using SharedPreferences.
> How to elegantly manage the keys involved? The answer is also the same: do not manage it yourself and leave it to the system, and cares only about your business.

5. Managing how to start an activity or service.
> Generating a SmartTargets class to manage it.

As android-apt comes to an end, we upgraded our settings to annotationProcessor
## How to use all these?
1. In your root project's build.gradle, add the following
At buildscript -> in allprojects -> repositories:
```gradle
maven {url 'https://jitpack.io' }
```

2. In your module's build.gradle, add the following:
Add dependencies:
```gradle
// please note the available history version is 0.1.1, 0.1.5, 0.1.6, now 0.1.7
compile 'com.github.foreveruseful.smartkey:annotation:0.1.7'
compile 'com.github.foreveruseful.smartkey:sdks:0.1.7'
annotationProcessor 'com.github.foreveruseful.smartkey:apt:0.1.7'
```

3. Rebuild your project.
Check out the app module as an example.
