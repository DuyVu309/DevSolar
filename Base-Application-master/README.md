# Base Application
Simple Base Class For Application
## Feature
### This library use data binding to create:
* Base Activity
* Base Fragment
* Base Model and Media Model
* Base Adapter for simple Recycler View
### Other features:
* Interface Ripple Click for view
## Getting Started
* This library use AndroidX and Kotlin, so you need Kotlin and migrate your project to AndroidX
* Add maven in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
* Add the dependency to file build.gradle(Module:app):
```
implementation 'com.github.ngtien137:Base-Application:TAG'
```
TAG is the version of library. If you don't know, remove it with +
* Because of using Base Adapter and it's created from RecyclerView.Adapter. So you need RecyclerView too.
Google Material contains it:
```
implementation 'com.google.android.material:material:+'
```
* Enable Data binding in build.gradle(Module:app) with plugin: kotlin-kapt
```
apply plugin: 'kotlin-kapt' //Add this to the beginning of the file
android {
    ...
    dataBinding {
        enabled = true
    }
}
```
