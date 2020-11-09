import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import lib.XMLtoMongoHandler
import org.litote.kmongo.KMongo.createClient
import org.slf4j.LoggerFactory
import org.xml.sax.InputSource
import java.util.zip.ZipFile
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

object Mongo {
    val client = createClient(MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(
            System.getenv("MONGO_CONNECTION")
        ))
        .retryWrites(true)
        .build())
}

fun main(args: Array<String>) {

    val logger = LoggerFactory.getLogger("App")
    logger.info("Start App!!")


    ZipFile("data.zip").use {
        val entry = it
            .entries()
            .asSequence()
            .first { entry -> entry.name.contains("export.xml") }


        val factory = SAXParserFactory.newInstance()
        val parser: SAXParser = factory.newSAXParser()
        val handler = XMLtoMongoHandler()
        parser.parse(InputSource(it.getInputStream(entry)), handler)
    }
    logger.info("End App!!")
}
