package com.fun_refresh.server

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
       /* withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }*/
    }

    @Test
    fun testClientMock() {
        runBlocking {
            /* val client = HttpClient(MockEngine {
                 if (InputType.url.encodedPath == "/") {
                     MockHttpResponse(
                         call,
                         HttpStatusCode.OK,
                         ByteReadChannel(byteArrayOf(1, 2, 3)),
                         headersOf("X-MyHeader", "MyValue")
                     )
                 } else {
                     responseError(HttpStatusCode.NotFound, "Not Found ${InputType.url.encodedPath}")
                 }
             }) {
                 expectSuccess = false
             }
            assertEquals(byteArrayOf(1, 2, 3).toList(), client.get<ByteArray>("/").toList())
            assertEquals("MyValue", client.call("/").response.headers["X-MyHeader"])
            assertEquals("Not Found other/path", client.get<String>("/other/path"))*/
        }
    }
}
