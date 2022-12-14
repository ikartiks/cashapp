package com.kartik.grevocab

/**
 * Annotate a class with [OpenForTesting] if you want it to be extendable in debug builds.
 */
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting

// Forked from https://github.com/googlesamples/android-architecture-components/blob/d81da2cb1e3d61e40f052e631bb15883d0f9f637/GithubBrowserSample/app/src/debug/java/com/android/example/github/testing/OpenForTesting.kt
