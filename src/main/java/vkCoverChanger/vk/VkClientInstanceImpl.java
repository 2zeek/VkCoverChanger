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
import vkCoverChanger.config.properties.VkClientProperties;

import java.io.File;
import java.util.List;

/**
 * Created by Nikolay V. Petrov on 28.08.2017.
 */

public class VkClientInstanceImpl implements VkClientInstance {

    private final String NAMESPACE = "vk";

    private VkApiClient vkApiClient;
    private Integer userId;
    private UserActor userActor;
    private Integer groupId;
    private GroupActor groupActor;

    VkClientInstanceImpl(VkClientProperties vkClientProperties) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        this.vkApiClient = new VkApiClient(transportClient);
        this.userId = vkClientProperties.getUser().getId();
        this.userActor = new UserActor(userId, vkClientProperties.getUser().getAccessToken());
        this.groupId = vkClientProperties.getGroup().getId();
        this.groupActor = new GroupActor(groupId, vkClientProperties.getGroup().getAccessToken());
    }

//    @GetMapping(NAMESPACE + "/getWall")
//    @ResponseBody
    public GetResponse getWall() throws ClientException, ApiException {
        return vkApiClient.wall().get(userActor)
                .ownerId(userId)
                .count(5)
                .offset(0)
                .execute();
    }

//    @GetMapping(NAMESPACE + "/setPhotoOnTheWall")
//    @ResponseBody
    public PostResponse setPhotoOnTheWall() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\photo_for_upload.jpg");
        PhotoUpload serverResponse = vkApiClient.photos().getWallUploadServer(userActor).execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();
        List<Photo> photoList = vkApiClient.photos().saveWallPhoto(userActor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

        Photo photo = photoList.get(0);
        String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();

        return vkApiClient.wall().post(userActor)
                .attachments(attachId)
                .execute();
    }

//    @ResponseBody
//    @GetMapping(NAMESPACE + "/setAvatar")
    public SaveOwnerPhotoResponse setAvatar() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\user\\photo_for_upload.jpg");
        GetOwnerPhotoUploadServerResponse serverResponse = vkApiClient.photos().getOwnerPhotoUploadServer(userActor).execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        return  vkApiClient.photos().
                saveOwnerPhoto(userActor)
                .photo(uploadResponse.getPhoto())
                .hash(uploadResponse.getHash())
                .server(String.valueOf(uploadResponse.getServer()))
                .execute();
    }

//    @ResponseBody
//    @GetMapping(NAMESPACE + "/getGroupMembers")
    public GetMembersResponse getGroupMembers() throws ClientException, ApiException {
        return vkApiClient.groups()
                .getMembers(groupActor)
                .groupId(String.valueOf(groupId))
                .execute();
    }

//    @ResponseBody
//    @GetMapping(NAMESPACE + "/setGroupAvatar")
    public SaveOwnerPhotoResponse setGroupAvatar() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\group\\avatar_for_upload.jpg");
        GetOwnerPhotoUploadServerResponse serverResponse = vkApiClient.photos().getOwnerPhotoUploadServer(userActor).ownerId(0- groupId).execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        return vkApiClient.photos()
                .saveOwnerPhoto(userActor)
                .photo(uploadResponse.getPhoto())
                .hash(uploadResponse.getHash())
                .server(String.valueOf(uploadResponse.getServer()))
                .execute();
    }

    public void setGroupCover() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\group\\cover_for_upload.png");
        GetOwnerCoverPhotoUploadServerResponse serverResponse = vkApiClient.photos().getOwnerCoverPhotoUploadServer(groupActor).execute();
        OwnerCoverUploadResponse uploadResponse = vkApiClient.upload().photoOwnerCover(serverResponse.getUploadUrl(), file).execute();

        vkApiClient.photos()
                .saveOwnerCoverPhoto(groupActor , uploadResponse.getPhoto(), uploadResponse.getHash())
                .execute();
    }
}