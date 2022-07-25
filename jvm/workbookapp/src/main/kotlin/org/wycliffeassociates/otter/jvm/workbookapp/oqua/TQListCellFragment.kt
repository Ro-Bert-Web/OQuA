package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.scene.control.ToggleGroup
import tornadofx.*

class TQListCellFragment: ListCellFragment<Question>() {
    private val toggleGroup = ToggleGroup()

    val questionProperty = stringBinding(itemProperty) {
        when (this.value) {
            null -> null
            else -> this.value?.question
        }
    }
    val answerProperty = stringBinding(itemProperty) {
        when (this.value) {
            null -> null
            else -> this.value?.answer
        }
    }
    val verseProperty = stringBinding(itemProperty) {
        when (this.value) {
            null -> null
            else -> {
                if (this.value.start == this.value.end) {
                    "Verse ${this.value.start}"
                } else {
                    "Verses ${this.value.start} - ${this.value.end}"
                }
            }
        }
    }

    override val root = vbox {
        text(verseProperty)
        text(questionProperty)
        vbox {
            text(answerProperty)
            hbox {
                val correct = togglebutton("Correct", toggleGroup) {
                    action {
                        item.result = "correct"
                    }
                }
                val incorrect = togglebutton("Incorrect", toggleGroup) {
                    action {
                        item.result = "incorrect"
                    }
                }
                val invalid = togglebutton("Invalid", toggleGroup) {
                    action {
                        item.result = "invalid"
                    }
                }

                itemProperty.onChange {
                    when (it?.result) {
                        "correct" -> toggleGroup.selectToggle(correct)
                        "incorrect" -> toggleGroup.selectToggle(incorrect)
                        "invalid" -> toggleGroup.selectToggle(invalid)
                        else -> toggleGroup.selectToggle(null)
                    }
                }
            }
        }
    }
}