/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cd.go.plugin.notification.rocketchat.executors;

import com.google.gson.Gson;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GetPluginConfigurationExecutorTest {

    @Test
    public void shouldSerializeAllFields() throws Exception {
        GoPluginApiResponse response = new GetPluginConfigurationExecutor().execute();
        HashMap hashMap = new Gson().fromJson(response.responseBody(), HashMap.class);
        assertEquals("Are you using anonymous inner classes — see https://github.com/google/gson/issues/298",
                hashMap.size(),
                GetPluginConfigurationExecutor.FIELDS.size()
        );
    }

    @Test
    public void assertJsonStructure() throws Exception {
        GoPluginApiResponse response = new GetPluginConfigurationExecutor().execute();

        assertThat(response.responseCode(), CoreMatchers.is(200));
        String expectedJSON = "{\n" +
                "  \"go_server_url\": {\n" +
                "    \"display-name\": \"Go Server URL\",\n" +
                "    \"required\": true,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"0\"\n" +
                "  },\n" +
                "  \"api_url\": {\n" +
                "    \"display-name\": \"Rocketchat API URL\",\n" +
                "    \"required\": true,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"1\"\n" +
                "  },\n" +
                "  \"api_user\": {\n" +
                "    \"display-name\": \"Rocketchat API User\",\n" +
                "    \"required\": true,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"2\"\n" +
                "  },\n" +
                "  \"api_key\": {\n" +
                "    \"display-name\": \"Rocketchat API password\",\n" +
                "    \"required\": true,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"3\"\n" +
                "  },\n" +
                "  \"room\": {\n" +
                "    \"display-name\": \"Rocketchat room\",\n" +
                "    \"required\": true,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"4\"\n" +
                "  },\n" +
                "  \"passed_pipelines_whitelist\": {\n" +
                "    \"display-name\": \"Notify about passed pipelines\",\n" +
                "    \"required\": false,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"5\"\n" +
                "  },\n" +
                "  \"failed_pipelines_whitelist\": {\n" +
                "    \"display-name\": \"Notify about failed pipelines\",\n" +
                "    \"required\": false,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"6\"\n" +
                "  },\n" +
                "  \"cancelled_pipelines_whitelist\": {\n" +
                "    \"display-name\": \"Notify about cancelled pipelines\",\n" +
                "    \"required\": false,\n" +
                "    \"secure\": false,\n" +
                "    \"display-order\": \"7\"\n" +
                "  }\n" +
                "}";

        JSONAssert.assertEquals(expectedJSON, response.responseBody(), true);
    }
}
