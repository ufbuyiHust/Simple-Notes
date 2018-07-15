package com.simplemobiletools.notes.dialogs

import android.content.DialogInterface.BUTTON_POSITIVE
import android.support.v7.app.AlertDialog
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.notes.R
import com.simplemobiletools.notes.activities.SimpleActivity
import com.simplemobiletools.notes.extensions.dbHelper
import com.simplemobiletools.notes.models.Note
import kotlinx.android.synthetic.main.dialog_new_note.view.*
import java.io.File

//这个dialog的构造函数最后要传入一个callback, 为一个lambda
class RenameNoteDialog(val activity: SimpleActivity, val note: Note, callback: (note: Note) -> Unit) {

    init {
        val view = activity.layoutInflater.inflate(R.layout.dialog_rename_note, null)
        view.note_name.setText(note.title)

        AlertDialog.Builder(activity)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create().apply {
                    activity.setupDialogStuff(view, this, R.string.rename_note) {
                        showKeyboard(view.note_name)
                        getButton(BUTTON_POSITIVE).setOnClickListener {
                            val title = view.note_name.value
                            when {
                                title.isEmpty() -> activity.toast(R.string.no_title)
                                activity.dbHelper.doesTitleExist(title) -> activity.toast(R.string.title_taken)
                                else -> {
                                    note.title = title
                                    val path = note.path
                                    if (path.isNotEmpty()) {
                                        if (title.isEmpty()) {
                                            activity.toast(R.string.filename_cannot_be_empty)
                                            //标签返回
                                            return@setOnClickListener
                                        }

                                        val file = File(path)
                                        val newFile = File(file.parent, title)
                                        if (!newFile.name.isAValidFilename()) {
                                            activity.toast(R.string.invalid_name)
                                            return@setOnClickListener
                                        }

                                        activity.renameFile(file.absolutePath, newFile.absolutePath) {
                                            if (it) {
                                                note.path = newFile.absolutePath
                                                activity.dbHelper.updateNotePath(note)
                                            } else {
                                                activity.toast(R.string.rename_file_error)
                                                return@renameFile
                                            }
                                        }
                                    }
                                    activity.dbHelper.updateNoteTitle(note)
                                    dismiss()
                                    callback(note)
                                }
                            }
                        }
                    }
                }
    }
}
