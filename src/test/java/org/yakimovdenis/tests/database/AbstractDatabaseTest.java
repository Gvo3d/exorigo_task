package org.yakimovdenis.tests.database;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.yakimovdenis.tests.AbstractTest;

/**
 * Created by Gvozd on 06.01.2017.
 */
@ContextConfiguration(classes = {TestDAOConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public abstract class AbstractDatabaseTest extends AbstractTest {
    protected final String SEPARATOR = "**********************************************************";
}
