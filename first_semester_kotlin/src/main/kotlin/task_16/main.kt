package task_16

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import java.net.URL
import java.util.concurrent.LinkedBlockingQueue


data class Container<T>(val value: T?)

fun main(){
    val address = "http://parallels.nsu.ru/~fat/unixsvr4-new/trunk/"
    val url = URL(address)
    val lines = LinkedBlockingQueue<Container<String>>()

    Thread{
        val socket = Socket(url.host, if(url.port != -1) url.port else 80)
        val request = "GET ${url.path} HTTP/1.1\nHost: ${url.host}\nUser-Agent: cshit/228\nAccept: text/*\nConnection: close\n\n"
        println("----- RQUEST -----")
        print(request)
        println("----- RESPONSE -----")
        socket.getOutputStream().write(request.toByteArray())
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        var skippedHeaders = true
        reader.lines().forEach {
            if(skippedHeaders){
                lines.add(Container(it))
            }else if(it.isEmpty()){
                skippedHeaders = true
            }
        }
        lines.add(Container(null))
    }.start()

    loop@ while (true){
        for(i in 0 until 25){
            val line = lines.take()
            line.value ?: break@loop
            println(line.value)
        }
        println("----- PRESS RETURN TO CONTINUE -----")
        readln()
    }
}