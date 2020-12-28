package com.example.algamoney.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AppUser.class)
public abstract class AppUser_ {

	public static volatile SingularAttribute<AppUser, String> password;
	public static volatile SingularAttribute<AppUser, String> name;
	public static volatile ListAttribute<AppUser, Permition> permitions;
	public static volatile SingularAttribute<AppUser, Long> id;
	public static volatile SingularAttribute<AppUser, String> email;

	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String PERMITIONS = "permitions";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

