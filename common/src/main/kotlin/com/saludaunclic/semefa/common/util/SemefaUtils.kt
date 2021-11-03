package com.saludaunclic.semefa.common.util

import org.apache.commons.lang3.StringUtils

object SemefaUtils {
    const val SPACES_FILL_LENGTH: Int = 10

    fun fillWithSpaces(initial: String = "", length: Int = SPACES_FILL_LENGTH, append: Boolean = true): String {
        val diff = length - initial.length
        if (diff < 0) {
            return initial
        }

        val spaces = StringUtils.repeat(StringUtils.SPACE, diff)
        return if (append) initial + spaces else spaces + initial
    }
}
