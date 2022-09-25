package task_12


class List<T : Comparable<T>> : Iterable<T> {
    internal class Node<T>(var value: T, var next: Node<T>?)

    private var head: Node<T>? = null
    fun insert(e: T) {
        val prevHead = head
        head = Node(e, prevHead)
    }

    fun sort() {
        var i = head
        while (i != null) {
            var j = i.next
            while (j != null) {
                if (i.value > j.value) {
                    val tmp = i.value
                    i.value = j.value
                    j.value = tmp
                }
                j = j.next
            }
            i = i.next
        }
    }

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var n = head

            override fun hasNext(): Boolean {
                return n != null
            }

            override fun next(): T {
                val ret = n!!.value
                n = n!!.next
                return ret
            }
        }
    }
}


fun main() {
    val list = List<String>()

    Thread {
        while (true) {
            synchronized(list) {
                list.sort()
            }
            Thread.sleep(5000)
        }
    }.start()

    while (true) {
        val s = readln()
        if (s.isEmpty()) {
            println("---------")
            synchronized(list) {
                list.forEach(::println)
            }
        } else {
            synchronized(list) {
                list.insert(s)
            }
        }
    }
}