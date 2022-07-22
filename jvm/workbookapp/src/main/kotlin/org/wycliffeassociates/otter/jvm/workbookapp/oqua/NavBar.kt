package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import tornadofx.*

class NavBar : View() {
    private val viewModel: OQuAViewModel by inject()

    override val root = hbox(5) {
        style { padding = box(5.px) }
        button("Back") {
            action {}
        }
        button("OQuA") {
            action {
                viewModel.wbDataStore.activeWorkbookProperty.set(null)
            }
        }
        button(viewModel.projectTitleProperty){
            style { visibleWhen(booleanBinding(viewModel.projectTitleProperty) { value != null }) }
            action {
                viewModel.wbDataStore.activeChapterProperty.set(null)
            }
        }
        button(viewModel.chapterTitleProperty) {
            style { visibleWhen(booleanBinding(viewModel.wbDataStore.activeChapterProperty) { value != null }) }
        }
    }
}