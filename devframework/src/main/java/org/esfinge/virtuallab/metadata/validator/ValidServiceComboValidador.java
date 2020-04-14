package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Entity;

import org.esfinge.virtuallab.services.ValidationService;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;
import net.sf.esfinge.querybuilder.Repository;

/**
 * Valida classes anotadas com @ServiceDAO.
 */
public class ValidServiceComboValidador implements AnnotationValidator
{

	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		// cast para o metodo
		Method method = (Method) annotated;
		
		try
		{
			// valida o metodo
			ValidationService.getInstance().assertValidMethod(method);
		}
		catch ( Exception e )
		{
			throw new AnnotationValidationException(String.format("Falha ao validar o metodo '%s': \n%s", method.getName(), e));
		}

	}
}
