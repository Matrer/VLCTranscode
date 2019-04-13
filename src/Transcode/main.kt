package Transcode

import MyForm
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileSystemView


fun main()
{
    MyForm()
}

fun getFile():String
{
    val chooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
    chooser.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
    chooser.fileFilter = object:FileFilter() {
        override fun accept(f: File): Boolean {
            if (f.isDirectory) {
                return true
            } else {
                val filename = f.name.toLowerCase()
                return filename.endsWith(".mov")
                        || filename.endsWith(".avi")
                        || filename.endsWith(".mp4")
                        || filename.endsWith(".ts")
            }
        }

        override fun getDescription(): String {
            return "Video formats"
        }
    }

    val wynik = chooser.showDialog(null,"Select")
    when (wynik) {
        JFileChooser.APPROVE_OPTION -> return """"${chooser.selectedFile.path}""""
        JFileChooser.CANCEL_OPTION -> return ""
    }
    return ""
}

