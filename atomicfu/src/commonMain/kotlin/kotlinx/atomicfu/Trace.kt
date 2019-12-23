@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package kotlinx.atomicfu

import kotlin.js.JsName
import kotlin.internal.InlineOnly

/**
 * Creates `Trace` object for tracing atomic operations.
 *
 * ### Usage
 *
 * Create a separate field for `Trace`:
 *
 * ```
 * val trace = Trace(size)
 * ```
 *
 * Using it to add trace messages:
 *
 * ```
 * trace { "Doing something" }
 * ```
 *
 * Pass it to `atomic` constructor to automatically trace all modifications of the corresponding field:
 *
 * ```
 * val a = atomic(initialValue, trace)
 * ```
 */
public expect fun Trace(size: Int = 32, format: (Int, String) -> String = { index, text -> "$index: $text" } ): Trace

/**
 * Trace implementation.
 */
public sealed class Trace {
    @JsName("atomicfu\$Trace\$append\$")
    @PublishedApi
    internal open fun append(text: String) {}

    @InlineOnly
    public inline operator fun invoke(text: () -> String) {
        append(text())
    }

    /**
     * NOP tracing.
     */
    public object None : Trace()
}

public class TraceImpl(size: Int, val format: (Int, String) -> String) : Trace() {
    init { require(size >= 1) }
    private val size = ((size shl 1) - 1).takeHighestOneBit() // next power of 2
    private val mask = this.size - 1
    private val trace = arrayOfNulls<String>(this.size)
    private var index = atomic(0)

    override fun append(text: String) {
        val i = index.getAndIncrement()
        trace[i and mask] = format(index.value, text)
    }
}