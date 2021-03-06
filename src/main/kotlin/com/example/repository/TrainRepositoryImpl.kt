package com.example.repository

import com.example.models.ApiResponse
import com.example.models.Train

const val PREV_PAGE_KEY = "prevPage"
const val NEXT_PAGE_KEY = "nextPage"

class TrainRepositoryImpl : TrainRepository {

    override val trains: Map<Int, List<Train>> by lazy {
        mapOf(
            1 to page1,
            2 to page2,
            3 to page3,
        )
    }


    override val page1: List<Train> = listOf(
        Train(
            id = 1,
            model = "Agniveena Express",
            image = "/images/agniveena_express.jpg",
            about = "In Bangla it means “The Fiery Lute”. This is the name given to the collection of poems by the celebrated Bengali poet, musician, revolutionary and philosopher, Kazi Nazrul Islam. He was born in Burdwan district in 1899 and died in Dhaka in 1976."
        ),
        Train(
            id = 2,
            model = "Ahilyanagari Express",
            image = "/images/ahilyanagari_express.jpg",
            about = "Rajmata Ahilyadevi Holkar (1725-1795, ruled 1767-1795) also known as the Philosopher Queen was a Holkar dynasty Queen of the Malwa kingdom. She took over reigns of the kingdom after the death of her husband and father-in-law. She moved the capital to Maheshwar south of Indore on the Narmada River."
        ),
        Train(
            id = 3,
            model = "Ahimsa Express",
            image = "/images/ahimsa_express.jpg",
            about = "The name is also sometimes given to 1087/ 1088 Veraval – Pune Express, 1089/ 1090 Jodhpur – Pune Express and 1091/ 1092 Bhuj – Pune Express, as all these trains are “derived” from 1095/ 1096."
        ),
    )
    override val page2: List<Train> = listOf(
        Train(
            id = 4,
            model = "Ajanta Express",
            image = "/images/ajanta_express.jpg",
            about = "The Ajanta Caves are rock-cut cave monuments dating from the 2nd century BC, containing paintings and sculpture considered to be masterpieces of both Buddhist religious art and universal pictorial art."
        ),
        Train(
            id = 5,
            model = "Akal Takht Express",
            image = "/images/akal_takht_express.jpg",
            about = "The Akal Takht (Akal: Timeless One and Takht: Throne in Persian). Literally means “Seat (Throne) of the Timeless One (God)”."
        ),
        Train(
            id = 6,
            model = "Ala Hazrat Express",
            image = "/images/ala_hazrat_express.jpg",
            about = "Imam Ahmad Raza Khan, popularly known as Ala Hazrat, was a prominent Muslim Alim from Bareilly during the late 19th and early 20th centuries"
        ),
    )
    override val page3: List<Train> = listOf(
        Train(
            id = 7,
            model = "Amarkantak Epress",
            image = "/images/amarkantak_epress.jpg",
            about = "Mata Amritanandamayee Devi, born Sudhamani in 1953 is a Hindu spiritual leader revered as a saint by her followers, who also know her as Amma, Ammachi or Mother."
        ),
        Train(
            id = 8,
            model = "Arunachal Xpress",
            image = "/images/arunachal_xpress.jpg",
            about = "Amarkantak is a pilgrim town in Anuppur district in Madhya Pradesh. It is the meeting point of the Vindhyas and the Satpuras with the Maikal Hills being the fulcrum."
        ),
        Train(
            id = 9,
            model = "Stefano Lombardo",
            image = "/images/stefano_lombardo.jpg",
            about = "The Amarnath caves are one of the most famous shrines in Hinduism, dedicated to the god Shiva (Amarnath: “the Eternal Lord”), located 141 km from Srinagar"
        ),
    )


    override suspend fun getAllTrains(page: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = "OK",
            prevPage = calculatePage(page)[PREV_PAGE_KEY],
            nextPage = calculatePage(page)[NEXT_PAGE_KEY],
            trains = trains[page]!!,
            lastUpdate = System.currentTimeMillis()
        )
    }

    override suspend fun searchTrains(model: String?): ApiResponse {
        return ApiResponse(
                success = true,
        message = "OK",
        trains = findTrains(model)
        )
    }


    private fun calculatePage(page: Int) =
        mapOf<String, Int?>(
            PREV_PAGE_KEY to if (page in 2..3) page.minus(1) else null,
            NEXT_PAGE_KEY to if (page in 1..2) page.plus(1) else null
        )
    private fun findTrains(query: String?): List<Train> {

        val searchResult = mutableListOf<Train>()

        return if (!query.isNullOrEmpty()) {
            trains.forEach { (_, trains) ->
                trains.forEach { train ->
                    if (train.model.lowercase().contains(query.lowercase())) {
                        searchResult.add(train)
                    }
                }
            }
            searchResult
        } else {
            return emptyList()
        }
    }
}