package com.example.portfolio.exemplos.features.kotlinConf


import java.time.LocalDate
import java.time.Month


class Client(var name: String? = null, var birthDate: LocalDate? = null)

fun buildClient(block: Client.() -> Unit): Client {
    val client = Client()
    client.block()
    return client
}

infix fun Int.March(year: Int) = LocalDate.of(year, Month.MARCH, this)

object ClientBuilderContext

context(_: ClientBuilderContext)
infix fun Int.October(year: Int) = LocalDate.of(year, Month.OCTOBER, this)

fun buildClientContext(init: context(ClientBuilderContext) Client.() -> Unit) : Client =
    with(ClientBuilderContext) {
        var client = Client()
        init(client)
       return client
    }


fun test01() {
    val client1 = buildClient {
        name = "Claudio"
        birthDate = 10 March 2000
    }

    val client2 = buildClientContext {
        name = "Claudio"
        birthDate = 10 October 2000
    }

    println("Client1: ${client1.name}, Born: ${client1.birthDate}")
    println("Client1: ${client2.name}, Born: ${client2.birthDate}")
}
