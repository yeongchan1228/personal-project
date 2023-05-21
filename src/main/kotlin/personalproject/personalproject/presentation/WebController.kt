package personalproject.personalproject.presentation

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WebController : ErrorController {
    @RequestMapping(value = ["/", "/error", "/test"])
    fun index(): String {
        return "index.html"
    }
}
