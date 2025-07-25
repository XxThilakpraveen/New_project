import kotlin.random.Random

fun main() {

    val numbers = MutableList(50)   { it + 1 }
    println(numbers.size)

    for (i in numbers.indices) {
        numbers[i] = Random.nextInt(1, 1000)
    }

    println(numbers.joinToString())
    for (i in 0..24) {

        numbers.removeAt(Random.nextInt(numbers.size))

    }
    println(numbers.joinToString())
    println(numbers.size)
}

