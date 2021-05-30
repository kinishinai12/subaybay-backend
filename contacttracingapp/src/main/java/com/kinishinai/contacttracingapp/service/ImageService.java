package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.dto.ImageRequest;
import com.kinishinai.contacttracingapp.model.Image;
import com.kinishinai.contacttracingapp.model.User;
import com.kinishinai.contacttracingapp.repository.ImageRepository;
import com.kinishinai.contacttracingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ImageService {

    private final ImageRepository IMAGEREPOSITORY;
    private final UserRepository USERREPOSITORY;

    //TODO
    public void updateImage(long userid, ImageRequest imageRequest){
        User user = USERREPOSITORY.findById(userid).get();
        Image image = IMAGEREPOSITORY.findByUser(user);
        image.setImageName(imageRequest.getImageName());
        image.setPath(imageRequest.getPath());

        IMAGEREPOSITORY.save(image);
    }
    //TODO done testing delete image
    public void deleteImage(long imageId){
        IMAGEREPOSITORY.deleteById(imageId);
    }
    // TODO isDefault in image model if applying multiple profile pictures
    public Image getImage(long userid){
        User user = USERREPOSITORY.findById(userid).get();
        Image image = IMAGEREPOSITORY.findByUser(user);
        return image;
    }
}
