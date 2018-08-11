package net.cattaka.android.snippets.example.test;

import android.support.test.InstrumentationRegistry;

import net.cattaka.android.snippets.example.core.AppComponent;
import net.cattaka.android.snippets.example.core.DaggerAppComponent;
import net.cattaka.android.snippets.example.core.MyApplication;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;

/**
 * Created by cattaka on 16/07/10.
 */
public class IsolateEnvRule implements TestRule {
    public TestMyModule mTestMyModule;

    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    private void before() {
        MyApplication application = (MyApplication) InstrumentationRegistry
                .getTargetContext().getApplicationContext();
        mTestMyModule = new TestMyModule(application);
        mTestMyModule.reset();
        AppComponent testComponent = DaggerAppComponent.builder()
                .myModule(mTestMyModule).build();
        application.setAppComponent(testComponent);
    }

    private void after() {
        try {
            mTestMyModule.shutdown();
        } catch (IOException e) {
            // ignore
        }
    }
}
