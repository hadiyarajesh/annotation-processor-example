# Annotation Processor Example

This is a demo project to showcase how to write your own annotation and
its [KSP (Kotlin Symbol Processing)](https://github.com/google/ksp) processor to generate binding
modules for Hilt automatically.

## ✨ What It Does

With just one annotation on your implementation class:

```kotlin
@GenerateBinding(type = MyRepository::class)
class MyRepositoryImpl @Inject constructor() : MyRepository
```

It generates this for you automatically:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class MyRepositoryModule {

   @Binds
   abstract fun bindMyRepository(impl: MyRepositoryImpl): MyRepository
}
```

## 📦 How It Works

1. `@GenerateBinding` annotation accepts a type parameter.
2. A KSP processor reads all classes annotated with `@GenerateBinding`.
3. It generates an abstract Hilt module that binds the implementation to its interface using
   `@Binds`.

## Contribution

Contributions to this project are welcome! If you encounter any problems or have suggestions for
improvement, feel free to submit a pull request or open an issue.
