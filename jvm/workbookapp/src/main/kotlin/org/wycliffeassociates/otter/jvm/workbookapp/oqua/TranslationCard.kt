package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.collections.ObservableList
import org.wycliffeassociates.otter.common.data.workbook.Translation
import org.wycliffeassociates.otter.common.data.workbook.Workbook
import tornadofx.*

fun tCardFromWB (workbook: Workbook): TranslationCard {
    val translation = Translation(workbook.source.language, workbook.target.language, null)
    val projects = observableListOf<Workbook>()
    if (workbook.target.chapters.toList().blockingGet().any {
        it.audio.selected.value?.value != null
    }) {
        projects.add(workbook)
    }
    return TranslationCard(translation, projects)
}

class TranslationCard (
    val translation: Translation,
    val projects: ObservableList<Workbook>
) {
    override fun equals(other: Any?): Boolean =
        ((other is TranslationCard) && (translation == other.translation))
}