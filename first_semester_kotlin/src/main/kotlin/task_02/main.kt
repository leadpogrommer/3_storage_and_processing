package task_02

fun main() {
    Thread {
        (1..10).forEach {
            println("SECOND THREAD: $it")
        }
    }.apply {
        start()
        join()
    }

    (1..10).forEach {
        println("MAIN THREAD: $it")
    }
}