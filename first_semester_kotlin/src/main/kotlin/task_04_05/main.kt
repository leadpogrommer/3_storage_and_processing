package task_04_05

fun main(){
    Thread{
        var n = 0L
        while (!Thread.interrupted()){
            println("AMA PRINTING TO TERMINAL #${++n}")
        }
        println("I WAS INTERRUPTED!")
    }.apply {
        start()
        Thread.sleep(2000)
        interrupt()
    }
}
