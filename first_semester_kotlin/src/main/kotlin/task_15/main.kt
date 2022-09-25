package task_15

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousCloseException
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class SocketCompletionHandler<T> : CompletionHandler<T, Continuation<T>> {
    override fun completed(result: T, attachment: Continuation<T>) {
        attachment.resume(result)
    }

    override fun failed(exc: Throwable, attachment: Continuation<T>) {
        attachment.resumeWithException(exc)
    }
}

class AsyncSocket(private val socket: AsynchronousSocketChannel){
    suspend fun connect(address: InetSocketAddress): Void{
        return suspendCoroutine { continuation ->
            socket.connect(address, continuation, SocketCompletionHandler())
        }
    }
    
    suspend fun read(buffer: ByteBuffer): Int{
        return suspendCoroutine { continuation ->
            socket.read(buffer, continuation, SocketCompletionHandler())
        }
    }

    suspend fun write(buffer: ByteBuffer): Int{
        return suspendCoroutine { continuation ->
            socket.write(buffer, continuation, SocketCompletionHandler())
        }
    }
    
    fun close(){
        socket.close()
    }
}

class AsyncServer(private val socket: AsynchronousServerSocketChannel){
    fun bind(address: InetSocketAddress){
        socket.bind(address)
    }
    
    suspend fun accept(): AsynchronousSocketChannel{
        return suspendCoroutine { continuation ->
            socket.accept(continuation, SocketCompletionHandler())
        }
    }
}


fun main() = runBlocking(Dispatchers.IO){
    val port = 1337
    val remoteAddress = "127.0.0.1"
    val remotePort = 8080

    val server = AsyncServer(AsynchronousServerSocketChannel.open())
    server.bind(InetSocketAddress("0.0.0.0", port))
    while (true){
        val client = server.accept()
        launch { 
            handleClient(AsyncSocket(client), InetSocketAddress(remoteAddress, remotePort))
        }
    }
}

suspend fun handleClient(clientSocket: AsyncSocket, remoteAddress: InetSocketAddress) = withContext(Dispatchers.IO){
    println("Connection handler started")

    val remoteSocket = AsyncSocket(AsynchronousSocketChannel.open())
    try{
        remoteSocket.connect(remoteAddress)
    }catch (e: ConnectException){
        clientSocket.close()
        remoteSocket.close()
        println("Cannot connect to remote server")
        return@withContext
    }

    suspend fun senderJob(from: AsyncSocket, to: AsyncSocket, name: String){
        println("$name job started")
        val buffer = ByteBuffer.allocate(1024*1024*4) // 4 Mb

        try {
            while (from.read(buffer) != -1){
                buffer.flip()
                to.write(buffer)
                buffer.flip()
            }
        }catch (_: AsynchronousCloseException){
            println("$name: async close esception")
        }

        to.close()
        println("$name job ended")
    }

    val clientToServerJob = launch {
        senderJob(clientSocket, remoteSocket, "Client -> server")
    }

    val serverToClientJob = launch {
        senderJob(remoteSocket, clientSocket, "Server -> client")
    }

    arrayListOf(serverToClientJob, clientToServerJob).forEach { it.join() }


    println("Connection handler ended")
}