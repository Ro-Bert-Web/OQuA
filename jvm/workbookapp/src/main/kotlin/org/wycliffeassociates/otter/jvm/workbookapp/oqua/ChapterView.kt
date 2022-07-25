package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.scene.layout.Priority
import org.wycliffeassociates.otter.jvm.controls.media.simpleaudioplayer
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class ChapterView : View() {
    private val viewModel: ChapterViewModel by inject()
    private val wbDataStore: WorkbookDataStore by inject()

    override fun onDock() {
        super.onDock()
        viewModel.dock()
        println("Docked Chapter View")
    }

    override fun onUndock() {
        super.onUndock()
        viewModel.undock()
        println("Undocked Chapter View")
    }

    override val root = vbox {
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        simpleaudioplayer {
            hgrow = Priority.ALWAYS
            playerProperty.bind(viewModel.audioPlayerProperty)
            visibleWhen(playerProperty.isNotNull)
            managedProperty().bind(visibleProperty())
        }
        listview<Question> {
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
            itemsProperty().bind(viewModel.questionsProperty)
            cellFragment(TQListCellFragment::class)
        }
    }
}