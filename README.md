## 手写帝国
一款手写汉字打怪类游戏，使用Unity结合Android Studio开发(此为Android部分)
## IDE信息
Unity 2017.3.0f3(64bit)   
Android Studio 3.1
## 描述
手写识别功能集成在hwrlib模块，可以通过Build --> MakeMoudule生成arr包然后在Unity中工程中使用
注意事项: 生成arr包需要做一些修改
1. 用压缩软件打开arr包，讲根目录下的unity.class拖动电脑上，然后删除压缩包内该文件
2. 打开lib文件夹，删除里面的unity.class文件，讲刚才拖动电脑上的文件在拖到此文件夹内
3. 在Unity工程中创建对应的文件夹结构: Assets --> Plugins --> Android，然后将刚才的文件夹复制到此处，之后Unity就可以调用该包中的Android方法（注意，该内容只在Android平台上有效，所以在调用方法的时候需要判断平台，否则会报错）
