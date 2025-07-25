//import kotlin.reflect.typeOf
//
////// --- Book data class ---
//data class Book(
//    val id: Int,
//    var title: String,
//    var author: String,
//    var isAvailable: Boolean = true
//)
//
//// --- Library interface ---
//interface Library {
//    fun addBook(book: Book)
//    fun borrowBook(bookId: Int): Boolean
//    fun returnBook(bookId: Int): Boolean
//    fun autoupdate(tofind: String, tochange: String)
//    fun searchByAuthor(author: String): List<Book>
//    fun listAvailableBooks()
//}
//
//// --- SimpleLibrary implementation ---
//class SimpleLibrary : Library {
//    private val books = mutableListOf<Book>()
//
//    override fun searchByAuthor(author: String): List<Book> {
//        val fil = books.filter { it.author == author }
//        return fil
//    }
//
//    override fun addBook(book: Book) {
//        books.add(book)
//        println("Book added: ${book.title}")
//    }
//
//    override fun borrowBook(bookId: Int): Boolean {
//        val book = books.find { it.id == bookId && it.isAvailable }
//        return if (book != null) {
//            book.isAvailable = false
//            println("You borrowed: ${book.title}")
//            true
//        } else {
//            println("Book not available!")
//            false
//        }
//    }
//
//    override fun returnBook(bookId: Int): Boolean {
//        val book = books.find { it.id == bookId && !it.isAvailable }
//        return if (book != null) {
//            book.isAvailable = true
//            println("You returned: ${book.title}")
//            true
//        } else {
//            println("Invalid return!")
//            false
//        }
//    }
//
//    override fun listAvailableBooks() {
//        val available = books.filter { it.isAvailable }
//        if (available.isEmpty()) {
//            println("No books available.")
//        } else {
//            println("Available Books:")
//            available.forEach { println("- ${it.title} by ${it.author}") }
//        }
//    }
//    override fun autoupdate(tofind: String, tochange: String) {
//        val title_change = books.find { it.title == tofind }
//        println(title_change?.title)
//        if (title_change != null) {
//            title_change.title = tochange
//            println("title changed from $tofind to $tochange")
//        }
//        else {
//            val author_change = books.find { it.author == tofind }
//            println(author_change?.author)
//            if (author_change != null) {
//                author_change.author = tochange
//                println("title changed from $tofind to $tochange")
//            }
//            else {
//                println("given title or author name cant be found")
//            }
//        }
//
//    }
//
//
//    // Expose the internal book list safely (for extension use)
//    fun getBooks(): List<Book> = books
//}
//
//// --- Extension function for SimpleLibrary ---
//fun SimpleLibrary.countAvailableBooks(): Int {
//    return this.getBooks().count { it.isAvailable }
//}
//
//fun demo_lib(lib: Library) {
//    lib.borrowBook(2)
//
//}
//// --- Main function to test ---
//fun main() {
//    val library = SimpleLibrary()
//
//    library.addBook(Book(1, "The Alchemist", "Paulo Coelho"))
//    library.addBook(Book(2, "Clean Code", "Robert C. Martin"))
//    library.addBook(Book(3, "Kotlin in Action", "Dmitry Jemerov"))
//    library.autoupdate("The Alchemist", "hello")
////    library.listAvailableBooks()
////    demo_lib(library)
//    library.searchByAuthor("Paulo Coelho")
////    val availableCount = library.countAvailableBooks()
////    println("Number of available books: $availableCount")
//
////    println()
////    library.borrowBook(2)
////
////    println()
////    library.listAvailableBooks()
////
////    println()
////    library.returnBook(2)
////
////    println()
////    library.listAvailableBooks()
////
////    // --- Use the extension function here ---
////    println()
//}
//
//
//
//
//
//
//
////___________________________________________________________________________
////
////fun main() {
////    val input= "hello"
////    val predicate: (Char) -> Boolean = {it == 'o'}
//////    println(predicate("hello"))
////
//////    val new = input.charsep { predicate }
//////    println(new)
//////    val name = "hello"
////    val something = input.filter(predicate)
////    println(something)
////    println("$predicate")
////}
////fun String.charsep(inp_str: (Char) -> Boolean): String {
////   return buildString {
////       for (char in this@charsep){
////           if (inp_str(char)) {
////               append(char)
////           }
////       }
//
////   }
//
////}
//
//----------------------------------------------------------------------------
//static class for kotlin
//class Person {
//    companion object {
//        fun greet(name: String) {
//            val msg = getGreeting(name)  // ✅ Access private function internally
//            println(msg)
//        }
//
//        private fun getGreeting(name: String): String {
//            return "Hello, $name!"
//        }
//
//        var b
//    }
//}
//
//fun main() {
//    var get1 = peroson()
//
//    // ✅ Public method can be accessed
//    Person.greet("Alice")
//
//    // ❌ This will NOT compile (private function)
//    // println(Person.getGreeting("Alice"))
//}
//
//object Utils {
//
//    fun greet(name: String) {
//        val msg = getGreetingMessage(name)  // ✅ calling private function
//        println(msg)
//    }
//
//    private fun getGreetingMessage(name: String): String {
//        return "Hello, $name!"
//    }
//}
//
//fun main() {
//    Utils.greet("Alice")
//
//    //  This will not work because it's private
//    // println(Utils.getGreetingMessage("Alice"))
//}

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

