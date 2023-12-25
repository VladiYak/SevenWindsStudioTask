package com.vladiyak.sevenwindsstudiotask.data.utils

import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token

object TokenInstance {

    private var myToken: Token = Token("", 0)

    fun addToken(token: Token): Token {
        myToken = token
        return myToken
    }

    fun getToken(): Token {
        return myToken
    }
}