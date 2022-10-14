package com.kartik.grevocab

import com.kartik.grevocab.vm.FragmentWordPacketViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

// @RunWith is required only if you use a mix of JUnit3 and JUnit4.
class ExampleInstrumentedTest {

    val vm: FragmentWordPacketViewModel = mockk()

    @Test
    fun test() {
        every { vm.bringNextWord() } returns Unit
        assertEquals(vm.bringNextWord(), Unit)
    }
}
