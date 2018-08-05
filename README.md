# Debugo
Easy to debug your project with annotations

[![](https://jitpack.io/v/morening/Debugo.svg)](https://jitpack.io/#morening/Debugo)


## How to
1. add jitpack repo and classpath like below in project root build.gradle
```
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
        ...
    }

    dependencies {
        ...
        classpath 'com.github.morening.Debugo:debugo-plugin:0.0.6'
    }
}

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        ...
    }
}
```

2. add debugo plugin like below in module build.gradle
```
apply plugin: 'debugo'
```
