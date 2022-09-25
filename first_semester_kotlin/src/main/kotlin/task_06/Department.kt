package task_06

import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit


class Department(val identifier: Int) {
    private val workingSeconds: Int = ThreadLocalRandom.current().nextInt(1, 6)

    fun performCalculations() {
        for (i in 0 until workingSeconds) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1))
            } catch (ignored: InterruptedException) {}
            calculationResult += i
        }
    }

    var calculationResult = 0
        private set
}