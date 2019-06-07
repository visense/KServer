import io.ktor.application.*
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.*
import io.ktor.html.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.*
import repo.Person
import repo.PersonRepo
import java.time.Duration
import java.text.DateFormat

const val REST_ENDPOINT = "/person"

fun Application.main() {
    install(DefaultHeaders)
    install(CORS) {
        maxAge = Duration.ofDays(1)
    }
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    routing {
        get("$REST_ENDPOINT/{id}") {
            errorAware {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("找不到参数id")
                call.respond(PersonRepo.get(id))
            }
        }
        get(REST_ENDPOINT) {
            errorAware {
                call.respond(PersonRepo.getAll())
            }
        }
        delete("$REST_ENDPOINT/{id}") {
            errorAware {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("找不到参数id")
                call.respondSuccessJson(PersonRepo.remove(id))
            }
        }
        delete(REST_ENDPOINT) {
            errorAware {
                PersonRepo.clear()
                call.respondSuccessJson()
            }
        }
        post(REST_ENDPOINT) {
            errorAware {
                val receive = call.receive<Person>()
                println("Received Post Request: $receive")
                call.respond(PersonRepo.add(receive))
            }
        }

        get("/") {
            call.respondHtml {
                head {
                    title("趣刷Ktor API")
                }
                body {
                    div {
                        h1 {
                            +"欢迎使用人员API"
                        }
                        p {
                            +"请到/person路径下使用"
                        }
                    }
                }
            }
        }
    }
}

private suspend fun <R> PipelineContext<*, ApplicationCall>.errorAware(block: suspend () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        call.respondText(
            """{"error":"$e"}"""
            , ContentType.parse("application/json")
            , HttpStatusCode.InternalServerError
        )
        null
    }
}

private suspend fun ApplicationCall.respondSuccessJson(value: Boolean = true) =
    respond("""{"success": "$value"}""")
