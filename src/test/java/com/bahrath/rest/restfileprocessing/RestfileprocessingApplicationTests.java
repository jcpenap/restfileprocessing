package com.bahrath.rest.restfileprocessing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@SpringBootTest
class RestfileprocessingApplicationTests {

	public static final String FILE_UPLOAD_URL = "http://localhost:8080/upload";
	public static final String FILE_DOWNLOAD_URL = "http://localhost:8080/download/";
	@Autowired
	private RestTemplate restTemplate;

	@Value("${uploadDir}")
	private String UPLOAD_DIR;

	@Value("${downloadDir}")
	private String DOWNLOAD_DIR;

	@Test
	void testUpload() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new ClassPathResource("foto_juan.jpg"));

		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

		ResponseEntity<Boolean> response = restTemplate.postForEntity(FILE_UPLOAD_URL, httpEntity, Boolean.class);

		System.out.println(response.getBody());

	}

	@Test
	void testDownload() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

		String fileName = "foto_juan.jpg";

		ResponseEntity<byte[]> response = restTemplate.exchange(FILE_DOWNLOAD_URL+fileName, HttpMethod.GET, httpEntity, byte[].class);

		Files.write(Paths.get(DOWNLOAD_DIR +fileName), response.getBody());
	}

}
