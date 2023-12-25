package com.vladiyak.sevenwindsstudiotask.data.utils

import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token

object TokenInstance {

    private lateinit var myToken: Token

    fun addToken(token: Token): Token {
        myToken = token
        return myToken
    }

    fun getToken(): Token {
        return myToken
    }
}