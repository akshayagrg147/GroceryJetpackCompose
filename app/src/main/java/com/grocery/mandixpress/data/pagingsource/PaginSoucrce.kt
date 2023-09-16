package com.grocery.mandixpress.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grocery.mandixpress.Utils.Constants
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.network.ApiService
import com.grocery.mandixpress.data.network.CallingCategoryWiseData
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PaginSoucrce @Inject constructor(
    private val apiService: ApiService,
    val calling: CallingCategoryWiseData
) :
    PagingSource<Int, HomeAllProductsResponse.HomeResponse>() {

    val INITIAL_LOAD_SIZE = 0
    override fun getRefreshKey(state: PagingState<Int, HomeAllProductsResponse.HomeResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeAllProductsResponse.HomeResponse> {
        return try {

            val position = params.key ?: 1
            return try {
                // val jsonResponse = service.getCryptoList(start = offset, limit = params.loadSize).data
                val homeproduts =
                    apiService.getHomeAllProducts(position, params.loadSize, calling.gettingData())

                val nextKey = if (homeproduts.body()?.list?.isEmpty() == true) {
                    null
                } else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + 1
                }
                Log.d("pagination_Call"," ${ homeproduts.body()?.list}\n \n akkk\n ${((position - 1) * Constants.NETWORK_PAGE_SIZE) + 1}    ${INITIAL_LOAD_SIZE}")

                LoadResult.Page(
                    data = homeproduts.body()?.list ?: emptyList(),
                    prevKey = if(position==1)null else position+1, // Only paging forward.
                    // assume that if a full page is not loaded, that means the end of the data
                    nextKey = nextKey
                )

            } catch (exception: IOException) {
                return LoadResult.Error(exception)
            } catch (exception: HttpException) {
                return LoadResult.Error(exception)
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}