package ru.myblog.project.configuration.spring;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class SpringHelper {

	public static final String SCOPE_PROTOTYPE = "prototype";

	public static <X> X getBean(final String beanName, final Object... args) {

		if (beanName == null)
			throw new IllegalArgumentException("beanName must be not-null");
		if (args == null)
			throw new IllegalArgumentException("args must be not-null");

		BeanFactory beanFactory = new ClassPathXmlApplicationContext().getBeanFactory();
		try {

			final Object beanO = (args.length == 0) ? beanFactory.getBean(beanName)
					: beanFactory.getBean(beanName, args);

			@SuppressWarnings("unchecked")
			final X beanX = (X) beanO;

			return beanX;

		} catch (final BeansException e) {

			Throwable t = e;

			while ((t = t.getCause()) != null) {
				if (t instanceof BeanInstantiationException) {
					t = t.getCause();
					if (t instanceof RuntimeException) {
						throw (RuntimeException) t;
					} else if (t instanceof Error) {
						throw (Error) t;
					} else {
						throw new RuntimeException(t);
					}
				}
			}

			throw e;

		}

	}

	public static <X> X getBean(final Class<X> cls, final Object... args) {

		if (cls == null)
			throw new IllegalArgumentException("class must be not-null");

		final String className = cls.getCanonicalName();
		final String beanName = className.replace('.', '_').replace('$', '_');

		try {
			final X beanX = getBean(beanName, args);
			return beanX;
		} catch (final Exception e) {
			throw new RuntimeException("Unable to get implementation for \"" + className + "\"", e);
		}

	}

	private SpringHelper () {
    }

}
