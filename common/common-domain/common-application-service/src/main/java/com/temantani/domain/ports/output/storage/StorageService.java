package com.temantani.domain.ports.output.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface StorageService {

  String save(InputStream input, Path relativePath) throws IOException;

  String saveTemporary(InputStream input, Path relativePath) throws IOException;

}
