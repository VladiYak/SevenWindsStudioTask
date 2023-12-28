package com.vladiyak.sevenwindsstudiotask.utils

import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token

object TokenInstance {

    private var myToken: Token? = Token("", 0)

    fun addToken(token: String): Token? {
        myToken?.token = token
        return myToken
    }

    fun getToken(): Token? {
        return myToken
    }
}