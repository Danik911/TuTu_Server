package com.example.routs

import com.example.models.ApiResponse
import com.example.repository.TrainRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.getAllTrains(app: Application) {

    val trainRepository: TrainRepository by inject()

    get("/tutu/trains") {

        app.log.info("GET ALL HEROES CALLED")

        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..3)

            val apiResponse = trainRepository.getAllTrains(page = page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: java.lang.NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Invalid request, only numbers are accepted"),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: java.lang.IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Only numbers from 1 to 5 are accepted"),
                status = HttpStatusCode.NotFound
            )
        }
    }
}