package com.logihub.ui.activity.invoice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.logihub.R
import com.logihub.model.response.Page
import com.logihub.model.response.ShortInvoiceDTO
import com.logihub.service.ApiServiceImpl
import com.logihub.ui.activity.general.MainPage
import com.logihub.ui.adapter.InvoiceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InvoicesPage : Fragment() {

    private val activity: Fragment = this@InvoicesPage
    private val apiService = ApiServiceImpl()
    private lateinit var userId: String
    private lateinit var token: String
    private lateinit var invoicesIds: ArrayList<Long>
    private lateinit var dates: ArrayList<String>
    private lateinit var prices: ArrayList<Double>
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchField: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var resetButton: ImageButton
    private lateinit var invoiceAdapter: InvoiceAdapter
    private var type: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.activity_invoices_page, container, false)
        init(v)
        return v
    }

    private fun init(v: View) {
        userId = MainPage.getUserId().toString()
        token = MainPage.getToken().toString()

        invoicesIds = ArrayList()
        dates = ArrayList()
        prices = ArrayList()

        tabLayout = v.findViewById(R.id.tabs);

        recyclerView = v.findViewById(R.id.recycle_view)
        searchField = v.findViewById(R.id.searchField)
        searchButton = v.findViewById(R.id.searchButton)
        resetButton = v.findViewById(R.id.resetButton)

        searchButton.let { s ->
            s.setOnClickListener {
                clearRecycleView()
                when (type) {
                    1 -> displayAllData()
                    2 -> displayNotSignedByPark()
                    3 -> displayNotSignedByTruck()
                    4 -> displaySigned()
                    else -> {
                        displayAllData()
                    }
                }
                setAdapterOnRecycleView()
            }
        }

        resetButton.let { s ->
            s.setOnClickListener {
                searchField.setText("")
                clearRecycleView()
                when (type) {
                    1 -> displayAllData()
                    2 -> displayNotSignedByPark()
                    3 -> displayNotSignedByTruck()
                    4 -> displaySigned()
                    else -> {
                        displayAllData()
                    }
                }
                setAdapterOnRecycleView()
            }
        }

        displayAllData()

        selectingTabs()
    }

    private fun setAdapterOnRecycleView() {
        invoiceAdapter = InvoiceAdapter(
            getActivity(), invoicesIds, dates, prices
        )

        recyclerView.adapter = invoiceAdapter

        recyclerView.layoutManager = LinearLayoutManager(getActivity())
    }

    private fun displayAllData() {
        var search = ""

        if (searchField.text.isNotEmpty()) {
            search = searchField.text.toString()
        }

        if (search == "") {
            apiService.getInvoices("Bearer " + token, userId.toLong(), object :
                Callback<Page<List<ShortInvoiceDTO>>> {
                override fun onResponse(
                    call: Call<Page<List<ShortInvoiceDTO>>>,
                    response: Response<Page<List<ShortInvoiceDTO>>>
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

                override fun onFailure(call: Call<Page<List<ShortInvoiceDTO>>>, t: Throwable) {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            })
        } else {
            apiService.getInvoicesSearch("Bearer " + token, userId.toLong(), search, object :
                Callback<Page<List<ShortInvoiceDTO>>> {
                override fun onResponse(
                    call: Call<Page<List<ShortInvoiceDTO>>>,
                    response: Response<Page<List<ShortInvoiceDTO>>>
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

                override fun onFailure(call: Call<Page<List<ShortInvoiceDTO>>>, t: Throwable) {
                    Toast.makeText(getActivity(), "Something went wrong3!", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }

    private fun displayNotSignedByPark() {
        var search = ""

        if (searchField.text.isNotEmpty()) {
            search = searchField.text.toString()
        }

        apiService.getNotSignedByParkingManagerInvoices("Bearer " + token,
            userId.toLong(), search, object :
                Callback<Page<List<ShortInvoiceDTO>>> {
                override fun onResponse(
                    call: Call<Page<List<ShortInvoiceDTO>>>,
                    response: Response<Page<List<ShortInvoiceDTO>>>
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

                override fun onFailure(call: Call<Page<List<ShortInvoiceDTO>>>, t: Throwable) {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun displayNotSignedByTruck() {
        var search = ""

        if (searchField.text.isNotEmpty()) {
            search = searchField.text.toString()
        }

        apiService.getNotSignedByTruckManagerInvoices("Bearer " + token, userId.toLong(),
            search, object :
                Callback<Page<List<ShortInvoiceDTO>>> {
                override fun onResponse(
                    call: Call<Page<List<ShortInvoiceDTO>>>,
                    response: Response<Page<List<ShortInvoiceDTO>>>
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

                override fun onFailure(call: Call<Page<List<ShortInvoiceDTO>>>, t: Throwable) {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun displaySigned() {
        var search = ""

        if (searchField.text.isNotEmpty()) {
            search = searchField.text.toString()
        }

        apiService.getSignedInvoices("Bearer " + token, userId.toLong(),
            search, object :
                Callback<Page<List<ShortInvoiceDTO>>> {
                override fun onResponse(
                    call: Call<Page<List<ShortInvoiceDTO>>>,
                    response: Response<Page<List<ShortInvoiceDTO>>>
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

                override fun onFailure(call: Call<Page<List<ShortInvoiceDTO>>>, t: Throwable) {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun clearRecycleView() {
        invoicesIds = ArrayList()
        dates = ArrayList()
        prices = ArrayList()
    }

    @SuppressLint("Range")
    private fun fillData(dataList: Page<List<ShortInvoiceDTO>>) {
        if (dataList.content.isEmpty()) {

        } else {
            for (item in dataList.content) {
                invoicesIds.add(item.id)
                val date = LocalDateTime.parse(item.creationDate)
                dates.add(date.format(DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")))
                prices.add(item.price)
            }
        }
    }

    private fun selectingTabs() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        clearRecycleView()
                        type = 1
                        displayAllData()
                        setAdapterOnRecycleView()
                    }

                    1 -> {
                        clearRecycleView()
                        type = 2
                        displayNotSignedByPark()
                        setAdapterOnRecycleView()
                    }

                    2 -> {
                        clearRecycleView()
                        type = 3
                        displayNotSignedByTruck()
                        setAdapterOnRecycleView()
                    }

                    3 -> {
                        clearRecycleView()
                        type = 4
                        displaySigned()
                        setAdapterOnRecycleView()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}