package com.back_community.api.common.embedded.image;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Image {

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "change_name")
    private String changeName;

}
