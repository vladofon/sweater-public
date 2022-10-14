package com.bootexample.spring.services;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootexample.spring.models.Photo;
import com.bootexample.spring.models.PhotoUpload;
import com.bootexample.spring.repos.PhotoRepo;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;

@Service
public class PhotoService {

	@Value("${image.default.name}")
	private String defaultPhotoName;

	@Autowired
	private PhotoRepo photoRepository;

	public Photo savePhoto(MultipartFile file) throws IOException {
		if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
			return getDefaultImage();
		}

		String uuidFile = UUID.randomUUID().toString();
		String finalFilename = uuidFile + "." + file.getOriginalFilename();

		PhotoUpload toUpload = new PhotoUpload();
		toUpload.setFile(file);
		toUpload.setTitle(finalFilename);

		return upload(toUpload);
	}

	@SuppressWarnings({ "rawtypes", "removal" })
	public Photo upload(PhotoUpload photoUpload) throws IOException {

		Map uploadResult = null;
		if (photoUpload.getFile() != null && !photoUpload.getFile().isEmpty()) {
			uploadResult = Singleton.getCloudinary().uploader().upload(photoUpload.getFile().getBytes(),
					ObjectUtils.asMap("resource_type", "auto"));
			photoUpload.setPublicId((String) uploadResult.get("public_id"));
			Object version = uploadResult.get("version");
			if (version instanceof Integer) {
				photoUpload.setVersion(new Long((Integer) version));
			} else {
				photoUpload.setVersion((Long) version);
			}

			photoUpload.setSignature((String) uploadResult.get("signature"));
			photoUpload.setFormat((String) uploadResult.get("format"));
			photoUpload.setResourceType((String) uploadResult.get("resource_type"));
		}

		String editedPhotoUrl = photoUpload.getThumbnailUrl();
		editedPhotoUrl = editedPhotoUrl.substring(editedPhotoUrl.indexOf("image"));

		Photo photo = new Photo();
		photo.setTitle(photoUpload.getTitle());
		photo.setUpload(photoUpload);
		photo.setImage(editedPhotoUrl);
		photoRepository.save(photo);

		return photo;
	}

	public Photo getDefaultImage() {
		return photoRepository.findByTitle(defaultPhotoName);
	}

}
