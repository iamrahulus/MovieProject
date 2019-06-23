package com.demo.projects.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.demo.projects.Movie;

public class DDBMovieAccess {

	AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

	public DDBMovieAccess() {
	}

	public void addMovie(Movie movie) {
		ddb.putItem(arg0);
	}

	private PutItemRequest getPutItemRequest(Movie m) {
		PutItemRequest r = new PutItemRequest();
		r.addItemEntry("M_ID", new AttributeValue(m.getId()));
		r.addItemEntry(key, m.getTitle())
	}

}
