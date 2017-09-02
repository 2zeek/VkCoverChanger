package vkCoverChanger.vk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.objects.photos.responses.SaveOwnerPhotoResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;

import java.io.File;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */
public interface VkClientInstance {

    GetResponse getWall() throws ClientException, ApiException;
    PostResponse setPhotoOnTheWall(File file) throws ClientException, ApiException;
    SaveOwnerPhotoResponse setAvatar(File file) throws ClientException, ApiException;
    GetMembersResponse getGroupMembers() throws ClientException, ApiException;
    SaveOwnerPhotoResponse setGroupAvatar(File file) throws ClientException, ApiException;
    void setGroupCover(File file) throws ClientException, ApiException;
}
