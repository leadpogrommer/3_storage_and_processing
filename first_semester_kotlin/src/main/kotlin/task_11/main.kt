package task_11

import java.util.concurrent.Semaphore

fun main(){
    val s1 = Semaphore(1)
    val s2 = Semaphore(1)

    s1.acquire()


    Thread {
        (1..10).forEach {
            s1.acquire()
            println("Second THREAD: $it")
            s2.release()
        }
    }.start()

    (1..10).forEach {
        s2.acquire()
        println("MAIN THREAD: $it")
        s1.release()

    }
}