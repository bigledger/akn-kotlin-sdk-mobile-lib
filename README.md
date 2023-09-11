# AKAUN Kotlin Mobile Library SDK #
[![](https://jitpack.io/v/bigledger/akn-kotlin-sdk-mobile-lib.svg)](https://jitpack.io/#bigledger/akn-kotlin-sdk-mobile-lib)

A a shared UI library that would be used by various other mobile apps, all reusable UI components are be shared in this library. Examples would be the login screen, the standardisation of the logos, the user profile, the pagination, listing, tables like AG-Grid etc. This mobile library may depend on the “com.akaun.kt.sdk.*”, but not the other way round.


## Gradle
To get a Git project into your build:

Add the dependency below into your **module**'s `build.gradle` file:

### Kotlin

```gradle
dependencies {
    implementation("com.github.bigledger:akn-kotlin-sdk-mobile-lib:Tag")
}
```

### Groovy

```gradle
dependencies {
    implementation 'com.github.bigledger:akn-kotlin-sdk-mobile-lib:Tag'
}
```

Make sure to add this into the settings.gradle file of your project:

### Kotlin

```gradle
allprojects {
  repositories {
    ...
    maven("https://www.jitpack.io")
  }
}
```

### Groovy

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
