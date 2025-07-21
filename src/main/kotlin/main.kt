//// --- Book data class ---
data class Book(
    val id: Int,
    var title: String,
    var author: String,
    var isAvailable: Boolean = true
)

// --- Library interface ---
interface Library {
    fun addBook(book: Book)
    fun borrowBook(bookId: Int): Boolean
    fun returnBook(bookId: Int): Boolean
    fun autoupdate(tofind: String, tochange: String)
    fun listAvailableBooks()
}

// --- SimpleLibrary implementation ---
class SimpleLibrary : Library {
    private val books = mutableListOf<Book>()

    override fun addBook(book: Book) {
        books.add(book)
        println("Book added: ${book.title}")
    }

    override fun borrowBook(bookId: Int): Boolean {
        val book = books.find { it.id == bookId && it.isAvailable }
        return if (book != null) {
            book.isAvailable = false
            println("You borrowed: ${book.title}")
            true
        } else {
            println("Book not available!")
            false
        }
    }

    override fun returnBook(bookId: Int): Boolean {
        val book = books.find { it.id == bookId && !it.isAvailable }
        return if (book != null) {
            book.isAvailable = true
            println("You returned: ${book.title}")
            true
        } else {
            println("Invalid return!")
            false
        }
    }

    override fun listAvailableBooks() {
        val available = books.filter { it.isAvailable }
        if (available.isEmpty()) {
            println("No books available.")
        } else {
            println("Available Books:")
            available.forEach { println("- ${it.title} by ${it.author}") }
        }
    }
    override fun autoupdate(tofind: String, tochange: String) {
        val title_change = books.find { it.title == tofind }
        println(title_change?.title)
        if (title_change != null) {
            title_change.title = tochange
            println("title changed from $tofind to $tochange")
        }
        else {
            val author_change = books.find { it.author == tofind }
            println(author_change?.author)
            if (author_change != null) {
                author_change.author = tochange
                println("title changed from $tofind to $tochange")
            }
            else {
                println("given title or author name cant be found")
            }
        }

    }


    // Expose the internal book list safely (for extension use)
    fun getBooks(): List<Book> = books
}

// --- Extension function for SimpleLibrary ---
fun SimpleLibrary.countAvailableBooks(): Int {
    return this.getBooks().count { it.isAvailable }
}

fun demo_lib(lib: Library) {
    lib.borrowBook(2)

}
// --- Main function to test ---
fun main() {
    val library = SimpleLibrary()

    library.addBook(Book(1, "The Alchemist", "Paulo Coelho"))
    library.addBook(Book(2, "Clean Code", "Robert C. Martin"))
    library.addBook(Book(3, "Kotlin in Action", "Dmitry Jemerov"))
    library.autoupdate("The Alchemist", "hello")
    println()
    library.listAvailableBooks()
    demo_lib(library)
//    val availableCount = library.countAvailableBooks()
//    println("Number of available books: $availableCount")

//    println()
//    library.borrowBook(2)
//
//    println()
//    library.listAvailableBooks()
//
//    println()
//    library.returnBook(2)
//
//    println()
//    library.listAvailableBooks()
//
//    // --- Use the extension function here ---
//    println()
}







//___________________________________________________________________________
//
//fun main() {
//    val input= "hello"
//    val predicate: (Char) -> Boolean = {it == 'o'}
////    println(predicate("hello"))
//
////    val new = input.charsep { predicate }
////    println(new)
////    val name = "hello"
//    val something = input.filter(predicate)
//    println(something)
//    println("$predicate")
//}
//fun String.charsep(inp_str: (Char) -> Boolean): String {
//   return buildString {
//       for (char in this@charsep){
//           if (inp_str(char)) {
//               append(char)
//           }
//       }

//   }

//}

