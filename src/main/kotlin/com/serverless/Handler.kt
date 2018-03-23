package com.serverless

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.KeyAttribute
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Logger
import java.util.*

class Handler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {

        val dynamoDb = DynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build())
        val table = dynamoDb.getTable("Person")
        val item = table.getItem("id", "1")
        println(item.toJSONPretty())
        LOG.info("item is {}" + item.toJSONPretty())


        BasicConfigurator.configure()
        LOG.info("received: " + input.keys.toString())

        val responseBody = Response("Hey baby", input)
        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = responseBody
            headers = Collections.singletonMap<String, String>("X-Powered-By", "AWS Lambda & serverless")
        }
    }

    companion object {
        private val LOG = Logger.getLogger(Handler::class.java)
    }
}