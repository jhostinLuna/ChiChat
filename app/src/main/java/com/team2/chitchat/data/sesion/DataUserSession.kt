package com.team2.chitchat.data.sesion

import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataUserSession @Inject constructor() : Serializable {
    private var nameUser: String? = null
    var token: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1MSIsImlhdCI6MTcxODQ2Nzg3OCwiZXhwIjoxNzIxMDU5ODc4fQ.ctk9o4EMWlfoURV2rx2mIyw9sKkwS_TsLHcLcwPnQVg"
}