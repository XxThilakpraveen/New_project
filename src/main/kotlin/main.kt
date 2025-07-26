import kotlin.random.Random

fun main() {


    print("Enter Array size: ")
    val arraySize = readLine()?.toIntOrNull() ?: 0

    println("Enter Array Elements")
    val user_arr = Array<Int>(arraySize) { readLine()?.toIntOrNull() ?: 0 }

    val random_numbers = MutableList(50)   { it + 1 }

    for (i in random_numbers.indices) {
        random_numbers[i] = Random.nextInt(1, 20)
    }
    user_arr.sort()
    random_numbers.sort()
    println(random_numbers.joinToString())

    for ( value in user_arr ) {
        if (value <= random_numbers[random_numbers.size - 1])
            if (random_numbers.contains(value) ){

                val index = random_numbers.indexOf(value)
                println("the removed index is $index and the value is $value")
                random_numbers[index] = 0
            }

    }
    println(random_numbers.joinToString())
    val No_zero_array = random_numbers.filter { it != 0 }.toTypedArray()
    println(No_zero_array.joinToString())
}