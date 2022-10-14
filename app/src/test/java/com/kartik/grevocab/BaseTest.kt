package com.kartik.grevocab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest

@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
open class BaseTest : KoinTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // this works
    // or TestCoroutineDispatcher although its deprecated
    // do not use StandardTestDispatcher - tests fail
    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setupThreads() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDownThreads() {
        Dispatchers.resetMain()
    }
}
