package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.common.utils.capitalizeString
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class OQuAViewModel: ViewModel() {
    val wbDataStore: WorkbookDataStore by inject()
    private val workbookRepo = (app as OQuAApp).dependencyGraph.injectWorkbookRepository()

    val tCards = observableListOf<TranslationCard>()

    // Properties of active workbook
    val projectTitleProperty = stringBinding(wbDataStore.activeWorkbookProperty) {
        value?.target?.title
    }
    val chaptersProperty = objectBinding(wbDataStore.activeWorkbookProperty) {
        value?.target?.chapters?.toList()?.blockingGet()?.filter { chapter ->
            chapter.audio.selected.value?.value != null
        }?.asObservable()
    }

    // Properties of active chapter
    val chapterTitleProperty = stringBinding(wbDataStore.activeChapterProperty) {
        "${value?.label?.capitalizeString()} ${value?.title}"
    }
    val questionsProperty = objectBinding(wbDataStore.activeChapterProperty) {
        value?.let {
            wbDataStore.getSourceChapter().blockingGet()?.let { chapter ->
                questionsDedup(chapter.chunks.toList().blockingGet().mapNotNull(::questionFromChunk)).asObservable()
            }
        }
    }
    val takeProperty = objectBinding(wbDataStore.activeChapterProperty) {
        value?.let { chapter ->
            chapter.audio.selected.value?.value
        }
    }


    init {
        wbDataStore.activeWorkbookProperty.onChange {
            println("Active Workbook Changed: ${it?.target?.slug}")
            if (it == null) wbDataStore.activeChapterProperty.set(null)
        }
        wbDataStore.activeChapterProperty.onChange { println("Active Chapter Changed: ${it?.title}") }
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
                tCards.forEach { card -> card.projects.sortBy { workbook -> workbook.source.sort } }
                tCards.sortByDescending { card -> card.projects.size }
            }
        }
    }
}