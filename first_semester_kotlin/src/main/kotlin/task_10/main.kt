package task_10



fun main(){
    val lock = Object()
    var stage = false

    Thread {
        (1..10).forEach {
            synchronized(lock){
                while (!stage){
                    lock.wait()
                }
            }
            println("Second THREAD: $it")
            synchronized(lock){
                stage = false
                lock.notifyAll()
            }
        }
    }.start()

    (1..10).forEach {
        synchronized(lock){
            while (stage){
                lock.wait()
            }
        }
        println("MAIN THREAD: $it")
        synchronized(lock){
            stage = true
            lock.notifyAll()
        }
    }
}