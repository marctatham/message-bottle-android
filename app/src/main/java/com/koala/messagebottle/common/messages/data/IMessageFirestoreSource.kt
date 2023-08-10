package com.koala.messagebottle.common.messages.data

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.util.Util.autoId
import com.koala.messagebottle.common.messages.data.model.MessageFirestoreDataModel
import timber.log.Timber
import javax.inject.Inject

private const val COLLECTION = "messages"

// TODO: we'll clean all of this up, let's just see if it works
class IMessageFirestoreSource @Inject constructor(
    firestore: FirebaseFirestore
) : IMessageDataSource {

    private val collectionReference: CollectionReference = firestore.collection(COLLECTION)


    @SuppressLint("RestrictedApi")
    private fun getRandomIndexId() = autoId()

    override suspend fun getMessage(): MessageDataModel {

        // generate a random ID within the index
        val random = getRandomIndexId()

        // define the query to search up the index
        val searchUpTheIndex: Query = collectionReference
            .whereGreaterThanOrEqualTo(FieldPath.documentId(), random)
            .orderBy(FieldPath.documentId())
            .limit(1)

        // define the query to search down the index
        val searchDownTheIndex: Query = collectionReference
            .whereLessThanOrEqualTo(FieldPath.documentId(), random)
            .orderBy(FieldPath.documentId())
            .limit(1)

        var querySnapshot: QuerySnapshot = Tasks.await(searchUpTheIndex.get())
        if (querySnapshot.documents.size == 0) {
            querySnapshot = Tasks.await(searchDownTheIndex.get())
        }

        val messageContent: String = querySnapshot.documents[0].data!!["messageContent"].toString()
        return MessageDataModel(messageContent)
    }

    override suspend fun getMessages(): List<MessageDataModel> {
        Timber.i("Getting messages")
        var querySnapshot: QuerySnapshot = Tasks.await(collectionReference.get())
        return querySnapshot.documents.map {
            MessageDataModel(
                it.data!!["messageContent"].toString()
            )
        }
    }

    override suspend fun postMessage(message: MessageDataModel) {
        Timber.i("Posting new message")
        val addTask: Task<DocumentReference> = collectionReference.add(
            MessageFirestoreDataModel(
                message.message,
                "GxSib4PSfLgfL8IQzPDmjQcgpSk1"
            )
        )

        val docRef: DocumentReference = Tasks.await(addTask)
        Timber.i("New message posted")
    }
}
