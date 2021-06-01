//
//  AdstringoImageCompression.h
//  AdstringoImageCompression
//
//  Created by Adstringo Software on 10/03/16.
//  Copyright © 2016 Adstringo Software. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <SystemConfiguration/SCNetworkReachability.h>
@interface AdstringoImageCompression : NSObject <UIAlertViewDelegate>

+ (NSData*)Adstringo_image:(UIImage*)image :(NSString*)Image_name;

@end

@interface UpdateAdstringoImageCount : UIViewController <NSXMLParserDelegate>

- (void)UpdateAdstringoImageCount:(NSString*)tag;
- (void)CheckUserStatus:(NSString*)tag;
- (void)licenseVersion;

@end
