import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

const val portArgName = "--server.port"
const val defaultPort = 8888

fun main(args: Array<String>) {
    val portConfigured = args.isNotEmpty() && args[0].startsWith(portArgName)
    val port = if (portConfigured) {
        args[0].split("=").last().trim().toInt()
    } else defaultPort
    embeddedServer(Netty, port, "192.168.124.16", module = Application::main).start(wait = true)
}
