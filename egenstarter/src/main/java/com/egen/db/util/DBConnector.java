package com.egen.db.util;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.egen.db.model.RuleModel;
import com.egen.db.model.TempModel;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public final class DBConnector {
	private static final DBConnector INSTANCE = new DBConnector();
	private static Datastore ds;
	private static Mongo mongo;
	private static Morphia morphia;
	@SuppressWarnings("deprecation")
	private DBConnector() {
		try {
			 MongoClient client = new MongoClient("localhost", 27017);
			 mongo =new Mongo();
			 morphia = new Morphia();
		   ds = morphia.createDatastore(client, "ranaDB");
		   morphia.map(TempModel.class).map(RuleModel.class);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing mongo db", e);
		}
	}

	public static DBConnector instance() {
		return INSTANCE;
	}

	public Datastore getDatastore() {
		return ds;
	}
	
	public Mongo getMongo() {
		return mongo;
	}
	
	public Morphia getMorphia() {
		return morphia;
	}
}
