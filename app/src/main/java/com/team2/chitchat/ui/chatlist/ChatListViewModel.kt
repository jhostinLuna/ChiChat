package com.team2.chitchat.ui.chatlist

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.team2.chitchat.data.domain.model.chats.ListChatsModel
import com.team2.chitchat.data.domain.model.messages.GetMessagesModel
import com.team2.chitchat.data.domain.model.users.GetUserModel
import com.team2.chitchat.data.mapper.chats.ListChatsMapper
import com.team2.chitchat.data.repository.remote.response.BaseResponse
import com.team2.chitchat.data.sesion.DataUserSession
import com.team2.chitchat.data.usecase.GetChatsUseCase
import com.team2.chitchat.data.usecase.GetContactsUseCase
import com.team2.chitchat.data.usecase.GetMessagesUseCase
import com.team2.chitchat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val application: Application,
    private val dataUserSession: DataUserSession,
    private val getChatsUseCase: GetChatsUseCase,
    private val getUserUseCase: GetContactsUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
) :
    BaseViewModel() {
    private val chatsMutableSharedFlow: MutableSharedFlow<ArrayList<ListChatsModel>> =
        MutableSharedFlow()
    val chatsSharedFlow: SharedFlow<ArrayList<ListChatsModel>> = chatsMutableSharedFlow


    private suspend fun getUsers(): ArrayList<GetUserModel> {
        var listUsers: ArrayList<GetUserModel> = ArrayList()
        getUserUseCase().collect {
            listUsers = when (it) {
                is BaseResponse.Error -> {
                    ArrayList()
                }

                is BaseResponse.Success -> {
                    it.data
                }
            }
        }
        return listUsers
    }

    private suspend fun getMessages(): ArrayList<GetMessagesModel> {
        var listMessages: ArrayList<GetMessagesModel> = ArrayList()
        getMessagesUseCase().collect {
            listMessages = when (it) {
                is BaseResponse.Error -> {
                    ArrayList()
                }

                is BaseResponse.Success -> {
                    it.data
                }
            }
        }
        return listMessages
    }

    fun getChats() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingMutableSharedFlow.emit(true)
            getChatsUseCase().collect {
                when (it) {
                    is BaseResponse.Error -> {
                        loadingMutableSharedFlow.emit(false)
                        errorMutableSharedFlow.emit(it.error)
                    }

                    is BaseResponse.Success -> {
                        val listUsers: ArrayList<GetUserModel> = getUsers()
                        val listMessages: ArrayList<GetMessagesModel> = getMessages()
                        loadingMutableSharedFlow.emit(false)
                        val listChatsMapper =
                            ListChatsMapper(
                                dataUserSession,
                                it.data,
                                listUsers,
                                listMessages,
                                application
                            )
                        chatsMutableSharedFlow.emit(listChatsMapper.getList())
                    }
                }
            }
        }
    }
}