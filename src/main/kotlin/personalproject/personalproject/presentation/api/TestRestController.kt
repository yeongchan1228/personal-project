package personalproject.personalproject

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestRestController {
    @GetMapping
    fun test(): TestData {
        return TestData()
    }
}

data class TestData(
    val data: String = "testData!!!!",
)
