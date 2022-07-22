package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.common.data.workbook.Chunk
import org.wycliffeassociates.otter.common.data.workbook.ResourceGroup

fun questionsDedup(questions: List<Question>): List<Question> {
    val result = mutableListOf<Question>()
    questions.forEach {question ->
        val match = result.find { it == question }
        if (match != null) {
            match.end = question.end
        } else {
            result.add(question)
        }
    }
    return result
}

fun questionFromChunk(chunk: Chunk): Question? {
    val resourceGroup = chunk.resources.find { it.metadata.identifier == "tq" }
    return if (resourceGroup != null) {
        Question(chunk.start, chunk.end, resourceGroup)
    } else {
        null
    }
}

data class Question(
    val start: Int,
    var end: Int,
    val resources: ResourceGroup?
) {
    val question: String?
        get() = when (resources) {
            null -> null
            else -> (resources as ResourceGroup).resources.blockingFirst().title.textItem.text
        }
    val answer: String?
        get() = when (resources) {
            null -> null
            else -> (resources as ResourceGroup).resources.blockingFirst().body?.textItem?.text
        }
    var result: String? = null

    override fun equals(other: Any?): Boolean =
        (other is Question) && (question == other.question) && (answer == other.answer)
}