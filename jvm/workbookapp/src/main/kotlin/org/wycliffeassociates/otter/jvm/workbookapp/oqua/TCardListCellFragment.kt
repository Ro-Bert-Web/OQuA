package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.scene.paint.Color
import org.wycliffeassociates.otter.common.data.workbook.Workbook
import tornadofx.*
import javax.script.Bindings

class TCardListCellFragment: ListCellFragment<TranslationCard>() {
    private val sourceProperty = stringBinding(itemProperty) { this.value?.translation?.source?.name }
    private val targetProperty = stringBinding(itemProperty) { this.value?.translation?.target?.name }
    private val projects = objectBinding(itemProperty) { this.value?.projects }

    override val root = vbox {
        hbox {
            text(sourceProperty)
            text(" -> ")
            text(targetProperty)
        }
        listview<Workbook> {
            itemsProperty().bind(projects)
            cellFragment(ProjectListCellFragment::class)
        }
    }
}