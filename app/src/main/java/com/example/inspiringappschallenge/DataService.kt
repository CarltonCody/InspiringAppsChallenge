package com.example.inspiringappschallenge

import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.doAsync
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DataService {

    //Listed data to present.
    var presentationData = mutableListOf<DisplayItem>()


    init {
        val logURl = "https://files.inspiringapps.com/IAChallenge/30E02AAA-B947-4D4B-8FB6-9C57C43872A9/Apache.log"
        getLog(url = logURl)
    }

    private fun getLog(url:String) {
        //Using anko due to AsyncTask being deprecated.
        doAsync {
            try {
                val logData = URL(url).readText()
                parseLog(logData = logData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseLog(logData:String){
        //found this regex from researching how to parse an apache log.
        val logRegex = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})] \"(\\S+) (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)"

        //List to hold the parsed data.
        val extractedDataList = mutableListOf<LogItem>()

        //Splitting the logDataBlock by line to extract the data needed for sorting
        logData.lines().forEach { logItem ->
            /*
            * Looping through logdata and extracting via regex the parts of the server log
            * to make it easier for sorting.
            * */
            if (logItem.isNotEmpty()){
                val match = Regex(logRegex).find(logItem)!!
                val userIP = match.groups[1]
                val dateStamp = match.groups[4]
                val endPoint = match.groups[6]

                extractedDataList.add(element = LogItem(userIP = userIP!!.value, logDateTime = dateStamp!!.value, endPoint = endPoint!!.value))
            }
        }

        //Time to sort.
        sortData(extractedDataList)

    }

    private fun sortData(dataList:List<LogItem>){

        //List for holding all the occurrences of 3 consecutive requests.
        val sequenceThreeItems = mutableListOf<LogItem>()

        //List for getting the sorted by ip address data to add up the number of requested by ip.
        val sortedData = HashMap<String, ArrayList<String>>()

        dataList.forEachWithIndex { i, logItem ->
            //looping through dataList.
            if(i <= dataList.size){
                //Getting the next value in the list.
                if(i+1 <= dataList.size){
                    //Looking to the next value after index + 1.
                    if (i+2 <= dataList.size){
                        //Checking if the logItem is equal to the next two items and adding it to the sequence of three.
                        if (logItem.userIP == dataList[i+1].userIP && logItem.userIP == dataList[i+2].userIP){
                            sequenceThreeItems.add(logItem)
                            sequenceThreeItems.add(dataList[i+1])
                            sequenceThreeItems.add(dataList[i+2])
                        }
                    }
                }
            }
        }

        sequenceThreeItems.sortBy { it.userIP  }

        //A placeholder to get distinct endpoints later.
        val preData = mutableListOf<DisplayItem>()

        //Looping to get each logItem.
        sequenceThreeItems.forEach{ logItem ->

            //checking to see if the key exists in the map.
            if (!sortedData.containsKey(logItem.userIP)){

                //If it doesn't exist add it.
                sortedData[logItem.userIP] = ArrayList()
            }

            //Else it adds the endpoint to value list based on userIP.
            sortedData[logItem.userIP]?.add(logItem.endPoint)
        }

        sortedData.forEach{
            for (item in it.value){

                //Looping through map to add to list. While determining how many times the IP has requested the endpoint.
                preData.add(DisplayItem(userIP = it.key, endPoint = item, freqRequests = Collections.frequency(it.value, item)  ))

            }
        }

        //Getting the distinct endpoints so remove duplicates because it shows a readout for each item in the list.
        val distinct = preData.distinctBy { it.endPoint }
        //Sorting the number of endpoint requests showing most common to least common.
        presentationData.addAll(distinct.sortedByDescending { it.freqRequests })

    }

}