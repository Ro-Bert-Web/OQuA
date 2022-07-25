package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import javafx.beans.property.SimpleObjectProperty
import org.wycliffeassociates.otter.common.device.IAudioPlayer
import org.wycliffeassociates.otter.jvm.workbookapp.di.IDependencyGraphProvider
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.SettingsViewModel
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class ChapterViewModel : ViewModel() {
    val wbDataStore: WorkbookDataStore by inject()
    val settingsViewModel: SettingsViewModel by inject()

    val questionsProperty = objectBinding(wbDataStore.activeChapterProperty) {
        value?.let {
            wbDataStore.getSourceChapter().blockingGet()?.let { chapter ->
                questionsDedup(chapter.chunks.toList().blockingGet().mapNotNull(::questionFromChunk)).asObservable()
            }
        }
    }
    val audioPlayerProperty = SimpleObjectProperty<IAudioPlayer?>(null)

    fun dock() {
        wbDataStore.chapter.audio.selected.value?.value?.let { take ->
            val audioPlayer = (app as IDependencyGraphProvider).dependencyGraph.injectPlayer()
            audioPlayer.load(take.file)
            audioPlayerProperty.set(audioPlayer)
        }
    }
    fun undock() {}
}