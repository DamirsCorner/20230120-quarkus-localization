package org.example

import io.vertx.ext.web.RoutingContext
import java.util.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

private val fallbackLocale = Locale.forLanguageTag("en")

@Path("/hello")
class GreetingResource(
    private val routingContext: RoutingContext
) {

    private fun getMessage(key: String): String {
        val messages = ResourceBundle.getBundle("messages", object: ResourceBundle.Control() {
            override fun getCandidateLocales(baseName: String?, locale: Locale?): List<Locale> {
                return routingContext.acceptableLanguages().map { Locale.forLanguageTag(it.tag()) } + fallbackLocale
            }
        })
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