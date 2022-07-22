package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.beans.property.SimpleObjectProperty
import org.wycliffeassociates.otter.common.data.workbook.Chapter
import org.wycliffeassociates.otter.common.data.workbook.Workbook
import org.wycliffeassociates.otter.common.utils.capitalizeString
import tornadofx.*

fun chapterEquals(a: Chapter?, b: Chapter?): Boolean =
    (a != null && b != null) && (a.sort == b.sort) && (a.title == b.title)

class OQuAViewModel: ViewModel() {
    private val workbookRepo = (app as OQuAApp).dependencyGraph.injectWorkbookRepository()

    val tCards = observableListOf<TranslationCard>()

    // Properties of active workbook
    val activeWorkbookProperty = SimpleObjectProperty<Workbook>()
    val workbook: Workbook?
        get() = activeWorkbookProperty.value
    val projectTitleProperty = stringBinding(activeWorkbookProperty) {
        when (this.value) {
            null -> null
            else -> this.value.target.title
        }
    }
    val targetChaptersProperty = objectBinding(activeWorkbookProperty) {
        when (this.value) {
            null -> null
            else -> this.value.target.chapters.toList().blockingGet().filter{
                it.audio.selected.value?.value != null
            }.asObservable()
        }
    }
    val sourceChaptersProperty = objectBinding(targetChaptersProperty) {
        when (activeWorkbookProperty.value) {
            null -> null
            else -> activeWorkbookProperty.value.source.chapters.toList().blockingGet().filter { source ->
                targetChaptersProperty.value?.find { chapterEquals(it, source) } != null
            }.asObservable()
        }
    }

    // Properties of active chapter
    val targetChapterProperty = SimpleObjectProperty<Chapter>()
    val chapter: Chapter?
        get() = targetChapterProperty.value
    val sourceChapterProperty = objectBinding(targetChapterProperty) {
        sourceChaptersProperty.value?.find { chapterEquals(it, this.value) }
    }
    val chapterTitleProperty = stringBinding(targetChapterProperty) {
        "${this.value?.label?.capitalizeString()} ${this.value?.title}"
    }
    val chapterTake
        get() = targetChapterProperty.value?.audio?.selected?.value?.value
    val questionsProperty = objectBinding(sourceChapterProperty) {
        when (this.value) {
            null -> null
            else -> questionsDedup((this.value as Chapter).chunks.toList().blockingGet().mapNotNull(::questionFromChunk)).asObservable()
        }
    }
    val takeFileProperty = stringBinding(targetChapterProperty) {
        chapterTake?.file?.absolutePath
    }


    init {
        activeWorkbookProperty.onChange {
            println("Active Workbook Changed: ${it?.target?.slug}")
            if (it == null) targetChapterProperty.set(null)
        }
        targetChapterProperty.onChange { println("Active Chapter Changed: ${it?.title}") }
        initTranslations()
    }

    private fun initTranslations() {
        workbookRepo.getProjects().subscribe { sources ->
            sources.forEach { workbook ->
                val tCard = tCardFromWB(workbook)
                if (tCard.projects.size > 0) {
                    val found = tCards.find { card -> card == tCard }
                    if (found == null) {
                        tCards.add(tCard)
                    } else {
                        found.projects.addAll(tCard.projects)
                    }
                }
            }
            tCards.forEach { it.projects.sortBy { it.source.sort } }
            tCards.sortByDescending { it.projects.size }
        }
    }
}