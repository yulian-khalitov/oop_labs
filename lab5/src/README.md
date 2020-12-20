## Gradle Setup

```kotlin
plugins {
    // ...
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.4.10'
}

dependencies {
    // ...
    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1'
}
```
## New Shape Implementation
* add NewShape class to shapes package
* add subclass(NewShape::class) [here](https://github.com/yulian-khalitov/oop_labs/blob/master/lab5/src/main.kt#L14)

