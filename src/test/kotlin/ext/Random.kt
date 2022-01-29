package ext

import kotlin.random.Random

fun Random.nextFloat(max: Int): Float {
    return this.nextFloat() * max
}

fun Random.nextOddInt(min: Int, max: Int): Int {
    val r: Int = this.nextInt(min, max)
    if (r % 2 == 0) {
        return r - 1
    }
    return r
}