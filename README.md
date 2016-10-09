# SwitchButton
类似ios效果的滑动按钮,支持自定义背景，动画效果，渐变效果

# 示例
![例子](docs/show.gif)


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
> + 自定义背景图片：  *switchbutton:switchbackground*
+  自定义图片： *switchbutton:switchcursor*

# 图片
> + **背景图片**   ![例子](docs/switch_bg.png)
+  **游标图片(图片纯白做了特殊处理)**  ![例子](docs/switch_white_1.png)
