package cd.go.plugin.notification.rocketchat.rocket;

import cd.go.plugin.notification.rocketchat.PluginRequest;
import cd.go.plugin.notification.rocketchat.ServerRequestFailedException;
import cd.go.plugin.notification.rocketchat.requests.StageStatusRequest;
import cd.go.plugin.notification.rocketchat.requests.StageStatusRequest.Job;
import com.github.baloise.rocketchatrestclient.model.Attachment;
import com.github.baloise.rocketchatrestclient.model.AttachmentField;
import com.github.baloise.rocketchatrestclient.model.Message;
import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;

import java.net.URI;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class MessageBuilderService {
    private static final Logger LOG = Logger.getLoggerFor(MessageBuilderService.class);

    private static final String STAGE_STATE_FAILED = "Failed";
    private static final String STAGE_STATE_PASSED = "Passed";
    private static final String STAGE_STATE_CANCELLED = "Cancelled";
    private static final String STAGE_STATE_BUILDING = "Building";

    private static final String DEFAULT_WHITELIST = "";

    public Message onStageStatusChanged(PluginRequest pluginRequest, StageStatusRequest.Pipeline pipeline) {
        // The request.pipeline object has all the details about the pipeline, materials, stages and jobs
        if(pipelineInWhitelist(pipeline, pluginRequest)) {
            return onStageChanged(pipeline, pluginRequest);
        }
        LOG.warn(format("Skipping message processing for stage {0}/{1} because stage state is unknown - {2}", pipeline.name, pipeline.stage.name, pipeline.stage.state));
        return null;
    }

    private Boolean pipelineInWhitelist(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest) {
        String whitelist = getPipelinesWhitelist(pluginRequest, pipeline);
        if (whitelist != null){
            for ( String group : whitelist.split(",") ) {
                LOG.debug("checking if pipeline group matches whitelist");
                if ( strmatch(pipeline.group, group.trim()) ) return true;
            }
        }
        return false;
    }

    private String getPipelinesWhitelist(PluginRequest pluginRequest, StageStatusRequest.Pipeline pipeline) {
        try {
            if(pipeline.stage.state.equals(STAGE_STATE_FAILED))
                return pluginRequest.getPluginSettings().getFailedPipelinesWhitelist();
            if(pipeline.stage.state.equals(STAGE_STATE_PASSED))
                return pluginRequest.getPluginSettings().getPassedPipelinesWhitelist();
            if(pipeline.stage.state.equals(STAGE_STATE_CANCELLED))
                return pluginRequest.getPluginSettings().getCancelledPipelinesWhitelist();
        }
        catch(Exception ex) {
            LOG.error(format("Failed to load whitelist for {0} pipelines", pipeline.stage.state), ex);
            return DEFAULT_WHITELIST;
        }
        return DEFAULT_WHITELIST;
    }

    private Message onStageBuilding(StageStatusRequest.Pipeline pipeline) {
        return null;
    }

    private Message onStageCancelled(StageStatusRequest.Pipeline pipeline) {
        return null;
    }

    private Message onStagePassed(StageStatusRequest.Pipeline pipeline) {
        return null;
    }

    public String stageFullUrl(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest) {
        try {
            String host = pluginRequest.getPluginSettings().getGoServerUrl();
            return new URI(String.format("%s/go/pipelines/%s/%s/%s/%s", host, pipeline.name, pipeline.counter, pipeline.stage.name, pipeline.stage.counter)).normalize().toASCIIString();
        }
        catch(Exception ex) {
            LOG.error("Failed to form an URL to stage", ex);
            return "";
        }
    }

    public String vsmFullUrl(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest) {
        try {
            String host = pluginRequest.getPluginSettings().getGoServerUrl();
            return new URI(String.format("%s/go/pipelines/value_stream_map/%s/%s", host, pipeline.name, pipeline.counter)).normalize().toASCIIString();
        }
        catch(Exception ex) {
            LOG.error("Failed to form an URL to VSM", ex);
            return "";
        }
    }

    private String jobConsoleFullUrl(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest, Job job) {
        try {
            String host = pluginRequest.getPluginSettings().getGoServerUrl();
            URI link = new URI(String.format("%s/go/tab/build/detail/%s/%s/%s/%s/%s#tab-console", host, pipeline.name, pipeline.counter, pipeline.stage.name, pipeline.stage.counter, job.name));
            return link.normalize().toASCIIString();
        }
        catch(Exception ex) {
            LOG.error("Failed to form an URL to job console", ex);
            return "";
        }
    }

    public String stageRelativeUri(StageStatusRequest.Pipeline pipeline) {
        return pipeline.name + "/" + pipeline.counter + "/" + pipeline.stage.name + "/" + pipeline.stage.counter;
    }

    private Message onStageChanged(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest) {
        String topText = getTopMessage(pipeline, pluginRequest);
        Message message = new Message(topText);
        Attachment buildAttachment = new Attachment();
        AttachmentField labelField =  new AttachmentField();
        labelField.setShort(true);
        labelField.setTitle("Label");
        labelField.setValue(format("[{0}]({1})", pipeline.label, vsmFullUrl(pipeline, pluginRequest)));
        // Changed jobs:
        AttachmentField jobs = new AttachmentField();
        jobs.setTitle(format("{0} Jobs", pipeline.stage.state));
        String changedJobsText = getChangedJobsText(pipeline, pluginRequest);
        jobs.setValue(changedJobsText);

        buildAttachment.setFields(new AttachmentField[] { labelField, jobs });
        message.addAttachment(buildAttachment);

        return  message;
    }

    public String getChangedJobsText(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest) {
        List<Job> failedJobs = pipeline.stage.jobs.stream()
                .filter(j -> "Failed".equals(j.result) || "Cancelled".equals(j.result))
                .collect(Collectors.toList());
        StringBuilder failedJobsText = new StringBuilder();
        for(int i = 0; i < failedJobs.size(); i++) {
            failedJobsText.append(" - [");
            Job j = failedJobs.get(i);
            failedJobsText.append(j.name);
            failedJobsText.append("](");
            failedJobsText.append(jobConsoleFullUrl(pipeline, pluginRequest, j));
            failedJobsText.append(") ");
            if(j.result.equals("Cancelled"))
                failedJobsText.append("was cancelled");
            if(j.result.equals("Failed"))
                failedJobsText.append("failed");
            if(i < failedJobs.size() -1) {
                failedJobsText.append('\n');
            }
        }
        return failedJobsText.toString();
    }

    public String getTopMessage(StageStatusRequest.Pipeline pipeline, PluginRequest pluginRequest) {
        return String.format("Stage [%s](%s) has state: %s", stageRelativeUri(pipeline), stageFullUrl(pipeline, pluginRequest), pipeline.stage.state);
    }

    // * --> Matches with 0 or more instances of any character or set of characters.
    // ? --> Matches with any one character.
    private static Boolean strmatch(String str, String pattern)
    {
        int n = str.length();
        int m = pattern.length();

        if (m == 0)
            return (n == 0);
 
        Boolean[][] lookup = new Boolean[n + 1][m + 1];
 
        for (int i = 0; i < n + 1; i++)
            Arrays.fill(lookup[i], false);
 
        lookup[0][0] = true;
 
        for (int j = 1; j <= m; j++)
            if (pattern.charAt(j - 1) == '*')
                lookup[0][j] = lookup[0][j - 1];
 
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                if (pattern.charAt(j - 1) == '*')
                    lookup[i][j] = lookup[i][j - 1] || lookup[i - 1][j];
 
                else if ( pattern.charAt(j - 1) == '?' || str.charAt(i - 1) == pattern.charAt(j - 1) )
                    lookup[i][j] = lookup[i - 1][j - 1];
                else
                    lookup[i][j] = false;
            }
        }
        return lookup[n][m];
    }
    
}
