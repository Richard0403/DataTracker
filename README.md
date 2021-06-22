# DataTracker

#### 介绍

Android 数据曝光、打点工具

**支持的控件** 普通View,  ViewPager, RecyclerView, Fragment，View各种嵌套

**支持的操作：** 打点开关，点击打点，数据曝光打点，曝光时间限制，可见区域限制， 实时/批量上传， 点击/曝光百分比采样

#### 安装教程

[![](https://jitpack.io/v/Richard0403/DataTracker.svg)](https://jitpack.io/#Richard0403/DataTracker)

project下的build.gradle 中添加

```
 maven { url 'https://jitpack.io' }
```
module下的build.gradle添加

```
implementation'com.github.Richard0403:DataTracker:latest
```

#### 使用说明
1. 初始化， 在App中

	```kotlin
	 TrackerManager.get().initTracker(
	            this,
	            trackerOpen = true,
	            trackerExposureOpen = true,
	            logOpen = true)
	```
2. 给view设置唯一标志, 此处选用了view的hashCode作为view唯一的value值

	```
	private fun setUniqueNameTag (view: View) {
        view.setTag(TrackerConstants.VIEW_TAG_UNIQUE_NAME, view.hashCode().toString())
    }
	```
3. 给view设置需要上传的参数

	```
	val exposureData = mutableMapOf<String, Any?>()
	view.setTag(tagType, exposureData)
	```
 	**tagType说明：**
       * 若为点击数据，使用TrackerConstants.TAG_CLICK_DATA
       * 若为曝光数据，使用TrackerConstants.TAG_EXPLORE_DATA
       * 若点击和曝光数据为同一个，则使用TrackerConstants.TAG_EXPLORE_AND_CLICK_DATA

4. 参数配置说明

	详见GlobalConfig参数配置的注释
