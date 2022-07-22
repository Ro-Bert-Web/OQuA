package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.scene.layout.Priority
import tornadofx.*

class HomeView : View() {
    private val viewModel: OQuAViewModel by inject()

    override val root = listview(viewModel.tCards) {
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS
        cellFragment(TCardListCellFragment::class)
    }
}