@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package kotlinx.atomicfu

import kotlin.internal.InlineOnly

@Suppress("FunctionName")
@InlineOnly
public actual fun Trace (size: Int, format: (Int, String) -> String): Trace =
    TraceImpl(size, format)