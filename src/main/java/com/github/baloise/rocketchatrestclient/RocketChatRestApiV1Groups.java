package com.github.baloise.rocketchatrestclient;

import java.io.IOException;

import com.github.baloise.rocketchatrestclient.model.Group;
import com.github.baloise.rocketchatrestclient.model.Room;
import com.github.baloise.rocketchatrestclient.model.User;
import com.github.baloise.rocketchatrestclient.requests.RoomAndUserRequest;
import com.github.baloise.rocketchatrestclient.requests.RoomCreateRequest;

public class RocketChatRestApiV1Groups {
    private static final String ROOM_ID_PARAM_KEY = "roomId";
    private RocketChatClientCallBuilder callBuilder;

    protected RocketChatRestApiV1Groups(RocketChatClientCallBuilder callBuilder) {
        this.callBuilder = callBuilder;
    }

    /**
     * Archives a group
     *
     * @param group
     *            the group to archive
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void archive(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsArchive, null, group);

        if (!res.isSuccessful())
            throw new IOException("The call to archive the Group was unsuccessful: \"" + res.getError() + "\"");
    }

    /**
     * Closes a group
     *
     * @param group
     *            the group to close
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void close(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsClose, null, group);

        if (!res.isSuccessful())
            throw new IOException("The call to close the Group was unsuccessful: \"" + res.getError() + "\"");
    }

    /**
     * Creates a new private group with any of the members added in the class.
     *
     * @param group
     *            the of the group to create
     * @return the {@link Room} which is the newly created channel
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public Group create(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsCreate, null, new RoomCreateRequest(group.getName(),group.getUsernames()));

        if (!res.isSuccessful())
            throw new IOException("The call to create a Group was unsuccessful: \"" + res.getError() + "\"");

        if (!res.hasGroup())
            throw new IOException("The response does not contain any group information.");

        return res.getGroup();
    }

    /**
     * Retrieves the information about the group.
     *
     * @param groupId
     *            the "_id" of the room to get information
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     * @return The {@link Room} of the channel
     */
    public Group info(String groupId) throws IOException {
        return this.info(new Group(groupId, ""));
    }

    /**
     * Retrieves the information about the group.
     *
     * @param group
     *            the group to get information about
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     * @return The {@link Room} of the channel
     */
    public Group info(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsInfo, new RocketChatQueryParams(ROOM_ID_PARAM_KEY, group.getId()));

        if (!res.isSuccessful())
            throw new IOException("The call to get the Group's information was unsuccessful: \"" + res.getError() + "\"");

        if (!res.hasGroup())
            throw new IOException("The call to get the Group's information was unsuccessful, failed to return channel data.");

        return res.getGroup();
    }

    /**
     * Invites a user to a private group
     *
     * @param group
     *            the group to invite user to
     * @param user
     *            the user to invite
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void invite(Group group, User user) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsInvite, null, new RoomAndUserRequest(group.getId(), user.getId()));

        if (!res.isSuccessful())
            throw new IOException("The call to invite a User to a Group was unsuccessful: \"" + res.getError() + "\"");
    }

    /**
     * Kicks a user from a private group
     *
     * @param group
     *            the group to remove user from
     * @param user
     *            the user to remove
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void kick(Group group, User user) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsKick, null, new RoomAndUserRequest(group.getId(), user.getId()));

        if (!res.isSuccessful())
            throw new IOException("The call to remove a User from a Group was unsuccessful: \"" + res.getError() + "\"");
    }

    /**
     * Removes the callee from a group
     *
     * @param group
     *            the group to leave
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void leave(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsLeave, null, group);

        if (!res.isSuccessful())
            throw new IOException("The call to leave a Group was unsuccessful: \"" + res.getError() + "\"");
    }

    /**
     * Gets the first number of the private groups the calling user has access
     * to from the Rocket.Chat server, the amount depends on what the server has
     * configured to return as the default count with the default being 50.
     *
     * @return an array of {@link Room}s that are groups
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public Group[] list() throws IOException {
        return this.list(new RocketChatQueryParams());
    }

    /**
     * Gets the private groups from the Rocket.Chat server, depending on the
     * query params provided otherwise uses the server's defaults.
     *
     * @param params
     *            an {@link RocketChatQueryParams} instance
     * @return an array of {@link Group}s
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public Group[] list(RocketChatQueryParams params) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsList, params);

        if (!res.isSuccessful())
            throw new IOException("The call to get the private Groups was unsuccessful: \"" + res.getError() + "\"");

        if (!res.hasGroups())
            throw new IOException("Get Groups failed to retrieve the groups.");

        return res.getGroups();
    }

    /**
     * Opens a group
     *
     * @param group
     *            the group to open
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void open(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsOpen, null, group);

        if (!res.isSuccessful())
            throw new IOException("The call to open the Group was unsuccessful: \"" + res.getError() + "\"");
    }

    /**
     * Renames the group
     *
     * @param group
     *            the group to rename
     * @return the newly renamed group
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public Group rename(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsRename, null, group);

        if (!res.isSuccessful())
            throw new IOException("The call to rename a Group was unsuccessful: \"" + res.getError() + "\"");

        if (!res.hasGroup())
            throw new IOException("The call to rename a Group failed to return the group data.");

        return res.getGroup();
    }

    /**
     * Unarchives a group
     *
     * @param group
     *            the group to unarchive
     * @throws IOException
     *             is thrown if there was a problem connecting, including if the
     *             result wasn't successful
     */
    public void unarchive(Group group) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsUnarchive, null, group);

        if (!res.isSuccessful())
            throw new IOException("The call to unarchive the Group was unsuccessful: \"" + res.getError() + "\"");
    }

    public void addOwner(Group group, User user) throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsAddOwner, null, new RoomAndUserRequest(group.getId(), user.getId()));

        if (!res.isSuccessful())
            throw new IOException("The call to add an owner was unsuccessful: \"" + res.getError() + "\"");
    }
}
