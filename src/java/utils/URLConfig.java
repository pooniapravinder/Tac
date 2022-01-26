package utils;

import user.content.process.AWSConstants;

public class URLConfig {

    private static final String prefix_non_ssl = "http://";
    
    private static final String prefix_ssl = "https://";
    
    private static final String suffix = ".s3.ap-south-1.amazonaws.com/";
    
    public static final String hostname = prefix_non_ssl + "tac.wookes.com/";
    
    public static final String music_url = prefix_ssl + AWSConstants.POST_VIDEOS_MUSIC_BUCKET_NAME + suffix;
    
    public static final String video_url = prefix_ssl + AWSConstants.POST_VIDEOS_BUCKET_NAME + suffix;
    
    public static final String cover_image_url = prefix_ssl + AWSConstants.POST_VIDEOS_THUMBNAILS_BUCKET_NAME + suffix;
    
    public static final String profile_photo_url = prefix_ssl + AWSConstants.PROFILE_PHOTO_BUCKET_NAME + suffix;
}
