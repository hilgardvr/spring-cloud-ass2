/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.magnum.mobilecloud.video;

import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class VideoController {

    @Autowired
    private VideoRepository videos;
	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
	
	@RequestMapping(value="/go",method=RequestMethod.GET)
	public @ResponseBody String goodLuck(){
		return "Good Luck!";
	}

	@RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
    public @ResponseBody Video getVideoById(
            @PathVariable long id
    ) {
	    return videos.findById(id);
    }

    @RequestMapping(value = "/video", method = RequestMethod.POST)
    public @ResponseBody Video postVideoMetadata(
            @RequestBody Video vid
    ) {
        videos.save(vid);
        return vid;
    }

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public @ResponseBody List<Video> getVideoMetadata() {
	    return videos.findAll();
    }

    @RequestMapping(value = "/video/search/findByName", method = RequestMethod.GET)
    public @ResponseBody List<Video> findByName(@RequestParam("title") String title) {
	    return videos.findByName(title);
    }

    @RequestMapping(value = "/video/search/findByDurationLessThan", method = RequestMethod.GET)
    public @ResponseBody List<Video> findByDurationLessThan(@RequestParam("duration") long duration) {
        return videos.findByDurationLessThan(duration);
    }

    @RequestMapping(value = "/video/{id}/like", method = RequestMethod.POST)
    public void likeVideo(
            @PathVariable("id") long id,
            HttpServletResponse response,
            Principal principal
    ) {
	    Video vid = videos.findById(id);
	    if (vid != null) {
	        if (!vid.getLikedBy().contains(principal.getName())) {
	            vid.setLikes(vid.getLikes() + 1);
	            Set<String> likedBy = vid.getLikedBy();
	            likedBy.add(principal.getName());
	            vid.setLikedBy(likedBy);
	            videos.save(vid);
                response.setStatus(200);
            } else {
	            response.setStatus(400);
            }
        } else {
            response.setStatus(404);
        }
    }

    @RequestMapping(value = "/video/{id}/unlike", method = RequestMethod.POST)
    public void unlikeVideo(
            @PathVariable("id") long id,
            HttpServletResponse response,
            Principal principal
    ) {
	    Video vid = videos.findById(id);
	    if (vid != null) {
	        if (vid.getLikedBy().contains(principal.getName())) {
	            vid.setLikes(vid.getLikes() - 1);
	            Set<String> likedBy = vid.getLikedBy();
	            likedBy.remove(principal.getName());
	            vid.setLikedBy(likedBy);
	            videos.save(vid);
	            response.setStatus(200);
            } else {
	            response.setStatus(400);
            }
        } else {
	        response.setStatus(404);
        }
    }
}
