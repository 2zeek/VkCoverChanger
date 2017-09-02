package vkCoverChanger.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.*;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import vkCoverChanger.vk.config.properties.VkClientProperties;

import java.io.File;
import java.util.List;

/**
 * Created by Nikolay V. Petrov on 28.08.2017.
 */

public class VkClientInstanceImpl implements VkClientInstance {

    private VkClientProperties vkClientProperties;
    private VkApiClient vkApiClient;

    private VkClientInstanceImpl(VkClientProperties vkClientProperties) {
        this.vkClientProperties = vkClientProperties;
        this.vkClientProperties.getUser().setActor(new UserActor(vkClientProperties.getUser().getId(),
                        vkClientProperties.getUser().getAccessToken()));
        this.vkClientProperties.getGroup().setActor(new GroupActor(vkClientProperties.getGroup().getId(),
                        vkClientProperties.getGroup().getAccessToken()));
        TransportClient transportClient = HttpTransportClient.getInstance();
        this.vkApiClient = new VkApiClient(transportClient);
    }

    public static VkClientInstance createNewClient(VkClientProperties vkClientProperties) {
        return new VkClientInstanceImpl(vkClientProperties);
    }

    public GetResponse getWall() throws ClientException, ApiException {
        return vkApiClient.wall().get((UserActor) vkClientProperties.getUser().getActor())
                .ownerId(vkClientProperties.getUser().getId())
                .count(5)
                .offset(0)
                .execute();
    }

    public PostResponse setPhotoOnTheWall(File file) throws ClientException, ApiException {
        PhotoUpload serverResponse = vkApiClient.photos()
                .getWallUploadServer((UserActor) vkClientProperties.getUser().getActor())
                .execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();
        List<Photo> photoList = vkApiClient.photos().
                saveWallPhoto((UserActor) vkClientProperties.getUser().getActor(), uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

        Photo photo = photoList.get(0);
        String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();

        return vkApiClient.wall().post((UserActor) vkClientProperties.getUser().getActor())
                .attachments(attachId)
                .execute();
    }

    public SaveOwnerPhotoResponse setAvatar(File file) throws ClientException, ApiException {
        GetOwnerPhotoUploadServerResponse serverResponse = vkApiClient.photos().
                getOwnerPhotoUploadServer((UserActor) vkClientProperties.getUser().getActor())
                .execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        return  vkApiClient.photos().
                saveOwnerPhoto((UserActor) vkClientProperties.getUser().getActor())
                .photo(uploadResponse.getPhoto())
                .hash(uploadResponse.getHash())
                .server(String.valueOf(uploadResponse.getServer()))
                .execute();
    }

    public GetMembersResponse getGroupMembers() throws ClientException, ApiException {
        return vkApiClient.groups()
                .getMembers((GroupActor) vkClientProperties.getGroup().getActor())
                .groupId(String.valueOf(vkClientProperties.getGroup().getId()))
                .execute();
    }

    public SaveOwnerPhotoResponse setGroupAvatar(File file) throws ClientException, ApiException {
        GetOwnerPhotoUploadServerResponse serverResponse = vkApiClient.photos()
                .getOwnerPhotoUploadServer((UserActor) vkClientProperties.getUser().getActor())
                .ownerId(0-vkClientProperties.getGroup().getId()).execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        return vkApiClient.photos()
                .saveOwnerPhoto((UserActor) vkClientProperties.getUser().getActor())
                .photo(uploadResponse.getPhoto())
                .hash(uploadResponse.getHash())
                .server(String.valueOf(uploadResponse.getServer()))
                .execute();
    }

    public void setGroupCover(File file) throws ClientException, ApiException {
        GetOwnerCoverPhotoUploadServerResponse serverResponse = vkApiClient.photos()
                .getOwnerCoverPhotoUploadServer((GroupActor) vkClientProperties.getGroup().getActor())
                .execute();
        OwnerCoverUploadResponse uploadResponse = vkApiClient.upload().photoOwnerCover(serverResponse.getUploadUrl(), file).execute();

        vkApiClient.photos()
                .saveOwnerCoverPhoto((GroupActor) vkClientProperties.getGroup().getActor(),
                        uploadResponse.getPhoto(), uploadResponse.getHash())
                .execute();
    }
}