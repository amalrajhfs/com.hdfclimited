#import "Adstringo.h"
#import "AdstringoImageCompression.h"
#import "AdstringoVideoCompression.h"
@implementation Adstringo

NSURL *videoURL;
NSString * base64StringVideo;

- (void) compressString:(CDVInvokedUrlCommand*)command
{
    NSString* name = [[command arguments] objectAtIndex:0];
    NSRange pngRange = [name rangeOfString:@"PNG" options:NSBackwardsSearch];
    if (pngRange.location != NSNotFound) {
        self.imageType = DownloadImageTypePng;
        NSString* compressStrinng;
        UIImage *zeroImage = [[UIImage alloc]init];
        if(![name isEqualToString:@""] && name)
        {
            zeroImage = [self decodeBase64ToImage:name];
            compressStrinng = [self processcompImage:zeroImage];
        }
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_OK
                                   messageAsString:compressStrinng];
        
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }
    else {
        NSString* compressStrinng;
        UIImage *zeroImage = [[UIImage alloc]init];
        if(![name isEqualToString:@""] && name)
        {
            zeroImage = [self decodeBase64ToImage:name];
            
            compressStrinng = [self processcompImage:zeroImage];
            
        }
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_OK
                                   messageAsString:compressStrinng];
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }
}
- (void) compressAudio:(CDVInvokedUrlCommand*)command
{
    NSString* name = [[command arguments] objectAtIndex:0];
    NSRange pngRange = [name rangeOfString:@"PNG" options:NSBackwardsSearch];
    if (pngRange.location != NSNotFound) {
        self.imageType = DownloadImageTypePng;
        NSString* compressStrinng;
        UIImage *zeroImage = [[UIImage alloc]init];
        if(![name isEqualToString:@""] && name)
        {
            zeroImage = [self decodeBase64ToImage:name];
            compressStrinng = [self processcompImage:zeroImage];
        }
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_OK
                                   messageAsString:compressStrinng];
        
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }
    else {
        NSString* compressStrinng;
        UIImage *zeroImage = [[UIImage alloc]init];
        if(![name isEqualToString:@""] && name)
        {
            zeroImage = [self decodeBase64ToImage:name];
            
            compressStrinng = [self processcompImage:zeroImage];
            
        }
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_OK
                                   messageAsString:compressStrinng];
        
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }
}
- (void) compressFile:(CDVInvokedUrlCommand*)command
{
    NSString* ogpath = [[command arguments] objectAtIndex:0];
    NSLog(@"ogpath image %@",ogpath);
    NSURL *url = [NSURL URLWithString:ogpath];
    NSData *data = [NSData dataWithContentsOfURL:url];
    UIImage *conimage = [UIImage imageWithData:data];
    _sizeLim = ((float)data.length);
        
        NSRange pngRange = [ogpath rangeOfString:@"PNG" options:NSBackwardsSearch];
        if (pngRange.location != NSNotFound) {
            self.imageType = DownloadImageTypePng;
            NSString* compressStrinng;
            if(![ogpath isEqualToString:@""] && ogpath)
            {
                compressStrinng = [self processcompImage:conimage];
            }
            CDVPluginResult* result = [CDVPluginResult
                                       resultWithStatus:CDVCommandStatus_OK
                                       messageAsString:compressStrinng];
            
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
        }
        else {
            NSString* compressStrinng;
            if(![ogpath isEqualToString:@""] && ogpath)
            {
                compressStrinng = [self processcompImage:conimage];
            }
            CDVPluginResult* result = [CDVPluginResult
                                       resultWithStatus:CDVCommandStatus_OK
                                       messageAsString:compressStrinng];
            
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
        }
}
- (UIImage *)decodeBase64ToImage:(NSString *)strEncodeData
{
    NSData *data = [[NSData alloc]initWithBase64EncodedString:strEncodeData options:NSDataBase64DecodingIgnoreUnknownCharacters];
    return [UIImage imageWithData:data];
}

- (NSData*)Adstringo_image1:(UIImage*)image :(NSString*)Image_name
{
    return [AdstringoImageCompression Adstringo_image:image :Image_name];
}

- (NSString*)processcompImage:(UIImage*)image{
    NSData* data = nil;
    NSDateFormatter *dateFormatter=[[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd-hh-mm-ss"];
    NSLog(@"%@",[dateFormatter stringFromDate:[NSDate date]]);
    NSString *image_name = [NSString stringWithFormat:@"adstringo_%@.jpg", [dateFormatter stringFromDate:[NSDate date]]];
    NSLog(@"imgNameCompressed %@",image_name);
    data = [self Adstringo_image1:image :image_name];
    NSLog(@"compress image size %lu",(unsigned long)data.length);
    NSString *compressedFilepath = [[NSUserDefaults standardUserDefaults]
                            stringForKey:@"storagePath_Image"];
    NSLog(@"compressedFilepath_Image = %@",compressedFilepath);
    UIImage *image2 = [[UIImage alloc] initWithContentsOfFile:compressedFilepath];
    UIImageWriteToSavedPhotosAlbum(image2, nil, nil, nil);

    return compressedFilepath;
}
-(NSString*)processcompImage2:(UIImage*)image{
    NSData* data = nil;
    NSDateFormatter *dateFormatter=[[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd-hh-mm-ss"];
    NSString *image_name = [NSString stringWithFormat:@"adstringo_%@.jpg", [dateFormatter stringFromDate:[NSDate date]]];
    NSLog(@"imgNameCompressed %@",image_name);
    data = [self Adstringo_image1:image :image_name];
    NSLog(@"compress image size %lu",(unsigned long)data.length);
    NSString * base64String = [data base64EncodedStringWithOptions:0];
    NSLog(@"base64String %@",base64String);
    
    return base64String;
}
- (void) compressVideo:(CDVInvokedUrlCommand*)command
    {
        NSString* compressStrinng;
        NSString* ogpath = [[command arguments] objectAtIndex:0];
        videoURL = [NSURL URLWithString:ogpath];
        NSLog(@"VideoPath:%@",videoURL);
        compressStrinng = base64StringVideo;
        NSLog(@"compressStrinng-videbase64 = %@",compressStrinng);
        //
        NSRange pngRange = [ogpath rangeOfString:@"mov" options:NSBackwardsSearch];
        if (pngRange.location != NSNotFound) {
            self.imageType = DownloadImageTypePng;
            NSString* compressStrinng;
            if(![ogpath isEqualToString:@""] && ogpath)
            {
                compressStrinng = [self processcompVideo:videoURL];
            }
            NSLog(@" compressStrinng video = %@",compressStrinng);
            CDVPluginResult* result = [CDVPluginResult
                                       resultWithStatus:CDVCommandStatus_OK
                                       messageAsString:compressStrinng];
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
        } else {
            NSString* compressStrinng;
            if(![ogpath isEqualToString:@""] && ogpath)
            {
                compressStrinng = [self processcompVideo:videoURL];
                
            }
            NSLog(@" compressStrinng video = %@",compressStrinng);
            CDVPluginResult* result = [CDVPluginResult
                                       resultWithStatus:CDVCommandStatus_OK
                                       messageAsString:compressStrinng];
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
            
        }
    }
- (NSString*)processcompVideo:(NSDictionary *)info {
    AdstringoVideoCompression *instance = [[AdstringoVideoCompression alloc] init];
    NSDictionary *compVideoDict = [instance adstringoVideoCompression:videoURL];
    NSURL *compVideoUrl = [compVideoDict valueForKey:@"compVideoUrl"];
    NSData *dataVideo = [NSData dataWithContentsOfURL:compVideoUrl];
    NSString *savedValue = [[NSUserDefaults standardUserDefaults]
                        stringForKey:@"storagePath_Video"];
    NSLog(@"savedvalueAdstringo_Video = %@",savedValue);
    NSString * base64StringVideo = [dataVideo base64EncodedStringWithOptions:0];
    NSLog(@"Base64string = %@",base64StringVideo);
    return savedValue;
}
- (void)processVideo:(NSDictionary *)info    {
        AdstringoVideoCompression *instance = [[AdstringoVideoCompression alloc] init];
        NSDictionary *compVideoDict = [instance adstringoVideoCompression:videoURL];
        NSURL *compVideoUrl = [compVideoDict valueForKey:@"compVideoUrl"];
        NSData *dataVideo = [NSData dataWithContentsOfURL:compVideoUrl];
        NSString *savedValue = [[NSUserDefaults standardUserDefaults]
                            stringForKey:@"storagePath_Video"];
        NSLog(@"savedvalueAdstringo_Video = %@",savedValue);
        NSString * base64String = [dataVideo base64EncodedStringWithOptions:0];
        NSLog(@"Base64string = %@",base64String);
    }


@end

