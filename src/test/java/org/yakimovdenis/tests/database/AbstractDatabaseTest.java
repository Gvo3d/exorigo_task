package org.yakimovdenis.tests.database;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;
import org.yakimovdenis.tests.AbstractTest;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public abstract class AbstractDatabaseTest extends AbstractTest {
    protected final String SEPARATOR = "**********************************************************";
}
