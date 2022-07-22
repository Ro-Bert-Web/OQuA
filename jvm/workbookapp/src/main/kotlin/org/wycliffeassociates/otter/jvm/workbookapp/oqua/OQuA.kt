package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.stage.Stage
import org.wycliffeassociates.otter.jvm.workbookapp.di.DaggerAppDependencyGraph
import tornadofx.*

fun main() {
    launch<OQuAApp>()
}

class OQuAApp : App(OQuAView::class) {
    val dependencyGraph = DaggerAppDependencyGraph.builder().build()

    override fun start(stage: Stage) {
        super.start(stage)

        stage.isMaximized = true
        stage.minWidth = 300.0
        stage.minHeight = 350.0
    }
}