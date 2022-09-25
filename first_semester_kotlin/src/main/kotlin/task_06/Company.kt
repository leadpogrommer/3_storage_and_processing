package task_06

class Company(departmentsCount: Int) {
    private val departments = Array(departmentsCount){ i ->
        Department(i)
    }

    fun showCollaborativeResult() {
        println("All departments have completed their work.")
        val result = departments.map(Department::calculationResult).sum()
        println("The sum of all calculations is: $result")
    }

    fun getDepartmentsCount(): Int {
        return departments.size
    }


    fun getFreeDepartment(index: Int): Department {
        return departments[index]
    }
}