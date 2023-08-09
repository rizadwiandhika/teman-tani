package com.temantani.storage.disk;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.temantani.domain.ports.output.storage.StorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DiskStorageService implements StorageService {

  private final String host;
  private final Path rootPath;
  private final Path tmpRootPath;

  public DiskStorageService(
      @Value("${storage.location}") String rootPath,
      @Value("${storage.tmp-location}") String tmpRootPath,
      @Value("${server.domain}") String domain,
      @Value("${server.port}") String port) {
    this.rootPath = Path.of(rootPath);
    this.tmpRootPath = Path.of(tmpRootPath);
    this.host = String.format("%s:%s", domain, port);
  }

  @Override
  public String save(InputStream input, Path destination) throws IOException {
    Path dest = rootPath.resolve(destination);
    saveInput(input, dest);
    return String.format("%s/content/%s", host, dest.getFileName());
  }

  @Override
  public String saveTemporary(InputStream input, Path relativePath) throws IOException {
    Path dest = tmpRootPath.resolve(relativePath);
    saveInput(input, dest);
    return String.format("%s/content/tmp/%s", host, dest.getFileName());
  }

  private void saveInput(InputStream input, Path dest) throws IOException {
    log.info("Saving file into: {}, parent: {}", dest.toString(), dest.getParent());

    // Create dir if not exists
    Files.createDirectories(dest.getParent());
    Files.copy(input, dest, StandardCopyOption.REPLACE_EXISTING);

    // return dest.toString();
  }

}
