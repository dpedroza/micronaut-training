package sample.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class HelloControllerTest {

    @Test
    @Throws(Exception::class)
    fun testHello() {
        setupServer()
        val request = HttpRequest.GET<Any>("/hello")
        val body = client!!.toBlocking().retrieve(request)
        assertNotNull(body)
        assertEquals(
                body,
                "Hello World"
        )
        stopServer()
    }

    companion object {

        private var server: EmbeddedServer? = null
        private var client: HttpClient? = null

        @BeforeClass
        fun setupServer() {
            server = ApplicationContext.run(EmbeddedServer::class.java)
            client = server!!
                    .applicationContext
                    .createBean(HttpClient::class.java, server!!.url)
        }

        @AfterClass
        fun stopServer() {
            if (server != null) {
                server!!.stop()
            }
            if (client != null) {
                client!!.stop()
            }
        }
    }
}
