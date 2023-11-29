package com.example.lesson18_2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 * @author Bulat Bagaviev
 * @created 16.11.2023
 */
class TestKtCompile
// EXAPMLE 1
//fun main() = runBlocking { // this: CoroutineScope
//    val job = async { // launch a new coroutine and continue  // launch => job, async => Deferred<Unit>
//        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
//        println("World!") // print after delay
//    }
////    job.join()
//    job.await()
//    println("Hello") // main coroutine continues while a previous one is delayed
//}

// EXAPMLE 2
//fun main() = runBlocking {
//    doWorld()
//    println("Done")
//}

// Concurrently executes both sections
//suspend fun doWorld() = coroutineScope { // this: CoroutineScope
//    launch {
//        delay(2000L)
//        println("World 2")
//    }
//    launch {
//        delay(1000L)
//        println("World 1")
//    }
//    println("Hello")
//}

// EXAPMLE 3
//fun main() = runBlocking {
//    launch(Dispatchers.IO) {
//        delay(2000L)
//        println("World!")
//        try {
//            throw ArithmeticException()
//        } catch(e: Exception) {
//            println(e)
//        }
//    }
//
//    println("Hello")
//}

// EXAPMLE 4
//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(2000L) // pretend we are doing something useful here, too
//    return 29
//}
//
//fun main() = runBlocking {
//    launch(Job()) {
//        val time = measureTimeMillis {
//            val one = async { doSomethingUsefulOne() }  // паралельное выполнение
//            val two = async { doSomethingUsefulTwo() }
//            println("The answer is ${one.await() + two.await()}") }
//        println("Completed in $time ms")
//    }
//    println("Hello")
////    cancel()    // launch не отработает
//}

//fun main() = runBlocking {
//    unknownFunction()
//}

//suspend fun unknownFunction() {     // exception
//    coroutineScope {
//        launch(Job()) {    // Job() сделает println("Hello") недостижимым
//            println("start")
//            delay(1000)
//            println("end")
//        }
//        cancel()    // тупо отмена, однако корутина успеет стартануть
//    }
//}

//suspend fun unknownFunction() {     // exception
//    coroutineScope {
//        launch {    // Job() сделает println("Hello") недостижимым
//            println("start")    // потом это 2
//            delay(1000)
//            println("end")
//        }
//        cancel()  // потом это 3 - надо обрабатывать исключение
//        println("fin")  // сначала это 1
//    }
//}

//val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
//    println("Handle $exception in CoroutineExceptionHandler")
//}

fun main() {
    val topLevelScope = CoroutineScope(Job())   // topLevelScope.launch(coroutineExceptionHandler)
    try {
        topLevelScope.launch {
            println("Outer coroutine")
            launch {
                println("inner coroutine")
                throw RuntimeException("RuntimeException in nested coroutine")
            }
        }
    } catch (exception: Exception) {
        println("Handle $exception")
    }
    Thread.sleep(100)
}