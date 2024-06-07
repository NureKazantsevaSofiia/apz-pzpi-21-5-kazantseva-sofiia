package com.logihub.ui.activity.truck

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.logihub.R
import com.logihub.model.response.ShortTruckDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.MainPage
import com.logihub.ui.adapter.TruckAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrucksPage : Fragment() {

    private val activity: Fragment = this@TrucksPage
    private val apiService = ApiServiceImpl()
    private lateinit var userId: String
    private lateinit var token: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var trucksIds: ArrayList<Long>
    private lateinit var numbers: ArrayList<String>
    private lateinit var emails: ArrayList<String>
    private lateinit var truckAdapter: TruckAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.activity_trucks_page, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        userId = MainPage.getUserId().toString()
        token = MainPage.getToken().toString()

        trucksIds = ArrayList()
        numbers = ArrayList()
        emails = ArrayList()

        recyclerView = v.findViewById(R.id.recycle_view)

        displayAllData()
    }

    private fun setAdapterOnRecycleView() {
        truckAdapter = TruckAdapter(
            getActivity(), trucksIds, numbers, emails
        )

        recyclerView.adapter = truckAdapter

        recyclerView.layoutManager = LinearLayoutManager(getActivity())
    }

    private fun displayAllData() {
        apiService.getTrucks("Bearer " + token, userId.toLong(), object :
            Callback<List<ShortTruckDTO>> {
            override fun onResponse(
                call: Call<List<ShortTruckDTO>>,
                response: Response<List<ShortTruckDTO>>
            ) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        clearRecycleView()
                        setAdapterOnRecycleView()
                        fillData(dataList)
                        setAdapterOnRecycleView()
                    } else {
                        Toast.makeText(
                            getActivity(), "Something went wrong!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    setAdapterOnRecycleView()
                } else {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<ShortTruckDTO>>, t: Throwable) {
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun clearRecycleView() {
        trucksIds = ArrayList()
        numbers = ArrayList()
        emails = ArrayList()
    }

    @SuppressLint("Range")
    private fun fillData(dataList: List<ShortTruckDTO>) {
        if (dataList.isEmpty()) {
            Toast.makeText(activity.context, "No data!", Toast.LENGTH_LONG).show()
        } else {
            for (item in dataList) {
                trucksIds.add(item.id)
                numbers.add(item.number)
                emails.add(item.truckManagerEmail)
            }
        }
    }
}