package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.layout.Priority
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class OQuAView : View() {
    private val homeView = HomeView()
    private val projectView = ProjectView()
    private val chapterView = ChapterView()

    private val wbDataStore: WorkbookDataStore by inject()

    private val view = SimpleObjectProperty<View>(homeView)

    init {
        wbDataStore.activeWorkbookProperty.onChange { updateView() }
        wbDataStore.activeChapterProperty.onChange { updateView() }
        view.value?.onDock()
    }

    private fun updateView() {
        view.value?.onUndock()
        if (wbDataStore.activeWorkbookProperty.value == null) {
            view.set(homeView)
        } else if (wbDataStore.activeChapterProperty.value == null) {
            view.set(projectView)
        } else {
            view.set(chapterView)
        }
        view.value?.onDock()
    }

    override val root = borderpane {
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        top<NavBar>()
        centerProperty().bind(objectBinding(view) { value?.root })
    }
}