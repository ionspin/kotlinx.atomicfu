package kotlinx.atomicfu.test

import kotlinx.atomicfu.Trace
import kotlinx.atomicfu.atomic
import org.junit.Test
import kotlin.test.assertEquals

class TraceToStringTest {
    private val trace = Trace()
    private val a = atomic(0, trace)
    private val trace1 = Trace { index, text -> "$index: " }
    private val b = atomic(false, trace1)

    @Test
    fun testTraceToStringAtomicInt() {
        a.compareAndSet(0, 5)
        a.lazySet(99)
        a.compareAndSet(99, 0)
        assertEquals(a.trace.toString(), "1: value CAS from 0 to 5, 2: value set to 99, 3: value CAS from 99 to 0")
    }
}