package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import com.github.thomasnield.rxkotlinfx.observeOnFx
import tornadofx.*

class HomeViewModel: ViewModel() {
    private val workbookRepo = (app as OQuAApp).dependencyGraph.injectWorkbookRepository()

    val tCards = observableListOf<TranslationCard>()

    init {
        getTranslations()
    }

    fun dock() {}

    fun undock() {}

    private fun getTranslations() {
        workbookRepo.getProjects().observeOnFx().subscribe { sources ->
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
            tCards.forEach { card -> card.projects.sortBy { workbook -> workbook.source.sort } }
            tCards.sortByDescending { card -> card.projects.size }
        }
    }
}