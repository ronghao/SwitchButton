# SwitchButton
类似ios效果的滑动按钮

# 示例
![例子](docs/show.gif =200x)


# Gradle配置
> repositories {<br>
&nbsp;&nbsp;&nbsp;&nbsp;maven {<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;url 'https://dl.bintray.com/haohao/maven/'<br>
&nbsp;&nbsp;&nbsp;&nbsp;}<br>
}<br><br>
...<br><br>
dependencies {<br>
&nbsp;&nbsp;&nbsp;&nbsp;compile 'com.haohao:switchbutton:1.0.1'<br>
}<br>

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
+ switchbutton:switchbackground 背景图片
  + ![例子](docs/switch_bg.png)
+ switchbutton:switchcursor 游标图片
  + ![例子](docs/switch_white.png)
