package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.common.data.workbook.Chapter
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class ChapterListCellFragment: ListCellFragment<Chapter>() {
    private val wbDataStore: WorkbookDataStore by inject()

    private val chapterTitleProperty = stringBinding(itemProperty) { this.value?.title }

    override val root = vbox {
        button(chapterTitleProperty) {
            action {
                wbDataStore.activeChapterProperty.set(item)
            }
        }
    }
}