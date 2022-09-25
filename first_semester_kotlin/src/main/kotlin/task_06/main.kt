package task_06

import java.util.concurrent.CyclicBarrier

fun Department.worker(barrier: CyclicBarrier){
    performCalculations()
    barrier.await()
}

fun main(){
    val company = Company(12)
    val barrier = CyclicBarrier(company.getDepartmentsCount()){
        company.showCollaborativeResult()
    }

    val threads = (0 until company.getDepartmentsCount()).map {
        Thread{
            company.getFreeDepartment(it).worker(barrier)
        }
    }

    threads.forEach { it.start() }
}