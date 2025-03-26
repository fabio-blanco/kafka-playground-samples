package br.com.globalbyte.sample.kafka.kfs.producer

import br.com.globalbyte.sample.kafka.kfs.utils.KafkaTopicConstants
import br.com.globalbyte.sample.kafka.kfs.utils.OddEventRandomizer
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

class App: CliktCommand() {
    private val message by argument("<message>")
    private val partition by option("-p", "--partition", help = "Partition to send the message (1, 2, 3)")
        .int().default(1).check("Must be 1, 2 or 3") { it in 1..3 }
    private val randomMessages by option("-r", "--random", help = "Generate random messages")
        .int().default(0)

    private val producerProps = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaTopicConstants.KAFKA_BOOTSTRAP_SERVER)
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    }

    override fun run() {
        val kafkaProducer = KafkaProducer<Int, String>(producerProps)
        val record = ProducerRecord(KafkaTopicConstants.KAFKA_TOPIC_NAME, partition, message)

        kafkaProducer.send(record)

        if (randomMessages > 0) {
            produceRandomMessages(kafkaProducer, randomMessages)
        }

        kafkaProducer.close()
    }

    private fun produceRandomMessages(kafkaProducer: KafkaProducer<Int, String>, count: Int) {
        for (i in 1..count) {
            val randomMessage = OddEventRandomizer.getTimedRandomEvent()
            val record = ProducerRecord(KafkaTopicConstants.KAFKA_TOPIC_NAME, partition, randomMessage)
            kafkaProducer.send(record)
        }
    }
}

fun main(args: Array<String>) = App().main(args)