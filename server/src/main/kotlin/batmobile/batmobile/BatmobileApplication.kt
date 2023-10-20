package batmobile.batmobile

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatmobileApplication

fun main(args: Array<String>) {
	runApplication<BatmobileApplication>(*args)
}
