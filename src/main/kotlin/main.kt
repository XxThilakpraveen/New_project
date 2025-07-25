import kotlin.random.Random

fun main() {
    val new_Arr = Array(25) {it +1 }
    val numbers = MutableList(50)   { it + 1 }
    println(numbers.size)

    for (i in numbers.indices) {
        numbers[i] = Random.nextInt(1, 1000)
    }

    println(numbers.joinToString())
    for (i in 0..24) {
        val rand_indx = Random.nextInt(numbers.size)
        println(rand_indx)
        numbers.removeAt(rand_indx)
    }

    println(new_Arr.joinToString())
    println(numbers.joinToString())
    println(numbers.size)
}

