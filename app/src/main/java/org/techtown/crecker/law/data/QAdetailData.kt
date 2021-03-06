package org.techtown.crecker.law.data

data class QAdetailData(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
) {
    data class Data(
        val Acontent: String,
        val Qcontent: String,
        val Qtitle: String,
        val answerUpdateAt: String,
        val categoryCode: String,
        val createAt: String,
        val description: String,
        val email: String,
        val expertConsultIdx: Int,
        val expertIdx: Int,
        val isComplete: Int,
        val isSecret: Int,
        val isUser: Boolean,
        val name: String,
        val photo: String,
        val userIdx: Int,
        val views: Int
    )
}