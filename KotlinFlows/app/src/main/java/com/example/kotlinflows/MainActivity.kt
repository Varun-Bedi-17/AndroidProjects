package com.example.kotlinflows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val TAG = "KotlinFlows"
const val CONSUMER1 = "Consumer1"
const val CONSUMER2 = "Consumer2"
const val CONSUMER3 = "Consumer3"
const val CONSUMER4 = "Consumer4"

class MainActivity : AppCompatActivity() {
    private val channel = Channel<Int>() // Create a Channel for sending and receiving integers.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Uncomment one of the following sections to demonstrate either Channels or Flows.

        // Channels
        // producerChannel()
        // consumerChannel()

        // Flows
        // consumerFlow()
        // consumerSharedFlow()
        consumerStateFlow()

    }

    // Demonstrates sending data to a Channel.
    private fun producerChannel() {
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            Log.i(TAG, "producerChannel: Channel 1 Sent")
            channel.send(2)
            Log.i(TAG, "producerChannel: Channel 2 Sent")
        }
    }

    // Demonstrates receiving data from a Channel.
    private fun consumerChannel() {
        // Using receive() to get elements one by one.
        // CoroutineScope(Dispatchers.Main).launch {
        //     Log.i(TAG, "consumerChannel: ${channel.receive()}")
        //     Log.i(TAG, "consumerChannel: ${channel.receive()}")
        // }

        // Another way to receive elements is using consumeEach.
        CoroutineScope(Dispatchers.Main).launch {
            channel.consumeEach {
                Log.i(TAG, "consumerChannel: $it")
            }
        }
    }



    // Create a Flow that emits integers after a delay.
    private fun producerFlow() = flow {
        Log.i(TAG, Thread.currentThread().name)
        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000) // Introduce a delay of 1 second before emitting each element.
            emit(it)    // Emit the current element.
        }
    }

    // Demonstrates consuming data from a Flow.
    private fun consumerFlow() {
        // Launch a coroutine to collect data from the Flow.
        // If there is not any collector present then the producer will not produce anything.
        val job = CoroutineScope(Dispatchers.Default).launch {
            val collector = producerFlow()  // Create a Flow collector from the producerFlow.
            collector.collect {             // Start collecting elements from the Flow.
                Log.i(CONSUMER1, "$it")
            }
        }

        // After 3.5 seconds, cancel the job to stop collecting from the Flow.
        CoroutineScope(Dispatchers.Default).launch {
            delay(3500)
            job.cancel()
        }

        // When we have multiple consumers
        // After 5 seconds, start a new coroutine to collect from the Flow again.
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)

            // We can add events in kotlin flows.
            producerFlow()
                .onStart {
                    // We can also emit values from onStart and onComplete
                    emit(-1)
                    Log.i(CONSUMER2, "Start Consuming")
                }
                .onCompletion {
                    emit(10)
                    Log.i(CONSUMER2, "End Consuming")
                }
                .onEach {
                    Log.i(CONSUMER2, "Consuming $it")
                }
                .map {
                    it * 2
                }
                .filter {
                    it < 8
                }
                .collect {
                    Log.i(CONSUMER2, it.toString())
                }

            //
            val firsEmittedValue = producerFlow().first()
            Log.i(CONSUMER3, firsEmittedValue.toString())
            Log.i(CONSUMER3, producerFlow().toList().toString())
        }

        // If consumer is slow. Then we ca use buffer
        // Coroutine will launch in same thread in which it is consuming. If we want to consume flow in different thread than we can
        // use flowOn method.The code above flowOn will run in flowOn thread and below will run on producer thread.
        // We can use multiple flowOn for context(thread) switching.
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            producerFlow()
                .buffer(3)
                .flowOn(Dispatchers.IO)
                // Code above this will work on IO thread.
                .collect {
                    Log.i(CONSUMER4, it.toString())
                }
        }
    }




    // Create a State Flow that emits integers after a delay.
    // Shared Flow is a hot stream.
    private fun producerSharedFlow(): Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(1)
        Log.i(TAG, Thread.currentThread().name)
        CoroutineScope(Dispatchers.Main).launch {
            val list = listOf(1, 2, 3, 4, 5)
            list.forEach {
                delay(1000) // Introduce a delay of 1 second before emitting each element.
                mutableSharedFlow.emit(it)    // Emit the current element.
            }
        }
        return mutableSharedFlow
    }

    // Demonstrates consuming data from a SharedFlow.
    private fun consumerSharedFlow() {
        val collector = producerSharedFlow()  // Create a SharedFlow collector from the producerFlow.

        // Launch a coroutine to collect data from the SharedFlow.
        // If there is not any collector present then the producer will not produce anything.
        CoroutineScope(Dispatchers.Main).launch {
            collector.collect {             // Start collecting elements from the SharedFlow.
                Log.i(CONSUMER1, "$it")
            }
        }


        // When we have multiple consumers
        // After 3 seconds, start a new coroutine to collect from the SharedFlow again.
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
                collector.collect {
                    Log.i(CONSUMER2, it.toString())
                }
        }
    }



    private fun producerStateFlow(): Flow<Int> {
        val mutableStateFlow = MutableStateFlow(10)
        Log.i(TAG, Thread.currentThread().name)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            mutableStateFlow.emit(1)
            delay(2000)
            mutableStateFlow.emit(2)
        }
        return mutableStateFlow
    }

    private fun consumerStateFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            val collector = producerStateFlow()
            delay(6000)
            collector.collect {             // Start collecting elements from the SharedFlow.
                Log.i(CONSUMER1, "$it")
            }
        }
    }
}
