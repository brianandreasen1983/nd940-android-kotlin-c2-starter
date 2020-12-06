package com.udacity.network

import com.udacity.database.DatabaseAsteroid

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

// TODO (03) Define extension function NetworkVideoHolder.asDatabaseModel(),
// I understand what is going on at a high level but not at the pattern level.
//fun NetworkVideoContainer.asDatabaseModel(): Array<DatabaseVideo> {
//    return videos.map {
//        DatabaseVideo (
//                title = it.title,
//                description = it.description,
//                url = it.url,
//                updated = it.updated,
//                thumbnail = it.thumbnail)
//    }.toTypedArray()
//}