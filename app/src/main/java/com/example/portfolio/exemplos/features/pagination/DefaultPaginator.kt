package com.example.portfolio.exemplos.features.pagination


class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private val getNextKey: suspend (List<Item>) -> Key,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (items: List<Item>, nextKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false


    override suspend fun loadNextItems() {

        if (isMakingRequest) return
        onLoadUpdated(true)
        try {

            val result = onRequest(currentKey)
            val items = result.getOrElse {
                onError(it)
                return
            }
            currentKey = getNextKey(items)
            onSuccess(items, currentKey)

        } finally {
            isMakingRequest = false
            onLoadUpdated(false)
        }

    }

    override fun reset() {
        currentKey = initialKey
        isMakingRequest = false
    }

}