package net.cattaka.android.snippets.example;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.cattaka.android.snippets.example.retrofit.GitHubService;
import net.cattaka.android.snippets.example.test.IsolateEnvRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;

import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.RecordedRequest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/07/10.
 */
@RunWith(AndroidJUnit4.class)
public class GitHubReposActivityTest {
    @Rule
    public IsolateEnvRule mIsolateEnvRule = new IsolateEnvRule();

    @Rule
    public ActivityTestRule<GitHubReposActivity> mActivityTestRule = new ActivityTestRule<>(GitHubReposActivity.class, false, false);

    @Test
    public void testCheckRequest_mMockWebServer() throws Exception {
        mActivityTestRule.launchActivity(null);
        do {
            RecordedRequest recordedRequest = mIsolateEnvRule.mTestMyModule.mMockWebServer.takeRequest(3, TimeUnit.SECONDS);
            assertThat(recordedRequest, is(notNullValue()));
            if ("GET".equals(recordedRequest.getMethod()) && "/users/cattaka/repos".equals(recordedRequest.getPath())) {
                break;
            }
        } while (true);
    }

    @Test
    public void testCheckRequest_mGitHubService() throws Exception {
        GitHubService service;
        {
            service = mIsolateEnvRule.mTestMyModule.mGitHubService;
            service = mock(GitHubService.class, new ForwardsInvocations(service));
            mIsolateEnvRule.mTestMyModule.mGitHubService = service;
        }

        mActivityTestRule.launchActivity(null);
        verify(service).listRepos(eq("cattaka"));
    }
}
