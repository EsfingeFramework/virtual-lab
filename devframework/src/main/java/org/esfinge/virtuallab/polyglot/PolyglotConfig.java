package org.esfinge.virtuallab.polyglot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface PolyglotConfig {

    public String secondaryType();

    public String secondaryUrl();

    public String secondaryUser() default "";

    public String secondaryPassword() default "";

    public String secondaryDialect() default "";

}
