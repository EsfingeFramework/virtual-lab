package org.esfinge.virtuallab.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.esfinge.virtuallab.metadata.validator.ValidComboMethod;
import org.esfinge.virtuallab.metadata.validator.ValidServiceMethod;

import net.sf.esfinge.metadata.annotation.validator.method.ForbiddenMethodReturn;
import net.sf.esfinge.metadata.annotation.validator.method.InstanceMethodOnly;
import net.sf.esfinge.metadata.annotation.validator.method.MethodVisibilityRequired;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

@ForbiddenMethodReturn(invalidTypesToReturn = { void.class })
@MethodVisibilityRequired(itNeedsToHaveThisVisibility = "public")
@InstanceMethodOnly


public @interface ComboMethod {

	String value();

}
