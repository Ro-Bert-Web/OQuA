package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class ProjectViewModel: ViewModel() {
    private val wbDataStore: WorkbookDataStore by inject()

    val chaptersProperty = objectBinding(wbDataStore.activeWorkbookProperty) {
        value?.target?.chapters?.toList()?.blockingGet()?.filter { chapter ->
            chapter.audio.selected.value?.value != null
        }?.asObservable()
    }

    fun dock() {}
    fun undock() {}
}