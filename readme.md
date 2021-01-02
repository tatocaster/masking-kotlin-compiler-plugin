Masking: Kotlin Compiler Plugin (Not Finished)
---
Exploring Kotlin compiler plugins. This is just an experiment.

This plugin outputs the additional text for each called method with an annotation
The annotation could be default "Masking" or any user provided as a gradle parameter.
When nothing is provided, compiler uses default which will be added project via Gradle sub-plugin

This whole thing is inspired by this talk and some parts of the code are from the sample
([video](https://youtu.be/w-GMlaziIyo)), kudos Kevin! 

TODO
---
Compiler also need to mask the user input for example by rewriting `toString` bytecode.
Asterisks is an optional paramter and user can change it
```Kotlin
data class User(
    val name: String,
    @Masking val password: String
)
println(User("First Name", "PassW0rD"))
```
should output
```
User(name=First Name, password=****)
```

Usage
---
Plugin is not published anywhere you can just pull this repo and run it. 
For now the root gradle file substitutes remote modules by local modules. 

project `build.gradle`:

```groovy
buildscript {
  dependencies {
      classpath "me.tatocaster.masking:masking-gradle-plugin:0.5.0"
  }
}
```

module `build.gradle`

```groovy
apply plugin: "me.tatocaster.masking.gradle.plugin"

maskingGradlePlugin {
    enabled = true // required
    mask = "*" // optional, not working for now
//    maskingAnnotation = "me.tatocaster.masking.sample.CustomMasking"
    // if not specified then use "Masking" default annotation
}
```
and of course 
__Needs tests__