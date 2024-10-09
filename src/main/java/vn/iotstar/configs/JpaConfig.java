package vn.iotstar.configs;

import jakarta.persistence.*;

@PersistenceContext
public class JpaConfig {
	public static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-hibernate-sql");
		return factory.createEntityManager();
	}
}
