package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.common.data.workbook.Workbook
import tornadofx.*

class ProjectListCellFragment: ListCellFragment<Workbook>() {
    private val viewModel: OQuAViewModel by inject()

    private val projectProperty = stringBinding(itemProperty) { this.value?.source?.title }

    override val root = vbox {
        button (projectProperty) {
            action {
                viewModel.wbDataStore.activeWorkbookProperty.set(item)
            }
        }
    }
}