# SwitchButton [![](https://jitpack.io/v/ronghao/FrameAnimationView.svg)](https://jitpack.io/#ronghao/SwitchButton) [![](https://travis-ci.org/ronghao/SwitchButton.svg?branch=master)](https://travis-ci.org/ronghao/SwitchButton)  [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/ronghao/CacheManage/master/LICENSE) [ ![Download](https://api.bintray.com/packages/haohao/maven/SwitchButton/images/download.svg?version=1.0.0) ](https://bintray.com/haohao/maven/SwitchButton/1.0.0/link)
类似ios效果的滑动按钮,支持自定义背景，动画效果，渐变效果

# 示例
![例子](docs/show.gif)


# 项目添加方法
在根 build.gradle中添加

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
在项目build.gradle中添加

	dependencies {
	    compile 'com.github.ronghao:SwitchButton:1.0.0'
	}

# XML代码
    <com.haohao.switchbutton.SwitchButton
        android:id="@+id/switchbutton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginTop="20dp"
        switchbutton:switchbackground="@drawable/switch_bg"
        switchbutton:switchcursor="@drawable/switch_white" />

# XML代码
+ 自定义背景图片：  *switchbutton:switchbackground*
+  自定义图片： *switchbutton:switchcursor*

# 图片
+ **背景图片**   ![例子](docs/switch_bg.png)
+  **游标图片(图片纯白做了特殊处理)**  ![例子](docs/switch_white.png)

# 关于
+ 个人博客：[www.haohaohu.com](http://www.haohaohu.com/)
+ 如果你也喜欢这个库，Star一下吧，欢迎Fork