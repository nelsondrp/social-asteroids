package com.ndr.socialasteroids.business.DTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;

import com.ndr.socialasteroids.domain.entity.ForumThread;
import com.ndr.socialasteroids.presentation.controller.ForumController;
import com.ndr.socialasteroids.presentation.controller.UserController;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThreadDTO extends RepresentationModel<ThreadDTO>
{
    private Long id;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;

    public ThreadDTO(ForumThread thread)
    {
        this.id = thread.getId();
        this.title = thread.getTitle();
        this.createdAt = thread.getCreatedAt();
        this.updatedAt = thread.getUpdatedAt();
        Pageable pageable = PageRequest.ofSize(20);
        //Self - posts - owner
        add(linkTo(methodOn(ForumController.class).getThread(id.toString())).withSelfRel());
        add(linkTo(methodOn(ForumController.class).getPostsByThread(id.toString(), pageable)).withRel("posts"));
        add(linkTo(methodOn(UserController.class).getUserById(thread.getOwner().getId().toString())).withRel("owner"));
        add(linkTo(methodOn(ForumController.class).getPaged(pageable)).withRel("threads"));
    }

    public ThreadDTO(ForumThread thread, Pageable pageable)
    {
        this.id = thread.getId();
        this.title = thread.getTitle();
        this.createdAt = thread.getCreatedAt();
        this.updatedAt = thread.getUpdatedAt();
        Pageable postsPage = PageRequest.ofSize(20);
        //Self - posts - owner
        add(linkTo(methodOn(ForumController.class).getThread(id.toString())).withSelfRel());
        add(linkTo(methodOn(ForumController.class).getPostsByThread(id.toString(), postsPage)).withRel("posts"));
        add(linkTo(methodOn(UserController.class).getUserById(thread.getOwner().getId().toString())).withRel("owner"));
        add(linkTo(methodOn(ForumController.class).getPaged(pageable)).withRel("threads"));
    }
    
}
