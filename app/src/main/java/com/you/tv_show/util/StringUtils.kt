package com.you.tv_show.util

/**
 * Created by Administrator on 2017/6/28.
 */
class StringUtils private constructor()
{

    init
    {
        throw AssertionError()
    }

    companion object
    {

        fun isBlank(str: CharSequence): Boolean
        {
            return isEmpty(str) || str.toString().trim { it <= ' ' }.length == 0
        }


        fun isEmpty(str: CharSequence?): Boolean
        {
            return str == null || str.length == 0
        }

        fun length(str: CharSequence?): Int
        {
            return str?.length ?: 0
        }


        fun isNotBlank(str: Any?): Boolean
        {
            if (str == null)
            {
                return false
            }
            else
            {
                if (str.toString().trim { it <= ' ' }.length == 0)
                {
                    return false
                }
            }
            return true
        }

        fun equals(str1: String?, str2: String?): Boolean
        {

            return if (str1 != null) str1 == str2 else str2 == null

        }
    }

}