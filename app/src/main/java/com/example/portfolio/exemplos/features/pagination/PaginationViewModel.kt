package com.example.portfolio.exemplos.features.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.exemplos.domain.pagination.ListItem
import com.example.portfolio.exemplos.domain.pagination.PaginationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


data class PaginationState(
    val isLoading: Boolean = false,
    val items: List<ListItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val pageSize: Int = 20
)

@HiltViewModel
class PaginationViewModel @Inject constructor(
    private var useCase: PaginationUseCase
) : ViewModel() {
    var state by mutableStateOf(PaginationState())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            useCase(nextPage, state.pageSize)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refresh() {
        // Reseta estado e paginação
        state = PaginationState(pageSize = state.pageSize)
        paginator.reset()
        loadNextItems()
    }
}