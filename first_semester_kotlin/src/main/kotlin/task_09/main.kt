package task_09

import java.util.concurrent.Semaphore

class Fork(val position: Int): Comparable<Fork>{
    private val mutex = Semaphore(1)
    fun acquire() = mutex.acquire()
    fun release() = mutex.release()

    override fun compareTo(other: Fork): Int {
        return this.position - other.position
    }
}


fun philosopher(name: String, eatTime: Long, thinkTime: Long, forks: List<Fork>){
    while (!Thread.interrupted()){
        println("$name started thinking")
        Thread.sleep(thinkTime)
        println("$name getting left fork")
        forks[0].acquire()
        Thread.yield()
        println("$name getting right fork")
        forks[1].acquire()
        Thread.yield()
        println("$name started eating")
        Thread.sleep(eatTime)
        println("$name releasing forks")
        forks[0].release()
        Thread.yield()
        forks[1].release()
        Thread.yield()
    }
}


fun main(){
    val numPhilosophers = 5

    val forks = Array(5){Fork(it)}

    val threads = (0 until numPhilosophers).map { i ->
        Thread{
            philosopher(i.toString(), 1, 1, listOf(forks[i], forks[(i+1)%5]).sorted())
        }
    }

    threads.forEach { it.start() }

}