//
//  AdstringoVideoCompression.h
//  AdstringoVideoCompression
//
//  Created by Ads Dev on 11/9/17.
//  Copyright © 2017 Adstringo. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface AdstringoVideoCompression : NSObject <NSXMLParserDelegate>

- (NSDictionary*)adstringoVideoCompression:(NSURL*)inputFilePath;

@end

@interface UpdateAdstringoVideoCount : UIViewController <NSXMLParserDelegate>

- (void)UpdateAdstringoVideoCount:(NSString*)tag;
- (void)licenseVersion;
@end
