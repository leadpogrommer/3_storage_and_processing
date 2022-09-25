package task_14

import java.util.concurrent.Semaphore

fun runInThreadLoop(f: Runnable){
    Thread{
        while(!Thread.interrupted()){
            f.run()
        }
    }.start()
}

fun main(){
    val a = Semaphore(0)
    val b = Semaphore(0)
    val c = Semaphore(0)
    val modules = Semaphore(0)

    runInThreadLoop{
        Thread.sleep(1000)
        a.release(1)
        println("Produced A")
    }

    runInThreadLoop{
        Thread.sleep(2000)
        b.release(1)
        println("Produced B")
    }

    runInThreadLoop{
        Thread.sleep(3000)
        c.release(1)
        println("Produced C")
    }

    runInThreadLoop{
        a.acquire(1)
        b.acquire(1)
        modules.release(1)
        println("Produced module")
    }

    runInThreadLoop{
        c.acquire(1)
        modules.acquire(1)
        println("Produced widget")
    }
}