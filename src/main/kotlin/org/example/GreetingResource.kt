package org.example

import io.vertx.ext.web.RoutingContext
import java.util.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
class GreetingResource(
    private val routingContext: RoutingContext
) {

    private fun getMessage(key: String): String {
        val locale = Locale.forLanguageTag(routingContext.preferredLanguage()?.tag() ?: "en")
        val messages = ResourceBundle.getBundle("messages", locale)
        return try {
            messages.getString(key)
        } catch (e: MissingResourceException) {
            key
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = getMessage("greeting")
}