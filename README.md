# SwitchButton [![](https://jitpack.io/v/ronghao/FrameAnimationView.svg)](https://jitpack.io/#ronghao/SwitchButton) [![](https://travis-ci.org/ronghao/SwitchButton.svg?branch=master)](https://travis-ci.org/ronghao/SwitchButton)  [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/ronghao/CacheManage/master/LICENSE) [ ![Download](https://api.bintray.com/packages/haohao/maven/SwitchButton/images/download.svg?version=1.0.0) ](https://bintray.com/haohao/maven/SwitchButton/1.0.0/link)
类似ios效果的滑动按钮,支持自定义背景，动画效果，渐变效果

# 示例
![例子](docs/show.gif)

# 功能
+ 可自定义背景和游标图片
+ 动画效果：滑动动画，颜色渐变
+ 背景变灰
+ 可支持手势滑动一半退回效果


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
        switchbutton:switchbackground="@drawable/switch_bg"
        switchbutton:switchcursor="@drawable/switch_white" />


# Attributes
	<attr name="switchbackground" format="reference"/>       自定义背景图片
	<attr name="switchcursor" format="reference"/>           自定义图片

# 方法简介
|||
|-|-|
|setStatus|设置开启关闭|
|setStatusImmediately|设置开启关闭(无动画)|
|setOnSwitchChangeListener|设置滑动开关监听|

# 图片示例
|名称|图片样式|
|-|-|
|背景图片|![例子](docs/switch_bg_1.png)|
|游标图片(图片纯白做了特殊处理)|![例子](docs/switch_white.png)|
 


# 关于
+ 个人博客：[www.haohaohu.com](http://www.haohaohu.com/)
+ 如果你也喜欢这个库，Star一下吧，欢迎Fork

# License

    Copyright 2016 haohaohu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.