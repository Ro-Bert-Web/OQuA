package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.scene.layout.Priority
import org.wycliffeassociates.otter.common.data.workbook.Chapter
import tornadofx.*

class ProjectView : View() {
    private val viewModel: OQuAViewModel by inject<OQuAViewModel>()

    override val root = listview<Chapter> {
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS
        itemsProperty().bind(viewModel.chaptersProperty)
        cellFragment(ChapterListCellFragment::class)
    }
}