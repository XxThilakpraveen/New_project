import kotlin.random.Random

fun main() {


    print("Enter Array size: ")
    val arraySize = readLine()?.toIntOrNull() ?: 0

    println("Enter Array Elements")
    val user_arr = Array<Int>(arraySize) { readLine()?.toIntOrNull() ?: 0 }

    val numbers = MutableList(50)   { it + 1 }

    for (i in numbers.indices) {
        numbers[i] = Random.nextInt(1, 20)
    }
    user_arr.sort()
    numbers.sort()
    println(numbers.joinToString())

    for ( value in user_arr ) {
        if (value <= numbers[numbers.size - 1])
            if (numbers.contains(value) ){

                val index = numbers.indexOf(value)
                println("the removed index is $index and the value is $value")
                numbers[index] = 0
            }

    }
    println(numbers.joinToString())
    val No_zero_array = numbers.filter { it != 0 }.toTypedArray()
    println(No_zero_array.joinToString())
}