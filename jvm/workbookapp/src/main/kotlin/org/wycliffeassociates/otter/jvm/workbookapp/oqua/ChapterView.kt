package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.scene.layout.Priority
import tornadofx.*

class ChapterView : View() {
    private val viewModel: OQuAViewModel by inject()

    override val root = vbox {
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS
        text (
            stringBinding(viewModel.takeProperty) {
                value?.file?.absolutePath
            }
        )

//        simpleaudioplayer {
//            hgrow = Priority.ALWAYS
//
//            viewModel.takeModelProperty.onChange { take ->
//                take?.let {
//                    playerProperty.set(take.audioPlayer)
//                }
//            }
//        }
        listview<Question> {
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
            itemsProperty().bind(viewModel.questionsProperty)
            cellFragment(TQListCellFragment::class)
        }
    }
}