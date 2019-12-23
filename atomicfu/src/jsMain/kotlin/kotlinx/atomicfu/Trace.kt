package kotlinx.atomicfu

import kotlin.js.JsName

@JsName("atomicfu\$Trace\$")
public actual fun Trace (size: Int, format: (Int, String) -> String): Trace = Trace.None