
#import <Cordova/CDV.h>

@interface Adstringo : CDVPlugin

- (void) compressString:(CDVInvokedUrlCommand*)command;

- (void) compressFile:(CDVInvokedUrlCommand*)command;
- (void) compressVideo:(CDVInvokedUrlCommand*)command;
- (void) compressAudio:(CDVInvokedUrlCommand*)command;

typedef NS_ENUM(NSInteger, DownloadImageType) {
    DownloadImageTypePng,
    DownloadImageTypeJpg
};

@property (assign, nonatomic) DownloadImageType imageType;

@property (nonatomic) float sizeLim;

@end

