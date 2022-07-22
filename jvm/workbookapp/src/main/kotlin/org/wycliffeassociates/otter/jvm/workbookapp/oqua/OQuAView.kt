package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import javafx.scene.layout.Priority
import tornadofx.*

class OQuAView : View() {
    private val viewModel: OQuAViewModel by inject()
    private val homeView: HomeView by inject()
    private val projectView: ProjectView by inject()
    private val chapterView: ChapterView by inject()

    private val view = SimpleObjectProperty<Node>(find(HomeView::class).root)

    init {
        viewModel.activeWorkbookProperty.onChange { updateView() }
        viewModel.targetChapterProperty.onChange { updateView() }
    }

    private fun updateView() {
        if (viewModel.workbook == null) { view.set(homeView.root) }
        else if (viewModel.chapter == null) { view.set(projectView.root) }
        else { view.set(chapterView.root) }
    }

    override val root = borderpane {
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        top<NavBar>()
        centerProperty().bind(view)
    }
}