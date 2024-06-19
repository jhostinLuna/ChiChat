package com.team2.chitchat.data.repository.remote.backend

import com.team2.chitchat.data.domain.model.users.GetUserModel
import com.team2.chitchat.data.domain.model.users.PostLoginModel
import com.team2.chitchat.data.domain.model.users.PostRegisterModel
import com.team2.chitchat.data.repository.remote.request.users.LoginUserRequest
import com.team2.chitchat.data.repository.remote.request.users.RegisterUserRequest
import com.team2.chitchat.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataProvider @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DataSource {
    //RegisterUSer
    override fun postRegisterUser(registerUserRequest: RegisterUserRequest): Flow<BaseResponse<PostRegisterModel>> {
        return remoteDataSource.postRegisterUser(registerUserRequest)
    }

    //LoginUser
    override fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<PostLoginModel>> {
        return remoteDataSource.postLoginUser(loginUserRequest)
    }

    //ContactsList
    override fun getContactsList(): Flow<BaseResponse<ArrayList<GetUserModel>>> {
        return remoteDataSource.getContactsList()
    }
}