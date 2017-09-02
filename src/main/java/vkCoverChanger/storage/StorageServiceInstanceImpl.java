package vkCoverChanger.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vkCoverChanger.config.properties.StorageServiceProperties;
import vkCoverChanger.storage.exception.StorageException;
import vkCoverChanger.storage.exception.StorageFileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * Created by Nikolay V. Petrov on 27.08.2017.
 */

public class StorageServiceInstanceImpl implements StorageServiceInstance {

    private StorageServiceProperties storageServiceProperties;

    private StorageServiceInstanceImpl(StorageServiceProperties storageServiceProperties) {
        this.storageServiceProperties = storageServiceProperties;
    }

    public static StorageServiceInstance createStorageService(StorageServiceProperties storageServiceProperties) {
        return new StorageServiceInstanceImpl(storageServiceProperties);
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Files.copy(file.getInputStream(), this.storageServiceProperties.getMainPath().resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageServiceProperties.getMainPath(), 1)
                    .filter(path -> !path.equals(this.storageServiceProperties.getMainPath()))
                    .map(path -> this.storageServiceProperties.getMainPath().relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return storageServiceProperties.getMainPath().resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(storageServiceProperties.getMainPath().toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(storageServiceProperties.getMainPath());
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}