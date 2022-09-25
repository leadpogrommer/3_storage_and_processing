package task_03

fun printLines(lines: List<String>) = lines.forEach(::println)

fun main(){
    val lines = listOf(
        listOf("1: wefv", "1: ggg", "1: tgefbt", "1: rsnart", "1: fdmtu", "1: rbe"),
        listOf("2: wefv", "2: ggg", "2: tgefbt", "2: rsnart", "2: fdmtu", "2: rbe"),
        listOf("3: wefv", "3: ggg", "3: tgefbt", "3: rsnart", "3: fdmtu", "3: rbe"),
        listOf("4: wefv", "4: ggg", "4: tgefbt", "4: rsnart", "4: fdmtu", "4: rbe"),
    )
    lines.forEach {
        Thread{
            printLines(it)
        }.start()
    }
}