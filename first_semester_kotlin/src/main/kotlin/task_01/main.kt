package task_01

fun main() {
    Thread {
        (1..10).forEach {
            println("SECOND THREAD: $it")
        }
    }.start()

    (1..10).forEach {
        println("MAIN THREAD: $it")
    }
}