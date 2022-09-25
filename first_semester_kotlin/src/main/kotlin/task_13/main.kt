package task_13

import java.util.Objects
import java.util.Random
import java.util.concurrent.Semaphore


val forksMutex = Semaphore(1)
val forksLock = Object()


fun philosopher(name: String, eatTime: Long, thinkTime: Long, forks: List<Semaphore>){
    Thread.currentThread().name = name
    fun log(s: String){
        println("$name: $s")
    }

    while (!Thread.interrupted()){
        log("thinking")
        Thread.sleep(thinkTime)
        log("getting forks")
        var gotForks = false
        while (!gotForks){
            forksMutex.acquire()
            if(forks[0].availablePermits() > 0 && forks[1].availablePermits() > 0){
                forks[0].acquire()
                forks[1].acquire()
                gotForks = true
                forksMutex.release()
            }else{
                synchronized(forksLock){
                    forksMutex.release()
                    forksLock.wait()
                }
            }
        }
        log("got forks, eating")
        Thread.sleep(eatTime)
        forksMutex.acquire()
        forks[0].release()
        forks[1].release()
        synchronized(forksLock){
            forksMutex.release()
            forksLock.notifyAll()
        }

    }
}

fun main(){
    val numPhilosophers = 5
    val random = Random()

    val forks = Array(5){Semaphore(1)}

    val threads = (0 until numPhilosophers).map { i ->
        Thread{
            philosopher(i.toString(), random.nextInt(100).toLong(), random.nextInt(100).toLong(), listOf(forks[i], forks[(i+1)%numPhilosophers]))
        }
    }

    threads.forEach {
        it.start()
    }
}
