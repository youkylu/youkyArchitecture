package com.example.commonlib.bean

import java.io.Serializable

/**
 * <p>Class: com.example.commonlib.bean.FileEntity</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/27/11:32
 */

data class FileEntity(
    var filePath: String = "", var fileName: String = "",
    var fileSize: String = "", var fileType: Type = Type.FILE
) :Serializable{

    enum class Type {
        FOLDER, FILE
    }

}

