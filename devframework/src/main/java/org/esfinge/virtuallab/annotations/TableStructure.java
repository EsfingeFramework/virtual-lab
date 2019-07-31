package org.esfinge.virtuallab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.FieldExists;

/***
 * PARA DOCUMENTAR!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@FieldExists
public @interface TableStructure
{
	String[] fields() default {};
}