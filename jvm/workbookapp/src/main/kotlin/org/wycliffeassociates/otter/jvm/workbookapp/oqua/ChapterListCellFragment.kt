package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.common.data.workbook.Chapter
import tornadofx.*

class ChapterListCellFragment: ListCellFragment<Chapter>() {
    private val viewModel: OQuAViewModel by inject()

    private val chapterTitleProperty = stringBinding(itemProperty) { this.value?.title }

    override val root = vbox {
        button(chapterTitleProperty) {
            action {
                viewModel.targetChapterProperty.set(item)
            }
        }
    }
}