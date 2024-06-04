package com.example.timetonictestapp.data.datamodels.booksmodels

data class LastMsg(
    val b_c: String,
    val b_o: String,
    val created: Int,
    val del: Boolean,
    val docs: List<Doc>,
    val lastCommentId: Int,
    val lastModified: Int,
    val linkMessage: String,
    val linkedFieldId: Any,
    val linkedRowId: Any,
    val linkedTabId: Any,
    val msg: String,
    val msgBody: String,
    val msgColor: String,
    val msgMethod: String,
    val msgType: String,
    val nbComments: Int,
    val nbDocs: Int,
    val nbEmailCids: Int,
    val nbMedias: Int,
    val pid: Int,
    val smid: Int,
    val sstamp: Long,
    val u_c: String,
    val uuid: String
)