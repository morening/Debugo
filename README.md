# Debugo
Easy to debug your project with annotations

[![Build Status](https://travis-ci.org/morening/Debugo.svg?branch=master)](https://travis-ci.org/morening/Debugo)
[![](https://jitpack.io/v/morening/Debugo.svg)](https://jitpack.io/#morening/Debugo)
[ ![Download](https://api.bintray.com/packages/morening/maven/Debugo/images/download.svg) ](https://bintray.com/morening/maven/Debugo/_latestVersion)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)


## How to
1. Add jitpack repo and classpath like below in project root build.gradle
```
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
        ...
    }

    dependencies {
        ...
        classpath 'com.github.morening.Debugo:debugo-plugin:0.0.25'
    }
}

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        ...
    }
}
```

2. Add debugo plugin like below in module build.gradle
```
apply plugin: 'debugo'
```

3. Add @Debugo above the target like below
```java
// for Class/Interface
@Debugo
public class MainActivity extends Activity {
    ...
}
```

or

```java
// for Method
@Override
@Debugo
protected void onCreate(Bundle savedInstanceState) {
    ...
}
```

or

```java
// for Constructor
@Debugo
public Staff(String name, int age){
    this.name = name;
    this.age = age;
}
```
