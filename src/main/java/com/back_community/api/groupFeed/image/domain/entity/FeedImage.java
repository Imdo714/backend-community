package com.back_community.api.groupFeed.image.domain.entity;

import com.back_community.api.common.embedded.image.Image;
import com.back_community.api.groupFeed.board.domain.entity.GroupFeed;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FEED_IMG")
public class FeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private GroupFeed feed;

    @Embedded
    private Image image;

}
