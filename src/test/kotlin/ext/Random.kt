package ext

import kotlin.random.Random

fun Random.nextDouble(max: Int): Double {
    return this.nextDouble() * max
}

fun Random.nextOddInt(min: Int, max: Int): Int {
    val r: Int = this.nextInt(min, max)
    if (r % 2 == 0) {
        return r - 1
    }
    return r
}