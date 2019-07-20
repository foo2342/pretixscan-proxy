package eu.pretix.pretixscan.scanproxy.endpoints

import eu.pretix.pretixscan.scanproxy.SyncFailedException
import eu.pretix.pretixscan.scanproxy.UnconfiguredException
import eu.pretix.pretixscan.scanproxy.syncAllEvents
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.InternalServerErrorResponse
import io.javalin.http.ServiceUnavailableResponse
import org.slf4j.LoggerFactory


object SyncNow : Handler {
    private val LOG = LoggerFactory.getLogger(SyncNow::class.java)

    override fun handle(ctx: Context) {
        try {
            syncAllEvents(true)
        } catch (e: UnconfiguredException) {
            throw ServiceUnavailableResponse("Not configured")
        } catch (e: SyncFailedException) {
            throw InternalServerErrorResponse(e.message ?: "Sync failed")
        }
    }
}

