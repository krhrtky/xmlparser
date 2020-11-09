package lib

import model.BodyTemperature
import model.StepCount
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.litote.kmongo.getCollection
import org.slf4j.LoggerFactory
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class XMLtoMongoHandler: DefaultHandler() {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val database = Mongo.client.getDatabase("test")

    private val dateTimeFormatPattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZ")

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        logger.info("Start Element. qName: $qName")

        if (attributes?.length == null) {
            logger.warn("Element has no attribute. qName: $qName")
            return
        }

        when(attributes.getValue("type")) {
            "HKQuantityTypeIdentifierStepCount" -> {
                val stepCount = StepCount(
                    value = attributes.getValue("value").toInt(),
                    unit = attributes.getValue("unit"),
                    creationDate = DateTime.parse(
                        attributes.getValue("creationDate"),
                        dateTimeFormatPattern
                    ).toDate(),
                    startDate = DateTime.parse(
                        attributes.getValue("startDate"),
                        dateTimeFormatPattern
                    ).toDate(),
                    endDate = DateTime.parse(
                        attributes.getValue("endDate"),
                        dateTimeFormatPattern
                    ).toDate(),
                )

                database.getCollection<StepCount>().insertOne(stepCount)
            }

            "HKQuantityTypeIdentifierBodyTemperature" -> {
                val bodyTemperature = BodyTemperature(
                    value = attributes.getValue("value").toDouble(),
                    unit = attributes.getValue("unit"),
                    creationDate = DateTime.parse(
                        attributes.getValue("creationDate"),
                        dateTimeFormatPattern
                    ).toDate(),
                    startDate = DateTime.parse(
                        attributes.getValue("startDate"),
                        dateTimeFormatPattern
                    ).toDate(),
                    endDate = DateTime.parse(
                        attributes.getValue("endDate"),
                        dateTimeFormatPattern
                    ).toDate(),
                )

                database.getCollection<BodyTemperature>().insertOne(bodyTemperature)
            }

            else -> {
                logger.info("Type not match. qName: $qName")
            }
        }
    }
}
