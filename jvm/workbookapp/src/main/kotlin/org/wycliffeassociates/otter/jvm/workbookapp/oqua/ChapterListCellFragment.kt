package org.wycliffeassociates.otter.jvm.workbookapp.oqua

import org.wycliffeassociates.otter.common.data.workbook.Chapter
import org.wycliffeassociates.otter.jvm.workbookapp.persistence.DirectoryProvider
import org.wycliffeassociates.otter.jvm.workbookapp.ui.viewmodel.WorkbookDataStore
import tornadofx.*

class ChapterListCellFragment: ListCellFragment<Chapter>() {
    private val directoryProvider = DirectoryProvider("oqua")
    private val wbDataStore: WorkbookDataStore by inject()
    //private val gradeRepo: GradeRepository = GradeRepository(directoryProvider, wbDataStore)

    private val chapterTitleProperty = stringBinding(itemProperty) { this.value?.title }

    override val root = borderpane {
        left = button(chapterTitleProperty) {
            action {
                wbDataStore.activeChapterProperty.set(item)
            }
        }
//        right = vbox {
//            addClass("progress-marker")
//            println(hasClass("progress-marker"))
//            itemProperty.onChange { chapter ->
//                chapter?.let {
//                    if (gradeRepo.isFinished(chapter.sort)) {
//                        addClass("active")
//                    }
//                }
//            }
//        }
    }
}