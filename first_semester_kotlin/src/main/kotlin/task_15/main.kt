package task_15

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


//fun handleClient(client: AsynchronousSocketChannel, remoteAddress: InetSocketAddress){
//    val server = AsynchronousSocketChannel.open().connect(remoteAddress)
//    val buffFromClientToServer = ByteBuffer.allocate(2048)
//    val buffFromServerToClient = ByteBuffer.allocate(2048)
//
//
////    client.as
//    client.read( buffFromClientToServer, client, object: CompletionHandler<Int, AsynchronousSocketChannel > {
//        override fun completed(result: Int?, attachment: AsynchronousSocketChannel?) {
//            buffFromClientToServer.flip()
//
//            server
//        }
//
////        override fun completed(Integer result, AsynchronousSocketChannel channel  ) {
////            buf.flip();
////
////            // echo the message
////            startWrite( channel, buf );
////
////            //start to read next message again
////            startRead( channel );
////        }
////
////        @Override
////        public void failed(Throwable exc, AsynchronousSocketChannel channel ) {
////            System.out.println( "fail to read message from client");
////        }
//    });
//}

class AsyncSocket(private val socket: AsynchronousSocketChannel){
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

    class SocketCompletionHandler<T> : CompletionHandler<T, Continuation<T>> {
        override fun completed(result: T, attachment: Continuation<T>) {
            attachment.resume(result)
        }

        override fun failed(exc: Throwable, attachment: Continuation<T>) {
            attachment.resumeWithException(exc)
        }
    }
}


fun main() = runBlocking(Dispatchers.IO){
    val port = 1337
    val remoteAddress = "127.0.0.1"
    val remotePort = 8080

    val server = AsynchronousServerSocketChannel.open()
    
}